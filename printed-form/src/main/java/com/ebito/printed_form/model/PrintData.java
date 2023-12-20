package com.ebito.printed_form.model;

import com.ebito.reference.model.enumeration.DocumentType;
import com.ebito.reference.model.request.PrinterRequest;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintData {

    DocumentType documentType;
    PrinterRequest printerRequest;
}
