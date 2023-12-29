package com.ebito.cloud.reposytory;

import com.ebito.cloud.model.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    List<DocumentEntity> findAllByClientId(String clientId);
}