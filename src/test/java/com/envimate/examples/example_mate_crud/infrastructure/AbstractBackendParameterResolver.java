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

package com.envimate.examples.example_mate_crud.infrastructure;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.envimate.examples.example_mate_crud.infrastructure.db.inmemory.ResourceInMemoryRepository;
import com.google.inject.Injector;
import org.junit.jupiter.api.extension.*;

import java.util.List;

public abstract class AbstractBackendParameterResolver implements ParameterResolver, ExecutionCondition {
    private static final List<Class<?>> SUPPORTED_PARAMETER_CLASSES = List.of(BackendClient.class, DynamoDB.class, ResourceInMemoryRepository.class);
    private static Boolean isStarted = false;

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext,
                                     final ExtensionContext extensionContext) throws ParameterResolutionException {
        return SUPPORTED_PARAMETER_CLASSES.contains(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {
        if (!isStarted) {
            start();
            isStarted = true;
        }
        final Class<?> parameterType = parameterContext.getParameter().getType();
        if (parameterType.equals(BackendClient.class)) {
            return backendClient();
        } else if (parameterType.equals(DynamoDB.class)) {
            final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
            return new DynamoDB(client);
        } else if (parameterType.equals(ResourceInMemoryRepository.class)) {
            return injector().getInstance(ResourceInMemoryRepository.class);
        }
        throw new UnsupportedOperationException("Unsupported dependency");
    }

    protected abstract BackendClient backendClient();

    protected abstract void start();

    protected abstract Injector injector();
}
