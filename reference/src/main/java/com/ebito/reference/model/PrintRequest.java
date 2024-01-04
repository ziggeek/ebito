package com.ebito.reference.model;

import com.ebito.reference.model.request.PrintData;
import com.ebito.reference.model.enumeration.DocumentType;
import lombok.*;

@Value
@Builder
public class PrintRequest {

    DocumentType documentType; // пока по дефолту пдф
    PrintData printData;
}
