package com.ebito.cloud.api.controller;

import com.ebito.cloud.api.CloudApi;
import com.ebito.cloud.model.response.PrintedGuids;
import com.ebito.cloud.service.CloudService;
import com.ebito.cloud.service.FileService;
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
    private final FileService fileService;

    @Override
    public ResponseEntity<Resource> getReferenceByLink(String name) {
        Resource fileResource = fileService.getFileByName(name);
        if (fileResource != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(fileResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<PrintedGuids> saveClientReference(String clientId, MultipartFile file) {

        PrintedGuids printedGuids = cloudService.create(clientId, file);
        if (printedGuids != null) {
            return ResponseEntity.ok(printedGuids);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<PrintedGuids>> getClientReferences(final String clientId) {

        List<PrintedGuids> clientReferences = cloudService.getDocumentReferences(clientId);
        if (clientReferences != null) {
            return ResponseEntity.ok(clientReferences);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
