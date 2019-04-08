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

package com.envimate.examples.example_mate_crud;

import com.envimate.examples.example_mate_crud.infrastructure.db.RepositoryDynamoDbModule;
import com.envimate.examples.example_mate_crud.infrastructure.db.inmemory.RepositoryInMemoryModule;
import com.envimate.examples.example_mate_crud.infrastructure.guice.CrudModule;
import com.envimate.examples.example_mate_crud.infrastructure.guice.MapMateModule;
import com.envimate.examples.example_mate_crud.infrastructure.guice.UseCaseModule;
import com.envimate.examples.example_mate_crud.infrastructure.http.HttpMateFactory;
import com.envimate.httpmate.HttpMate;
import com.envimate.httpmate.convenience.endpoints.PureJavaEndpoint;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Optional;

@ToString
@EqualsAndHashCode
public final class Application {
    private static final int LOCAL_ENDPOINT_PORT = 1337;

    private Application() {
    }

    public static void main(final String[] args) {

        final CrudModule persistenceModule = Optional.ofNullable(System.getenv("INMEMORY_MODE_ENABLED"))
                .map(s -> (CrudModule) new RepositoryInMemoryModule())
                .orElseGet(RepositoryDynamoDbModule::new);

        final Injector injector = Guice.createInjector(persistenceModule, new MapMateModule(), new UseCaseModule());
        final HttpMateFactory httpMateFactory = injector.getInstance(HttpMateFactory.class);
        final HttpMate httpMate = httpMateFactory.httpMate();
        PureJavaEndpoint.pureJavaEndpointFor(httpMate).listeningOnThePort(LOCAL_ENDPOINT_PORT);
    }
}
