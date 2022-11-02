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

import org.ne.concours.domain.Depot;
import org.ne.concours.domain.*; // for static metamodels
import org.ne.concours.repository.DepotRepository;
import org.ne.concours.service.dto.DepotCriteria;

/**
 * Service for executing complex queries for {@link Depot} entities in the database.
 * The main input is a {@link DepotCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Depot} or a {@link Page} of {@link Depot} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepotQueryService extends QueryService<Depot> {

    private final Logger log = LoggerFactory.getLogger(DepotQueryService.class);

    private final DepotRepository depotRepository;

    public DepotQueryService(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    /**
     * Return a {@link List} of {@link Depot} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Depot> findByCriteria(DepotCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Depot> specification = createSpecification(criteria);
        return depotRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Depot} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Depot> findByCriteria(DepotCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Depot> specification = createSpecification(criteria);
        return depotRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepotCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Depot> specification = createSpecification(criteria);
        return depotRepository.count(specification);
    }

    /**
     * Function to convert {@link DepotCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Depot> createSpecification(DepotCriteria criteria) {
        Specification<Depot> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Depot_.id));
            }
            if (criteria.getDateDepot() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDepot(), Depot_.dateDepot));
            }
            if (criteria.getIsArchive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsArchive(), Depot_.isArchive));
            }
            if (criteria.getIsAcepte() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAcepte(), Depot_.isAcepte));
            }
            if (criteria.getOffreId() != null) {
                specification = specification.and(buildSpecification(criteria.getOffreId(),
                    root -> root.join(Depot_.offre, JoinType.LEFT).get(Offre_.id)));
            }
            if (criteria.getCandidatId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidatId(),
                    root -> root.join(Depot_.candidat, JoinType.LEFT).get(Candidat_.id)));
            }
        }
        return specification;
    }
}
