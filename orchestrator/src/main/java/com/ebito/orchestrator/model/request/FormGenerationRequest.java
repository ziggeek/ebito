package com.ebito.orchestrator.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FormGenerationRequest {

    @Schema(description = "Код справки",
            example = "001")
    String referenceCode;
}
