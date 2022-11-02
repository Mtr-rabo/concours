package org.ne.concours.repository;

import org.ne.concours.domain.MinisterConcerner;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MinisterConcerner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MinisterConcernerRepository extends JpaRepository<MinisterConcerner, Long>, JpaSpecificationExecutor<MinisterConcerner> {
}
