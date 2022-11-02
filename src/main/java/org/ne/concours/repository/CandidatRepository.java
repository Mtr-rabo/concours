package org.ne.concours.repository;

import org.ne.concours.domain.Candidat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Candidat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long>, JpaSpecificationExecutor<Candidat> {
}
