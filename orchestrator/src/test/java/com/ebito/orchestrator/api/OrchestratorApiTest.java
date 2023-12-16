package com.ebito.orchestrator.api;

import com.ebito.orchestrator.client.cloud.CloudClient;
import com.ebito.orchestrator.client.reference.ReferenceClient;
import com.ebito.orchestrator.model.request.ReferenceGenerationRequest;
import com.ebito.orchestrator.model.response.PrintedGuids;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
class OrchestratorApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CloudClient cloudClient;
    @MockBean
    private ReferenceClient referenceClient;

    @Test
    @DisplayName("Получение списка справок от микросервиса cloud")
    void getAllClientReferences() throws Exception {
        List<PrintedGuids> printedGuidsList = Arrays.asList(
                PrintedGuids.builder()
                        .name("Выписка по начислениям абонента")
                        .pdfFileName("001_created_01_01_1970_08_40_12.pdf")
                        .link("/api/v1/forms/001_created_01_01_1970_08_40_12.pdf")
                        .build(),
                PrintedGuids.builder()
                        .name("Выписка по начислениям абонента")
                        .pdfFileName("002_created_01_01_1970_08_40_19.pdf")
                        .link("/api/v1/forms/002_created_01_01_1970_08_40_19.pdf")
                        .build());

        ResponseEntity<List<PrintedGuids>> responseEntity = new ResponseEntity<>(printedGuidsList, HttpStatus.OK);
        when(cloudClient.getClientReferences(anyString())).thenReturn(responseEntity);

        mockMvc.perform(get("/api/v1/{clientId}/get-client-references", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].link", containsInAnyOrder("/api/v1/forms/001_created_01_01_1970_08_40_12.pdf",
                        "/api/v1/forms/002_created_01_01_1970_08_40_19.pdf")));
    }

    @Test
    @DisplayName("Получение справки от микросервиса referense")
    void generateReference() throws Exception {
        var printedGuids = PrintedGuids.builder()
                .name("Выписка по начислениям абонента")
                .pdfFileName("001_created_01_01_1970_08_40_12.pdf")
                .link("/api/v1/forms/001_created_01_01_1970_08_40_12.pdf")
                .build();
        var referenceGenerationRequest = ReferenceGenerationRequest.builder().referenceCode("001").build();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(referenceGenerationRequest);

        ResponseEntity<PrintedGuids> responseEntity = new ResponseEntity<>(printedGuids, HttpStatus.OK);

        when(referenceClient.generateReference(anyString(), any())).thenReturn(responseEntity);

        mockMvc.perform(post("/api/v1/{clientId}/generate-reference", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.link", equalTo("/api/v1/forms/001_created_01_01_1970_08_40_12.pdf")));
    }
}