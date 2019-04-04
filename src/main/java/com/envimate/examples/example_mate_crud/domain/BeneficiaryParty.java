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
        ensureNotNull(accountName, "accountName of BeneficiaryParty");
        ensureNotNull(accountNumber, "accountNumber of BeneficiaryParty");
        ensureNotNull(accountNumberCode, "accountNumberCode of BeneficiaryParty");
        ensureNotNull(accountType, "accountType of BeneficiaryParty");
        ensureNotNull(address, "address of BeneficiaryParty");
        ensureNotNull(bankId, "bankId of BeneficiaryParty");
        ensureNotNull(bankIdCode, "bankIdCode of BeneficiaryParty");
        ensureNotNull(name, "name of BeneficiaryParty");
        return new BeneficiaryParty(accountName, accountNumber, accountNumberCode, accountType, address, bankId, bankIdCode, name);
    }

}
