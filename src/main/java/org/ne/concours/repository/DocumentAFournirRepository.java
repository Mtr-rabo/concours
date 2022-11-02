package org.ne.concours.repository;

import org.ne.concours.domain.DocumentAFournir;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DocumentAFournir entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentAFournirRepository extends JpaRepository<DocumentAFournir, Long>, JpaSpecificationExecutor<DocumentAFournir> {
}
