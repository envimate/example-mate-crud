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

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.envimate.examples.example_mate_crud.infrastructure.BackendClient;
import com.envimate.examples.example_mate_crud.infrastructure.DynamoDbLocalBackendParameterResolver;
import com.envimate.examples.example_mate_crud.infrastructure.RawResponse;
import com.envimate.examples.example_mate_crud.infrastructure.raw_request.ApiRequest;
import com.envimate.examples.example_mate_crud.infrastructure.raw_request.CreateResourceRequestBuilder;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static com.envimate.examples.example_mate_crud.infrastructure.raw_request.CreateResourceRequestBuilder.validAttributes;
import static com.envimate.examples.example_mate_crud.infrastructure.raw_request.UpdateResourceRequestBuilder.*;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ExtendWith({DynamoDbLocalBackendParameterResolver.class})
public class UpdateTestCaseForDynamoDb implements UpdateTestCase {

    @Test
    void updateConcurrentModification(final BackendClient backendClient, final DynamoDB dynamoDB) {
        final String organisationId = UUID.randomUUID().toString();
        final ApiRequest createRequest = ApiRequest.createResourceRequest()
                .with(CreateResourceRequestBuilder.resourceType("Payment"))
                .with(CreateResourceRequestBuilder.organisationId(organisationId))
                .with(validAttributes());

        final RawResponse createResponse = backendClient.execute(createRequest).isSuccess();

        final String id = createResponse.fieldValue("$.id");

        //artificially mess up version
        dynamoDB.getTable("resource").updateItem(new PrimaryKey("id", id), new AttributeUpdate("version").put("1"));


        final ApiRequest updateRequest = ApiRequest.updateResourceRequest(id)
                .with(id(id))
                .with(version("0"))
                .with(resourceType("Payment"))
                .with(organisationId(organisationId))
                .with(validAttributes());

        backendClient.execute(updateRequest)
                .assertStatusCode(412);
    }

}
