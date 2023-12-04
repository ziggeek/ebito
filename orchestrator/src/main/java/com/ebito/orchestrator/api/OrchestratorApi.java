package com.ebito.orchestrator.api;

import com.ebito.orchestrator.model.request.ReferenceGenerationRequest;
import com.ebito.orchestrator.model.response.PrintedGuids;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orchestrator", description = "Orchestrator API")
@RequestMapping("/api/v1")
public interface OrchestratorApi {


    @GetMapping("/{clientId}/get-client-references")
        //todo: сделать описание подробное
    ResponseEntity<List<PrintedGuids>> getAllClientReferences(@PathVariable("clientId") String clientId);


    @PostMapping("/{clientId}/generate-reference")
        //todo: сделать описание подробное
    ResponseEntity<PrintedGuids> generateReference(@PathVariable("clientId") String clientId,
                                                   @RequestBody ReferenceGenerationRequest request);


}
