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

import com.envimate.httpmate.convenience.Http.StatusCodes;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;

import java.util.function.Predicate;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Response<R> {
    public final R parsedResponse;
    public final RawResponse rawResponse;

    static <R> Response<R> response(final R parsedResponse, final RawResponse rawResponse) {
        return new Response<>(parsedResponse, rawResponse);
    }

    public Response<R> isSuccess() {
        Assertions.assertEquals(StatusCodes.OK, (int) this.rawResponse.statusCode);
        return this;
    }

    public Response<R> verifyResponse(final Predicate<R> assertion, final String description) {
        Assertions.assertTrue(assertion.test(this.parsedResponse), String.format("%s. Response: %s", description, this));
        return this;
    }

}
