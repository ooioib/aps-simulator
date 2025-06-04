package org.codenova.aps.repository;

import org.codenova.aps.entity.OperationRouting;
import org.codenova.aps.entity.Routing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRoutingRepository extends JpaRepository<OperationRouting, Long> {

}
