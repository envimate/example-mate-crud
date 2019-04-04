package com.envimate.examples.example_mate_crud.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BeneficiaryParty {
    public final AccountName accountName;
    public final AccountNumber accountNumber;
    public final AccountNumberCode accountNumberCode;
    public final AccountType accountType;
    public final Address address;
    public final BankId bankId;
    public final BankIdCode bankIdCode;
    public final Name name;

    public static BeneficiaryParty beneficiaryParty(
            final AccountName accountName,
            final AccountNumber accountNumber,
            final AccountNumberCode accountNumberCode,
            final AccountType accountType,
            final Address address,
            final BankId bankId,
            final BankIdCode bankIdCode,
            final Name name) {

        return new BeneficiaryParty(accountName, accountNumber, accountNumberCode, accountType, address, bankId, bankIdCode, name);
    }

}
