package com.ebito.cloud.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

/**
 * Ответ сгенерированной справки
 */
@Value
@Builder
public class DocumentResponse {


    @Schema(description = "Ссылка на документ",
            example = "https://example.com/12345.pdf")
    String link;


}
