package com.ebito.cloud.api.controller;

import com.ebito.cloud.api.CloudApi;
import com.ebito.cloud.model.response.DocumentResponse;
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
    public ResponseEntity<DocumentResponse> saveClientReference(String clientId, MultipartFile file) {
        DocumentResponse printedGuids = cloudService.create(clientId, file);
            return ResponseEntity.ok(printedGuids);

    }

    @Override
    public ResponseEntity<List<DocumentResponse>> getClientReferences(final String clientId) {
        List<DocumentResponse> clientReferences = cloudService.getDocumentReferences(clientId);
            return ResponseEntity.ok(clientReferences);
    }

    @Override
    public ResponseEntity<String> getURLByName(String name) {
        String url = documentService.downloadUrl(name);

            return ResponseEntity.ok(url);

    }

}
