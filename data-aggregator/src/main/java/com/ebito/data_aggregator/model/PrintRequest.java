package com.ebito.data_aggregator.model;

import com.ebito.data_aggregator.model.enumeration.DocumentType;
import com.ebito.data_aggregator.api.controller.request.PrintData;
import lombok.*;

@Value
@Builder
public class PrintRequest {

    DocumentType documentType; // пока по дефолту пдф
    PrintData printData;
}
