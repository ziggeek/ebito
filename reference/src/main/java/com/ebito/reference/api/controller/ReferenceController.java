package com.ebito.reference.api.controller;

import com.ebito.reference.api.ReferenceApi;
import com.ebito.reference.api.controller.request.PrintFormGenerationRequest;
import com.ebito.reference.api.controller.response.PrintedGuids;
import com.ebito.reference.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReferenceController implements ReferenceApi {

    private final CommonService commonService;

    @Override
    public ResponseEntity<PrintedGuids> generatePrintForm(final long clientId, final PrintFormGenerationRequest request) {
        final var response = commonService.selectPrintForm(clientId, request);
        return ResponseEntity.ok(response);
    }
}
