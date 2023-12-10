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

import static java.nio.file.Files.createDirectories;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private final String desktopPath = System.getProperty("user.dir") + File.separator + "documents";

    @Override
    public Document saveDoc(MultipartFile file, String clientId) {
        String fileName = generateFileName(file);
        String path = desktopPath + File.separator + fileName;

        try {
            createDirectories(Paths.get(path));
            file.transferTo(new File(path));

            return new Document(clientId, fileName);
        } catch (IOException e) {
            log.error("Ошибка при сохранении файла документа:{}", file.getName());
            return null;
        }
    }

    @Override
    public Resource getFileByLink(String link) {
        if (link.isEmpty()) {
            throw new IllegalArgumentException("Ссылка на файл не может быть пустой");
        }
        try {

            Path filePath = Paths.get(desktopPath).resolve(link);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Файл не найден или не доступен для чтения");
            }
                return resource;

        } catch (MalformedURLException ex) {
            throw new RuntimeException("Ошибка при получении файла по ссылке", ex);
        }
    }

    private String generateFileName(MultipartFile multiPart) {
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDate = currentDate.format(formatter);
        String type = Objects.requireNonNull(multiPart.getContentType()).replace("application/",".");
        return multiPart.getOriginalFilename()  + "_" +formattedDate +type;
    }
}
