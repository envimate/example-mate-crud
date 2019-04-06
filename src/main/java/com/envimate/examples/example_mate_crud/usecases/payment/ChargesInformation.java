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

package com.envimate.examples.example_mate_crud.usecases.payment;

import com.envimate.examples.example_mate_crud.domain.Amount;
import com.envimate.examples.example_mate_crud.domain.BearerCode;
import com.envimate.examples.example_mate_crud.domain.Currency;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator.ensureArrayNotEmpty;
import static com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator.ensureNotNull;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChargesInformation {
    public final BearerCode bearerCode;
    public final SenderCharge[] senderCharges;
    public final Amount receiverChargesAmount;
    public final Currency receiverChargesCurrency;

    public static ChargesInformation chargesInformation(
            final BearerCode bearerCode,
            final SenderCharge[] senderCharges,
            final Amount receiverChargesAmount,
            final Currency receiverChargesCurrency) {
        ensureNotNull(bearerCode, "bearerCode in ChargesInformation");
        ensureNotNull(receiverChargesAmount, "receiverChargesAmount in ChargesInformation");
        ensureNotNull(receiverChargesCurrency, "receiverChargesCurrency in ChargesInformation");
        ensureArrayNotEmpty(senderCharges, "senderCharges in ChargesInformation");
        return new ChargesInformation(bearerCode, senderCharges, receiverChargesAmount, receiverChargesCurrency);
    }
}
