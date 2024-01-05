package com.ebito.orchestrator.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Запрос для генерации новой справки
 */
@Value
@Builder
public class ReferenceGenerationRequest {

    @Schema(description = "Код справки",
            example = "001")
    @NotBlank(message = "must not be blank")
    @Size(max = 3, message = "must contain less than 3")
    String referenceCode;

    @Schema(description = "Дата C",
            example = "2021-03-15")
    @NotNull(message = "must not be null")
    LocalDate dateFrom;

    @Schema(description = "Дата ПО",
            example = "2023-03-15")
    @NotNull(message = "must not be null")
    LocalDate dateTo;
}
