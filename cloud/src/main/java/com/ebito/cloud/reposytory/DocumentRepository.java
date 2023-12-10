package com.ebito.cloud.reposytory;

import com.ebito.cloud.model.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllByClientId(String clientId);
}