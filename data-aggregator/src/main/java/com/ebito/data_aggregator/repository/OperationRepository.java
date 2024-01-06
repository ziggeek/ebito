package com.ebito.data_aggregator.repository;

import com.ebito.data_aggregator.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findAllByClientIdOrderByTimestamp(long clientId);
}
