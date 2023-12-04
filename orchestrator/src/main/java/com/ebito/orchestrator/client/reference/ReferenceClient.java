package com.ebito.orchestrator.client.reference;

import com.ebito.orchestrator.model.request.ReferenceGenerationRequest;
import com.ebito.orchestrator.model.response.PrintedGuids;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(
        value = "reference",
        url = "${feign.client.config.reference.url}")
@RequestMapping("/api/v1/")
public interface ReferenceClient {

    @PostMapping("/{clientId}/generate-reference")
    ResponseEntity<PrintedGuids> generateReference(@PathVariable("clientId") String clientId,
                                                   @RequestBody ReferenceGenerationRequest request);
}
