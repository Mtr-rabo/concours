package org.ne.concours.web.rest;

import org.ne.concours.domain.OffreDocument;
import org.ne.concours.service.OffreDocumentService;
import org.ne.concours.web.rest.errors.BadRequestAlertException;
import org.ne.concours.service.dto.OffreDocumentCriteria;
import org.ne.concours.service.OffreDocumentQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.ne.concours.domain.OffreDocument}.
 */
@RestController
@RequestMapping("/api")
public class OffreDocumentResource {

    private final Logger log = LoggerFactory.getLogger(OffreDocumentResource.class);

    private static final String ENTITY_NAME = "offreDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OffreDocumentService offreDocumentService;

    private final OffreDocumentQueryService offreDocumentQueryService;

    public OffreDocumentResource(OffreDocumentService offreDocumentService, OffreDocumentQueryService offreDocumentQueryService) {
        this.offreDocumentService = offreDocumentService;
        this.offreDocumentQueryService = offreDocumentQueryService;
    }

    /**
     * {@code POST  /offre-documents} : Create a new offreDocument.
     *
     * @param offreDocument the offreDocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offreDocument, or with status {@code 400 (Bad Request)} if the offreDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offre-documents")
    public ResponseEntity<OffreDocument> createOffreDocument(@RequestBody OffreDocument offreDocument) throws URISyntaxException {
        log.debug("REST request to save OffreDocument : {}", offreDocument);
        if (offreDocument.getId() != null) {
            throw new BadRequestAlertException("A new offreDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OffreDocument result = offreDocumentService.save(offreDocument);
        return ResponseEntity.created(new URI("/api/offre-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offre-documents} : Updates an existing offreDocument.
     *
     * @param offreDocument the offreDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offreDocument,
     * or with status {@code 400 (Bad Request)} if the offreDocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offreDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offre-documents")
    public ResponseEntity<OffreDocument> updateOffreDocument(@RequestBody OffreDocument offreDocument) throws URISyntaxException {
        log.debug("REST request to update OffreDocument : {}", offreDocument);
        if (offreDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OffreDocument result = offreDocumentService.save(offreDocument);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offreDocument.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /offre-documents} : get all the offreDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offreDocuments in body.
     */
    @GetMapping("/offre-documents")
    public ResponseEntity<List<OffreDocument>> getAllOffreDocuments(OffreDocumentCriteria criteria) {
        log.debug("REST request to get OffreDocuments by criteria: {}", criteria);
        List<OffreDocument> entityList = offreDocumentQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /offre-documents/count} : count all the offreDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/offre-documents/count")
    public ResponseEntity<Long> countOffreDocuments(OffreDocumentCriteria criteria) {
        log.debug("REST request to count OffreDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(offreDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /offre-documents/:id} : get the "id" offreDocument.
     *
     * @param id the id of the offreDocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offreDocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offre-documents/{id}")
    public ResponseEntity<OffreDocument> getOffreDocument(@PathVariable Long id) {
        log.debug("REST request to get OffreDocument : {}", id);
        Optional<OffreDocument> offreDocument = offreDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offreDocument);
    }

    /**
     * {@code DELETE  /offre-documents/:id} : delete the "id" offreDocument.
     *
     * @param id the id of the offreDocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offre-documents/{id}")
    public ResponseEntity<Void> deleteOffreDocument(@PathVariable Long id) {
        log.debug("REST request to delete OffreDocument : {}", id);
        offreDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
