package com.envimate.examples.example_mate_crud.domain;

import com.envimate.examples.example_mate_crud.validation.NumericValidator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeRate {
    private final double value;

    public static ExchangeRate exchangeRate(final String value) {
        final double validated = NumericValidator.ensureDouble(value, "value of ExchangeRate");
        return new ExchangeRate(validated);
    }
}
