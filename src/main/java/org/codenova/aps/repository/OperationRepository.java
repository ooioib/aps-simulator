package org.codenova.aps.repository;

import org.codenova.aps.entity.Demand;
import org.codenova.aps.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, String> {
}
