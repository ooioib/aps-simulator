package org.codenova.aps.repository;

import org.codenova.aps.entity.Workcenter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkcenterRepository extends JpaRepository<Workcenter, String> {
}
