package com.ebito.reference.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Запрос для генерации новой справки
 */
@Value
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class ReferenceGenerationRequest {

    @Schema(description = "Код справки",
            example = "001")
    String referenceCode;
}
