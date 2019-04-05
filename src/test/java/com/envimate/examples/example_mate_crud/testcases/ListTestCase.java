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

import com.envimate.examples.example_mate_crud.domain.Attributes;
import com.envimate.examples.example_mate_crud.domain.BeneficiaryParty;
import com.envimate.examples.example_mate_crud.infrastructure.BackendClient;
import com.envimate.examples.example_mate_crud.infrastructure.MapMateFactory;
import com.envimate.examples.example_mate_crud.infrastructure.RawResponse;
import com.envimate.examples.example_mate_crud.infrastructure.raw_request.ApiRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.envimate.examples.example_mate_crud.infrastructure.raw_request.CreateResourceRequestBuilder.*;
import static com.envimate.examples.example_mate_crud.infrastructure.raw_request.UpdateResourceRequestBuilder.version;

public interface ListTestCase {

    @Test
    default void listUseCaseEmptyDatabase(final BackendClient backendClient) {
        final ApiRequest listRequest = ApiRequest.listResourceRequest();
        backendClient
                .execute(listRequest)
                .isSuccess()
                .andVerifyThat(rawResponse -> {
                            final List<?> resultList = rawResponse.fieldValue("$.data");
                            Assertions.assertTrue(resultList.isEmpty(), "Unexpected result of the list request");
                        }
                );
    }

    @Test
    default void listUseCase(final BackendClient backendClient) {
        final String organisationId = UUID.randomUUID().toString();
        final String attributes =
                "        {\n" +
                "          \"accountName\": \"W Owens\",\n" +
                "          \"accountNumber\": \"31926819\",\n" +
                "          \"accountNumberCode\": \"BBAN\",\n" +
                "          \"accountType\": 0,\n" +
                "          \"address\": \"1 The Beneficiary Localtown SE2\",\n" +
                "          \"bankId\": \"403000\",\n" +
                "          \"bankIdCode\": \"GBDSC\",\n" +
                "          \"name\": \"Wilfred Jeremiah Owens\"\n" +
                "        }";
        MapMateFactory.deserializer().deserialize(attributes, BeneficiaryParty.class);

        final ApiRequest createRequest = ApiRequest.createResourceRequest()
                .with(resourceType("payment"))
                .with(organisationId(organisationId))
                .with(attributes(attributes));

        final RawResponse createResponse = backendClient.execute(createRequest);
        createResponse.isSuccess();
        final String createdId = createResponse.fieldValue("$.id");

        final ApiRequest listRequest = ApiRequest.listResourceRequest();
        backendClient
                .execute(listRequest)
                .isSuccess()
                .andVerifyThat(rawResponse -> {
                            final List<?> resultList = rawResponse.fieldValue("$.data");
                            Assertions.assertEquals(1, resultList.size(), "List must contain 1 object");
                            Assertions.assertEquals(createdId, rawResponse.fieldValue("$.data[0].id"), "Returned object's id is wrong");
                            Assertions.assertEquals(organisationId, rawResponse.fieldValue("$.data[0].organisationId"), "Returned object's organisation id is wrong");
                        }
                );
    }

}
