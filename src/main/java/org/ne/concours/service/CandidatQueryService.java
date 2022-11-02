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

import org.ne.concours.domain.Candidat;
import org.ne.concours.domain.*; // for static metamodels
import org.ne.concours.repository.CandidatRepository;
import org.ne.concours.service.dto.CandidatCriteria;

/**
 * Service for executing complex queries for {@link Candidat} entities in the database.
 * The main input is a {@link CandidatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Candidat} or a {@link Page} of {@link Candidat} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CandidatQueryService extends QueryService<Candidat> {

    private final Logger log = LoggerFactory.getLogger(CandidatQueryService.class);

    private final CandidatRepository candidatRepository;

    public CandidatQueryService(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    /**
     * Return a {@link List} of {@link Candidat} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Candidat> findByCriteria(CandidatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Candidat> specification = createSpecification(criteria);
        return candidatRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Candidat} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Candidat> findByCriteria(CandidatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Candidat> specification = createSpecification(criteria);
        return candidatRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CandidatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Candidat> specification = createSpecification(criteria);
        return candidatRepository.count(specification);
    }

    /**
     * Function to convert {@link CandidatCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Candidat> createSpecification(CandidatCriteria criteria) {
        Specification<Candidat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Candidat_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Candidat_.nom));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), Candidat_.prenom));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), Candidat_.telephone));
            }
            if (criteria.getDateNaissance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateNaissance(), Candidat_.dateNaissance));
            }
            if (criteria.getLieuNaissance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLieuNaissance(), Candidat_.lieuNaissance));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), Candidat_.age));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), Candidat_.adresse));
            }
        }
        return specification;
    }
}
