package com.ebito.data_aggregator.api.controller;

import com.ebito.data_aggregator.api.controller.request.PrintFormGenerationRequest;
import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import com.ebito.data_aggregator.service.common.CommonService;
import com.ebito.data_aggregator.api.ReferenceApi;
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
