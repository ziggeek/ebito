package com.ebito.reference.api;

import com.ebito.reference.model.request.ReferenceGenerationRequest;
import com.ebito.reference.model.response.PrintedGuids;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Reference", description = "Reference API")
@RequestMapping("/api/v1")
public interface ReferenceApi {
    @Operation(summary = "Сгенерировать форму определенного типа для клиента")
    @PostMapping("/clients/{clientId}/generate-reference")
    ResponseEntity<PrintedGuids> generateReference(@PathVariable("clientId") long clientId,
                                                   @RequestBody ReferenceGenerationRequest request);
}
