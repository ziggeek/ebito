package com.ebito.cloud.api.controller;

import com.ebito.cloud.api.CloudApi;
import com.ebito.cloud.model.response.PrintedGuids;
import com.ebito.cloud.service.CloudService;
import com.ebito.cloud.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CloudController implements CloudApi {
    private final CloudService cloudService;
    private final DocumentService documentService;

    @Override
    public ResponseEntity<Resource> getReferenceByName(String name) {
        Resource fileResource = documentService.download(name);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(fileResource);

    }

    @Override
    public ResponseEntity<PrintedGuids> saveClientReference(String clientId, MultipartFile file) {
        PrintedGuids printedGuids = cloudService.create(clientId, file);
            return ResponseEntity.ok(printedGuids);

    }

    @Override
    public ResponseEntity<List<PrintedGuids>> getClientReferences(final String clientId) {
        List<PrintedGuids> clientReferences = cloudService.getDocumentReferences(clientId);
            return ResponseEntity.ok(clientReferences);
    }
}
