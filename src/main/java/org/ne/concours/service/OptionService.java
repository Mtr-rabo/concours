package org.ne.concours.service;

import org.ne.concours.domain.Option;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Option}.
 */
public interface OptionService {

    /**
     * Save a option.
     *
     * @param option the entity to save.
     * @return the persisted entity.
     */
    Option save(Option option);

    /**
     * Get all the options.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Option> findAll(Pageable pageable);


    /**
     * Get the "id" option.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Option> findOne(Long id);

    /**
     * Delete the "id" option.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
