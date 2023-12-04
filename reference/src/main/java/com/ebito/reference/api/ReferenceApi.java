package com.ebito.reference.api;

import com.ebito.reference.model.request.ReferenceGenerationRequest;
import com.ebito.reference.model.response.PrintedGuids;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Reference", description = "Reference API")
@RequestMapping("/api/v1")
public interface ReferenceApi {

    @PostMapping("/{clientId}/generate-reference")
        //todo: сделать описание подробное
    ResponseEntity<PrintedGuids> generateReference(@PathVariable("clientId") String clientId,
                                                   @RequestBody ReferenceGenerationRequest request);
}
