package com.ebito.data_aggregator.model.dto;

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
    private String paymentMethod;
    private long sum;
}
