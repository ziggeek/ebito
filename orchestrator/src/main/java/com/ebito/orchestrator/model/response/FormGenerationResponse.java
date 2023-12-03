package com.ebito.orchestrator.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FormGenerationResponse {


    @Schema(description = "Ссылка на документ",
            example = "/api/v1/forms/")
    String link;

    @Schema(description = "Имя документа",
            example = "Выписка по начислениям абонента")
    String name;

    @Schema(description = "Имя документа",
            example = "created_01_01_1970_08_40_12.pdf")
    String pdfFileName;

}
