package com.ebito.cloud.service;

import com.ebito.cloud.model.entity.DocumentEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    /**
     * Сохраняет документ в хранилище
     *
     * @param doc документ
     * @return PrintedGuids
     */

    DocumentEntity upload(MultipartFile doc, String clientId);
    /**
     * Загружает документ из файловой системы по названию
     *
     * @param name документ в файловой системе
     * @return resource
     */
    Resource download(String name);

    /**
     * Загружает документ из файловой системы по названию
     *
     * @param name документ в файловой системе для загрузки из файловой системы по названию
     * @return url для загрузки документа из файловой системы по названию
     */
    String downloadUrl(final String name);
}
