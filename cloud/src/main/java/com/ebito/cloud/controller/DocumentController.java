package com.ebito.cloud.controller;

import com.ebito.cloud.dto.DocumentDTO;
import com.ebito.cloud.service.DocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/{userId}/documents")
@Tag(name = "Сохранение документов")
public class DocumentController {
    private final DocumentService documentService;


    @PostMapping(consumes = MediaType.APPLICATION_PDF_VALUE)
    public void saveDocument(@PathVariable("userId") Long id) {

    }

    @GetMapping()
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(documentService.getDocument(id));
    }


}
