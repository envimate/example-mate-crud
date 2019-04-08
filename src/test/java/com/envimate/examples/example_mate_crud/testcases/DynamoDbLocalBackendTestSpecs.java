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

package com.envimate.examples.example_mate_crud.testcases;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.internal.PageIterable;
import com.envimate.examples.example_mate_crud.infrastructure.DynamoDbLocalBackendParameterResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ExtendWith({DynamoDbLocalBackendParameterResolver.class})
public class DynamoDbLocalBackendTestSpecs implements CreateTestCase, FetchTestCase, UpdateTestCase, ListTestCase {
    private static final String TABLE_NAME = "resource";
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDbLocalBackendTestSpecs.class);

    @BeforeEach
    public void beforeEach(final DynamoDB dynamoDB) {
        LOGGER.debug("Cleaning up dynamoDB");
        final ItemCollection<ScanOutcome> items = dynamoDB.getTable(TABLE_NAME).scan();
        final PageIterable<Item, ScanOutcome> pages = items.pages();
        pages.forEach(page -> {
            page.getLowLevelResult().getItems().forEach(item -> {
                LOGGER.trace("deleting item {} from table {}", item, TABLE_NAME);
                dynamoDB.getTable(TABLE_NAME).deleteItem("id", item.getString("id"));
            });
        });
    }
}
