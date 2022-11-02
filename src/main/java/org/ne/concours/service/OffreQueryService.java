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

import org.ne.concours.domain.Offre;
import org.ne.concours.domain.*; // for static metamodels
import org.ne.concours.repository.OffreRepository;
import org.ne.concours.service.dto.OffreCriteria;

/**
 * Service for executing complex queries for {@link Offre} entities in the database.
 * The main input is a {@link OffreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Offre} or a {@link Page} of {@link Offre} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OffreQueryService extends QueryService<Offre> {

    private final Logger log = LoggerFactory.getLogger(OffreQueryService.class);

    private final OffreRepository offreRepository;

    public OffreQueryService(OffreRepository offreRepository) {
        this.offreRepository = offreRepository;
    }

    /**
     * Return a {@link List} of {@link Offre} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Offre> findByCriteria(OffreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Offre> specification = createSpecification(criteria);
        return offreRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Offre} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Offre> findByCriteria(OffreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Offre> specification = createSpecification(criteria);
        return offreRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OffreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Offre> specification = createSpecification(criteria);
        return offreRepository.count(specification);
    }

    /**
     * Function to convert {@link OffreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Offre> createSpecification(OffreCriteria criteria) {
        Specification<Offre> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Offre_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Offre_.code));
            }
            if (criteria.getNomPoste() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomPoste(), Offre_.nomPoste));
            }
            if (criteria.getDescrip() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescrip(), Offre_.descrip));
            }
            if (criteria.getDateOuverture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOuverture(), Offre_.dateOuverture));
            }
            if (criteria.getDateCloture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateCloture(), Offre_.dateCloture));
            }
            if (criteria.getIsArchive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsArchive(), Offre_.isArchive));
            }
            if (criteria.getDateConcours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateConcours(), Offre_.dateConcours));
            }
            if (criteria.getAgeLimite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAgeLimite(), Offre_.ageLimite));
            }
            if (criteria.getMinisterConcernerId() != null) {
                specification = specification.and(buildSpecification(criteria.getMinisterConcernerId(),
                    root -> root.join(Offre_.ministerConcerner, JoinType.LEFT).get(MinisterConcerner_.id)));
            }
            if (criteria.getCategrieId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategrieId(),
                    root -> root.join(Offre_.categrie, JoinType.LEFT).get(Categorie_.id)));
            }
            if (criteria.getOptionId() != null) {
                specification = specification.and(buildSpecification(criteria.getOptionId(),
                    root -> root.join(Offre_.option, JoinType.LEFT).get(Option_.id)));
            }
        }
        return specification;
    }
}
