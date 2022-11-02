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

import org.ne.concours.domain.Fichier;
import org.ne.concours.domain.*; // for static metamodels
import org.ne.concours.repository.FichierRepository;
import org.ne.concours.service.dto.FichierCriteria;

/**
 * Service for executing complex queries for {@link Fichier} entities in the database.
 * The main input is a {@link FichierCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Fichier} or a {@link Page} of {@link Fichier} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FichierQueryService extends QueryService<Fichier> {

    private final Logger log = LoggerFactory.getLogger(FichierQueryService.class);

    private final FichierRepository fichierRepository;

    public FichierQueryService(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
    }

    /**
     * Return a {@link List} of {@link Fichier} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Fichier> findByCriteria(FichierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fichier> specification = createSpecification(criteria);
        return fichierRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Fichier} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Fichier> findByCriteria(FichierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fichier> specification = createSpecification(criteria);
        return fichierRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FichierCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fichier> specification = createSpecification(criteria);
        return fichierRepository.count(specification);
    }

    /**
     * Function to convert {@link FichierCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Fichier> createSpecification(FichierCriteria criteria) {
        Specification<Fichier> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Fichier_.id));
            }
            if (criteria.getNomFichier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomFichier(), Fichier_.nomFichier));
            }
            if (criteria.getDepotId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepotId(),
                    root -> root.join(Fichier_.depot, JoinType.LEFT).get(Depot_.id)));
            }
        }
        return specification;
    }
}
