package org.ne.concours.service;

import org.ne.concours.domain.OffreDocument;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OffreDocument}.
 */
public interface OffreDocumentService {

    /**
     * Save a offreDocument.
     *
     * @param offreDocument the entity to save.
     * @return the persisted entity.
     */
    OffreDocument save(OffreDocument offreDocument);

    /**
     * Get all the offreDocuments.
     *
     * @return the list of entities.
     */
    List<OffreDocument> findAll();


    /**
     * Get the "id" offreDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OffreDocument> findOne(Long id);

    /**
     * Delete the "id" offreDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
