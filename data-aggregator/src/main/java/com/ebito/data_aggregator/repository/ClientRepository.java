package com.ebito.data_aggregator.repository;

import com.ebito.data_aggregator.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
