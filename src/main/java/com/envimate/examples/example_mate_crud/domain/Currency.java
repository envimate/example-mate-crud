package com.envimate.examples.example_mate_crud.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Currency {
    private final java.util.Currency value;

    public static Currency currency(final String value) {

        final java.util.Currency validated = java.util.Currency.getInstance(value);
        return new Currency(validated);
    }

    //TODO validate
}
