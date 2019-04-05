package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.WhitelistValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountNumberCode {
    private final String value;

    public static AccountNumberCode accountNumberCode(final String value) {
        final String validated = WhitelistValidator.ensureOneOf(value, List.of("BBAN", "IBAN"), "value of AccountNumberCode");
        return new AccountNumberCode(validated);
    }


    public String internalValue() {
        return this.value;
    }
}
