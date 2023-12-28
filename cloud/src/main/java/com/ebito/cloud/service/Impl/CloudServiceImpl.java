package com.ebito.cloud.service.Impl;

import com.ebito.cloud.mapper.DocumentMapper;
import com.ebito.cloud.model.entity.Document;
import com.ebito.cloud.model.response.PrintedGuids;
import com.ebito.cloud.reposytory.DocumentRepository;
import com.ebito.cloud.service.CloudService;
import com.ebito.cloud.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CloudServiceImpl implements CloudService {
    private final DocumentRepository documentRepo;
    private final FileService fileService;
    private final DocumentMapper  documentMapper;

    @Override
    public PrintedGuids create(String clientId, MultipartFile file) {
        Document document = fileService.saveDoc(file, clientId);
        documentRepo.save(document);
        return documentMapper.toDto(document);
    }


    @Override
    public List<PrintedGuids> getDocumentReferences(String clientId) {
        List<Document> documents = documentRepo.findAllByClientId(clientId);
        List<PrintedGuids> printedGuids = new ArrayList<>();
        documents.forEach(document -> printedGuids.add(documentMapper.toDto(document)));
        return printedGuids;
    }
}
