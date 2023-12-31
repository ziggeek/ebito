package com.ebito.data_aggregator.api.controller.request;

import com.ebito.data_aggregator.model.dto.TransactionDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

/**
 * Данные для заполнения Выписки по начислениям абонента
 */

@SuperBuilder
@Getter
@Setter
public class Reference001PrintData extends PrintData {
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final int agreementNumber;
    private final LocalDate dateSigningAgreement;
    private final long accountNumber;
    private final String accountCurrency;
    private final String accountCurrencyFullName;
    private final String referenceCode;
    private final long totalAmount;
    private final List<TransactionDTO> transactions;
}
