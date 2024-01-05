package com.ebito.cloud.service.Impl;

import com.ebito.cloud.exception.FileProcessingException;
import com.ebito.cloud.exception.ResourceAccessException;
import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.properties.MinioProperties;
import com.ebito.cloud.service.DocumentService;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public DocumentEntity upload(final MultipartFile file, final String clientId) {
        try {
            //Создание корзины для хранения файлов.
            createBucket();
        } catch (Exception e) {
            throw new FileProcessingException("Document upload failed: "
                    + e.getMessage());
        }
        Assert.notNull(file, "Document must not be null");

        String fileName = generateFileName(file);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new FileProcessingException("Document upload failed: "
                    + e.getMessage());
        }
        saveDocument(inputStream, fileName);
        log.info("Document file {} uploaded successfully", fileName);
        return new DocumentEntity(clientId, file.getOriginalFilename(), fileName);
    }

    @Override
    public Resource download(final String name) {
        try {
            // Создать временный файл для хранения загруженного содержимого
            File tempFile = File.createTempFile("download", ".tmp");
            Files.copy(loadDocument(name), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Resource resource = new FileSystemResource(tempFile);
            // Создать Resource на основе временного файла
            log.info("Document file {} downloaded successfully", name);
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResourceAccessException(name + " not found or not available ");
            }
            return resource;
        } catch (Exception e) {
            throw new FileProcessingException("Document download failed: " + e.getMessage());
        }


    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateFileName(final MultipartFile file) {
        //Дата и время форматируются для имени файла документа.
        String formattedDate = LocalDateTime.now().
                format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));

        //Работа с именем файла документа. достаёться из multipart
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
        //Удаление расширения файла документа из multipart.превращает имя.pdf в имя
        int dotIndex = originalFilename.indexOf(".");
        String name = originalFilename.substring(0, dotIndex);
        // Добавление рандомных символов и цифр для имени файла документа.
        String random = UUID.randomUUID().toString().replace('-', '_');
        // Получение расширения файла документа из multipart.
        String contentType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        //  String contentType = Objects.requireNonNull(file.getContentType()).replace("application/", ".");
        log.info("Name create {}", name + "_" + formattedDate + "_" + random + contentType);
        return name + "_" + formattedDate + "_" + random + "." + contentType;
    }

    @SneakyThrows
    private void saveDocument(final InputStream inputStream,
                              final String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }

    @SneakyThrows
    private InputStream loadDocument(final String fileName) {
        return minioClient.getObject(GetObjectArgs
                .builder()
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());

    }

}
