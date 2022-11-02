package org.ne.concours.repository;

import org.ne.concours.domain.OffreDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OffreDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OffreDocumentRepository extends JpaRepository<OffreDocument, Long>, JpaSpecificationExecutor<OffreDocument> {
}
