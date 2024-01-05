package com.ebito.reference.api.controller;

import com.ebito.reference.api.ReferenceApi;
import com.ebito.reference.model.request.ReferenceGenerationRequest;
import com.ebito.reference.model.response.PrintedGuids;
import com.ebito.reference.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReferenceController implements ReferenceApi {

    private final CommonService commonService;

    @Override
    public ResponseEntity<PrintedGuids> generateReference(final long clientId, final ReferenceGenerationRequest request) {
        final var response = commonService.selectReference(clientId, request);
        return ResponseEntity.created(URI.create(response.getLink())).body(response);
    }
}
