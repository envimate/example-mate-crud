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

import com.envimate.examples.example_mate_crud.infrastructure.BackendClient;
import com.envimate.examples.example_mate_crud.infrastructure.RawResponse;
import com.envimate.examples.example_mate_crud.infrastructure.raw_request.ApiRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.envimate.examples.example_mate_crud.infrastructure.raw_request.CreateResourceRequestBuilder.*;

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
                "  {\n" +
                        "    \"amount\": \"100.21\",\n" +
                        "    \"beneficiaryParty\": {\n" +
                        "      \"accountName\": \"W Owens\",\n" +
                        "      \"accountNumber\": \"31926819\",\n" +
                        "      \"accountNumberCode\": \"BBAN\",\n" +
                        "      \"accountType\": \"0\",\n" +
                        "      \"address\": \"1 The Beneficiary Localtown SE2\",\n" +
                        "      \"bankId\": \"403000\",\n" +
                        "      \"bankIdCode\": \"GBDSC\",\n" +
                        "      \"name\": \"Wilfred Jeremiah Owens\"\n" +
                        "    },\n" +
                        "    \"chargesInformation\": {\n" +
                        "      \"bearerCode\": \"SHAR\",\n" +
                        "      \"senderCharges\": [\n" +
                        "        {\n" +
                        "          \"amount\": \"5.00\",\n" +
                        "          \"currency\": \"GBP\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"amount\": \"10.00\",\n" +
                        "          \"currency\": \"USD\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"receiverChargesAmount\": \"1.00\",\n" +
                        "      \"receiverChargesCurrency\": \"USD\"\n" +
                        "    },\n" +
                        "    \"currency\": \"GBP\",\n" +
                        "    \"debtorParty\": {\n" +
                        "      \"accountName\": \"EJ Brown Black\",\n" +
                        "      \"accountNumber\": \"GB29XABC10161234567801\",\n" +
                        "      \"accountNumberCode\": \"IBAN\",\n" +
                        "      \"address\": \"10 Debtor Crescent Sourcetown NE1\",\n" +
                        "      \"bankId\": \"203301\",\n" +
                        "      \"bankIdCode\": \"GBDSC\",\n" +
                        "      \"name\": \"Emelia Jane Brown\"\n" +
                        "    },\n" +
                        "    \"endToEndReference\": \"Wil piano Jan\",\n" +
                        "    \"foreignExchange\": {\n" +
                        "      \"contractReference\": \"FX123\",\n" +
                        "      \"exchangeRate\": \"2.00000\",\n" +
                        "      \"originalAmount\": \"200.42\",\n" +
                        "      \"originalCurrency\": \"USD\"\n" +
                        "    },\n" +
                        "    \"numericReference\": \"1002001\",\n" +
                        "    \"paymentId\": \"123456789012345678\",\n" +
                        "    \"paymentPurpose\": \"Paying for goods/services\",\n" +
                        "    \"paymentScheme\": \"FPS\",\n" +
                        "    \"paymentType\": \"Credit\",\n" +
                        "    \"processingDate\": \"2017-01-18\",\n" +
                        "    \"reference\": \"Payment for Em's piano lessons\",\n" +
                        "    \"schemePaymentSubType\": \"InternetBanking\",\n" +
                        "    \"schemePaymentType\": \"ImmediatePayment\",\n" +
                        "    \"sponsorParty\": {\n" +
                        "      \"accountNumber\": \"56781234\",\n" +
                        "      \"bankId\": \"123123\",\n" +
                        "      \"bankIdCode\": \"GBDSC\"\n" +
                        "    }\n" +
                        "  }\n";

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
