package org.ne.concours.repository;

import org.ne.concours.domain.EpreuveAconcourir;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EpreuveAconcourir entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EpreuveAconcourirRepository extends JpaRepository<EpreuveAconcourir, Long>, JpaSpecificationExecutor<EpreuveAconcourir> {
}
