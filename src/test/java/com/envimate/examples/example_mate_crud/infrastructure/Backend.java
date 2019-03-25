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

import com.envimate.mapmate.deserialization.Deserializer;
import com.envimate.mapmate.serialization.Serializer;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static com.envimate.examples.example_mate_crud.infrastructure.RawRequest.rawRequest;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Backend {
    private final Serializer serializer;
    private final Deserializer deserializer;
    private final Map<Class<?>, Endpoint> endpoints;
    private final BackendClient backendClient;
    private Response response;

    public static Backend backend(final Serializer serializer,
                                 final Deserializer deserializer,
                                 final Map<Class<?>, Endpoint> endpoints,
                                 final BackendClient backendClient) {
        return new Backend(serializer, deserializer, endpoints, backendClient);
    }

    public Backend given(final Scenario... scenarios) {
        Arrays.stream(scenarios).forEach(this::execute);
        return this;
    }

    public <R, P> Response<R> verifyThat(final Scenario<R, P> scenario) {
        return execute(scenario);
    }

    private <R, P> Response<R> execute(final Scenario<R, P> scenario) {
        final P payload = scenario.payload();
        final String serializedPayload = Optional.ofNullable(payload).map(body -> serializer.serialize(body)).orElse(null);
        final Endpoint endpoint = endpoints.get(scenario.useCaseClass());

        final RawRequest rawRequest = rawRequest(endpoint.url.internalValue(),
                endpoint.httpMethod.internalValue(),
                Map.of("Content-Type", "application/json"),
                scenario.routeParameters().asMap(),
                serializedPayload);
        final RawResponse rawResponse = backendClient.execute(rawRequest);

        final R parsedResponse = (R) deserializer.deserialize(rawResponse.body, scenario.responseClass());

        return Response.response(parsedResponse, rawResponse);
    }
}
