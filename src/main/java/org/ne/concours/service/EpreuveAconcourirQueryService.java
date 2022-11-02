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

import org.ne.concours.domain.EpreuveAconcourir;
import org.ne.concours.domain.*; // for static metamodels
import org.ne.concours.repository.EpreuveAconcourirRepository;
import org.ne.concours.service.dto.EpreuveAconcourirCriteria;

/**
 * Service for executing complex queries for {@link EpreuveAconcourir} entities in the database.
 * The main input is a {@link EpreuveAconcourirCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EpreuveAconcourir} or a {@link Page} of {@link EpreuveAconcourir} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EpreuveAconcourirQueryService extends QueryService<EpreuveAconcourir> {

    private final Logger log = LoggerFactory.getLogger(EpreuveAconcourirQueryService.class);

    private final EpreuveAconcourirRepository epreuveAconcourirRepository;

    public EpreuveAconcourirQueryService(EpreuveAconcourirRepository epreuveAconcourirRepository) {
        this.epreuveAconcourirRepository = epreuveAconcourirRepository;
    }

    /**
     * Return a {@link List} of {@link EpreuveAconcourir} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EpreuveAconcourir> findByCriteria(EpreuveAconcourirCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EpreuveAconcourir> specification = createSpecification(criteria);
        return epreuveAconcourirRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EpreuveAconcourir} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EpreuveAconcourir> findByCriteria(EpreuveAconcourirCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EpreuveAconcourir> specification = createSpecification(criteria);
        return epreuveAconcourirRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EpreuveAconcourirCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EpreuveAconcourir> specification = createSpecification(criteria);
        return epreuveAconcourirRepository.count(specification);
    }

    /**
     * Function to convert {@link EpreuveAconcourirCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EpreuveAconcourir> createSpecification(EpreuveAconcourirCriteria criteria) {
        Specification<EpreuveAconcourir> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EpreuveAconcourir_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), EpreuveAconcourir_.nom));
            }
            if (criteria.getDuree() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuree(), EpreuveAconcourir_.duree));
            }
            if (criteria.getCoefficiant() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoefficiant(), EpreuveAconcourir_.coefficiant));
            }
            if (criteria.getNoteEleminatoire() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoteEleminatoire(), EpreuveAconcourir_.noteEleminatoire));
            }
            if (criteria.getOffreId() != null) {
                specification = specification.and(buildSpecification(criteria.getOffreId(),
                    root -> root.join(EpreuveAconcourir_.offre, JoinType.LEFT).get(Offre_.id)));
            }
        }
        return specification;
    }
}
