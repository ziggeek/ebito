package com.ebito.cloud.service.Impl;

import com.ebito.cloud.exception.FileProcessingException;
import com.ebito.cloud.exception.InvalidUrlException;
import com.ebito.cloud.exception.ResourceAccessException;
import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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

    @Value(value = "${file.storage.location}")
    private String fileStorageLocation;


    @Override
    public DocumentEntity saveDoc(MultipartFile file, String clientId) {
        Assert.hasText(clientId, "Client ID cannot be empty");
        log.info("Saving a document file: {}", file.getOriginalFilename());
        String fileName = generateFileName(file);
        try {
            createDirectories(getFilePath());
            file.transferTo(new File(getFilePath() + File.separator + fileName));

            return new DocumentEntity(clientId, file.getOriginalFilename(), fileName);
        } catch (IOException e) {
            throw new FileProcessingException("Error saving document file:" + file.getName());
        }
    }

    @Override
    public Resource getFileByName(String name) {
        log.info("Getting a document file by name: {}", name);
        Assert.hasText(name, "The document file name cannot be empty");
        try {
            Path filePath = getFilePath().resolve(name);
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResourceAccessException(name+" not found or not available ");
            }
            return resource;
        } catch (MalformedURLException ex) {
            throw new InvalidUrlException("Error when retrieving file from link: " + name);
        }
    }

    /**
     * Генерация имени файла документа.
     *
     * @param multiPart файл документа.
     * @return имя файла документа.
     */

    private String generateFileName(MultipartFile multiPart) {
        //Дата и время форматируются для имени файла документа.
        String formattedDate = LocalDateTime.now().
                format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));

        //Работа с именем файла документа. достаёться из multipart
        String originalFilename = Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
        //Удаление расширения файла документа из multipart.превращает имя.pdf в имя
        int dotIndex = originalFilename.indexOf(".");
        String name = (dotIndex != -1) ? originalFilename.substring(0, dotIndex) : originalFilename;
        // Добавление рандомных символов и цифр для имени файла документа.
        String random = UUID.randomUUID().toString().replace('-', '_');
        // Получение расширения файла документа из multipart.
        String contentType = Objects.requireNonNull(multiPart.getContentType()).replace("application/", ".");
        log.info("Name create {}", name + "_" + formattedDate + "_" + random + contentType);
        return name + "_" + formattedDate + "_" + random + contentType;
    }

    /**
     * Получение пути к файлам документов из String.
     *
     * @return путь к файлам документов Path.
     */
    private Path getFilePath() {
        return Paths.get(fileStorageLocation.replace(".", File.separator))
                .normalize()
                .toAbsolutePath();
    }
}
