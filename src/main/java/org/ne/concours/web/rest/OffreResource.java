package org.ne.concours.web.rest;

import org.ne.concours.domain.Offre;
import org.ne.concours.service.OffreService;
import org.ne.concours.web.rest.errors.BadRequestAlertException;
import org.ne.concours.service.dto.OffreCriteria;
import org.ne.concours.service.OffreQueryService;

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
 * REST controller for managing {@link org.ne.concours.domain.Offre}.
 */
@RestController
@RequestMapping("/api")
public class OffreResource {

    private final Logger log = LoggerFactory.getLogger(OffreResource.class);

    private static final String ENTITY_NAME = "offre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OffreService offreService;

    private final OffreQueryService offreQueryService;

    public OffreResource(OffreService offreService, OffreQueryService offreQueryService) {
        this.offreService = offreService;
        this.offreQueryService = offreQueryService;
    }

    /**
     * {@code POST  /offres} : Create a new offre.
     *
     * @param offre the offre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offre, or with status {@code 400 (Bad Request)} if the offre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offres")
    public ResponseEntity<Offre> createOffre(@RequestBody Offre offre) throws URISyntaxException {
        log.debug("REST request to save Offre : {}", offre);
        if (offre.getId() != null) {
            throw new BadRequestAlertException("A new offre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Offre result = offreService.save(offre);
        return ResponseEntity.created(new URI("/api/offres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offres} : Updates an existing offre.
     *
     * @param offre the offre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offre,
     * or with status {@code 400 (Bad Request)} if the offre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offres")
    public ResponseEntity<Offre> updateOffre(@RequestBody Offre offre) throws URISyntaxException {
        log.debug("REST request to update Offre : {}", offre);
        if (offre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Offre result = offreService.save(offre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offre.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /offres} : get all the offres.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offres in body.
     */
    @GetMapping("/offres")
    public ResponseEntity<List<Offre>> getAllOffres(OffreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Offres by criteria: {}", criteria);
        Page<Offre> page = offreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /offres/count} : count all the offres.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/offres/count")
    public ResponseEntity<Long> countOffres(OffreCriteria criteria) {
        log.debug("REST request to count Offres by criteria: {}", criteria);
        return ResponseEntity.ok().body(offreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /offres/:id} : get the "id" offre.
     *
     * @param id the id of the offre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offres/{id}")
    public ResponseEntity<Offre> getOffre(@PathVariable Long id) {
        log.debug("REST request to get Offre : {}", id);
        Optional<Offre> offre = offreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offre);
    }

    /**
     * {@code DELETE  /offres/:id} : delete the "id" offre.
     *
     * @param id the id of the offre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offres/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id) {
        log.debug("REST request to delete Offre : {}", id);
        offreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
