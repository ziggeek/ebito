package com.ebito.cloud.service.Impl;

import com.ebito.cloud.mapper.DocumentMapper;
import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.model.response.DocumentResponse;
import com.ebito.cloud.reposytory.DocumentRepository;
import com.ebito.cloud.service.CloudService;
import com.ebito.cloud.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CloudServiceImpl implements CloudService {
    private final DocumentRepository documentRepo;
    private final DocumentMapper documentMapper;
    private final DocumentService documentService;

    @Override
    public DocumentResponse create(String clientId, MultipartFile file) {
        log.info("Creating document for client: {}", clientId);
        DocumentEntity document = documentService.upload(file, clientId);
        documentRepo.save(document);
        return documentMapper.toDto(document,documentService);
    }


    @Override
    public List<DocumentResponse> getDocumentReferences(String clientId) {
        log.info("Getting document references for client: {}", clientId);
        Assert.hasText(clientId, "Client ID cannot be empty");
        List<DocumentEntity> documents = documentRepo.findAllByClientId(clientId);
        List<DocumentResponse> printedGuids = new ArrayList<>();
        documents.forEach(document -> printedGuids.add(documentMapper.toDto(document,documentService)));
        return printedGuids;
    }
}
