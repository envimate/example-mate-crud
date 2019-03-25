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

import com.envimate.examples.example_mate_crud.usecases.resource.create.CreateResource;
import com.envimate.examples.example_mate_crud.usecases.resource.list.ListResource;
import com.envimate.mapmate.deserialization.Deserializer;
import com.envimate.mapmate.serialization.Serializer;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Map;

import static com.envimate.examples.example_mate_crud.infrastructure.HttpMethod.httpMethod;

public abstract class AbstractBackendParameterResolver implements ParameterResolver {
    private static Boolean isStarted = false;

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext,
                                     final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Backend.class);
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {
        if(!isStarted) {
            start();
            isStarted = true;
        }
        final Class<?> parameterType = parameterContext.getParameter().getType();
        if(parameterType.equals(Backend.class)) {
            final Serializer serializer = MapMateFactory.serializer();
            final Deserializer deserializer = MapMateFactory.deserializer();

            final Map<Class<?>, Endpoint> endpoints = Map.of(
                    ListResource.class, Endpoint.endpoint(Url.url("/api/resource"), httpMethod("GET")),
                    CreateResource.class, Endpoint.endpoint(Url.url("/api/resource"), httpMethod("POST"))
            );

            return Backend.backend(serializer, deserializer, endpoints, backendClient());
        }
        throw new UnsupportedOperationException("Unsupported dependency");
    }

    protected abstract BackendClient backendClient();

    protected abstract void start();
}
