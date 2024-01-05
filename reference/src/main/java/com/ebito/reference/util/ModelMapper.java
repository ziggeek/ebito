package com.ebito.reference.util;

import com.ebito.reference.model.Operation;
import com.ebito.reference.model.dto.TransactionDTO;

public class ModelMapper {
    public static TransactionDTO convertToTransactionDTO(Operation operation) {
        return TransactionDTO.builder()
                .id(operation.getId())
                .sum(operation.getSum())
                .paymentMethod(operation.getPaymentMethod().getPaymentMethodName())
                .date(operation.getDate())
                .time(operation.getTime())
                .build();
    }
}
