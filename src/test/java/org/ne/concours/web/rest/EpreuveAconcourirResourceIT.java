package org.ne.concours.web.rest;

import org.ne.concours.ConcoursFonctionPubliqueApp;
import org.ne.concours.domain.EpreuveAconcourir;
import org.ne.concours.domain.Offre;
import org.ne.concours.repository.EpreuveAconcourirRepository;
import org.ne.concours.service.EpreuveAconcourirService;
import org.ne.concours.service.dto.EpreuveAconcourirCriteria;
import org.ne.concours.service.EpreuveAconcourirQueryService;

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
 * Integration tests for the {@link EpreuveAconcourirResource} REST controller.
 */
@SpringBootTest(classes = ConcoursFonctionPubliqueApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EpreuveAconcourirResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_DUREE = 1;
    private static final Integer UPDATED_DUREE = 2;
    private static final Integer SMALLER_DUREE = 1 - 1;

    private static final Integer DEFAULT_COEFFICIANT = 1;
    private static final Integer UPDATED_COEFFICIANT = 2;
    private static final Integer SMALLER_COEFFICIANT = 1 - 1;

    private static final String DEFAULT_NOTE_ELEMINATOIRE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE_ELEMINATOIRE = "BBBBBBBBBB";

    @Autowired
    private EpreuveAconcourirRepository epreuveAconcourirRepository;

    @Autowired
    private EpreuveAconcourirService epreuveAconcourirService;

    @Autowired
    private EpreuveAconcourirQueryService epreuveAconcourirQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEpreuveAconcourirMockMvc;

    private EpreuveAconcourir epreuveAconcourir;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EpreuveAconcourir createEntity(EntityManager em) {
        EpreuveAconcourir epreuveAconcourir = new EpreuveAconcourir()
            .nom(DEFAULT_NOM)
            .duree(DEFAULT_DUREE)
            .coefficiant(DEFAULT_COEFFICIANT)
            .noteEleminatoire(DEFAULT_NOTE_ELEMINATOIRE);
        return epreuveAconcourir;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EpreuveAconcourir createUpdatedEntity(EntityManager em) {
        EpreuveAconcourir epreuveAconcourir = new EpreuveAconcourir()
            .nom(UPDATED_NOM)
            .duree(UPDATED_DUREE)
            .coefficiant(UPDATED_COEFFICIANT)
            .noteEleminatoire(UPDATED_NOTE_ELEMINATOIRE);
        return epreuveAconcourir;
    }

    @BeforeEach
    public void initTest() {
        epreuveAconcourir = createEntity(em);
    }

    @Test
    @Transactional
    public void createEpreuveAconcourir() throws Exception {
        int databaseSizeBeforeCreate = epreuveAconcourirRepository.findAll().size();
        // Create the EpreuveAconcourir
        restEpreuveAconcourirMockMvc.perform(post("/api/epreuve-aconcourirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(epreuveAconcourir)))
            .andExpect(status().isCreated());

        // Validate the EpreuveAconcourir in the database
        List<EpreuveAconcourir> epreuveAconcourirList = epreuveAconcourirRepository.findAll();
        assertThat(epreuveAconcourirList).hasSize(databaseSizeBeforeCreate + 1);
        EpreuveAconcourir testEpreuveAconcourir = epreuveAconcourirList.get(epreuveAconcourirList.size() - 1);
        assertThat(testEpreuveAconcourir.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEpreuveAconcourir.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testEpreuveAconcourir.getCoefficiant()).isEqualTo(DEFAULT_COEFFICIANT);
        assertThat(testEpreuveAconcourir.getNoteEleminatoire()).isEqualTo(DEFAULT_NOTE_ELEMINATOIRE);
    }

    @Test
    @Transactional
    public void createEpreuveAconcourirWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = epreuveAconcourirRepository.findAll().size();

        // Create the EpreuveAconcourir with an existing ID
        epreuveAconcourir.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEpreuveAconcourirMockMvc.perform(post("/api/epreuve-aconcourirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(epreuveAconcourir)))
            .andExpect(status().isBadRequest());

        // Validate the EpreuveAconcourir in the database
        List<EpreuveAconcourir> epreuveAconcourirList = epreuveAconcourirRepository.findAll();
        assertThat(epreuveAconcourirList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEpreuveAconcourirs() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList
        restEpreuveAconcourirMockMvc.perform(get("/api/epreuve-aconcourirs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(epreuveAconcourir.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].coefficiant").value(hasItem(DEFAULT_COEFFICIANT)))
            .andExpect(jsonPath("$.[*].noteEleminatoire").value(hasItem(DEFAULT_NOTE_ELEMINATOIRE)));
    }
    
    @Test
    @Transactional
    public void getEpreuveAconcourir() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get the epreuveAconcourir
        restEpreuveAconcourirMockMvc.perform(get("/api/epreuve-aconcourirs/{id}", epreuveAconcourir.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(epreuveAconcourir.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.coefficiant").value(DEFAULT_COEFFICIANT))
            .andExpect(jsonPath("$.noteEleminatoire").value(DEFAULT_NOTE_ELEMINATOIRE));
    }


    @Test
    @Transactional
    public void getEpreuveAconcourirsByIdFiltering() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        Long id = epreuveAconcourir.getId();

        defaultEpreuveAconcourirShouldBeFound("id.equals=" + id);
        defaultEpreuveAconcourirShouldNotBeFound("id.notEquals=" + id);

        defaultEpreuveAconcourirShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEpreuveAconcourirShouldNotBeFound("id.greaterThan=" + id);

        defaultEpreuveAconcourirShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEpreuveAconcourirShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where nom equals to DEFAULT_NOM
        defaultEpreuveAconcourirShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the epreuveAconcourirList where nom equals to UPDATED_NOM
        defaultEpreuveAconcourirShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where nom not equals to DEFAULT_NOM
        defaultEpreuveAconcourirShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the epreuveAconcourirList where nom not equals to UPDATED_NOM
        defaultEpreuveAconcourirShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultEpreuveAconcourirShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the epreuveAconcourirList where nom equals to UPDATED_NOM
        defaultEpreuveAconcourirShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where nom is not null
        defaultEpreuveAconcourirShouldBeFound("nom.specified=true");

        // Get all the epreuveAconcourirList where nom is null
        defaultEpreuveAconcourirShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNomContainsSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where nom contains DEFAULT_NOM
        defaultEpreuveAconcourirShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the epreuveAconcourirList where nom contains UPDATED_NOM
        defaultEpreuveAconcourirShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where nom does not contain DEFAULT_NOM
        defaultEpreuveAconcourirShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the epreuveAconcourirList where nom does not contain UPDATED_NOM
        defaultEpreuveAconcourirShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByDureeIsEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where duree equals to DEFAULT_DUREE
        defaultEpreuveAconcourirShouldBeFound("duree.equals=" + DEFAULT_DUREE);

        // Get all the epreuveAconcourirList where duree equals to UPDATED_DUREE
        defaultEpreuveAconcourirShouldNotBeFound("duree.equals=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByDureeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where duree not equals to DEFAULT_DUREE
        defaultEpreuveAconcourirShouldNotBeFound("duree.notEquals=" + DEFAULT_DUREE);

        // Get all the epreuveAconcourirList where duree not equals to UPDATED_DUREE
        defaultEpreuveAconcourirShouldBeFound("duree.notEquals=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByDureeIsInShouldWork() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where duree in DEFAULT_DUREE or UPDATED_DUREE
        defaultEpreuveAconcourirShouldBeFound("duree.in=" + DEFAULT_DUREE + "," + UPDATED_DUREE);

        // Get all the epreuveAconcourirList where duree equals to UPDATED_DUREE
        defaultEpreuveAconcourirShouldNotBeFound("duree.in=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByDureeIsNullOrNotNull() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where duree is not null
        defaultEpreuveAconcourirShouldBeFound("duree.specified=true");

        // Get all the epreuveAconcourirList where duree is null
        defaultEpreuveAconcourirShouldNotBeFound("duree.specified=false");
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByDureeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where duree is greater than or equal to DEFAULT_DUREE
        defaultEpreuveAconcourirShouldBeFound("duree.greaterThanOrEqual=" + DEFAULT_DUREE);

        // Get all the epreuveAconcourirList where duree is greater than or equal to UPDATED_DUREE
        defaultEpreuveAconcourirShouldNotBeFound("duree.greaterThanOrEqual=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByDureeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where duree is less than or equal to DEFAULT_DUREE
        defaultEpreuveAconcourirShouldBeFound("duree.lessThanOrEqual=" + DEFAULT_DUREE);

        // Get all the epreuveAconcourirList where duree is less than or equal to SMALLER_DUREE
        defaultEpreuveAconcourirShouldNotBeFound("duree.lessThanOrEqual=" + SMALLER_DUREE);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByDureeIsLessThanSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where duree is less than DEFAULT_DUREE
        defaultEpreuveAconcourirShouldNotBeFound("duree.lessThan=" + DEFAULT_DUREE);

        // Get all the epreuveAconcourirList where duree is less than UPDATED_DUREE
        defaultEpreuveAconcourirShouldBeFound("duree.lessThan=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByDureeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where duree is greater than DEFAULT_DUREE
        defaultEpreuveAconcourirShouldNotBeFound("duree.greaterThan=" + DEFAULT_DUREE);

        // Get all the epreuveAconcourirList where duree is greater than SMALLER_DUREE
        defaultEpreuveAconcourirShouldBeFound("duree.greaterThan=" + SMALLER_DUREE);
    }


    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByCoefficiantIsEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where coefficiant equals to DEFAULT_COEFFICIANT
        defaultEpreuveAconcourirShouldBeFound("coefficiant.equals=" + DEFAULT_COEFFICIANT);

        // Get all the epreuveAconcourirList where coefficiant equals to UPDATED_COEFFICIANT
        defaultEpreuveAconcourirShouldNotBeFound("coefficiant.equals=" + UPDATED_COEFFICIANT);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByCoefficiantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where coefficiant not equals to DEFAULT_COEFFICIANT
        defaultEpreuveAconcourirShouldNotBeFound("coefficiant.notEquals=" + DEFAULT_COEFFICIANT);

        // Get all the epreuveAconcourirList where coefficiant not equals to UPDATED_COEFFICIANT
        defaultEpreuveAconcourirShouldBeFound("coefficiant.notEquals=" + UPDATED_COEFFICIANT);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByCoefficiantIsInShouldWork() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where coefficiant in DEFAULT_COEFFICIANT or UPDATED_COEFFICIANT
        defaultEpreuveAconcourirShouldBeFound("coefficiant.in=" + DEFAULT_COEFFICIANT + "," + UPDATED_COEFFICIANT);

        // Get all the epreuveAconcourirList where coefficiant equals to UPDATED_COEFFICIANT
        defaultEpreuveAconcourirShouldNotBeFound("coefficiant.in=" + UPDATED_COEFFICIANT);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByCoefficiantIsNullOrNotNull() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where coefficiant is not null
        defaultEpreuveAconcourirShouldBeFound("coefficiant.specified=true");

        // Get all the epreuveAconcourirList where coefficiant is null
        defaultEpreuveAconcourirShouldNotBeFound("coefficiant.specified=false");
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByCoefficiantIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where coefficiant is greater than or equal to DEFAULT_COEFFICIANT
        defaultEpreuveAconcourirShouldBeFound("coefficiant.greaterThanOrEqual=" + DEFAULT_COEFFICIANT);

        // Get all the epreuveAconcourirList where coefficiant is greater than or equal to UPDATED_COEFFICIANT
        defaultEpreuveAconcourirShouldNotBeFound("coefficiant.greaterThanOrEqual=" + UPDATED_COEFFICIANT);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByCoefficiantIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where coefficiant is less than or equal to DEFAULT_COEFFICIANT
        defaultEpreuveAconcourirShouldBeFound("coefficiant.lessThanOrEqual=" + DEFAULT_COEFFICIANT);

        // Get all the epreuveAconcourirList where coefficiant is less than or equal to SMALLER_COEFFICIANT
        defaultEpreuveAconcourirShouldNotBeFound("coefficiant.lessThanOrEqual=" + SMALLER_COEFFICIANT);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByCoefficiantIsLessThanSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where coefficiant is less than DEFAULT_COEFFICIANT
        defaultEpreuveAconcourirShouldNotBeFound("coefficiant.lessThan=" + DEFAULT_COEFFICIANT);

        // Get all the epreuveAconcourirList where coefficiant is less than UPDATED_COEFFICIANT
        defaultEpreuveAconcourirShouldBeFound("coefficiant.lessThan=" + UPDATED_COEFFICIANT);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByCoefficiantIsGreaterThanSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where coefficiant is greater than DEFAULT_COEFFICIANT
        defaultEpreuveAconcourirShouldNotBeFound("coefficiant.greaterThan=" + DEFAULT_COEFFICIANT);

        // Get all the epreuveAconcourirList where coefficiant is greater than SMALLER_COEFFICIANT
        defaultEpreuveAconcourirShouldBeFound("coefficiant.greaterThan=" + SMALLER_COEFFICIANT);
    }


    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNoteEleminatoireIsEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where noteEleminatoire equals to DEFAULT_NOTE_ELEMINATOIRE
        defaultEpreuveAconcourirShouldBeFound("noteEleminatoire.equals=" + DEFAULT_NOTE_ELEMINATOIRE);

        // Get all the epreuveAconcourirList where noteEleminatoire equals to UPDATED_NOTE_ELEMINATOIRE
        defaultEpreuveAconcourirShouldNotBeFound("noteEleminatoire.equals=" + UPDATED_NOTE_ELEMINATOIRE);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNoteEleminatoireIsNotEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where noteEleminatoire not equals to DEFAULT_NOTE_ELEMINATOIRE
        defaultEpreuveAconcourirShouldNotBeFound("noteEleminatoire.notEquals=" + DEFAULT_NOTE_ELEMINATOIRE);

        // Get all the epreuveAconcourirList where noteEleminatoire not equals to UPDATED_NOTE_ELEMINATOIRE
        defaultEpreuveAconcourirShouldBeFound("noteEleminatoire.notEquals=" + UPDATED_NOTE_ELEMINATOIRE);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNoteEleminatoireIsInShouldWork() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where noteEleminatoire in DEFAULT_NOTE_ELEMINATOIRE or UPDATED_NOTE_ELEMINATOIRE
        defaultEpreuveAconcourirShouldBeFound("noteEleminatoire.in=" + DEFAULT_NOTE_ELEMINATOIRE + "," + UPDATED_NOTE_ELEMINATOIRE);

        // Get all the epreuveAconcourirList where noteEleminatoire equals to UPDATED_NOTE_ELEMINATOIRE
        defaultEpreuveAconcourirShouldNotBeFound("noteEleminatoire.in=" + UPDATED_NOTE_ELEMINATOIRE);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNoteEleminatoireIsNullOrNotNull() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where noteEleminatoire is not null
        defaultEpreuveAconcourirShouldBeFound("noteEleminatoire.specified=true");

        // Get all the epreuveAconcourirList where noteEleminatoire is null
        defaultEpreuveAconcourirShouldNotBeFound("noteEleminatoire.specified=false");
    }
                @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNoteEleminatoireContainsSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where noteEleminatoire contains DEFAULT_NOTE_ELEMINATOIRE
        defaultEpreuveAconcourirShouldBeFound("noteEleminatoire.contains=" + DEFAULT_NOTE_ELEMINATOIRE);

        // Get all the epreuveAconcourirList where noteEleminatoire contains UPDATED_NOTE_ELEMINATOIRE
        defaultEpreuveAconcourirShouldNotBeFound("noteEleminatoire.contains=" + UPDATED_NOTE_ELEMINATOIRE);
    }

    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByNoteEleminatoireNotContainsSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);

        // Get all the epreuveAconcourirList where noteEleminatoire does not contain DEFAULT_NOTE_ELEMINATOIRE
        defaultEpreuveAconcourirShouldNotBeFound("noteEleminatoire.doesNotContain=" + DEFAULT_NOTE_ELEMINATOIRE);

        // Get all the epreuveAconcourirList where noteEleminatoire does not contain UPDATED_NOTE_ELEMINATOIRE
        defaultEpreuveAconcourirShouldBeFound("noteEleminatoire.doesNotContain=" + UPDATED_NOTE_ELEMINATOIRE);
    }


    @Test
    @Transactional
    public void getAllEpreuveAconcourirsByOffreIsEqualToSomething() throws Exception {
        // Initialize the database
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);
        Offre offre = OffreResourceIT.createEntity(em);
        em.persist(offre);
        em.flush();
        epreuveAconcourir.setOffre(offre);
        epreuveAconcourirRepository.saveAndFlush(epreuveAconcourir);
        Long offreId = offre.getId();

        // Get all the epreuveAconcourirList where offre equals to offreId
        defaultEpreuveAconcourirShouldBeFound("offreId.equals=" + offreId);

        // Get all the epreuveAconcourirList where offre equals to offreId + 1
        defaultEpreuveAconcourirShouldNotBeFound("offreId.equals=" + (offreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEpreuveAconcourirShouldBeFound(String filter) throws Exception {
        restEpreuveAconcourirMockMvc.perform(get("/api/epreuve-aconcourirs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(epreuveAconcourir.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].coefficiant").value(hasItem(DEFAULT_COEFFICIANT)))
            .andExpect(jsonPath("$.[*].noteEleminatoire").value(hasItem(DEFAULT_NOTE_ELEMINATOIRE)));

        // Check, that the count call also returns 1
        restEpreuveAconcourirMockMvc.perform(get("/api/epreuve-aconcourirs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEpreuveAconcourirShouldNotBeFound(String filter) throws Exception {
        restEpreuveAconcourirMockMvc.perform(get("/api/epreuve-aconcourirs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEpreuveAconcourirMockMvc.perform(get("/api/epreuve-aconcourirs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEpreuveAconcourir() throws Exception {
        // Get the epreuveAconcourir
        restEpreuveAconcourirMockMvc.perform(get("/api/epreuve-aconcourirs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEpreuveAconcourir() throws Exception {
        // Initialize the database
        epreuveAconcourirService.save(epreuveAconcourir);

        int databaseSizeBeforeUpdate = epreuveAconcourirRepository.findAll().size();

        // Update the epreuveAconcourir
        EpreuveAconcourir updatedEpreuveAconcourir = epreuveAconcourirRepository.findById(epreuveAconcourir.getId()).get();
        // Disconnect from session so that the updates on updatedEpreuveAconcourir are not directly saved in db
        em.detach(updatedEpreuveAconcourir);
        updatedEpreuveAconcourir
            .nom(UPDATED_NOM)
            .duree(UPDATED_DUREE)
            .coefficiant(UPDATED_COEFFICIANT)
            .noteEleminatoire(UPDATED_NOTE_ELEMINATOIRE);

        restEpreuveAconcourirMockMvc.perform(put("/api/epreuve-aconcourirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEpreuveAconcourir)))
            .andExpect(status().isOk());

        // Validate the EpreuveAconcourir in the database
        List<EpreuveAconcourir> epreuveAconcourirList = epreuveAconcourirRepository.findAll();
        assertThat(epreuveAconcourirList).hasSize(databaseSizeBeforeUpdate);
        EpreuveAconcourir testEpreuveAconcourir = epreuveAconcourirList.get(epreuveAconcourirList.size() - 1);
        assertThat(testEpreuveAconcourir.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEpreuveAconcourir.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testEpreuveAconcourir.getCoefficiant()).isEqualTo(UPDATED_COEFFICIANT);
        assertThat(testEpreuveAconcourir.getNoteEleminatoire()).isEqualTo(UPDATED_NOTE_ELEMINATOIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingEpreuveAconcourir() throws Exception {
        int databaseSizeBeforeUpdate = epreuveAconcourirRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEpreuveAconcourirMockMvc.perform(put("/api/epreuve-aconcourirs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(epreuveAconcourir)))
            .andExpect(status().isBadRequest());

        // Validate the EpreuveAconcourir in the database
        List<EpreuveAconcourir> epreuveAconcourirList = epreuveAconcourirRepository.findAll();
        assertThat(epreuveAconcourirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEpreuveAconcourir() throws Exception {
        // Initialize the database
        epreuveAconcourirService.save(epreuveAconcourir);

        int databaseSizeBeforeDelete = epreuveAconcourirRepository.findAll().size();

        // Delete the epreuveAconcourir
        restEpreuveAconcourirMockMvc.perform(delete("/api/epreuve-aconcourirs/{id}", epreuveAconcourir.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EpreuveAconcourir> epreuveAconcourirList = epreuveAconcourirRepository.findAll();
        assertThat(epreuveAconcourirList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
