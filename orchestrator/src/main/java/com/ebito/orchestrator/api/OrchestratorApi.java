package com.ebito.orchestrator.api;

import com.ebito.orchestrator.model.request.FormGenerationRequest;
import com.ebito.orchestrator.model.response.FormGenerationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Orchestrator", description = "Orchestrator API")
@RequestMapping("/api/v1")
public interface OrchestratorApi {

    ResponseEntity<Void> getAllClientReferences();

    @PostMapping("/{clientId}/generate-reference")
    ResponseEntity<FormGenerationResponse> generateReference(@RequestParam("clientId") String clientId,
                                                             @RequestBody FormGenerationRequest request);


}
