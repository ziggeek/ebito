package com.ebito.cloud.service.Impl;

import com.ebito.cloud.exception.FileProcessingException;
import com.ebito.cloud.exception.InvalidUrlException;
import com.ebito.cloud.exception.ResourceAccessException;
import com.ebito.cloud.model.entity.DocumentEntity;
import com.ebito.cloud.properties.MinioProperties;
import com.ebito.cloud.service.DocumentService;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

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

        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new FileProcessingException("Document upload failed: "
                    + e.getMessage());
        }
        saveDocument(inputStream, file.getOriginalFilename());
        log.info("Document file {} uploaded successfully", file.getOriginalFilename());
        return new DocumentEntity(clientId, file.getOriginalFilename());
    }

    @Override
    public Resource download(final String name) {
        try {
            // Создать временный файл для хранения загруженного содержимого
            File tempFile = File.createTempFile("download", ".tmp");
            try (InputStream inputStream = loadDocument(name)) {
                Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new InvalidUrlException("Failed to copy document: " + e.getMessage());
            }
            Resource resource = new FileSystemResource(tempFile);
            // Создать Resource на основе временного файла
            log.info("Document file {} downloaded successfully", name);
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResourceAccessException(name + " not found or not available");
            }
            return resource;
        } catch (IOException e) {
            throw new FileProcessingException("Document download failed: " + e.getMessage());
        }
    }

    @Override
    public String downloadUrl(final String name) {
        String url = loadUrlDocument(name);
        Assert.notNull(url, "Document url must not be null");
        try {
        log.info("Document file {} downloaded successfully", name);

           return url;
        } catch (Exception e) {
            throw new InvalidUrlException(e.getMessage());
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
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .object(fileName)
                        .build());
    }

    @SneakyThrows
    private String loadUrlDocument(final String name) {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(minioProperties.getBucket())
                        .object(name)
                        .expiry(2, TimeUnit.HOURS)
                        .build());
    }

}
