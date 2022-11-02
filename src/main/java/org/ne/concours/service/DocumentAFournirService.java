package org.ne.concours.service;

import org.ne.concours.domain.DocumentAFournir;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DocumentAFournir}.
 */
public interface DocumentAFournirService {

    /**
     * Save a documentAFournir.
     *
     * @param documentAFournir the entity to save.
     * @return the persisted entity.
     */
    DocumentAFournir save(DocumentAFournir documentAFournir);

    /**
     * Get all the documentAFournirs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentAFournir> findAll(Pageable pageable);


    /**
     * Get the "id" documentAFournir.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentAFournir> findOne(Long id);

    /**
     * Delete the "id" documentAFournir.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
