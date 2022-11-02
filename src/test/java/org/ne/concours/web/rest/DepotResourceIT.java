package org.ne.concours.web.rest;

import org.ne.concours.ConcoursFonctionPubliqueApp;
import org.ne.concours.domain.Depot;
import org.ne.concours.domain.Offre;
import org.ne.concours.domain.Candidat;
import org.ne.concours.repository.DepotRepository;
import org.ne.concours.service.DepotService;
import org.ne.concours.service.dto.DepotCriteria;
import org.ne.concours.service.DepotQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DepotResource} REST controller.
 */
@SpringBootTest(classes = ConcoursFonctionPubliqueApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DepotResourceIT {

    private static final Instant DEFAULT_DATE_DEPOT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEPOT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_ARCHIVE = false;
    private static final Boolean UPDATED_IS_ARCHIVE = true;

    private static final Boolean DEFAULT_IS_ACEPTE = false;
    private static final Boolean UPDATED_IS_ACEPTE = true;

    @Autowired
    private DepotRepository depotRepository;

    @Autowired
    private DepotService depotService;

    @Autowired
    private DepotQueryService depotQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepotMockMvc;

    private Depot depot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depot createEntity(EntityManager em) {
        Depot depot = new Depot()
            .dateDepot(DEFAULT_DATE_DEPOT)
            .isArchive(DEFAULT_IS_ARCHIVE)
            .isAcepte(DEFAULT_IS_ACEPTE);
        return depot;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depot createUpdatedEntity(EntityManager em) {
        Depot depot = new Depot()
            .dateDepot(UPDATED_DATE_DEPOT)
            .isArchive(UPDATED_IS_ARCHIVE)
            .isAcepte(UPDATED_IS_ACEPTE);
        return depot;
    }

    @BeforeEach
    public void initTest() {
        depot = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepot() throws Exception {
        int databaseSizeBeforeCreate = depotRepository.findAll().size();
        // Create the Depot
        restDepotMockMvc.perform(post("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isCreated());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeCreate + 1);
        Depot testDepot = depotList.get(depotList.size() - 1);
        assertThat(testDepot.getDateDepot()).isEqualTo(DEFAULT_DATE_DEPOT);
        assertThat(testDepot.isIsArchive()).isEqualTo(DEFAULT_IS_ARCHIVE);
        assertThat(testDepot.isIsAcepte()).isEqualTo(DEFAULT_IS_ACEPTE);
    }

    @Test
    @Transactional
    public void createDepotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depotRepository.findAll().size();

        // Create the Depot with an existing ID
        depot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepotMockMvc.perform(post("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDepots() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList
        restDepotMockMvc.perform(get("/api/depots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depot.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDepot").value(hasItem(DEFAULT_DATE_DEPOT.toString())))
            .andExpect(jsonPath("$.[*].isArchive").value(hasItem(DEFAULT_IS_ARCHIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isAcepte").value(hasItem(DEFAULT_IS_ACEPTE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get the depot
        restDepotMockMvc.perform(get("/api/depots/{id}", depot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(depot.getId().intValue()))
            .andExpect(jsonPath("$.dateDepot").value(DEFAULT_DATE_DEPOT.toString()))
            .andExpect(jsonPath("$.isArchive").value(DEFAULT_IS_ARCHIVE.booleanValue()))
            .andExpect(jsonPath("$.isAcepte").value(DEFAULT_IS_ACEPTE.booleanValue()));
    }


    @Test
    @Transactional
    public void getDepotsByIdFiltering() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        Long id = depot.getId();

        defaultDepotShouldBeFound("id.equals=" + id);
        defaultDepotShouldNotBeFound("id.notEquals=" + id);

        defaultDepotShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDepotShouldNotBeFound("id.greaterThan=" + id);

        defaultDepotShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDepotShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDepotsByDateDepotIsEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where dateDepot equals to DEFAULT_DATE_DEPOT
        defaultDepotShouldBeFound("dateDepot.equals=" + DEFAULT_DATE_DEPOT);

        // Get all the depotList where dateDepot equals to UPDATED_DATE_DEPOT
        defaultDepotShouldNotBeFound("dateDepot.equals=" + UPDATED_DATE_DEPOT);
    }

    @Test
    @Transactional
    public void getAllDepotsByDateDepotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where dateDepot not equals to DEFAULT_DATE_DEPOT
        defaultDepotShouldNotBeFound("dateDepot.notEquals=" + DEFAULT_DATE_DEPOT);

        // Get all the depotList where dateDepot not equals to UPDATED_DATE_DEPOT
        defaultDepotShouldBeFound("dateDepot.notEquals=" + UPDATED_DATE_DEPOT);
    }

    @Test
    @Transactional
    public void getAllDepotsByDateDepotIsInShouldWork() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where dateDepot in DEFAULT_DATE_DEPOT or UPDATED_DATE_DEPOT
        defaultDepotShouldBeFound("dateDepot.in=" + DEFAULT_DATE_DEPOT + "," + UPDATED_DATE_DEPOT);

        // Get all the depotList where dateDepot equals to UPDATED_DATE_DEPOT
        defaultDepotShouldNotBeFound("dateDepot.in=" + UPDATED_DATE_DEPOT);
    }

    @Test
    @Transactional
    public void getAllDepotsByDateDepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where dateDepot is not null
        defaultDepotShouldBeFound("dateDepot.specified=true");

        // Get all the depotList where dateDepot is null
        defaultDepotShouldNotBeFound("dateDepot.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepotsByIsArchiveIsEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where isArchive equals to DEFAULT_IS_ARCHIVE
        defaultDepotShouldBeFound("isArchive.equals=" + DEFAULT_IS_ARCHIVE);

        // Get all the depotList where isArchive equals to UPDATED_IS_ARCHIVE
        defaultDepotShouldNotBeFound("isArchive.equals=" + UPDATED_IS_ARCHIVE);
    }

    @Test
    @Transactional
    public void getAllDepotsByIsArchiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where isArchive not equals to DEFAULT_IS_ARCHIVE
        defaultDepotShouldNotBeFound("isArchive.notEquals=" + DEFAULT_IS_ARCHIVE);

        // Get all the depotList where isArchive not equals to UPDATED_IS_ARCHIVE
        defaultDepotShouldBeFound("isArchive.notEquals=" + UPDATED_IS_ARCHIVE);
    }

    @Test
    @Transactional
    public void getAllDepotsByIsArchiveIsInShouldWork() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where isArchive in DEFAULT_IS_ARCHIVE or UPDATED_IS_ARCHIVE
        defaultDepotShouldBeFound("isArchive.in=" + DEFAULT_IS_ARCHIVE + "," + UPDATED_IS_ARCHIVE);

        // Get all the depotList where isArchive equals to UPDATED_IS_ARCHIVE
        defaultDepotShouldNotBeFound("isArchive.in=" + UPDATED_IS_ARCHIVE);
    }

    @Test
    @Transactional
    public void getAllDepotsByIsArchiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where isArchive is not null
        defaultDepotShouldBeFound("isArchive.specified=true");

        // Get all the depotList where isArchive is null
        defaultDepotShouldNotBeFound("isArchive.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepotsByIsAcepteIsEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where isAcepte equals to DEFAULT_IS_ACEPTE
        defaultDepotShouldBeFound("isAcepte.equals=" + DEFAULT_IS_ACEPTE);

        // Get all the depotList where isAcepte equals to UPDATED_IS_ACEPTE
        defaultDepotShouldNotBeFound("isAcepte.equals=" + UPDATED_IS_ACEPTE);
    }

    @Test
    @Transactional
    public void getAllDepotsByIsAcepteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where isAcepte not equals to DEFAULT_IS_ACEPTE
        defaultDepotShouldNotBeFound("isAcepte.notEquals=" + DEFAULT_IS_ACEPTE);

        // Get all the depotList where isAcepte not equals to UPDATED_IS_ACEPTE
        defaultDepotShouldBeFound("isAcepte.notEquals=" + UPDATED_IS_ACEPTE);
    }

    @Test
    @Transactional
    public void getAllDepotsByIsAcepteIsInShouldWork() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where isAcepte in DEFAULT_IS_ACEPTE or UPDATED_IS_ACEPTE
        defaultDepotShouldBeFound("isAcepte.in=" + DEFAULT_IS_ACEPTE + "," + UPDATED_IS_ACEPTE);

        // Get all the depotList where isAcepte equals to UPDATED_IS_ACEPTE
        defaultDepotShouldNotBeFound("isAcepte.in=" + UPDATED_IS_ACEPTE);
    }

    @Test
    @Transactional
    public void getAllDepotsByIsAcepteIsNullOrNotNull() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where isAcepte is not null
        defaultDepotShouldBeFound("isAcepte.specified=true");

        // Get all the depotList where isAcepte is null
        defaultDepotShouldNotBeFound("isAcepte.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepotsByOffreIsEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);
        Offre offre = OffreResourceIT.createEntity(em);
        em.persist(offre);
        em.flush();
        depot.setOffre(offre);
        depotRepository.saveAndFlush(depot);
        Long offreId = offre.getId();

        // Get all the depotList where offre equals to offreId
        defaultDepotShouldBeFound("offreId.equals=" + offreId);

        // Get all the depotList where offre equals to offreId + 1
        defaultDepotShouldNotBeFound("offreId.equals=" + (offreId + 1));
    }


    @Test
    @Transactional
    public void getAllDepotsByCandidatIsEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);
        Candidat candidat = CandidatResourceIT.createEntity(em);
        em.persist(candidat);
        em.flush();
        depot.setCandidat(candidat);
        depotRepository.saveAndFlush(depot);
        Long candidatId = candidat.getId();

        // Get all the depotList where candidat equals to candidatId
        defaultDepotShouldBeFound("candidatId.equals=" + candidatId);

        // Get all the depotList where candidat equals to candidatId + 1
        defaultDepotShouldNotBeFound("candidatId.equals=" + (candidatId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepotShouldBeFound(String filter) throws Exception {
        restDepotMockMvc.perform(get("/api/depots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depot.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDepot").value(hasItem(DEFAULT_DATE_DEPOT.toString())))
            .andExpect(jsonPath("$.[*].isArchive").value(hasItem(DEFAULT_IS_ARCHIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isAcepte").value(hasItem(DEFAULT_IS_ACEPTE.booleanValue())));

        // Check, that the count call also returns 1
        restDepotMockMvc.perform(get("/api/depots/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepotShouldNotBeFound(String filter) throws Exception {
        restDepotMockMvc.perform(get("/api/depots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepotMockMvc.perform(get("/api/depots/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDepot() throws Exception {
        // Get the depot
        restDepotMockMvc.perform(get("/api/depots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepot() throws Exception {
        // Initialize the database
        depotService.save(depot);

        int databaseSizeBeforeUpdate = depotRepository.findAll().size();

        // Update the depot
        Depot updatedDepot = depotRepository.findById(depot.getId()).get();
        // Disconnect from session so that the updates on updatedDepot are not directly saved in db
        em.detach(updatedDepot);
        updatedDepot
            .dateDepot(UPDATED_DATE_DEPOT)
            .isArchive(UPDATED_IS_ARCHIVE)
            .isAcepte(UPDATED_IS_ACEPTE);

        restDepotMockMvc.perform(put("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepot)))
            .andExpect(status().isOk());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeUpdate);
        Depot testDepot = depotList.get(depotList.size() - 1);
        assertThat(testDepot.getDateDepot()).isEqualTo(UPDATED_DATE_DEPOT);
        assertThat(testDepot.isIsArchive()).isEqualTo(UPDATED_IS_ARCHIVE);
        assertThat(testDepot.isIsAcepte()).isEqualTo(UPDATED_IS_ACEPTE);
    }

    @Test
    @Transactional
    public void updateNonExistingDepot() throws Exception {
        int databaseSizeBeforeUpdate = depotRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepotMockMvc.perform(put("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepot() throws Exception {
        // Initialize the database
        depotService.save(depot);

        int databaseSizeBeforeDelete = depotRepository.findAll().size();

        // Delete the depot
        restDepotMockMvc.perform(delete("/api/depots/{id}", depot.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
