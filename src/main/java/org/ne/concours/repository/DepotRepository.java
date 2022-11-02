package org.ne.concours.repository;

import org.ne.concours.domain.Depot;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Depot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepotRepository extends JpaRepository<Depot, Long>, JpaSpecificationExecutor<Depot> {
}
