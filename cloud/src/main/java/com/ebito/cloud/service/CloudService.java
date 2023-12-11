package com.ebito.cloud.service;

import com.ebito.cloud.model.response.PrintedGuids;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudService {
    PrintedGuids create(String clientId, MultipartFile file);

    List<PrintedGuids> getDocumentReferences(String clientId);
}
