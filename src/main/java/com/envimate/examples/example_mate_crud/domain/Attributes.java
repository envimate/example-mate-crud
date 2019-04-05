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

import com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator.ensureNotNull;

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
    public final NumericReference numericReference;
    public final PaymentId paymentId;
    public final PaymentPurpose paymentPurpose;
    public final PaymentScheme paymentScheme;
    public final PaymentType paymentType;
    public final ProcessingDate processingDate;
    public final Reference reference;
    public final SchemePaymentSubType schemePaymentSubType;
    public final SchemePaymentType schemePaymentType;
    public final SponsorParty sponsorParty;

    public static Attributes attributes(
            final Amount amount,
            final BeneficiaryParty beneficiaryParty,
            final ChargesInformation chargesInformation,
            final Currency currency,
            final DebtorParty deptorParty,
            final EndToEndReference endToEndReference,
            final ForeignExchange foreignExchange,
            final NumericReference numericReference,
            final PaymentId paymentId,
            final PaymentPurpose paymentPurpose,
            final PaymentScheme paymentScheme,
            final PaymentType paymentType,
            final ProcessingDate processingDate,
            final Reference reference,
            final SchemePaymentSubType schemePaymentSubType,
            final SchemePaymentType schemePaymentType,
            final SponsorParty sponsorParty) {
        ensureNotNull(amount, "amount of Attributes");
        ensureNotNull(beneficiaryParty, "beneficiaryParty of Attributes");
        ensureNotNull(chargesInformation, " chargesInformation of Attributes");
        ensureNotNull(currency, "currency of Attributes");
        ensureNotNull(deptorParty, "deptorParty of Attributes");
        ensureNotNull(endToEndReference, "endToEndReference of Attributes");
        ensureNotNull(foreignExchange, "foreignExchange of Attributes");
        ensureNotNull(numericReference, "numericReference of Attributes");
        ensureNotNull(paymentId, "paymentId of Attributes");
        ensureNotNull(paymentPurpose, "paymentPurpose of Attributes");
        ensureNotNull(paymentScheme, "paymentScheme of Attributes");
        ensureNotNull(paymentType, "paymentType of Attributes");
        ensureNotNull(processingDate, "processingDate of Attributes");
        ensureNotNull(reference, "reference of Attributes");
        ensureNotNull(schemePaymentSubType, "schemePaymentSubType of Attributes");
        ensureNotNull(schemePaymentType, "schemePaymentType of Attributes");
        ensureNotNull(sponsorParty, "sponsorPart of Attributes");

        return new Attributes(amount,
                beneficiaryParty,
                chargesInformation,
                currency,
                deptorParty,
                endToEndReference,
                foreignExchange,
                numericReference,
                paymentId,
                paymentPurpose,
                paymentScheme,
                paymentType,
                processingDate,
                reference,
                schemePaymentSubType,
                schemePaymentType,
                sponsorParty);
    }
}
