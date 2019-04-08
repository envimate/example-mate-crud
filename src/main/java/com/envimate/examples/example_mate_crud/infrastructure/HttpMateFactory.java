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

import com.envimate.examples.example_mate_crud.domain.ErrorMessage;
import com.envimate.examples.example_mate_crud.domain.Id;
import com.envimate.examples.example_mate_crud.usecases.ErrorDTO;
import com.envimate.examples.example_mate_crud.usecases.payment.ResourceNotFoundException;
import com.envimate.examples.example_mate_crud.usecases.payment.create.CreateResource;
import com.envimate.examples.example_mate_crud.usecases.payment.fetch.FetchResource;
import com.envimate.examples.example_mate_crud.usecases.payment.list.ListResource;
import com.envimate.examples.example_mate_crud.usecases.payment.update.UpdateResource;
import com.envimate.examples.example_mate_crud.validation.CustomTypeValidationException;
import com.envimate.httpmate.HttpMate;
import com.envimate.mapmate.deserialization.Deserializer;
import com.envimate.mapmate.serialization.Serializer;
import com.envimate.mapmate.validation.AggregatedValidationException;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.http.HttpStatus;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Objects;

import static com.envimate.examples.example_mate_crud.domain.Id.id;
import static com.envimate.examples.example_mate_crud.usecases.ErrorDTO.error;
import static com.envimate.examples.example_mate_crud.validation.CustomTypeValidationException.customTypeValidationException;
import static com.envimate.httpmate.HttpMate.aHttpMateInstance;
import static com.envimate.httpmate.chains.HttpMateChainKeys.*;
import static com.envimate.httpmate.convenience.Http.Headers.CONTENT_TYPE;
import static com.envimate.httpmate.convenience.Http.StatusCodes.*;
import static com.envimate.httpmate.convenience.cors.CorsModule.corsModule;
import static com.envimate.httpmate.request.ContentType.json;
import static com.envimate.httpmate.request.HttpRequestMethod.*;
import static com.envimate.httpmate.unpacking.BodyMapParsingModule.aBodyMapParsingModule;
import static java.util.Arrays.asList;

@ToString
@EqualsAndHashCode
public final class HttpMateFactory {
    private final Injector injector;
    private final Serializer serializer;
    private final Deserializer deserializer;

    @Inject
    HttpMateFactory(final Injector injector, final Serializer serializer, final Deserializer deserializer) {
        this.injector = injector;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    @SuppressWarnings("unchecked")
    public HttpMate httpMate() {
        return aHttpMateInstance()
                .servingTheUseCase(ListResource.class)
                .forRequestPath("api/resource").andRequestMethod(GET)
                .servingTheUseCase(CreateResource.class)
                .forRequestPath("api/resource").andRequestMethod(POST)
                .servingTheUseCase(FetchResource.class)
                .forRequestPath("api/resource/<id>").andRequestMethod(GET)
                .servingTheUseCase(UpdateResource.class)
                .forRequestPath("api/resource/<id>").andRequestMethod(PUT)
                .mappingUseCaseParametersOfType(Id.class).using((targetType, map) -> id((String) map.get("id")))
                .mappingUseCaseParametersByDefaultUsing((targetType, map) ->
                        this.deserializer.deserializeFromMap(map, targetType)
                )
                .preparingRequestsForParameterMappingThatByDirectlyMappingAllData()
                .serializingResponseObjectsByDefaultUsing(this.serializer::serializeToMap)
                .mappingResponsesUsing((event, metaData) -> metaData.set(STRING_RESPONSE, new Gson().toJson(event)))
                .configuredBy((configurator, useCaseConfigurator) -> {
                    useCaseConfigurator
                            .configureUseCaseInstantiation()
                            .obtainingUseCaseInstancesUsing(this.injector::getInstance);
                    configurator.configureResponseTemplate().usingTheResponseTemplate(metaData -> {
                        metaData.get(RESPONSE_HEADERS).put(CONTENT_TYPE, "application/json");
                        metaData.set(RESPONSE_STATUS, OK);
                    });
                    configurator.filterRequests(metaData -> metaData.get(PATH_PARAMETERS).getPathParameter("id")
                            .ifPresent(idInPath -> {
                                final String idInBody = (String) metaData.get(BODY_MAP).get("id");
                                if (idInBody != null && !Objects.equals(idInPath, idInBody)) {
                                    throw customTypeValidationException(String.format(
                                            "The provided id(%s) does not correspond to the id in the request(%s)",
                                            idInPath,
                                            idInBody)
                                    );
                                }
                            }));
                    configurator.configureExceptionMapping()
                            .mappingExceptionsOfType(ResourceNotFoundException.class)
                            .using((exception, metaData) -> metaData.set(RESPONSE_STATUS, HttpStatus.SC_NOT_FOUND));
                    configurator.configureExceptionMapping()
                            .mappingExceptionsOfType(CustomTypeValidationException.class)
                            .using((object, metaData) -> {
                                final ErrorDTO error = error(ErrorMessage.errorMessage(object.getMessage()));
                                metaData.set(STRING_RESPONSE, this.serializer.serialize(error));
                                metaData.set(RESPONSE_STATUS, HttpStatus.SC_BAD_REQUEST);
                            });
                    configurator.configureExceptionMapping()
                            .mappingExceptionsOfType(AggregatedValidationException.class)
                            .using((object, metaData) -> {
                                final ErrorDTO error = error(object);
                                metaData.set(STRING_RESPONSE, this.serializer.serialize(error));
                                metaData.set(RESPONSE_STATUS, HttpStatus.SC_BAD_REQUEST);
                            });
                    configurator.configureExceptionMapping()
                            .mappingExceptionsOfType(ConcurrentModificationException.class)
                            .using((object, metaData) -> {
                                metaData.set(RESPONSE_STATUS, HttpStatus.SC_PRECONDITION_FAILED);
                            });
                    configurator.configureExceptionMapping()
                            .mappingExceptionsByDefaultUsing((exception, metaData) -> {
                                exception.printStackTrace();
                                metaData.set(RESPONSE_STATUS, INTERNAL_SERVER_ERROR);
                            });
                    configurator.configureLogger().loggingToStdoutAndStderr();
                    configurator.registerModule(
                            corsModule("*",
                                    asList(GET, POST, PUT, DELETE),
                                    asList("Content-Type",
                                            "Authorization",
                                            "X-Requested-With",
                                            "Content-Length",
                                            "Accept,Origin"
                                    )
                            )
                    );
                    configurator.registerModule(aBodyMapParsingModule()
                            .parsingContentType(json()).with(body -> new Gson().fromJson(body, Map.class))
                            .usingTheDefaultContentType(json()));
                });
    }
}
