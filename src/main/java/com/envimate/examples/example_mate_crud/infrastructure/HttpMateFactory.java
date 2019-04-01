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

import com.envimate.examples.example_mate_crud.domain.Id;
import com.envimate.examples.example_mate_crud.usecases.resource.create.CreateResource;
import com.envimate.examples.example_mate_crud.usecases.resource.fetch.FetchResource;
import com.envimate.examples.example_mate_crud.usecases.resource.fetch.ResourceNotFoundException;
import com.envimate.examples.example_mate_crud.usecases.resource.list.ListResource;
import com.envimate.examples.example_mate_crud.validation.CustomTypeValidationException;
import com.envimate.httpmate.HttpMate;
import com.envimate.httpmate.convenience.Http;
import com.envimate.mapmate.deserialization.Deserializer;
import com.envimate.mapmate.serialization.Serializer;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.envimate.httpmate.convenience.cors.CorsHandler.handleCorsOptionsRequests;
import static com.envimate.httpmate.request.HttpRequestMethod.GET;
import static com.envimate.httpmate.request.HttpRequestMethod.POST;

@ToString
@EqualsAndHashCode
final class HttpMateFactory {
    private final Injector injector;
    private Serializer serializer;
    private Deserializer deserializer;

    @Inject
    HttpMateFactory(final Injector injector, final Serializer serializer, final Deserializer deserializer) {
        this.injector = injector;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    public HttpMate httpMate() {
        return HttpMate.aHttpMateInstance()
                .servingTheUseCase(ListResource.class)
                .forRequestPath("api/resource").andRequestMethod(GET)
                .servingTheUseCase(CreateResource.class)
                .forRequestPath("api/resource").andRequestMethod(POST)
                .servingTheUseCase(FetchResource.class)
                .forRequestPath("api/resource/<id>").andRequestMethod(GET)
                .handlingOptionsRequestsUsing(handleCorsOptionsRequests())
                .obtainingUseCaseInstancesUsing(
                        (useCase, webserviceRequest) -> this.injector.getInstance(useCase.useCaseClass())
                )
                .withTheRequestBodyReadToString()
                .mappingRequestsToUseCaseParametersOfType(Id.class).using((webServiceRequest, targetType, context) ->
                        webServiceRequest.getPathParameter("id").map(Id::id).get()
                )
                .mappingRequestsToUseCaseParametersByDefaultUsing((webServiceRequest, targetType, context) ->
                        this.deserializer.deserialize(webServiceRequest.getBodyAs(String.class), targetType)
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
                .mappingExceptionsOfType(ResourceNotFoundException.class).using((object, responseBuilder, context) ->
                        responseBuilder.withStatusCode(Http.StatusCodes.NOT_FOUND)
                )
                .mappingExceptionsOfType(CustomTypeValidationException.class).using((object, responseBuilder, context) ->
                        responseBuilder.withStatusCode(Http.StatusCodes.BAD_REQUEST)
                )
                .mappingExceptionsByDefaultUsing((exception, responseBuilder, context) -> {
                    exception.printStackTrace();
                    responseBuilder.withStatusCode(Http.StatusCodes.INTERNAL_SERVER_ERROR);
                })
                .loggingToStdoutAndStderr();
    }
}
