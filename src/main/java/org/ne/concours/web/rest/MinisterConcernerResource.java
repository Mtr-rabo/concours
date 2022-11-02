package org.ne.concours.web.rest;

import org.ne.concours.domain.MinisterConcerner;
import org.ne.concours.service.MinisterConcernerService;
import org.ne.concours.web.rest.errors.BadRequestAlertException;
import org.ne.concours.service.dto.MinisterConcernerCriteria;
import org.ne.concours.service.MinisterConcernerQueryService;

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
 * REST controller for managing {@link org.ne.concours.domain.MinisterConcerner}.
 */
@RestController
@RequestMapping("/api")
public class MinisterConcernerResource {

    private final Logger log = LoggerFactory.getLogger(MinisterConcernerResource.class);

    private static final String ENTITY_NAME = "ministerConcerner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MinisterConcernerService ministerConcernerService;

    private final MinisterConcernerQueryService ministerConcernerQueryService;

    public MinisterConcernerResource(MinisterConcernerService ministerConcernerService, MinisterConcernerQueryService ministerConcernerQueryService) {
        this.ministerConcernerService = ministerConcernerService;
        this.ministerConcernerQueryService = ministerConcernerQueryService;
    }

    /**
     * {@code POST  /minister-concerners} : Create a new ministerConcerner.
     *
     * @param ministerConcerner the ministerConcerner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ministerConcerner, or with status {@code 400 (Bad Request)} if the ministerConcerner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/minister-concerners")
    public ResponseEntity<MinisterConcerner> createMinisterConcerner(@RequestBody MinisterConcerner ministerConcerner) throws URISyntaxException {
        log.debug("REST request to save MinisterConcerner : {}", ministerConcerner);
        if (ministerConcerner.getId() != null) {
            throw new BadRequestAlertException("A new ministerConcerner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MinisterConcerner result = ministerConcernerService.save(ministerConcerner);
        return ResponseEntity.created(new URI("/api/minister-concerners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /minister-concerners} : Updates an existing ministerConcerner.
     *
     * @param ministerConcerner the ministerConcerner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ministerConcerner,
     * or with status {@code 400 (Bad Request)} if the ministerConcerner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ministerConcerner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/minister-concerners")
    public ResponseEntity<MinisterConcerner> updateMinisterConcerner(@RequestBody MinisterConcerner ministerConcerner) throws URISyntaxException {
        log.debug("REST request to update MinisterConcerner : {}", ministerConcerner);
        if (ministerConcerner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MinisterConcerner result = ministerConcernerService.save(ministerConcerner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ministerConcerner.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /minister-concerners} : get all the ministerConcerners.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ministerConcerners in body.
     */
    @GetMapping("/minister-concerners")
    public ResponseEntity<List<MinisterConcerner>> getAllMinisterConcerners(MinisterConcernerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MinisterConcerners by criteria: {}", criteria);
        Page<MinisterConcerner> page = ministerConcernerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /minister-concerners/count} : count all the ministerConcerners.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/minister-concerners/count")
    public ResponseEntity<Long> countMinisterConcerners(MinisterConcernerCriteria criteria) {
        log.debug("REST request to count MinisterConcerners by criteria: {}", criteria);
        return ResponseEntity.ok().body(ministerConcernerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /minister-concerners/:id} : get the "id" ministerConcerner.
     *
     * @param id the id of the ministerConcerner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ministerConcerner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/minister-concerners/{id}")
    public ResponseEntity<MinisterConcerner> getMinisterConcerner(@PathVariable Long id) {
        log.debug("REST request to get MinisterConcerner : {}", id);
        Optional<MinisterConcerner> ministerConcerner = ministerConcernerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ministerConcerner);
    }

    /**
     * {@code DELETE  /minister-concerners/:id} : delete the "id" ministerConcerner.
     *
     * @param id the id of the ministerConcerner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/minister-concerners/{id}")
    public ResponseEntity<Void> deleteMinisterConcerner(@PathVariable Long id) {
        log.debug("REST request to delete MinisterConcerner : {}", id);
        ministerConcernerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
