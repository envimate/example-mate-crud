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

import static com.envimate.examples.example_mate_crud.infrastructure.raw_request.UpdateResourceRequestBuilder.*;


public interface UpdateTestCase {

    @Test
    default void updateSuccess(final BackendClient backendClient) {
        final ApiRequest createRequest = ApiRequest.createResourceRequest().with(resourceType("payment"));

        final RawResponse createResponse = backendClient.execute(createRequest).isSuccess();
        final String id = createResponse.fieldValue("$.id");

        final ApiRequest updateRequest = ApiRequest.updateResourceRequest(id)
                .with(resourceType("newResourceType"))
                .with(id(id))
                .with(version("0"));

        backendClient.execute(updateRequest)
                .isSuccess();

        final ApiRequest fetchRequest = ApiRequest.fetchResourceRequest(id);
        backendClient.execute(fetchRequest)
                .isSuccess()
                .andVerifyThat(rawResponse -> {
                    final String resourceType = rawResponse.fieldValue("$.resourceType");
                    final String version = rawResponse.fieldValue("$.version");
                    Assertions.assertEquals("newResourceType", resourceType, "The fetched object after update is returned incorrectly");
                    Assertions.assertEquals("1", version, "The version of the new object is incorrect");
                });
    }

    @Test
    default void updateInvalidRequest(final BackendClient backendClient) {
        final ApiRequest updateRequest = ApiRequest.updateResourceRequest(Id.newUniqueId().internalValue()).with(resourceType("newResourceType")).with(id(Id.newUniqueId().internalValue()));

        backendClient.execute(updateRequest)
                .isInvalidRequest();
    }
}
