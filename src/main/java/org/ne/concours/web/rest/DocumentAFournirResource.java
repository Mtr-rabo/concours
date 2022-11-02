package org.ne.concours.web.rest;

import org.ne.concours.domain.DocumentAFournir;
import org.ne.concours.service.DocumentAFournirService;
import org.ne.concours.web.rest.errors.BadRequestAlertException;
import org.ne.concours.service.dto.DocumentAFournirCriteria;
import org.ne.concours.service.DocumentAFournirQueryService;

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
 * REST controller for managing {@link org.ne.concours.domain.DocumentAFournir}.
 */
@RestController
@RequestMapping("/api")
public class DocumentAFournirResource {

    private final Logger log = LoggerFactory.getLogger(DocumentAFournirResource.class);

    private static final String ENTITY_NAME = "documentAFournir";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentAFournirService documentAFournirService;

    private final DocumentAFournirQueryService documentAFournirQueryService;

    public DocumentAFournirResource(DocumentAFournirService documentAFournirService, DocumentAFournirQueryService documentAFournirQueryService) {
        this.documentAFournirService = documentAFournirService;
        this.documentAFournirQueryService = documentAFournirQueryService;
    }

    /**
     * {@code POST  /document-a-fournirs} : Create a new documentAFournir.
     *
     * @param documentAFournir the documentAFournir to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentAFournir, or with status {@code 400 (Bad Request)} if the documentAFournir has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-a-fournirs")
    public ResponseEntity<DocumentAFournir> createDocumentAFournir(@RequestBody DocumentAFournir documentAFournir) throws URISyntaxException {
        log.debug("REST request to save DocumentAFournir : {}", documentAFournir);
        if (documentAFournir.getId() != null) {
            throw new BadRequestAlertException("A new documentAFournir cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentAFournir result = documentAFournirService.save(documentAFournir);
        return ResponseEntity.created(new URI("/api/document-a-fournirs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-a-fournirs} : Updates an existing documentAFournir.
     *
     * @param documentAFournir the documentAFournir to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentAFournir,
     * or with status {@code 400 (Bad Request)} if the documentAFournir is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentAFournir couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-a-fournirs")
    public ResponseEntity<DocumentAFournir> updateDocumentAFournir(@RequestBody DocumentAFournir documentAFournir) throws URISyntaxException {
        log.debug("REST request to update DocumentAFournir : {}", documentAFournir);
        if (documentAFournir.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentAFournir result = documentAFournirService.save(documentAFournir);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentAFournir.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-a-fournirs} : get all the documentAFournirs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentAFournirs in body.
     */
    @GetMapping("/document-a-fournirs")
    public ResponseEntity<List<DocumentAFournir>> getAllDocumentAFournirs(DocumentAFournirCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DocumentAFournirs by criteria: {}", criteria);
        Page<DocumentAFournir> page = documentAFournirQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-a-fournirs/count} : count all the documentAFournirs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/document-a-fournirs/count")
    public ResponseEntity<Long> countDocumentAFournirs(DocumentAFournirCriteria criteria) {
        log.debug("REST request to count DocumentAFournirs by criteria: {}", criteria);
        return ResponseEntity.ok().body(documentAFournirQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /document-a-fournirs/:id} : get the "id" documentAFournir.
     *
     * @param id the id of the documentAFournir to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentAFournir, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-a-fournirs/{id}")
    public ResponseEntity<DocumentAFournir> getDocumentAFournir(@PathVariable Long id) {
        log.debug("REST request to get DocumentAFournir : {}", id);
        Optional<DocumentAFournir> documentAFournir = documentAFournirService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentAFournir);
    }

    /**
     * {@code DELETE  /document-a-fournirs/:id} : delete the "id" documentAFournir.
     *
     * @param id the id of the documentAFournir to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-a-fournirs/{id}")
    public ResponseEntity<Void> deleteDocumentAFournir(@PathVariable Long id) {
        log.debug("REST request to delete DocumentAFournir : {}", id);
        documentAFournirService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
