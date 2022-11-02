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

import org.ne.concours.domain.DocumentAFournir;
import org.ne.concours.domain.*; // for static metamodels
import org.ne.concours.repository.DocumentAFournirRepository;
import org.ne.concours.service.dto.DocumentAFournirCriteria;

/**
 * Service for executing complex queries for {@link DocumentAFournir} entities in the database.
 * The main input is a {@link DocumentAFournirCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocumentAFournir} or a {@link Page} of {@link DocumentAFournir} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentAFournirQueryService extends QueryService<DocumentAFournir> {

    private final Logger log = LoggerFactory.getLogger(DocumentAFournirQueryService.class);

    private final DocumentAFournirRepository documentAFournirRepository;

    public DocumentAFournirQueryService(DocumentAFournirRepository documentAFournirRepository) {
        this.documentAFournirRepository = documentAFournirRepository;
    }

    /**
     * Return a {@link List} of {@link DocumentAFournir} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentAFournir> findByCriteria(DocumentAFournirCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DocumentAFournir> specification = createSpecification(criteria);
        return documentAFournirRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DocumentAFournir} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentAFournir> findByCriteria(DocumentAFournirCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DocumentAFournir> specification = createSpecification(criteria);
        return documentAFournirRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentAFournirCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DocumentAFournir> specification = createSpecification(criteria);
        return documentAFournirRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentAFournirCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DocumentAFournir> createSpecification(DocumentAFournirCriteria criteria) {
        Specification<DocumentAFournir> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DocumentAFournir_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), DocumentAFournir_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), DocumentAFournir_.libelle));
            }
        }
        return specification;
    }
}
