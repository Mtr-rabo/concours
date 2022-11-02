package org.ne.concours.web.rest;

import org.ne.concours.domain.Candidat;
import org.ne.concours.service.CandidatService;
import org.ne.concours.web.rest.errors.BadRequestAlertException;
import org.ne.concours.service.dto.CandidatCriteria;
import org.ne.concours.service.CandidatQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.ne.concours.domain.Candidat}.
 */
@RestController
@RequestMapping("/api")
public class CandidatResource {

    private final Logger log = LoggerFactory.getLogger(CandidatResource.class);

    private static final String ENTITY_NAME = "candidat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidatService candidatService;

    private final CandidatQueryService candidatQueryService;

    public CandidatResource(CandidatService candidatService, CandidatQueryService candidatQueryService) {
        this.candidatService = candidatService;
        this.candidatQueryService = candidatQueryService;
    }

    /**
     * {@code POST  /candidats} : Create a new candidat.
     *
     * @param candidat the candidat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidat, or with status {@code 400 (Bad Request)} if the candidat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidats")
    public ResponseEntity<Candidat> createCandidat(@RequestBody Candidat candidat) throws URISyntaxException {
        log.debug("REST request to save Candidat : {}", candidat);
        if (candidat.getId() != null) {
            throw new BadRequestAlertException("A new candidat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Candidat result = candidatService.save(candidat);
        return ResponseEntity.created(new URI("/api/candidats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /candidats} : Updates an existing candidat.
     *
     * @param candidat the candidat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidat,
     * or with status {@code 400 (Bad Request)} if the candidat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidats")
    public ResponseEntity<Candidat> updateCandidat(@RequestBody Candidat candidat) throws URISyntaxException {
        log.debug("REST request to update Candidat : {}", candidat);
        if (candidat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Candidat result = candidatService.save(candidat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /candidats} : get all the candidats.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidats in body.
     */
    @GetMapping("/candidats")
    public ResponseEntity<List<Candidat>> getAllCandidats(CandidatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Candidats by criteria: {}", criteria);
        Page<Candidat> page = candidatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /candidats/count} : count all the candidats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/candidats/count")
    public ResponseEntity<Long> countCandidats(CandidatCriteria criteria) {
        log.debug("REST request to count Candidats by criteria: {}", criteria);
        return ResponseEntity.ok().body(candidatQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /candidats/:id} : get the "id" candidat.
     *
     * @param id the id of the candidat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidats/{id}")
    public ResponseEntity<Candidat> getCandidat(@PathVariable Long id) {
        log.debug("REST request to get Candidat : {}", id);
        Optional<Candidat> candidat = candidatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidat);
    }

    /**
     * {@code DELETE  /candidats/:id} : delete the "id" candidat.
     *
     * @param id the id of the candidat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/candidats/{id}")
    public ResponseEntity<Void> deleteCandidat(@PathVariable Long id) {
        log.debug("REST request to delete Candidat : {}", id);
        candidatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
