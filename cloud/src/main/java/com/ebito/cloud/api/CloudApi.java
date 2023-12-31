package com.ebito.cloud.api;

import com.ebito.cloud.model.response.PrintedGuids;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Cloud", description = "Cloud API сервис для загрузки и получения документов. ")
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.")
})
@RequestMapping("/api/v1")
public interface CloudApi {
    @Operation(summary = "Получить документ в UI",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Всё хорошо.",
                            content = {@Content(mediaType = MediaType.APPLICATION_PDF_VALUE,
                                    schema = @Schema(implementation = Resource.class))}),
                    @ApiResponse(responseCode = "404", description = "Файл не найден.")
            })
    @GetMapping(value = "/get-reference/{name}")
    ResponseEntity<Resource> getReferenceByName(@PathVariable("name")
                                                @Parameter(description = "Имя документа", required = true)
                                                String name);

    @Operation(summary = "Сохранить файл и отправить ответ сгенерированной справки",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Created",
                            content = {@Content(schema = @Schema(implementation = PrintedGuids.class))})
            })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            value = "/{clientId}/save-client-reference")
    ResponseEntity<PrintedGuids> saveClientReference(@PathVariable("clientId")
                                                     @Parameter(description = "id клиента", required = true)
                                                     String clientId,
                                                     @RequestPart(name = "file")
                                                     @Parameter(description = "Файл для загрузки", required = true)
                                                     MultipartFile file);

    @Operation(summary = "Получить список ответов сгенерированной справки",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Created",
                            content = {@Content(schema = @Schema(implementation = PrintedGuids.class))})
            })
    @GetMapping("/{clientId}/get-client-references")
    ResponseEntity<List<PrintedGuids>> getClientReferences(@PathVariable("clientId")
                                                           @Parameter(description = "id клиента", required = true)
                                                           String clientId);


}
