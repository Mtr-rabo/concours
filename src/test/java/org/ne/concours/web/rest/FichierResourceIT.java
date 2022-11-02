package org.ne.concours.web.rest;

import org.ne.concours.ConcoursFonctionPubliqueApp;
import org.ne.concours.domain.Fichier;
import org.ne.concours.domain.Depot;
import org.ne.concours.repository.FichierRepository;
import org.ne.concours.service.FichierService;
import org.ne.concours.service.dto.FichierCriteria;
import org.ne.concours.service.FichierQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FichierResource} REST controller.
 */
@SpringBootTest(classes = ConcoursFonctionPubliqueApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FichierResourceIT {

    private static final String DEFAULT_NOM_FICHIER = "AAAAAAAAAA";
    private static final String UPDATED_NOM_FICHIER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FICH = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICH = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICH_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICH_CONTENT_TYPE = "image/png";

    @Autowired
    private FichierRepository fichierRepository;

    @Autowired
    private FichierService fichierService;

    @Autowired
    private FichierQueryService fichierQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFichierMockMvc;

    private Fichier fichier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fichier createEntity(EntityManager em) {
        Fichier fichier = new Fichier()
            .nomFichier(DEFAULT_NOM_FICHIER)
            .fich(DEFAULT_FICH)
            .fichContentType(DEFAULT_FICH_CONTENT_TYPE);
        return fichier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fichier createUpdatedEntity(EntityManager em) {
        Fichier fichier = new Fichier()
            .nomFichier(UPDATED_NOM_FICHIER)
            .fich(UPDATED_FICH)
            .fichContentType(UPDATED_FICH_CONTENT_TYPE);
        return fichier;
    }

    @BeforeEach
    public void initTest() {
        fichier = createEntity(em);
    }

    @Test
    @Transactional
    public void createFichier() throws Exception {
        int databaseSizeBeforeCreate = fichierRepository.findAll().size();
        // Create the Fichier
        restFichierMockMvc.perform(post("/api/fichiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fichier)))
            .andExpect(status().isCreated());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeCreate + 1);
        Fichier testFichier = fichierList.get(fichierList.size() - 1);
        assertThat(testFichier.getNomFichier()).isEqualTo(DEFAULT_NOM_FICHIER);
        assertThat(testFichier.getFich()).isEqualTo(DEFAULT_FICH);
        assertThat(testFichier.getFichContentType()).isEqualTo(DEFAULT_FICH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createFichierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichierRepository.findAll().size();

        // Create the Fichier with an existing ID
        fichier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichierMockMvc.perform(post("/api/fichiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fichier)))
            .andExpect(status().isBadRequest());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFichiers() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get all the fichierList
        restFichierMockMvc.perform(get("/api/fichiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichier.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomFichier").value(hasItem(DEFAULT_NOM_FICHIER)))
            .andExpect(jsonPath("$.[*].fichContentType").value(hasItem(DEFAULT_FICH_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fich").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICH))));
    }
    
    @Test
    @Transactional
    public void getFichier() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get the fichier
        restFichierMockMvc.perform(get("/api/fichiers/{id}", fichier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fichier.getId().intValue()))
            .andExpect(jsonPath("$.nomFichier").value(DEFAULT_NOM_FICHIER))
            .andExpect(jsonPath("$.fichContentType").value(DEFAULT_FICH_CONTENT_TYPE))
            .andExpect(jsonPath("$.fich").value(Base64Utils.encodeToString(DEFAULT_FICH)));
    }


    @Test
    @Transactional
    public void getFichiersByIdFiltering() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        Long id = fichier.getId();

        defaultFichierShouldBeFound("id.equals=" + id);
        defaultFichierShouldNotBeFound("id.notEquals=" + id);

        defaultFichierShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFichierShouldNotBeFound("id.greaterThan=" + id);

        defaultFichierShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFichierShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFichiersByNomFichierIsEqualToSomething() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get all the fichierList where nomFichier equals to DEFAULT_NOM_FICHIER
        defaultFichierShouldBeFound("nomFichier.equals=" + DEFAULT_NOM_FICHIER);

        // Get all the fichierList where nomFichier equals to UPDATED_NOM_FICHIER
        defaultFichierShouldNotBeFound("nomFichier.equals=" + UPDATED_NOM_FICHIER);
    }

    @Test
    @Transactional
    public void getAllFichiersByNomFichierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get all the fichierList where nomFichier not equals to DEFAULT_NOM_FICHIER
        defaultFichierShouldNotBeFound("nomFichier.notEquals=" + DEFAULT_NOM_FICHIER);

        // Get all the fichierList where nomFichier not equals to UPDATED_NOM_FICHIER
        defaultFichierShouldBeFound("nomFichier.notEquals=" + UPDATED_NOM_FICHIER);
    }

    @Test
    @Transactional
    public void getAllFichiersByNomFichierIsInShouldWork() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get all the fichierList where nomFichier in DEFAULT_NOM_FICHIER or UPDATED_NOM_FICHIER
        defaultFichierShouldBeFound("nomFichier.in=" + DEFAULT_NOM_FICHIER + "," + UPDATED_NOM_FICHIER);

        // Get all the fichierList where nomFichier equals to UPDATED_NOM_FICHIER
        defaultFichierShouldNotBeFound("nomFichier.in=" + UPDATED_NOM_FICHIER);
    }

    @Test
    @Transactional
    public void getAllFichiersByNomFichierIsNullOrNotNull() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get all the fichierList where nomFichier is not null
        defaultFichierShouldBeFound("nomFichier.specified=true");

        // Get all the fichierList where nomFichier is null
        defaultFichierShouldNotBeFound("nomFichier.specified=false");
    }
                @Test
    @Transactional
    public void getAllFichiersByNomFichierContainsSomething() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get all the fichierList where nomFichier contains DEFAULT_NOM_FICHIER
        defaultFichierShouldBeFound("nomFichier.contains=" + DEFAULT_NOM_FICHIER);

        // Get all the fichierList where nomFichier contains UPDATED_NOM_FICHIER
        defaultFichierShouldNotBeFound("nomFichier.contains=" + UPDATED_NOM_FICHIER);
    }

    @Test
    @Transactional
    public void getAllFichiersByNomFichierNotContainsSomething() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get all the fichierList where nomFichier does not contain DEFAULT_NOM_FICHIER
        defaultFichierShouldNotBeFound("nomFichier.doesNotContain=" + DEFAULT_NOM_FICHIER);

        // Get all the fichierList where nomFichier does not contain UPDATED_NOM_FICHIER
        defaultFichierShouldBeFound("nomFichier.doesNotContain=" + UPDATED_NOM_FICHIER);
    }


    @Test
    @Transactional
    public void getAllFichiersByDepotIsEqualToSomething() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);
        Depot depot = DepotResourceIT.createEntity(em);
        em.persist(depot);
        em.flush();
        fichier.setDepot(depot);
        fichierRepository.saveAndFlush(fichier);
        Long depotId = depot.getId();

        // Get all the fichierList where depot equals to depotId
        defaultFichierShouldBeFound("depotId.equals=" + depotId);

        // Get all the fichierList where depot equals to depotId + 1
        defaultFichierShouldNotBeFound("depotId.equals=" + (depotId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFichierShouldBeFound(String filter) throws Exception {
        restFichierMockMvc.perform(get("/api/fichiers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichier.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomFichier").value(hasItem(DEFAULT_NOM_FICHIER)))
            .andExpect(jsonPath("$.[*].fichContentType").value(hasItem(DEFAULT_FICH_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fich").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICH))));

        // Check, that the count call also returns 1
        restFichierMockMvc.perform(get("/api/fichiers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFichierShouldNotBeFound(String filter) throws Exception {
        restFichierMockMvc.perform(get("/api/fichiers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFichierMockMvc.perform(get("/api/fichiers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFichier() throws Exception {
        // Get the fichier
        restFichierMockMvc.perform(get("/api/fichiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFichier() throws Exception {
        // Initialize the database
        fichierService.save(fichier);

        int databaseSizeBeforeUpdate = fichierRepository.findAll().size();

        // Update the fichier
        Fichier updatedFichier = fichierRepository.findById(fichier.getId()).get();
        // Disconnect from session so that the updates on updatedFichier are not directly saved in db
        em.detach(updatedFichier);
        updatedFichier
            .nomFichier(UPDATED_NOM_FICHIER)
            .fich(UPDATED_FICH)
            .fichContentType(UPDATED_FICH_CONTENT_TYPE);

        restFichierMockMvc.perform(put("/api/fichiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFichier)))
            .andExpect(status().isOk());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeUpdate);
        Fichier testFichier = fichierList.get(fichierList.size() - 1);
        assertThat(testFichier.getNomFichier()).isEqualTo(UPDATED_NOM_FICHIER);
        assertThat(testFichier.getFich()).isEqualTo(UPDATED_FICH);
        assertThat(testFichier.getFichContentType()).isEqualTo(UPDATED_FICH_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFichier() throws Exception {
        int databaseSizeBeforeUpdate = fichierRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichierMockMvc.perform(put("/api/fichiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fichier)))
            .andExpect(status().isBadRequest());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFichier() throws Exception {
        // Initialize the database
        fichierService.save(fichier);

        int databaseSizeBeforeDelete = fichierRepository.findAll().size();

        // Delete the fichier
        restFichierMockMvc.perform(delete("/api/fichiers/{id}", fichier.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
