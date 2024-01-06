package com.ebito.document_generator.api.controller;

import com.ebito.document_generator.api.PrinterApi;
import com.ebito.document_generator.api.controller.request.FormGenerationRequest;
import com.ebito.document_generator.api.controller.response.FormGenerationResponse;
import com.ebito.document_generator.service.FormGeneratorServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PrinterController implements PrinterApi {

    private final FormGeneratorServiceImpl formGeneratorServiceImpl;

    @Override
    public ResponseEntity<FormGenerationResponse> generatePrintForm(final FormGenerationRequest request) throws IOException {
        FormGenerationResponse body = formGeneratorServiceImpl.generate(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(body);
    }
}
