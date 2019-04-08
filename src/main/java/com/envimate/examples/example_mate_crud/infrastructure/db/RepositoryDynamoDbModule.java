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
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.envimate.examples.example_mate_crud.domain.repository.ResourceRepository;
import com.envimate.examples.example_mate_crud.infrastructure.guice.CrudModule;
import com.google.inject.Singleton;

public class RepositoryDynamoDbModule extends CrudModule {
    @Override
    protected void bindDependencies() {
        final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        bind(AmazonDynamoDB.class).toInstance(client);
        bindToSingleConstructor(ResourceDynamoDbRepository.class).in(Singleton.class);
        bind(ResourceRepository.class).to(ResourceDynamoDbRepository.class);
    }
}
