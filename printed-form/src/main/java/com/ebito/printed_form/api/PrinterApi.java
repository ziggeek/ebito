package com.ebito.printed_form.api;


import com.ebito.printed_form.model.request.PrinterRequest;
import com.ebito.printed_form.model.response.PrintedGuids;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Reference", description = "Reference API")
@RequestMapping("/api/v1")
public interface PrinterApi {

    @PostMapping(value = "/pdf")
    ResponseEntity<PrintedGuids> generatePdf(@RequestBody PrinterRequest request);

    @PostMapping(value = "/docx")
    ResponseEntity<PrintedGuids> generateDocx(@RequestBody PrinterRequest request);
}
