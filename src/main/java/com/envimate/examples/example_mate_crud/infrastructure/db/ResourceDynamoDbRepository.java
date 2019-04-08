/*
 * Copyright (c) 2018 envimate GmbH - https://envimate.com/.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.envimate.examples.example_mate_crud.infrastructure.db;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.internal.PageIterable;
import com.amazonaws.services.dynamodbv2.model.*;
import com.envimate.examples.example_mate_crud.domain.Id;
import com.envimate.examples.example_mate_crud.domain.Version;
import com.envimate.examples.example_mate_crud.domain.repository.ResourceRepository;
import com.envimate.examples.example_mate_crud.usecases.Resource;
import com.envimate.mapmate.deserialization.Deserializer;
import com.envimate.mapmate.serialization.Serializer;

import java.util.*;
import java.util.stream.Collectors;

import static com.envimate.examples.example_mate_crud.infrastructure.db.DynamoDBAttributes.attributeValue;
import static com.envimate.examples.example_mate_crud.infrastructure.db.DynamoDBAttributes.primaryKey;

public class ResourceDynamoDbRepository implements ResourceRepository {
    private static final String TABLE_NAME = "resource";
    private final DynamoDB dynamoDb;
    private final AmazonDynamoDB client;
    private final Deserializer deserializer;
    private final Serializer serializer;

    public ResourceDynamoDbRepository(final AmazonDynamoDB client,
                                      final Deserializer deserializer,
                                      final Serializer serializer) {
        this.dynamoDb = new DynamoDB(client);
        this.client = client;
        this.deserializer = deserializer;
        this.serializer = serializer;
    }

    @Override
    public List<Resource> all() {
        final ItemCollection<ScanOutcome> items = this.dynamoDb.getTable(TABLE_NAME).scan();
        final PageIterable<Item, ScanOutcome> pages = items.pages();
        final List<Resource> all = new LinkedList<>();
        pages.forEach(page -> {
            final List<Resource> pageResources = page.getLowLevelResult()
                    .getItems()
                    .stream()
                    .map(item -> {
                        final String resourceSerialized = item.getString("resource");
                        return this.deserializer.deserialize(resourceSerialized, Resource.class);
                    }).collect(Collectors.toList());
            all.addAll(pageResources);
        });
        return all;
    }

    @Override
    public void create(final Resource resource) {
        final Table table = this.dynamoDb.getTable(TABLE_NAME);
        final String serializedResource = this.serializer.serialize(resource);
        final Item item = new Item()
                .with("id", resource.id.internalValue())
                .with("version", resource.version.internalValue())
                .with("resource", serializedResource);
        table.putItem(item);
    }

    @Override
    public Resource find(final Id id) {
        final Item item = this.dynamoDb.getTable(TABLE_NAME).getItem("id", id.internalValue());
        if (item == null) {
            return null;
        }
        return this.deserializer.deserialize(item.getString("resource"), Resource.class);
    }

    @Override
    public void update(final Resource resource) {
        final Map<String, AttributeValue> expressionAttributeValuesForPut = new HashMap<>();
        final AttributeValue versionAttributeValue = attributeValue(resource.version.internalValue());
        expressionAttributeValuesForPut.put(":expected_version", versionAttributeValue);

        final Item item = this.dynamoDb.getTable(TABLE_NAME).getItem("id", resource.id.internalValue());
        if (item == null) {
            throw new UnsupportedOperationException(String.format(
                    "Item you are trying to update does not exists.Resource: %%s%s", resource)
            );
        }

        final String serializedResource = this.serializer.serialize(clone(resource, resource.version.next()));

        final Map<String, AttributeValue> newItem = Map.of(
                "id", attributeValue(resource.id.internalValue()),
                "version", attributeValue(resource.version.next().internalValue()),
                "resource", attributeValue(serializedResource));

        final Put updateItem = new Put()
                .withTableName("resource")
                .withItem(newItem)
                .withExpressionAttributeValues(expressionAttributeValuesForPut)
                .withConditionExpression("version = :expected_version")
                .withReturnValuesOnConditionCheckFailure(ReturnValuesOnConditionCheckFailure.ALL_OLD);

        final TransactWriteItem transactWriteItem = new TransactWriteItem().withPut(updateItem);
        final TransactWriteItemsRequest request = new TransactWriteItemsRequest().withTransactItems(transactWriteItem);
        try {
            this.client.transactWriteItems(request);
        } catch (final TransactionCanceledException exception) {
            throw new ConcurrentModificationException(String.format(
                    "%s. Unexpected version of Resource(%s).", exception.getMessage(), resource
            ), exception);
        }
    }

    @Override
    public void delete(final Id id) {
        dynamoDb.getTable(TABLE_NAME).deleteItem(primaryKey(id.internalValue()));
    }

    private Resource clone(final Resource resource, final Version nextVersion) {
        final String serialized = this.serializer.serialize(resource);
        return this.deserializer.deserialize(serialized, Resource.class, injector -> {
            injector.put("version", nextVersion);
            return injector;
        });
    }
}
