package com.ebito.cloud.service;

import com.ebito.cloud.model.response.DocumentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    /**
     * Сохраняет всю нужную информацию в БД
     *
     * @param clientId идентификатор клиента, которому относится документ или документы. Используется String
     * @param file     документ или документы для загрузки. Используется MultipartFile.
     * @return объект PrintedGuids, который содержит идентификаторы документов, загруженных клиентом
     */
    DocumentResponse create(String clientId, MultipartFile file);

    /**
     * Возвращает список идентификаторов документов, загруженных клиентом.
     *
     * @param clientId идентификатор клиента. Используется String.
     * @return список объектов PrintedGuids, который содержит идентификаторы документов, загруженных клиентом.
     */
    Page<DocumentResponse> getDocumentReferences(String clientId, Pageable page);
}
