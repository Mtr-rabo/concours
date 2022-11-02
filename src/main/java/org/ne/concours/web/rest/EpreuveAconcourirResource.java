package org.ne.concours.web.rest;

import org.ne.concours.domain.EpreuveAconcourir;
import org.ne.concours.service.EpreuveAconcourirService;
import org.ne.concours.web.rest.errors.BadRequestAlertException;
import org.ne.concours.service.dto.EpreuveAconcourirCriteria;
import org.ne.concours.service.EpreuveAconcourirQueryService;

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
 * REST controller for managing {@link org.ne.concours.domain.EpreuveAconcourir}.
 */
@RestController
@RequestMapping("/api")
public class EpreuveAconcourirResource {

    private final Logger log = LoggerFactory.getLogger(EpreuveAconcourirResource.class);

    private static final String ENTITY_NAME = "epreuveAconcourir";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EpreuveAconcourirService epreuveAconcourirService;

    private final EpreuveAconcourirQueryService epreuveAconcourirQueryService;

    public EpreuveAconcourirResource(EpreuveAconcourirService epreuveAconcourirService, EpreuveAconcourirQueryService epreuveAconcourirQueryService) {
        this.epreuveAconcourirService = epreuveAconcourirService;
        this.epreuveAconcourirQueryService = epreuveAconcourirQueryService;
    }

    /**
     * {@code POST  /epreuve-aconcourirs} : Create a new epreuveAconcourir.
     *
     * @param epreuveAconcourir the epreuveAconcourir to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new epreuveAconcourir, or with status {@code 400 (Bad Request)} if the epreuveAconcourir has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/epreuve-aconcourirs")
    public ResponseEntity<EpreuveAconcourir> createEpreuveAconcourir(@RequestBody EpreuveAconcourir epreuveAconcourir) throws URISyntaxException {
        log.debug("REST request to save EpreuveAconcourir : {}", epreuveAconcourir);
        if (epreuveAconcourir.getId() != null) {
            throw new BadRequestAlertException("A new epreuveAconcourir cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EpreuveAconcourir result = epreuveAconcourirService.save(epreuveAconcourir);
        return ResponseEntity.created(new URI("/api/epreuve-aconcourirs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /epreuve-aconcourirs} : Updates an existing epreuveAconcourir.
     *
     * @param epreuveAconcourir the epreuveAconcourir to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated epreuveAconcourir,
     * or with status {@code 400 (Bad Request)} if the epreuveAconcourir is not valid,
     * or with status {@code 500 (Internal Server Error)} if the epreuveAconcourir couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/epreuve-aconcourirs")
    public ResponseEntity<EpreuveAconcourir> updateEpreuveAconcourir(@RequestBody EpreuveAconcourir epreuveAconcourir) throws URISyntaxException {
        log.debug("REST request to update EpreuveAconcourir : {}", epreuveAconcourir);
        if (epreuveAconcourir.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EpreuveAconcourir result = epreuveAconcourirService.save(epreuveAconcourir);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, epreuveAconcourir.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /epreuve-aconcourirs} : get all the epreuveAconcourirs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of epreuveAconcourirs in body.
     */
    @GetMapping("/epreuve-aconcourirs")
    public ResponseEntity<List<EpreuveAconcourir>> getAllEpreuveAconcourirs(EpreuveAconcourirCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EpreuveAconcourirs by criteria: {}", criteria);
        Page<EpreuveAconcourir> page = epreuveAconcourirQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /epreuve-aconcourirs/count} : count all the epreuveAconcourirs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/epreuve-aconcourirs/count")
    public ResponseEntity<Long> countEpreuveAconcourirs(EpreuveAconcourirCriteria criteria) {
        log.debug("REST request to count EpreuveAconcourirs by criteria: {}", criteria);
        return ResponseEntity.ok().body(epreuveAconcourirQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /epreuve-aconcourirs/:id} : get the "id" epreuveAconcourir.
     *
     * @param id the id of the epreuveAconcourir to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the epreuveAconcourir, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/epreuve-aconcourirs/{id}")
    public ResponseEntity<EpreuveAconcourir> getEpreuveAconcourir(@PathVariable Long id) {
        log.debug("REST request to get EpreuveAconcourir : {}", id);
        Optional<EpreuveAconcourir> epreuveAconcourir = epreuveAconcourirService.findOne(id);
        return ResponseUtil.wrapOrNotFound(epreuveAconcourir);
    }

    /**
     * {@code DELETE  /epreuve-aconcourirs/:id} : delete the "id" epreuveAconcourir.
     *
     * @param id the id of the epreuveAconcourir to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/epreuve-aconcourirs/{id}")
    public ResponseEntity<Void> deleteEpreuveAconcourir(@PathVariable Long id) {
        log.debug("REST request to delete EpreuveAconcourir : {}", id);
        epreuveAconcourirService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
