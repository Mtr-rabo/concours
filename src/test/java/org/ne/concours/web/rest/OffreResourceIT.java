package org.ne.concours.web.rest;

import org.ne.concours.ConcoursFonctionPubliqueApp;
import org.ne.concours.domain.Offre;
import org.ne.concours.domain.MinisterConcerner;
import org.ne.concours.domain.Categorie;
import org.ne.concours.domain.Option;
import org.ne.concours.repository.OffreRepository;
import org.ne.concours.service.OffreService;
import org.ne.concours.service.dto.OffreCriteria;
import org.ne.concours.service.OffreQueryService;

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
 * Integration tests for the {@link OffreResource} REST controller.
 */
@SpringBootTest(classes = ConcoursFonctionPubliqueApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OffreResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_POSTE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_POSTE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIP = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIP = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OUVERTURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OUVERTURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_CLOTURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CLOTURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_ARCHIVE = false;
    private static final Boolean UPDATED_IS_ARCHIVE = true;

    private static final Instant DEFAULT_DATE_CONCOURS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CONCOURS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_AGE_LIMITE = 1;
    private static final Integer UPDATED_AGE_LIMITE = 2;
    private static final Integer SMALLER_AGE_LIMITE = 1 - 1;

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private OffreService offreService;

    @Autowired
    private OffreQueryService offreQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOffreMockMvc;

    private Offre offre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offre createEntity(EntityManager em) {
        Offre offre = new Offre()
            .code(DEFAULT_CODE)
            .nomPoste(DEFAULT_NOM_POSTE)
            .descrip(DEFAULT_DESCRIP)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateCloture(DEFAULT_DATE_CLOTURE)
            .isArchive(DEFAULT_IS_ARCHIVE)
            .dateConcours(DEFAULT_DATE_CONCOURS)
            .ageLimite(DEFAULT_AGE_LIMITE);
        return offre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offre createUpdatedEntity(EntityManager em) {
        Offre offre = new Offre()
            .code(UPDATED_CODE)
            .nomPoste(UPDATED_NOM_POSTE)
            .descrip(UPDATED_DESCRIP)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .isArchive(UPDATED_IS_ARCHIVE)
            .dateConcours(UPDATED_DATE_CONCOURS)
            .ageLimite(UPDATED_AGE_LIMITE);
        return offre;
    }

    @BeforeEach
    public void initTest() {
        offre = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffre() throws Exception {
        int databaseSizeBeforeCreate = offreRepository.findAll().size();
        // Create the Offre
        restOffreMockMvc.perform(post("/api/offres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offre)))
            .andExpect(status().isCreated());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate + 1);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOffre.getNomPoste()).isEqualTo(DEFAULT_NOM_POSTE);
        assertThat(testOffre.getDescrip()).isEqualTo(DEFAULT_DESCRIP);
        assertThat(testOffre.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testOffre.getDateCloture()).isEqualTo(DEFAULT_DATE_CLOTURE);
        assertThat(testOffre.isIsArchive()).isEqualTo(DEFAULT_IS_ARCHIVE);
        assertThat(testOffre.getDateConcours()).isEqualTo(DEFAULT_DATE_CONCOURS);
        assertThat(testOffre.getAgeLimite()).isEqualTo(DEFAULT_AGE_LIMITE);
    }

    @Test
    @Transactional
    public void createOffreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offreRepository.findAll().size();

        // Create the Offre with an existing ID
        offre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffreMockMvc.perform(post("/api/offres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offre)))
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOffres() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList
        restOffreMockMvc.perform(get("/api/offres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offre.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nomPoste").value(hasItem(DEFAULT_NOM_POSTE)))
            .andExpect(jsonPath("$.[*].descrip").value(hasItem(DEFAULT_DESCRIP)))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dateCloture").value(hasItem(DEFAULT_DATE_CLOTURE.toString())))
            .andExpect(jsonPath("$.[*].isArchive").value(hasItem(DEFAULT_IS_ARCHIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateConcours").value(hasItem(DEFAULT_DATE_CONCOURS.toString())))
            .andExpect(jsonPath("$.[*].ageLimite").value(hasItem(DEFAULT_AGE_LIMITE)));
    }
    
    @Test
    @Transactional
    public void getOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get the offre
        restOffreMockMvc.perform(get("/api/offres/{id}", offre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offre.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nomPoste").value(DEFAULT_NOM_POSTE))
            .andExpect(jsonPath("$.descrip").value(DEFAULT_DESCRIP))
            .andExpect(jsonPath("$.dateOuverture").value(DEFAULT_DATE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.dateCloture").value(DEFAULT_DATE_CLOTURE.toString()))
            .andExpect(jsonPath("$.isArchive").value(DEFAULT_IS_ARCHIVE.booleanValue()))
            .andExpect(jsonPath("$.dateConcours").value(DEFAULT_DATE_CONCOURS.toString()))
            .andExpect(jsonPath("$.ageLimite").value(DEFAULT_AGE_LIMITE));
    }


    @Test
    @Transactional
    public void getOffresByIdFiltering() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        Long id = offre.getId();

        defaultOffreShouldBeFound("id.equals=" + id);
        defaultOffreShouldNotBeFound("id.notEquals=" + id);

        defaultOffreShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOffreShouldNotBeFound("id.greaterThan=" + id);

        defaultOffreShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOffreShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOffresByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where code equals to DEFAULT_CODE
        defaultOffreShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the offreList where code equals to UPDATED_CODE
        defaultOffreShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllOffresByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where code not equals to DEFAULT_CODE
        defaultOffreShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the offreList where code not equals to UPDATED_CODE
        defaultOffreShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllOffresByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where code in DEFAULT_CODE or UPDATED_CODE
        defaultOffreShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the offreList where code equals to UPDATED_CODE
        defaultOffreShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllOffresByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where code is not null
        defaultOffreShouldBeFound("code.specified=true");

        // Get all the offreList where code is null
        defaultOffreShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllOffresByCodeContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where code contains DEFAULT_CODE
        defaultOffreShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the offreList where code contains UPDATED_CODE
        defaultOffreShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllOffresByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where code does not contain DEFAULT_CODE
        defaultOffreShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the offreList where code does not contain UPDATED_CODE
        defaultOffreShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllOffresByNomPosteIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where nomPoste equals to DEFAULT_NOM_POSTE
        defaultOffreShouldBeFound("nomPoste.equals=" + DEFAULT_NOM_POSTE);

        // Get all the offreList where nomPoste equals to UPDATED_NOM_POSTE
        defaultOffreShouldNotBeFound("nomPoste.equals=" + UPDATED_NOM_POSTE);
    }

    @Test
    @Transactional
    public void getAllOffresByNomPosteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where nomPoste not equals to DEFAULT_NOM_POSTE
        defaultOffreShouldNotBeFound("nomPoste.notEquals=" + DEFAULT_NOM_POSTE);

        // Get all the offreList where nomPoste not equals to UPDATED_NOM_POSTE
        defaultOffreShouldBeFound("nomPoste.notEquals=" + UPDATED_NOM_POSTE);
    }

    @Test
    @Transactional
    public void getAllOffresByNomPosteIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where nomPoste in DEFAULT_NOM_POSTE or UPDATED_NOM_POSTE
        defaultOffreShouldBeFound("nomPoste.in=" + DEFAULT_NOM_POSTE + "," + UPDATED_NOM_POSTE);

        // Get all the offreList where nomPoste equals to UPDATED_NOM_POSTE
        defaultOffreShouldNotBeFound("nomPoste.in=" + UPDATED_NOM_POSTE);
    }

    @Test
    @Transactional
    public void getAllOffresByNomPosteIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where nomPoste is not null
        defaultOffreShouldBeFound("nomPoste.specified=true");

        // Get all the offreList where nomPoste is null
        defaultOffreShouldNotBeFound("nomPoste.specified=false");
    }
                @Test
    @Transactional
    public void getAllOffresByNomPosteContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where nomPoste contains DEFAULT_NOM_POSTE
        defaultOffreShouldBeFound("nomPoste.contains=" + DEFAULT_NOM_POSTE);

        // Get all the offreList where nomPoste contains UPDATED_NOM_POSTE
        defaultOffreShouldNotBeFound("nomPoste.contains=" + UPDATED_NOM_POSTE);
    }

    @Test
    @Transactional
    public void getAllOffresByNomPosteNotContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where nomPoste does not contain DEFAULT_NOM_POSTE
        defaultOffreShouldNotBeFound("nomPoste.doesNotContain=" + DEFAULT_NOM_POSTE);

        // Get all the offreList where nomPoste does not contain UPDATED_NOM_POSTE
        defaultOffreShouldBeFound("nomPoste.doesNotContain=" + UPDATED_NOM_POSTE);
    }


    @Test
    @Transactional
    public void getAllOffresByDescripIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where descrip equals to DEFAULT_DESCRIP
        defaultOffreShouldBeFound("descrip.equals=" + DEFAULT_DESCRIP);

        // Get all the offreList where descrip equals to UPDATED_DESCRIP
        defaultOffreShouldNotBeFound("descrip.equals=" + UPDATED_DESCRIP);
    }

    @Test
    @Transactional
    public void getAllOffresByDescripIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where descrip not equals to DEFAULT_DESCRIP
        defaultOffreShouldNotBeFound("descrip.notEquals=" + DEFAULT_DESCRIP);

        // Get all the offreList where descrip not equals to UPDATED_DESCRIP
        defaultOffreShouldBeFound("descrip.notEquals=" + UPDATED_DESCRIP);
    }

    @Test
    @Transactional
    public void getAllOffresByDescripIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where descrip in DEFAULT_DESCRIP or UPDATED_DESCRIP
        defaultOffreShouldBeFound("descrip.in=" + DEFAULT_DESCRIP + "," + UPDATED_DESCRIP);

        // Get all the offreList where descrip equals to UPDATED_DESCRIP
        defaultOffreShouldNotBeFound("descrip.in=" + UPDATED_DESCRIP);
    }

    @Test
    @Transactional
    public void getAllOffresByDescripIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where descrip is not null
        defaultOffreShouldBeFound("descrip.specified=true");

        // Get all the offreList where descrip is null
        defaultOffreShouldNotBeFound("descrip.specified=false");
    }
                @Test
    @Transactional
    public void getAllOffresByDescripContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where descrip contains DEFAULT_DESCRIP
        defaultOffreShouldBeFound("descrip.contains=" + DEFAULT_DESCRIP);

        // Get all the offreList where descrip contains UPDATED_DESCRIP
        defaultOffreShouldNotBeFound("descrip.contains=" + UPDATED_DESCRIP);
    }

    @Test
    @Transactional
    public void getAllOffresByDescripNotContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where descrip does not contain DEFAULT_DESCRIP
        defaultOffreShouldNotBeFound("descrip.doesNotContain=" + DEFAULT_DESCRIP);

        // Get all the offreList where descrip does not contain UPDATED_DESCRIP
        defaultOffreShouldBeFound("descrip.doesNotContain=" + UPDATED_DESCRIP);
    }


    @Test
    @Transactional
    public void getAllOffresByDateOuvertureIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateOuverture equals to DEFAULT_DATE_OUVERTURE
        defaultOffreShouldBeFound("dateOuverture.equals=" + DEFAULT_DATE_OUVERTURE);

        // Get all the offreList where dateOuverture equals to UPDATED_DATE_OUVERTURE
        defaultOffreShouldNotBeFound("dateOuverture.equals=" + UPDATED_DATE_OUVERTURE);
    }

    @Test
    @Transactional
    public void getAllOffresByDateOuvertureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateOuverture not equals to DEFAULT_DATE_OUVERTURE
        defaultOffreShouldNotBeFound("dateOuverture.notEquals=" + DEFAULT_DATE_OUVERTURE);

        // Get all the offreList where dateOuverture not equals to UPDATED_DATE_OUVERTURE
        defaultOffreShouldBeFound("dateOuverture.notEquals=" + UPDATED_DATE_OUVERTURE);
    }

    @Test
    @Transactional
    public void getAllOffresByDateOuvertureIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateOuverture in DEFAULT_DATE_OUVERTURE or UPDATED_DATE_OUVERTURE
        defaultOffreShouldBeFound("dateOuverture.in=" + DEFAULT_DATE_OUVERTURE + "," + UPDATED_DATE_OUVERTURE);

        // Get all the offreList where dateOuverture equals to UPDATED_DATE_OUVERTURE
        defaultOffreShouldNotBeFound("dateOuverture.in=" + UPDATED_DATE_OUVERTURE);
    }

    @Test
    @Transactional
    public void getAllOffresByDateOuvertureIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateOuverture is not null
        defaultOffreShouldBeFound("dateOuverture.specified=true");

        // Get all the offreList where dateOuverture is null
        defaultOffreShouldNotBeFound("dateOuverture.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffresByDateClotureIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateCloture equals to DEFAULT_DATE_CLOTURE
        defaultOffreShouldBeFound("dateCloture.equals=" + DEFAULT_DATE_CLOTURE);

        // Get all the offreList where dateCloture equals to UPDATED_DATE_CLOTURE
        defaultOffreShouldNotBeFound("dateCloture.equals=" + UPDATED_DATE_CLOTURE);
    }

    @Test
    @Transactional
    public void getAllOffresByDateClotureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateCloture not equals to DEFAULT_DATE_CLOTURE
        defaultOffreShouldNotBeFound("dateCloture.notEquals=" + DEFAULT_DATE_CLOTURE);

        // Get all the offreList where dateCloture not equals to UPDATED_DATE_CLOTURE
        defaultOffreShouldBeFound("dateCloture.notEquals=" + UPDATED_DATE_CLOTURE);
    }

    @Test
    @Transactional
    public void getAllOffresByDateClotureIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateCloture in DEFAULT_DATE_CLOTURE or UPDATED_DATE_CLOTURE
        defaultOffreShouldBeFound("dateCloture.in=" + DEFAULT_DATE_CLOTURE + "," + UPDATED_DATE_CLOTURE);

        // Get all the offreList where dateCloture equals to UPDATED_DATE_CLOTURE
        defaultOffreShouldNotBeFound("dateCloture.in=" + UPDATED_DATE_CLOTURE);
    }

    @Test
    @Transactional
    public void getAllOffresByDateClotureIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateCloture is not null
        defaultOffreShouldBeFound("dateCloture.specified=true");

        // Get all the offreList where dateCloture is null
        defaultOffreShouldNotBeFound("dateCloture.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffresByIsArchiveIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where isArchive equals to DEFAULT_IS_ARCHIVE
        defaultOffreShouldBeFound("isArchive.equals=" + DEFAULT_IS_ARCHIVE);

        // Get all the offreList where isArchive equals to UPDATED_IS_ARCHIVE
        defaultOffreShouldNotBeFound("isArchive.equals=" + UPDATED_IS_ARCHIVE);
    }

    @Test
    @Transactional
    public void getAllOffresByIsArchiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where isArchive not equals to DEFAULT_IS_ARCHIVE
        defaultOffreShouldNotBeFound("isArchive.notEquals=" + DEFAULT_IS_ARCHIVE);

        // Get all the offreList where isArchive not equals to UPDATED_IS_ARCHIVE
        defaultOffreShouldBeFound("isArchive.notEquals=" + UPDATED_IS_ARCHIVE);
    }

    @Test
    @Transactional
    public void getAllOffresByIsArchiveIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where isArchive in DEFAULT_IS_ARCHIVE or UPDATED_IS_ARCHIVE
        defaultOffreShouldBeFound("isArchive.in=" + DEFAULT_IS_ARCHIVE + "," + UPDATED_IS_ARCHIVE);

        // Get all the offreList where isArchive equals to UPDATED_IS_ARCHIVE
        defaultOffreShouldNotBeFound("isArchive.in=" + UPDATED_IS_ARCHIVE);
    }

    @Test
    @Transactional
    public void getAllOffresByIsArchiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where isArchive is not null
        defaultOffreShouldBeFound("isArchive.specified=true");

        // Get all the offreList where isArchive is null
        defaultOffreShouldNotBeFound("isArchive.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffresByDateConcoursIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateConcours equals to DEFAULT_DATE_CONCOURS
        defaultOffreShouldBeFound("dateConcours.equals=" + DEFAULT_DATE_CONCOURS);

        // Get all the offreList where dateConcours equals to UPDATED_DATE_CONCOURS
        defaultOffreShouldNotBeFound("dateConcours.equals=" + UPDATED_DATE_CONCOURS);
    }

    @Test
    @Transactional
    public void getAllOffresByDateConcoursIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateConcours not equals to DEFAULT_DATE_CONCOURS
        defaultOffreShouldNotBeFound("dateConcours.notEquals=" + DEFAULT_DATE_CONCOURS);

        // Get all the offreList where dateConcours not equals to UPDATED_DATE_CONCOURS
        defaultOffreShouldBeFound("dateConcours.notEquals=" + UPDATED_DATE_CONCOURS);
    }

    @Test
    @Transactional
    public void getAllOffresByDateConcoursIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateConcours in DEFAULT_DATE_CONCOURS or UPDATED_DATE_CONCOURS
        defaultOffreShouldBeFound("dateConcours.in=" + DEFAULT_DATE_CONCOURS + "," + UPDATED_DATE_CONCOURS);

        // Get all the offreList where dateConcours equals to UPDATED_DATE_CONCOURS
        defaultOffreShouldNotBeFound("dateConcours.in=" + UPDATED_DATE_CONCOURS);
    }

    @Test
    @Transactional
    public void getAllOffresByDateConcoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where dateConcours is not null
        defaultOffreShouldBeFound("dateConcours.specified=true");

        // Get all the offreList where dateConcours is null
        defaultOffreShouldNotBeFound("dateConcours.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffresByAgeLimiteIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where ageLimite equals to DEFAULT_AGE_LIMITE
        defaultOffreShouldBeFound("ageLimite.equals=" + DEFAULT_AGE_LIMITE);

        // Get all the offreList where ageLimite equals to UPDATED_AGE_LIMITE
        defaultOffreShouldNotBeFound("ageLimite.equals=" + UPDATED_AGE_LIMITE);
    }

    @Test
    @Transactional
    public void getAllOffresByAgeLimiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where ageLimite not equals to DEFAULT_AGE_LIMITE
        defaultOffreShouldNotBeFound("ageLimite.notEquals=" + DEFAULT_AGE_LIMITE);

        // Get all the offreList where ageLimite not equals to UPDATED_AGE_LIMITE
        defaultOffreShouldBeFound("ageLimite.notEquals=" + UPDATED_AGE_LIMITE);
    }

    @Test
    @Transactional
    public void getAllOffresByAgeLimiteIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where ageLimite in DEFAULT_AGE_LIMITE or UPDATED_AGE_LIMITE
        defaultOffreShouldBeFound("ageLimite.in=" + DEFAULT_AGE_LIMITE + "," + UPDATED_AGE_LIMITE);

        // Get all the offreList where ageLimite equals to UPDATED_AGE_LIMITE
        defaultOffreShouldNotBeFound("ageLimite.in=" + UPDATED_AGE_LIMITE);
    }

    @Test
    @Transactional
    public void getAllOffresByAgeLimiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where ageLimite is not null
        defaultOffreShouldBeFound("ageLimite.specified=true");

        // Get all the offreList where ageLimite is null
        defaultOffreShouldNotBeFound("ageLimite.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffresByAgeLimiteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where ageLimite is greater than or equal to DEFAULT_AGE_LIMITE
        defaultOffreShouldBeFound("ageLimite.greaterThanOrEqual=" + DEFAULT_AGE_LIMITE);

        // Get all the offreList where ageLimite is greater than or equal to UPDATED_AGE_LIMITE
        defaultOffreShouldNotBeFound("ageLimite.greaterThanOrEqual=" + UPDATED_AGE_LIMITE);
    }

    @Test
    @Transactional
    public void getAllOffresByAgeLimiteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where ageLimite is less than or equal to DEFAULT_AGE_LIMITE
        defaultOffreShouldBeFound("ageLimite.lessThanOrEqual=" + DEFAULT_AGE_LIMITE);

        // Get all the offreList where ageLimite is less than or equal to SMALLER_AGE_LIMITE
        defaultOffreShouldNotBeFound("ageLimite.lessThanOrEqual=" + SMALLER_AGE_LIMITE);
    }

    @Test
    @Transactional
    public void getAllOffresByAgeLimiteIsLessThanSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where ageLimite is less than DEFAULT_AGE_LIMITE
        defaultOffreShouldNotBeFound("ageLimite.lessThan=" + DEFAULT_AGE_LIMITE);

        // Get all the offreList where ageLimite is less than UPDATED_AGE_LIMITE
        defaultOffreShouldBeFound("ageLimite.lessThan=" + UPDATED_AGE_LIMITE);
    }

    @Test
    @Transactional
    public void getAllOffresByAgeLimiteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where ageLimite is greater than DEFAULT_AGE_LIMITE
        defaultOffreShouldNotBeFound("ageLimite.greaterThan=" + DEFAULT_AGE_LIMITE);

        // Get all the offreList where ageLimite is greater than SMALLER_AGE_LIMITE
        defaultOffreShouldBeFound("ageLimite.greaterThan=" + SMALLER_AGE_LIMITE);
    }


    @Test
    @Transactional
    public void getAllOffresByMinisterConcernerIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);
        MinisterConcerner ministerConcerner = MinisterConcernerResourceIT.createEntity(em);
        em.persist(ministerConcerner);
        em.flush();
        offre.setMinisterConcerner(ministerConcerner);
        offreRepository.saveAndFlush(offre);
        Long ministerConcernerId = ministerConcerner.getId();

        // Get all the offreList where ministerConcerner equals to ministerConcernerId
        defaultOffreShouldBeFound("ministerConcernerId.equals=" + ministerConcernerId);

        // Get all the offreList where ministerConcerner equals to ministerConcernerId + 1
        defaultOffreShouldNotBeFound("ministerConcernerId.equals=" + (ministerConcernerId + 1));
    }


    @Test
    @Transactional
    public void getAllOffresByCategrieIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);
        Categorie categrie = CategorieResourceIT.createEntity(em);
        em.persist(categrie);
        em.flush();
        offre.setCategrie(categrie);
        offreRepository.saveAndFlush(offre);
        Long categrieId = categrie.getId();

        // Get all the offreList where categrie equals to categrieId
        defaultOffreShouldBeFound("categrieId.equals=" + categrieId);

        // Get all the offreList where categrie equals to categrieId + 1
        defaultOffreShouldNotBeFound("categrieId.equals=" + (categrieId + 1));
    }


    @Test
    @Transactional
    public void getAllOffresByOptionIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);
        Option option = OptionResourceIT.createEntity(em);
        em.persist(option);
        em.flush();
        offre.setOption(option);
        offreRepository.saveAndFlush(offre);
        Long optionId = option.getId();

        // Get all the offreList where option equals to optionId
        defaultOffreShouldBeFound("optionId.equals=" + optionId);

        // Get all the offreList where option equals to optionId + 1
        defaultOffreShouldNotBeFound("optionId.equals=" + (optionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOffreShouldBeFound(String filter) throws Exception {
        restOffreMockMvc.perform(get("/api/offres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offre.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nomPoste").value(hasItem(DEFAULT_NOM_POSTE)))
            .andExpect(jsonPath("$.[*].descrip").value(hasItem(DEFAULT_DESCRIP)))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dateCloture").value(hasItem(DEFAULT_DATE_CLOTURE.toString())))
            .andExpect(jsonPath("$.[*].isArchive").value(hasItem(DEFAULT_IS_ARCHIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateConcours").value(hasItem(DEFAULT_DATE_CONCOURS.toString())))
            .andExpect(jsonPath("$.[*].ageLimite").value(hasItem(DEFAULT_AGE_LIMITE)));

        // Check, that the count call also returns 1
        restOffreMockMvc.perform(get("/api/offres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOffreShouldNotBeFound(String filter) throws Exception {
        restOffreMockMvc.perform(get("/api/offres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOffreMockMvc.perform(get("/api/offres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOffre() throws Exception {
        // Get the offre
        restOffreMockMvc.perform(get("/api/offres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffre() throws Exception {
        // Initialize the database
        offreService.save(offre);

        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Update the offre
        Offre updatedOffre = offreRepository.findById(offre.getId()).get();
        // Disconnect from session so that the updates on updatedOffre are not directly saved in db
        em.detach(updatedOffre);
        updatedOffre
            .code(UPDATED_CODE)
            .nomPoste(UPDATED_NOM_POSTE)
            .descrip(UPDATED_DESCRIP)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .isArchive(UPDATED_IS_ARCHIVE)
            .dateConcours(UPDATED_DATE_CONCOURS)
            .ageLimite(UPDATED_AGE_LIMITE);

        restOffreMockMvc.perform(put("/api/offres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffre)))
            .andExpect(status().isOk());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOffre.getNomPoste()).isEqualTo(UPDATED_NOM_POSTE);
        assertThat(testOffre.getDescrip()).isEqualTo(UPDATED_DESCRIP);
        assertThat(testOffre.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testOffre.getDateCloture()).isEqualTo(UPDATED_DATE_CLOTURE);
        assertThat(testOffre.isIsArchive()).isEqualTo(UPDATED_IS_ARCHIVE);
        assertThat(testOffre.getDateConcours()).isEqualTo(UPDATED_DATE_CONCOURS);
        assertThat(testOffre.getAgeLimite()).isEqualTo(UPDATED_AGE_LIMITE);
    }

    @Test
    @Transactional
    public void updateNonExistingOffre() throws Exception {
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffreMockMvc.perform(put("/api/offres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offre)))
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOffre() throws Exception {
        // Initialize the database
        offreService.save(offre);

        int databaseSizeBeforeDelete = offreRepository.findAll().size();

        // Delete the offre
        restOffreMockMvc.perform(delete("/api/offres/{id}", offre.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
