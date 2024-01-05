package com.ebito.reference.repository;

import com.ebito.reference.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findAllByClientIdOrderByTimestamp(long clientId);
}
