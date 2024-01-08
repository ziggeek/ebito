package com.ebito.cloud.service.Impl;

import com.ebito.cloud.mapper.DocumentMapper;
import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.model.response.DocumentResponse;
import com.ebito.cloud.reposytory.DocumentRepository;
import com.ebito.cloud.service.CloudService;
import com.ebito.cloud.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

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
        return documentMapper.toDto(document, documentService);
    }


    @Override
    public Page<DocumentResponse> getDocumentReferences(String clientId, Pageable page) {
        log.info("Getting document references for client: {}", clientId);
        Assert.hasText(clientId, "Client ID cannot be empty");
        Page<DocumentEntity> documents = documentRepo.findAllByClientId(clientId, page);
        return documents.map(document -> documentMapper.toDto(document, documentService));
    }
}
