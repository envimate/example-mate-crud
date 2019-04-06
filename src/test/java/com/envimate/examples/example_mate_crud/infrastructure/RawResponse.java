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

import com.envimate.examples.example_mate_crud.infrastructure.raw_request.ApiRequest;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.mashape.unirest.http.HttpResponse;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;

import java.util.function.Consumer;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RawResponse {
    private final ApiRequest apiRequest;
    private final HttpResponse<String> jsonResponse;
    private final DocumentContext parsedResponse;

    public static RawResponse rawResponse(final ApiRequest apiRequest, final HttpResponse<String> jsonResponse) {
        final DocumentContext parsedResponse = JsonPath.parse(jsonResponse.getRawBody());
        return new RawResponse(apiRequest, jsonResponse, parsedResponse);
    }

    private String assertionMessage(final String additionalInfo) {
        return String.format("%s. Request: %s. Response: Body(%s), StatusText(%s).", additionalInfo, apiRequest, jsonResponse.getBody(), jsonResponse.getStatusText());
    }

    public RawResponse isSuccess() {
        return assertStatusCode(200);
    }

    public <T> T fieldValue(final String path) {
        try {
            return parsedResponse.read(path);
        } catch (PathNotFoundException e) {
            Assertions.fail(assertionMessage(String.format("Did not find requested field %s", path)), e);
            throw e;
        }
    }

    public void andVerifyThat(final Consumer<RawResponse> verification) {
        verification.accept(this);
    }

    public RawResponse isNotFound() {
        return assertStatusCode(404);
    }

    private RawResponse assertStatusCode(final int expectedCode) {
        Assertions.assertEquals(expectedCode, this.jsonResponse.getStatus(), assertionMessage("Unexpected status code"));
        return this;
    }

    public RawResponse isInvalidRequest() {
        return assertStatusCode(400);
    }
}
