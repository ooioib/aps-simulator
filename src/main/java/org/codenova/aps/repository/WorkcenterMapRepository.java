package org.codenova.aps.repository;

import org.codenova.aps.entity.Operation;
import org.codenova.aps.entity.WorkcenterMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkcenterMapRepository extends JpaRepository<WorkcenterMap, Integer> {
    List<WorkcenterMap> findByOperation(Operation operation);
}
