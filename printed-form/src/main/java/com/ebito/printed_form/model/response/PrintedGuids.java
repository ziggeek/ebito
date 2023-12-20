package com.ebito.printed_form.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

/**
 * Ответ сгенерированной справки
 */
@Value
@Builder
public class PrintedGuids {


    @Schema(description = "Ссылка на документ",
            example = "/api/v1/forms/001_created_01_01_1970_08_40_12.pdf")
    String link;

    @Schema(description = "Тип справки",
            example = "Выписка по начислениям абонента")
    String name;

    @Schema(description = "Имя справки",
            example = "001_created_01_01_1970_08_40_12.pdf")
    String pdfFileName;

}
