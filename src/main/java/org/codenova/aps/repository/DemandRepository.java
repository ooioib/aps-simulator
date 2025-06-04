package org.codenova.aps.repository;

import org.codenova.aps.entity.Demand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandRepository extends JpaRepository<Demand, Long> {

}
