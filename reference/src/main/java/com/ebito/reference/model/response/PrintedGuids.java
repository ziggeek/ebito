package com.ebito.reference.model.response;

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

    @Schema(description = "Чек сумма документа",
            example = "7596c9e5bcb5dca0a6ea8a0704ad79ded2888950cfd077e2ff0d4962291acfc9")
    String checkSum;

    @Schema(description = "Имя справки",
            example = "001_created_01_01_1970_08_40_12.pdf")
    String pdfFileName;

}
