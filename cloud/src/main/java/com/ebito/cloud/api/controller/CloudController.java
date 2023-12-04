package com.ebito.cloud.api.controller;

import com.ebito.cloud.api.CloudApi;
import com.ebito.cloud.model.response.PrintedGuids;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CloudController implements CloudApi {


    @Override
    public ResponseEntity<MultipartFile> getReferenceByLink(String link) {
        return null;
    }

    @Override
    public ResponseEntity<PrintedGuids> saveClientReference(final String clientId, MultipartFile file) {
        return null;
    }

    @Override
    public ResponseEntity<List<PrintedGuids>> getClientReferences(final String clientId) {
        return null;
    }
}
