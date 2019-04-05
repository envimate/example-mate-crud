package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.RequiredParameterValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SponsorParty {
    public final AccountNumber accountNumber;
    public final BankId bankId;
    public final BankIdCode bankIdCode;

    public static SponsorParty sponsorParty(final AccountNumber accountNumber,
                                            final BankId bankId,
                                            final BankIdCode bankIdCode) {
        RequiredParameterValidator.ensureNotNull(accountNumber, "accountNumber of SponsorParty");
        RequiredParameterValidator.ensureNotNull(bankId, "bankId of SponsorParty");
        RequiredParameterValidator.ensureNotNull(bankIdCode, "bankIdCode of SponsorParty");
        return new SponsorParty(accountNumber, bankId, bankIdCode);
    }
}
