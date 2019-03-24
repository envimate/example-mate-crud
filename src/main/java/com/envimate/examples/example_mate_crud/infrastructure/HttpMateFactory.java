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

import com.envimate.examples.example_mate_crud.usecases.ListResource;
import com.envimate.httpmate.HttpMate;
import com.envimate.httpmate.convenience.Http;
import com.envimate.httpmate.request.HttpRequestMethod;
import com.envimate.mapmate.deserialization.Deserializer;
import com.envimate.mapmate.serialization.Serializer;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.envimate.httpmate.convenience.cors.CorsHandler.handleCorsOptionsRequests;

@ToString
@EqualsAndHashCode
final class HttpMateFactory {
    private final Injector injector;

    @Inject
    HttpMateFactory(final Injector injector) {
        this.injector = injector;
    }

    public HttpMate httpMate() {
        final Gson gson = new Gson();
        final Serializer serializer = MapMateFactory.serializer();
        final Deserializer deserializer = MapMateFactory.deserializer();

        return HttpMate.aHttpMateInstance()
                .servingTheUseCase(ListResource.class)
                .forRequestPath("api/resource").andRequestMethod(HttpRequestMethod.GET)
                .handlingOptionsRequestsUsing(handleCorsOptionsRequests())
                .obtainingUseCaseInstancesUsing(
                        (useCase, webserviceRequest) -> this.injector.getInstance(useCase.useCaseClass())
                )
                .mappingRequestsToUseCaseParametersByDefaultUsing(
                        (webServiceRequest, targetType, context) -> deserializer.deserialize(
                                webServiceRequest.getBodyAs(String.class),
                                targetType)
                )
                .usingTheResponseTemplate(
                        (responseBuilder, context) ->
                                responseBuilder.withEmptyBody().withContentType("application/json")
                )
                .serializingResponseObjectsByDefaultUsing(
                        (object, responseBuilder, context) -> {
                            responseBuilder.withBody(serializer.serialize(object));
                        }
                )
                .mappingExceptionsByDefaultUsing((exception, responseBuilder, context) -> {
                    exception.printStackTrace();
                    responseBuilder.withStatusCode(Http.StatusCodes.INTERNAL_SERVER_ERROR);
                })
                .loggingToStdoutAndStderr();
    }

}
