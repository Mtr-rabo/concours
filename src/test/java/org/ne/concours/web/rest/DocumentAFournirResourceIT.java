package org.ne.concours.web.rest;

import org.ne.concours.ConcoursFonctionPubliqueApp;
import org.ne.concours.domain.DocumentAFournir;
import org.ne.concours.repository.DocumentAFournirRepository;
import org.ne.concours.service.DocumentAFournirService;
import org.ne.concours.service.dto.DocumentAFournirCriteria;
import org.ne.concours.service.DocumentAFournirQueryService;

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
 * Integration tests for the {@link DocumentAFournirResource} REST controller.
 */
@SpringBootTest(classes = ConcoursFonctionPubliqueApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DocumentAFournirResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private DocumentAFournirRepository documentAFournirRepository;

    @Autowired
    private DocumentAFournirService documentAFournirService;

    @Autowired
    private DocumentAFournirQueryService documentAFournirQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentAFournirMockMvc;

    private DocumentAFournir documentAFournir;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentAFournir createEntity(EntityManager em) {
        DocumentAFournir documentAFournir = new DocumentAFournir()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return documentAFournir;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentAFournir createUpdatedEntity(EntityManager em) {
        DocumentAFournir documentAFournir = new DocumentAFournir()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return documentAFournir;
    }

    @BeforeEach
    public void initTest() {
        documentAFournir = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentAFournir() throws Exception {
        int databaseSizeBeforeCreate = documentAFournirRepository.findAll().size();
        // Create the DocumentAFournir
        restDocumentAFournirMockMvc.perform(post("/api/document-a-fournirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentAFournir)))
            .andExpect(status().isCreated());

        // Validate the DocumentAFournir in the database
        List<DocumentAFournir> documentAFournirList = documentAFournirRepository.findAll();
        assertThat(documentAFournirList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentAFournir testDocumentAFournir = documentAFournirList.get(documentAFournirList.size() - 1);
        assertThat(testDocumentAFournir.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDocumentAFournir.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createDocumentAFournirWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentAFournirRepository.findAll().size();

        // Create the DocumentAFournir with an existing ID
        documentAFournir.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentAFournirMockMvc.perform(post("/api/document-a-fournirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentAFournir)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentAFournir in the database
        List<DocumentAFournir> documentAFournirList = documentAFournirRepository.findAll();
        assertThat(documentAFournirList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocumentAFournirs() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList
        restDocumentAFournirMockMvc.perform(get("/api/document-a-fournirs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentAFournir.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getDocumentAFournir() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get the documentAFournir
        restDocumentAFournirMockMvc.perform(get("/api/document-a-fournirs/{id}", documentAFournir.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentAFournir.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }


    @Test
    @Transactional
    public void getDocumentAFournirsByIdFiltering() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        Long id = documentAFournir.getId();

        defaultDocumentAFournirShouldBeFound("id.equals=" + id);
        defaultDocumentAFournirShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentAFournirShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentAFournirShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentAFournirShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentAFournirShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDocumentAFournirsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where code equals to DEFAULT_CODE
        defaultDocumentAFournirShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the documentAFournirList where code equals to UPDATED_CODE
        defaultDocumentAFournirShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDocumentAFournirsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where code not equals to DEFAULT_CODE
        defaultDocumentAFournirShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the documentAFournirList where code not equals to UPDATED_CODE
        defaultDocumentAFournirShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDocumentAFournirsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where code in DEFAULT_CODE or UPDATED_CODE
        defaultDocumentAFournirShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the documentAFournirList where code equals to UPDATED_CODE
        defaultDocumentAFournirShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDocumentAFournirsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where code is not null
        defaultDocumentAFournirShouldBeFound("code.specified=true");

        // Get all the documentAFournirList where code is null
        defaultDocumentAFournirShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentAFournirsByCodeContainsSomething() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where code contains DEFAULT_CODE
        defaultDocumentAFournirShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the documentAFournirList where code contains UPDATED_CODE
        defaultDocumentAFournirShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDocumentAFournirsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where code does not contain DEFAULT_CODE
        defaultDocumentAFournirShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the documentAFournirList where code does not contain UPDATED_CODE
        defaultDocumentAFournirShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllDocumentAFournirsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where libelle equals to DEFAULT_LIBELLE
        defaultDocumentAFournirShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the documentAFournirList where libelle equals to UPDATED_LIBELLE
        defaultDocumentAFournirShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllDocumentAFournirsByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where libelle not equals to DEFAULT_LIBELLE
        defaultDocumentAFournirShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the documentAFournirList where libelle not equals to UPDATED_LIBELLE
        defaultDocumentAFournirShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllDocumentAFournirsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultDocumentAFournirShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the documentAFournirList where libelle equals to UPDATED_LIBELLE
        defaultDocumentAFournirShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllDocumentAFournirsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where libelle is not null
        defaultDocumentAFournirShouldBeFound("libelle.specified=true");

        // Get all the documentAFournirList where libelle is null
        defaultDocumentAFournirShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentAFournirsByLibelleContainsSomething() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where libelle contains DEFAULT_LIBELLE
        defaultDocumentAFournirShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the documentAFournirList where libelle contains UPDATED_LIBELLE
        defaultDocumentAFournirShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllDocumentAFournirsByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        documentAFournirRepository.saveAndFlush(documentAFournir);

        // Get all the documentAFournirList where libelle does not contain DEFAULT_LIBELLE
        defaultDocumentAFournirShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the documentAFournirList where libelle does not contain UPDATED_LIBELLE
        defaultDocumentAFournirShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentAFournirShouldBeFound(String filter) throws Exception {
        restDocumentAFournirMockMvc.perform(get("/api/document-a-fournirs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentAFournir.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restDocumentAFournirMockMvc.perform(get("/api/document-a-fournirs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentAFournirShouldNotBeFound(String filter) throws Exception {
        restDocumentAFournirMockMvc.perform(get("/api/document-a-fournirs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentAFournirMockMvc.perform(get("/api/document-a-fournirs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentAFournir() throws Exception {
        // Get the documentAFournir
        restDocumentAFournirMockMvc.perform(get("/api/document-a-fournirs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentAFournir() throws Exception {
        // Initialize the database
        documentAFournirService.save(documentAFournir);

        int databaseSizeBeforeUpdate = documentAFournirRepository.findAll().size();

        // Update the documentAFournir
        DocumentAFournir updatedDocumentAFournir = documentAFournirRepository.findById(documentAFournir.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentAFournir are not directly saved in db
        em.detach(updatedDocumentAFournir);
        updatedDocumentAFournir
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restDocumentAFournirMockMvc.perform(put("/api/document-a-fournirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentAFournir)))
            .andExpect(status().isOk());

        // Validate the DocumentAFournir in the database
        List<DocumentAFournir> documentAFournirList = documentAFournirRepository.findAll();
        assertThat(documentAFournirList).hasSize(databaseSizeBeforeUpdate);
        DocumentAFournir testDocumentAFournir = documentAFournirList.get(documentAFournirList.size() - 1);
        assertThat(testDocumentAFournir.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDocumentAFournir.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentAFournir() throws Exception {
        int databaseSizeBeforeUpdate = documentAFournirRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentAFournirMockMvc.perform(put("/api/document-a-fournirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentAFournir)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentAFournir in the database
        List<DocumentAFournir> documentAFournirList = documentAFournirRepository.findAll();
        assertThat(documentAFournirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentAFournir() throws Exception {
        // Initialize the database
        documentAFournirService.save(documentAFournir);

        int databaseSizeBeforeDelete = documentAFournirRepository.findAll().size();

        // Delete the documentAFournir
        restDocumentAFournirMockMvc.perform(delete("/api/document-a-fournirs/{id}", documentAFournir.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentAFournir> documentAFournirList = documentAFournirRepository.findAll();
        assertThat(documentAFournirList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
