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

import com.envimate.examples.example_mate_crud.domain.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator.ensureNotNull;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Party {
    public final AccountName accountName;
    public final AccountNumber accountNumber;
    public final AccountNumberCode accountNumberCode;
    public final AccountType accountType;
    public final Address address;
    public final BankId bankId;
    public final BankIdCode bankIdCode;
    public final Name name;

    public static Party party(
            final AccountName accountName,
            final AccountNumber accountNumber,
            final AccountNumberCode accountNumberCode,
            final AccountType accountType,
            final Address address,
            final BankId bankId,
            final BankIdCode bankIdCode,
            final Name name) {
        ensureNotNull(accountNumber, "accountNumber of Party");
        ensureNotNull(bankId, "bankId of Party");
        ensureNotNull(bankIdCode, "bankIdCode of Party");
        return new Party(accountName, accountNumber, accountNumberCode, accountType, address, bankId, bankIdCode, name);
    }

}
