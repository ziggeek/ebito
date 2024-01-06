package com.ebito.reference.api.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Запрос для генерации новой справки
 */
@Value
@Builder
public class PrintFormGenerationRequest {

    @Schema(description = "Код справки",
            example = "001")
    String referenceCode;
}
