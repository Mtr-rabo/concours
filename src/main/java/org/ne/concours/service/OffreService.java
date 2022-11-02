package org.ne.concours.service;

import org.ne.concours.domain.Offre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Offre}.
 */
public interface OffreService {

    /**
     * Save a offre.
     *
     * @param offre the entity to save.
     * @return the persisted entity.
     */
    Offre save(Offre offre);

    /**
     * Get all the offres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Offre> findAll(Pageable pageable);


    /**
     * Get the "id" offre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Offre> findOne(Long id);

    /**
     * Delete the "id" offre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
