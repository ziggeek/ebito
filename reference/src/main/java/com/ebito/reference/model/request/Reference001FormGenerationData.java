package com.ebito.reference.model.request;

import com.ebito.reference.model.dto.TransactionDTO;
import com.ebito.reference.model.enumeration.Channel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Данные для заполнения Выписки по начислениям абонента
 */

@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class Reference001FormGenerationData implements FormGenerationData {
    private final String form;
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
    private final Channel channel;
    private final long totalAmount;
    private final List<TransactionDTO> transactions;
}
