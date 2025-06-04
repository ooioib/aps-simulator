package org.codenova.aps.repository;

import org.codenova.aps.entity.OperationRouting;
import org.codenova.aps.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, String> {
}
