package com.ebito.orchestrator.api;

import com.ebito.orchestrator.model.request.ReferenceGenerationRequest;
import com.ebito.orchestrator.model.response.PrintedGuids;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Tag(name = "Orchestrator", description = "Orchestrator API")
@RequestMapping("/api/v1")
public interface OrchestratorApi {


    @GetMapping("/{clientId}/get-client-references")
    @Operation(
            summary = "Получение всех сгенерированных справок клиента",
            description = "Позволяет получить список ссылок на справки по id клиента"
    )
    @ApiResponse(responseCode = "200",
            description = "Список справок получен",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PrintedGuids.class),
                    examples = @ExampleObject(value = "[ " +
                            "{ \"link\": \"/api/v1/forms/001_created_01_01_1970_08_40_12.pdf\", " +
                            "\"name\": \"Выписка по начислениям абонента\", " +
                            "\"pdfFileName\": \"001_created_01_01_1970_08_40_12.pdf\" }, " +
                            "{ \"link\": \"/api/v1/forms/002_created_01_01_1970_09_30_45.pdf\", " +
                            "\"name\": \"Еще одна справка\", " +
                            "\"pdfFileName\": \"002_created_01_01_1970_09_30_45.pdf\" } ]"))
            })
    @ApiResponse(
            responseCode = "404",
            description = "Запрошенный ресурс не существует",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PrintedGuids.class))
            })
    ResponseEntity<List<PrintedGuids>> getAllClientReferences(
            @PathVariable("clientId") @Parameter(description = "Идентификатор  клиента") String clientId
    );


    @PostMapping("/{clientId}/generate-reference")
    @Operation(
            summary = "Получение определенного тип справки клиента",
            description = "Позволяет получить справку по заданным типу справки и id клиента"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Справка получена",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PrintedGuids.class))
            })
    @ApiResponse(
            responseCode = "404",
            description = "Запрошенный ресурс не существует",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PrintedGuids.class))
            })
    ResponseEntity<PrintedGuids> generateReference(
            @PathVariable("clientId") @Parameter(description = "Идентификатор  клиента") String clientId,
            @RequestBody(description = "Тип справки и время от и до") ReferenceGenerationRequest request
    );
}
