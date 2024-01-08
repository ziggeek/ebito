package com.ebito.cloud.reposytory;

import com.ebito.cloud.model.entity.DocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    @Query("select d from DocumentEntity d where d.clientId = :client_id" +
            " ORDER BY d.id DESC ")
    Page<DocumentEntity> findAllByClientId(@Param("client_id") String clientId, Pageable pageable);

}