package com.ebito.printed_form.api.controller;

import com.ebito.printed_form.api.PrinterApi;
import com.ebito.printed_form.model.request.PrinterRequest;
import com.ebito.printed_form.model.response.PrintedGuids;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PrinterController implements PrinterApi {


    @Override
    public ResponseEntity<PrintedGuids> generatePdf(PrinterRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<PrintedGuids> generateDocx(PrinterRequest request) {
        return null;
    }
}
