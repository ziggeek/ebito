package com.ebito.cloud.service.Impl;

import com.ebito.cloud.model.entity.Document;
import com.ebito.cloud.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

import static java.nio.file.Files.createDirectories;

@Service
@Slf4j
public class FileServiceImpl implements FileService {


    private final String fileStorageLocation = System.getProperty("user.dir") + File.separator + "cloud"
            + File.separator + "src" + File.separator + "main" + File.separator + "resources"
            + File.separator + "documents";


    @Override
    public Document saveDoc(MultipartFile file, String clientId) {
        String fileName = generateFileName(file);
        String path = fileStorageLocation + File.separator + fileName;

        try {
            createDirectories(Paths.get(path));
            file.transferTo(new File(path));

            return new Document(clientId, file.getOriginalFilename(), fileName);
        } catch (IOException e) {
            log.error("Ошибка при сохранении файла документа:{}", file.getName());
            return null;
        }
    }

    @Override
    public Resource getFileByName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Ссылка на файл не может быть пустой");
        }
        try {
            Path filePath = Paths.get(fileStorageLocation)
                    .toAbsolutePath()
                    .normalize()
                    .resolve(name);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                log.error("Файл не найден или не доступен для чтения: {}", name);
                return null;
            }
            return resource;

        } catch (MalformedURLException ex) {
            log.error("Ошибка при получении файла по ссылке: {}", name);
            return null;
        }
    }

    private String generateFileName(MultipartFile multiPart) {
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDate = currentDate.format(formatter);

        String originalFilename = Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
        int dotIndex = originalFilename.indexOf(".");
        String name = (dotIndex != -1) ? originalFilename.substring(0, dotIndex) : originalFilename;

        String random = UUID.randomUUID().toString().replace('-', '_');

        String contentType = Objects.requireNonNull(multiPart.getContentType()).replace("application/", ".");

        return  name + "_" + formattedDate + "_" + random + contentType;
    }
}
