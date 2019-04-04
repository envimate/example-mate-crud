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

package com.envimate.examples.example_mate_crud.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Attributes {
    public final Amount amount;
    public final BeneficiaryParty beneficiaryParty;
    public final ChargesInformation chargesInformation;
    public final Currency currency;
    public final DebtorParty deptorParty;
    public final EndToEndReference endToEndReference;
    public final ForeignExchange foreignExchange;
    public final NumbericReference numbericReference;
    public final PaymentId paymentId;
    public final PaymentPurpose paymentPurpose;
    public final PaymentScheme paymentScheme;
    public final PaymentType paymentType;
    public final ProcessingDate processingDate;
    public final Reference reference;
    public final SchemePaymentSubType schemePaymentSubType;
    public final SchemePaymentType schemePaymentType;
    public final SponsorParty sponsorParty;
}
