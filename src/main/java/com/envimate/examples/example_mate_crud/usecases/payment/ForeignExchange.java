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
import com.envimate.examples.example_mate_crud.domain.ContractReference;
import com.envimate.examples.example_mate_crud.domain.Currency;
import com.envimate.examples.example_mate_crud.domain.ExchangeRate;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator.ensureNotNull;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ForeignExchange {
    public final ContractReference contractReference;
    public final ExchangeRate exchangeRate;
    public final Amount originalAmount;
    public final Currency originalCurrency;

    public static ForeignExchange foreignExchange(
            final ContractReference contractReference,
            final ExchangeRate exchangeRate,
            final Amount originalAmount,
            final Currency originalCurrency) {
        ensureNotNull(contractReference, "contractReference of ForeignEchange");
        ensureNotNull(exchangeRate, "exchangeRate of ForeignEchange");
        ensureNotNull(originalAmount, "originalAmount of ForeignEchange");
        ensureNotNull(originalCurrency, "originalCurrency of ForeignEchange");
        return new ForeignExchange(contractReference, exchangeRate, originalAmount, originalCurrency);
    }
}
