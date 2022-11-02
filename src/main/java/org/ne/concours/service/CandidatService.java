package org.ne.concours.service;

import org.ne.concours.domain.Candidat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Candidat}.
 */
public interface CandidatService {

    /**
     * Save a candidat.
     *
     * @param candidat the entity to save.
     * @return the persisted entity.
     */
    Candidat save(Candidat candidat);

    /**
     * Get all the candidats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Candidat> findAll(Pageable pageable);


    /**
     * Get the "id" candidat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Candidat> findOne(Long id);

    /**
     * Delete the "id" candidat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
