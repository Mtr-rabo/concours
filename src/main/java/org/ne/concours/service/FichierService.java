package org.ne.concours.service;

import org.ne.concours.domain.Fichier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Fichier}.
 */
public interface FichierService {

    /**
     * Save a fichier.
     *
     * @param fichier the entity to save.
     * @return the persisted entity.
     */
    Fichier save(Fichier fichier);

    /**
     * Get all the fichiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Fichier> findAll(Pageable pageable);


    /**
     * Get the "id" fichier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Fichier> findOne(Long id);

    /**
     * Delete the "id" fichier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
