package org.ne.concours.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.ne.concours.domain.MinisterConcerner;
import org.ne.concours.domain.*; // for static metamodels
import org.ne.concours.repository.MinisterConcernerRepository;
import org.ne.concours.service.dto.MinisterConcernerCriteria;

/**
 * Service for executing complex queries for {@link MinisterConcerner} entities in the database.
 * The main input is a {@link MinisterConcernerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MinisterConcerner} or a {@link Page} of {@link MinisterConcerner} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MinisterConcernerQueryService extends QueryService<MinisterConcerner> {

    private final Logger log = LoggerFactory.getLogger(MinisterConcernerQueryService.class);

    private final MinisterConcernerRepository ministerConcernerRepository;

    public MinisterConcernerQueryService(MinisterConcernerRepository ministerConcernerRepository) {
        this.ministerConcernerRepository = ministerConcernerRepository;
    }

    /**
     * Return a {@link List} of {@link MinisterConcerner} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MinisterConcerner> findByCriteria(MinisterConcernerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MinisterConcerner> specification = createSpecification(criteria);
        return ministerConcernerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MinisterConcerner} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MinisterConcerner> findByCriteria(MinisterConcernerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MinisterConcerner> specification = createSpecification(criteria);
        return ministerConcernerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MinisterConcernerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MinisterConcerner> specification = createSpecification(criteria);
        return ministerConcernerRepository.count(specification);
    }

    /**
     * Function to convert {@link MinisterConcernerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MinisterConcerner> createSpecification(MinisterConcernerCriteria criteria) {
        Specification<MinisterConcerner> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MinisterConcerner_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), MinisterConcerner_.nom));
            }
            if (criteria.getDomaine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDomaine(), MinisterConcerner_.domaine));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MinisterConcerner_.description));
            }
        }
        return specification;
    }
}
