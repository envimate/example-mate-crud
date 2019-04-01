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

package com.envimate.examples.example_mate_crud.testcases;

import com.envimate.examples.example_mate_crud.domain.Id;
import com.envimate.examples.example_mate_crud.infrastructure.BackendClient;
import com.envimate.examples.example_mate_crud.infrastructure.RawResponse;
import com.envimate.examples.example_mate_crud.infrastructure.raw_request.ApiRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.envimate.examples.example_mate_crud.infrastructure.raw_request.CreateResourceRequestBuilder.resourceType;

public interface FetchTestCase {

    @Test
    default void fetchNotFound(final BackendClient backendClient) {
        final Id id = Id.id(UUID.randomUUID().toString());

        final ApiRequest fetchRequest = ApiRequest.fetchResourceRequest(id.internalValue());

        backendClient
                .execute(fetchRequest)
                .isNotFound();
    }

    @Test
    default void fetchInvalidId(final BackendClient backendClient) {
        final String id = "ThisIsNotAUUID";

        final ApiRequest fetchRequest = ApiRequest.fetchResourceRequest(id);

        backendClient
                .execute(fetchRequest)
                .isInvalidRequest();
    }

    @Test
    default void awesomeTest(final BackendClient backendClient) {
        final ApiRequest createRequest = ApiRequest.createResourceRequest().with(resourceType("payment"));

        final RawResponse createResponse = backendClient.execute(createRequest);
        createResponse.isSuccess();
        final String id = createResponse.fieldValue("$.id");


        final ApiRequest fetchRequest = ApiRequest.fetchResourceRequest(id);

        backendClient
                .execute(fetchRequest)
                .isSuccess()
                .andVerifyThat(rawResponse -> {
                            Assertions.assertEquals("payment", rawResponse.fieldValue("$.resourceType"), "Unexpected resourceType");
                            Assertions.assertEquals(id, rawResponse.fieldValue("$.id"), "Wrong ID");
                        }
                );
    }
}
