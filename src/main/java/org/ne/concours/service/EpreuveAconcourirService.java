package org.ne.concours.service;

import org.ne.concours.domain.EpreuveAconcourir;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EpreuveAconcourir}.
 */
public interface EpreuveAconcourirService {

    /**
     * Save a epreuveAconcourir.
     *
     * @param epreuveAconcourir the entity to save.
     * @return the persisted entity.
     */
    EpreuveAconcourir save(EpreuveAconcourir epreuveAconcourir);

    /**
     * Get all the epreuveAconcourirs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EpreuveAconcourir> findAll(Pageable pageable);


    /**
     * Get the "id" epreuveAconcourir.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EpreuveAconcourir> findOne(Long id);

    /**
     * Delete the "id" epreuveAconcourir.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
