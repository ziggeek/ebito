package com.ebito.reference.client;

import com.ebito.reference.model.request.FormGenerationData;
import com.ebito.reference.model.request.PrinterRequest;
import com.ebito.reference.model.response.PrintedGuids;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "printed-form",
        url = "${feign.client.config.print.url}",
        path = "/api/v1"
)
public interface PrinterClient {

    @PostMapping(value = "/forms/generate-print-form")
    ResponseEntity<PrintedGuids> generatePdf(@RequestBody FormGenerationData request);
}
