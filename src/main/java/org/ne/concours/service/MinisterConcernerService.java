package org.ne.concours.service;

import org.ne.concours.domain.MinisterConcerner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MinisterConcerner}.
 */
public interface MinisterConcernerService {

    /**
     * Save a ministerConcerner.
     *
     * @param ministerConcerner the entity to save.
     * @return the persisted entity.
     */
    MinisterConcerner save(MinisterConcerner ministerConcerner);

    /**
     * Get all the ministerConcerners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MinisterConcerner> findAll(Pageable pageable);


    /**
     * Get the "id" ministerConcerner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MinisterConcerner> findOne(Long id);

    /**
     * Delete the "id" ministerConcerner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
