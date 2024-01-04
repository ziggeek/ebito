package com.ebito.reference.model;

import com.ebito.reference.model.request.PrinterRequest;
import com.ebito.reference.model.enumeration.DocumentType;
import lombok.*;

@Value
@Builder
public class PrintData {

    DocumentType documentType; // пока по дефолту пдф
    PrinterRequest printerRequest;
}
