package com.ebito.reference.api;

import com.ebito.reference.model.request.ReferenceGenerationRequest;
import com.ebito.reference.model.response.PrintedGuids;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Reference", description = "Reference API")
@RequestMapping("/api/v1")
public interface ReferenceApi {

    @Operation(summary = "Сгенерировать форму определенного типа для клиента")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Форма сгенерирована",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Пример ответа в случае успешной генерации формы",
                                    value = "{\"link\":\"link\",\"name\":\"Выписка по начислениям абонента\",\"checkSum\":\"fa32e4f0637dae65ca68437fa24e7f275cdd6d12517ed270061ac8be7ee97aab\",\"pdfFileName\":\"created_04_01_2024_22_44_21_ySgxWN.pdf\"}"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный формат данных необходимых для создания формы",
                    content = @Content(
                            mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "В случае внутренних ошибок.",
                    content = @Content(
                            mediaType = "application/json")
            )
    })
    @PostMapping("/clients/{clientId}/generate-reference")
    ResponseEntity<PrintedGuids> generateReference(@PathVariable("clientId") long clientId,
                                                   @RequestBody ReferenceGenerationRequest request);
}
