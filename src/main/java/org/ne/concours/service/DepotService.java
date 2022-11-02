package org.ne.concours.service;

import org.ne.concours.domain.Depot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Depot}.
 */
public interface DepotService {

    /**
     * Save a depot.
     *
     * @param depot the entity to save.
     * @return the persisted entity.
     */
    Depot save(Depot depot);

    /**
     * Get all the depots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Depot> findAll(Pageable pageable);


    /**
     * Get the "id" depot.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Depot> findOne(Long id);

    /**
     * Delete the "id" depot.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
