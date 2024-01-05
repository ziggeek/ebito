package com.ebito.reference.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
    @NotNull(message = "referenceCode must not be null")
    String referenceCode;

    @Schema(description = "Дата C",
            example = "2013-01-27")
    LocalDate dateFrom;

    @Schema(description = "Дата ПО",
            example = "2023-01-27")
    LocalDate dateTo;
}
