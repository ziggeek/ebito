package com.ebito.cloud.service.Impl;

import com.ebito.cloud.model.entity.Document;
import com.ebito.cloud.model.response.PrintedGuids;
import com.ebito.cloud.reposytory.DocumentRepository;
import com.ebito.cloud.service.CloudService;
import com.ebito.cloud.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CloudServiceImpl implements CloudService {
    private final DocumentRepository documentRepo;
    private final FileService fileService;

    @Override
    public PrintedGuids create(String clientId, MultipartFile file) {
        Document document = fileService.saveDoc(file, clientId);
        documentRepo.save(document);
        System.out.println(file.getOriginalFilename());
        return PrintedGuids.builder()
                .link("/api/v1/forms/" + document.getName())
                .name(file.getOriginalFilename())
                .pdfFileName(document.getName())
                .build();
    }


    @Override
    public List<PrintedGuids> getDocumentReferences(String clientId) {
        List<Document> documents = documentRepo.findAllByClientId(clientId);
        List<PrintedGuids> printedGuids = new ArrayList<>();
        documents.forEach(document -> printedGuids.add(PrintedGuids.builder()
                .link("/api/v1/forms/" + document.getName())
                .name(document.getType())
                .pdfFileName(document.getName())
                .build()));
        return printedGuids;
    }


}
