package com.ebito.cloud.service.Impl;

import com.ebito.cloud.model.entity.Document;
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
    public Document saveDoc(MultipartFile file, String clientId) {
        String fileName = generateFileName(file);
        try {
            createDirectories(getFilePath());
            file.transferTo(new File(getFilePath() + File.separator + fileName));

            return new Document(clientId, file.getOriginalFilename(), fileName);
        } catch (IOException e) {
            log.error("Ошибка при сохранении файла документа:{}", file.getName());
            return null;
        }
    }

    @Override
    public Resource getFileByName(String name) {
        Assert.hasText(name, "Имя файла документа не может быть пустой");
        try {
            Path filePath = getFilePath().resolve(name);
            Resource resource = new UrlResource(filePath.toUri());
            Assert.isTrue(resource.exists()||resource.isReadable(),
                    "Файл не найден или не доступен");
            return resource;
        } catch (MalformedURLException ex) {
            log.error("Ошибка при получении файла по ссылке: {}", name);
            return null;
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

        //Работа с именем файла документа достаёться из multipart
        String originalFilename = Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
        //Удаление расширения файла документа из multipart.
       int dotIndex = originalFilename.indexOf(".");
        String name = (dotIndex != -1) ? originalFilename.substring(0, dotIndex) : originalFilename;
        // Добавление рандомных символов и цифр для имени файла документа.
        String random = UUID.randomUUID().toString().replace('-', '_');
        // Получение расширения файла документа из multipart.
        String contentType = Objects.requireNonNull(multiPart.getContentType()).replace("application/", ".");

        return name + "_" + formattedDate + "_" + random + contentType;
    }

    /**
     * Получение пути к файлам документов из String.
     *
     * @return путь к файлам документов Path.
     */
    private Path getFilePath() {
        return Paths.get(fileStorageLocation.replace(".",File.separator))
                .normalize()
                .toAbsolutePath();
    }
}
