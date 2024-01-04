package com.ebito.reference.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {
    CARD("Карта"),
    SBP("СБП"),
    CASH("Наличные");

    private final String paymentMethodName;
}
