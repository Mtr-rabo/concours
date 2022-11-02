package org.ne.concours.web.rest;

import org.ne.concours.ConcoursFonctionPubliqueApp;
import org.ne.concours.domain.OffreDocument;
import org.ne.concours.domain.Offre;
import org.ne.concours.domain.DocumentAFournir;
import org.ne.concours.repository.OffreDocumentRepository;
import org.ne.concours.service.OffreDocumentService;
import org.ne.concours.service.dto.OffreDocumentCriteria;
import org.ne.concours.service.OffreDocumentQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OffreDocumentResource} REST controller.
 */
@SpringBootTest(classes = ConcoursFonctionPubliqueApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OffreDocumentResourceIT {

    @Autowired
    private OffreDocumentRepository offreDocumentRepository;

    @Autowired
    private OffreDocumentService offreDocumentService;

    @Autowired
    private OffreDocumentQueryService offreDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOffreDocumentMockMvc;

    private OffreDocument offreDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OffreDocument createEntity(EntityManager em) {
        OffreDocument offreDocument = new OffreDocument();
        return offreDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OffreDocument createUpdatedEntity(EntityManager em) {
        OffreDocument offreDocument = new OffreDocument();
        return offreDocument;
    }

    @BeforeEach
    public void initTest() {
        offreDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffreDocument() throws Exception {
        int databaseSizeBeforeCreate = offreDocumentRepository.findAll().size();
        // Create the OffreDocument
        restOffreDocumentMockMvc.perform(post("/api/offre-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offreDocument)))
            .andExpect(status().isCreated());

        // Validate the OffreDocument in the database
        List<OffreDocument> offreDocumentList = offreDocumentRepository.findAll();
        assertThat(offreDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        OffreDocument testOffreDocument = offreDocumentList.get(offreDocumentList.size() - 1);
    }

    @Test
    @Transactional
    public void createOffreDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offreDocumentRepository.findAll().size();

        // Create the OffreDocument with an existing ID
        offreDocument.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffreDocumentMockMvc.perform(post("/api/offre-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offreDocument)))
            .andExpect(status().isBadRequest());

        // Validate the OffreDocument in the database
        List<OffreDocument> offreDocumentList = offreDocumentRepository.findAll();
        assertThat(offreDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOffreDocuments() throws Exception {
        // Initialize the database
        offreDocumentRepository.saveAndFlush(offreDocument);

        // Get all the offreDocumentList
        restOffreDocumentMockMvc.perform(get("/api/offre-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offreDocument.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getOffreDocument() throws Exception {
        // Initialize the database
        offreDocumentRepository.saveAndFlush(offreDocument);

        // Get the offreDocument
        restOffreDocumentMockMvc.perform(get("/api/offre-documents/{id}", offreDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offreDocument.getId().intValue()));
    }


    @Test
    @Transactional
    public void getOffreDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        offreDocumentRepository.saveAndFlush(offreDocument);

        Long id = offreDocument.getId();

        defaultOffreDocumentShouldBeFound("id.equals=" + id);
        defaultOffreDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultOffreDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOffreDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultOffreDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOffreDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOffreDocumentsByOffreIsEqualToSomething() throws Exception {
        // Initialize the database
        offreDocumentRepository.saveAndFlush(offreDocument);
        Offre offre = OffreResourceIT.createEntity(em);
        em.persist(offre);
        em.flush();
        offreDocument.setOffre(offre);
        offreDocumentRepository.saveAndFlush(offreDocument);
        Long offreId = offre.getId();

        // Get all the offreDocumentList where offre equals to offreId
        defaultOffreDocumentShouldBeFound("offreId.equals=" + offreId);

        // Get all the offreDocumentList where offre equals to offreId + 1
        defaultOffreDocumentShouldNotBeFound("offreId.equals=" + (offreId + 1));
    }


    @Test
    @Transactional
    public void getAllOffreDocumentsByDocumentAFournirIsEqualToSomething() throws Exception {
        // Initialize the database
        offreDocumentRepository.saveAndFlush(offreDocument);
        DocumentAFournir documentAFournir = DocumentAFournirResourceIT.createEntity(em);
        em.persist(documentAFournir);
        em.flush();
        offreDocument.setDocumentAFournir(documentAFournir);
        offreDocumentRepository.saveAndFlush(offreDocument);
        Long documentAFournirId = documentAFournir.getId();

        // Get all the offreDocumentList where documentAFournir equals to documentAFournirId
        defaultOffreDocumentShouldBeFound("documentAFournirId.equals=" + documentAFournirId);

        // Get all the offreDocumentList where documentAFournir equals to documentAFournirId + 1
        defaultOffreDocumentShouldNotBeFound("documentAFournirId.equals=" + (documentAFournirId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOffreDocumentShouldBeFound(String filter) throws Exception {
        restOffreDocumentMockMvc.perform(get("/api/offre-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offreDocument.getId().intValue())));

        // Check, that the count call also returns 1
        restOffreDocumentMockMvc.perform(get("/api/offre-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOffreDocumentShouldNotBeFound(String filter) throws Exception {
        restOffreDocumentMockMvc.perform(get("/api/offre-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOffreDocumentMockMvc.perform(get("/api/offre-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOffreDocument() throws Exception {
        // Get the offreDocument
        restOffreDocumentMockMvc.perform(get("/api/offre-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffreDocument() throws Exception {
        // Initialize the database
        offreDocumentService.save(offreDocument);

        int databaseSizeBeforeUpdate = offreDocumentRepository.findAll().size();

        // Update the offreDocument
        OffreDocument updatedOffreDocument = offreDocumentRepository.findById(offreDocument.getId()).get();
        // Disconnect from session so that the updates on updatedOffreDocument are not directly saved in db
        em.detach(updatedOffreDocument);

        restOffreDocumentMockMvc.perform(put("/api/offre-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffreDocument)))
            .andExpect(status().isOk());

        // Validate the OffreDocument in the database
        List<OffreDocument> offreDocumentList = offreDocumentRepository.findAll();
        assertThat(offreDocumentList).hasSize(databaseSizeBeforeUpdate);
        OffreDocument testOffreDocument = offreDocumentList.get(offreDocumentList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingOffreDocument() throws Exception {
        int databaseSizeBeforeUpdate = offreDocumentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffreDocumentMockMvc.perform(put("/api/offre-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offreDocument)))
            .andExpect(status().isBadRequest());

        // Validate the OffreDocument in the database
        List<OffreDocument> offreDocumentList = offreDocumentRepository.findAll();
        assertThat(offreDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOffreDocument() throws Exception {
        // Initialize the database
        offreDocumentService.save(offreDocument);

        int databaseSizeBeforeDelete = offreDocumentRepository.findAll().size();

        // Delete the offreDocument
        restOffreDocumentMockMvc.perform(delete("/api/offre-documents/{id}", offreDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OffreDocument> offreDocumentList = offreDocumentRepository.findAll();
        assertThat(offreDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
