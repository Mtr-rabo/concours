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

import org.ne.concours.domain.OffreDocument;
import org.ne.concours.domain.*; // for static metamodels
import org.ne.concours.repository.OffreDocumentRepository;
import org.ne.concours.service.dto.OffreDocumentCriteria;

/**
 * Service for executing complex queries for {@link OffreDocument} entities in the database.
 * The main input is a {@link OffreDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OffreDocument} or a {@link Page} of {@link OffreDocument} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OffreDocumentQueryService extends QueryService<OffreDocument> {

    private final Logger log = LoggerFactory.getLogger(OffreDocumentQueryService.class);

    private final OffreDocumentRepository offreDocumentRepository;

    public OffreDocumentQueryService(OffreDocumentRepository offreDocumentRepository) {
        this.offreDocumentRepository = offreDocumentRepository;
    }

    /**
     * Return a {@link List} of {@link OffreDocument} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OffreDocument> findByCriteria(OffreDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OffreDocument> specification = createSpecification(criteria);
        return offreDocumentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OffreDocument} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OffreDocument> findByCriteria(OffreDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OffreDocument> specification = createSpecification(criteria);
        return offreDocumentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OffreDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OffreDocument> specification = createSpecification(criteria);
        return offreDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link OffreDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OffreDocument> createSpecification(OffreDocumentCriteria criteria) {
        Specification<OffreDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OffreDocument_.id));
            }
            if (criteria.getOffreId() != null) {
                specification = specification.and(buildSpecification(criteria.getOffreId(),
                    root -> root.join(OffreDocument_.offre, JoinType.LEFT).get(Offre_.id)));
            }
            if (criteria.getDocumentAFournirId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentAFournirId(),
                    root -> root.join(OffreDocument_.documentAFournir, JoinType.LEFT).get(DocumentAFournir_.id)));
            }
        }
        return specification;
    }
}
