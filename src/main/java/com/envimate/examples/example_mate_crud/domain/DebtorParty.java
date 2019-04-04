package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DebtorParty {
    public final AccountName accountName;
    public final AccountNumber accountNumber;
    public final AccountNumberCode accountNumberCode;
    public final Address address;
    public final BankId bankId;
    public final BankIdCode bankIdCode;
    public final Name name;

    public static DebtorParty debtorParty(final AccountName accountName,
            final AccountNumber accountNumber,
            final AccountNumberCode accountNumberCode,
            final Address address,
            final BankId bankId,
            final BankIdCode bankIdCode,
            final Name name) {
        RequiredParameterValidator.ensureNotNull(accountName, "accountName in DeptorParty");
        RequiredParameterValidator.ensureNotNull(accountNumber, "accountNumber in DeptorParty");
        RequiredParameterValidator.ensureNotNull(accountNumberCode, "accountNumberCode in DeptorParty");
        RequiredParameterValidator.ensureNotNull(address, "address in DeptorParty");
        RequiredParameterValidator.ensureNotNull(bankId, "bankId in DeptorParty");
        RequiredParameterValidator.ensureNotNull(bankIdCode, " bankIdCode in DeptorParty");
        RequiredParameterValidator.ensureNotNull(name, "name in DeptorParty");
        return new DebtorParty(accountName, accountNumber, accountNumberCode, address, bankId, bankIdCode, name);
    }
}
