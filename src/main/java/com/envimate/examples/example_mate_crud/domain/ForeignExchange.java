package com.envimate.examples.example_mate_crud.domain;

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
