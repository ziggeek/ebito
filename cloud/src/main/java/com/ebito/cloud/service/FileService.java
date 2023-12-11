package com.ebito.cloud.service;

import com.ebito.cloud.model.entity.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * Сохраняет документ в файловой системе
     *
     * @param doc документ
     * @return PrintedGuids
     */
    Document saveDoc(MultipartFile doc, String clientId);

    /**
     * Загружает документ из файловой системы по названию
     *
     * @param name документ в файловой системе
     * @return resource
     */
    Resource getFileByName(String name);
}
