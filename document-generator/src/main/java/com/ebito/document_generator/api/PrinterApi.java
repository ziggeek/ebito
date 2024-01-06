package com.ebito.document_generator.api;


import com.ebito.document_generator.api.controller.response.FormGenerationResponse;
import com.ebito.document_generator.api.controller.request.FormGenerationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "PrintedForm", description = "PrintedForm API")
@RequestMapping("/api/v1/forms")
public interface PrinterApi {

    @Operation(summary = "Сгенерировать форму определенного типа")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Форма сгенерирована",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Пример ответа в случае успешной генерации формы",
                                    ref = "#/components/examples/successFormGenerationResponseExample"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный формат данных необходимых для создания формы",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Пример ответа в случае запроса нарушающего контракт",
                                            ref = "#/components/examples/constraintValidationErrorResponseExample"),
                                    @ExampleObject(
                                            name = "Пример ответа в случае запроса с недесериализуемыми данными",
                                            ref = "#/components/examples/notReadableRequestDataErrorExample"),
                            })
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "В случае внутренних ошибок.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Пример ответа в случае внутренних ошибок",
                                    ref = "#/components/examples/internalErrorResponseExample"))
            )
    })
    @PostMapping("/generate-print-form")
    ResponseEntity<FormGenerationResponse> generatePrintForm(@RequestBody @Valid FormGenerationRequest event) throws IOException;
}
