package com.ebito.orchestrator.api.controller;

import com.ebito.orchestrator.api.OrchestratorApi;
import com.ebito.orchestrator.client.cloud.CloudClient;
import com.ebito.orchestrator.client.reference.ReferenceClient;
import com.ebito.orchestrator.model.request.ReferenceGenerationRequest;
import com.ebito.orchestrator.model.response.PrintedGuids;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrchestratorController implements OrchestratorApi {

    private final ReferenceClient referenceClient;
    private final CloudClient cloudClient;

    @Override
    public ResponseEntity<List<PrintedGuids>> getAllClientReferences(final String clientId) {
        final var response = cloudClient.getAllClientReferences(clientId);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<PrintedGuids> generateReference(final String clientId, final ReferenceGenerationRequest request) {
        final var response = referenceClient.generateReference(clientId, request);
        return ResponseEntity.ok().body(response);
    }
}
