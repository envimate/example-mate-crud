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

package com.envimate.examples.example_mate_crud.infrastructure.raw_request;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateResourceRequestBuilder {
    private final static String VALID_ATTRIBUTES =
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
    private static final Gson GSON = new Gson();
    private final JSONObject jsonObject = new JSONObject();

    public static ApiRequestBodyPropertyProvider resourceType(final String resourceType) {
        return jsonObject -> jsonObject.put("type", resourceType);
    }

    public static ApiRequestBodyPropertyProvider organisationId(final String organisationId) {
        return jsonObject -> jsonObject.put("organisationId", organisationId);
    }

    public static ApiRequestBodyPropertyProvider validAttributes() {
        return jsonObject -> jsonObject.put("attributes", GSON.fromJson(VALID_ATTRIBUTES, Map.class));
    }

    public static ApiRequestBodyPropertyProvider attributes(final String attributes) {
        return jsonObject -> jsonObject.put("attributes", GSON.fromJson(attributes, Map.class));
    }

    public JSONObject build() {
        return this.jsonObject;
    }
}
