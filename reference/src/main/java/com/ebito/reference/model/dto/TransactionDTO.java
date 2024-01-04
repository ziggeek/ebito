package com.ebito.reference.model.dto;

import com.ebito.reference.model.enumeration.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
public class TransactionDTO {
    private long id;
    private LocalDate date;
    private LocalTime time;
    private PaymentMethod paymentMethod;
    private long sum;
}
