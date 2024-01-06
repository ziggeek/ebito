package com.ebito.data_aggregator.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
    RUB("Рубль РФ"),
    BYN("Белорусский рубль"),
    KZT("Казахстанский тенге");

    private final String currencyFullName;
}
