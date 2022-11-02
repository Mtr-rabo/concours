package org.ne.concours.web.rest;

import org.ne.concours.ConcoursFonctionPubliqueApp;
import org.ne.concours.domain.Candidat;
import org.ne.concours.repository.CandidatRepository;
import org.ne.concours.service.CandidatService;
import org.ne.concours.service.dto.CandidatCriteria;
import org.ne.concours.service.CandidatQueryService;

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
 * Integration tests for the {@link CandidatResource} REST controller.
 */
@SpringBootTest(classes = ConcoursFonctionPubliqueApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CandidatResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LIEU_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISSANCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final Integer SMALLER_AGE = 1 - 1;

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private CandidatService candidatService;

    @Autowired
    private CandidatQueryService candidatQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidatMockMvc;

    private Candidat candidat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidat createEntity(EntityManager em) {
        Candidat candidat = new Candidat()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .age(DEFAULT_AGE)
            .adresse(DEFAULT_ADRESSE);
        return candidat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidat createUpdatedEntity(EntityManager em) {
        Candidat candidat = new Candidat()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .age(UPDATED_AGE)
            .adresse(UPDATED_ADRESSE);
        return candidat;
    }

    @BeforeEach
    public void initTest() {
        candidat = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidat() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();
        // Create the Candidat
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isCreated());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate + 1);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCandidat.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testCandidat.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testCandidat.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testCandidat.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testCandidat.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testCandidat.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    public void createCandidatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();

        // Create the Candidat with an existing ID
        candidat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCandidats() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList
        restCandidatMockMvc.perform(get("/api/candidats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)));
    }
    
    @Test
    @Transactional
    public void getCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", candidat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidat.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE));
    }


    @Test
    @Transactional
    public void getCandidatsByIdFiltering() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        Long id = candidat.getId();

        defaultCandidatShouldBeFound("id.equals=" + id);
        defaultCandidatShouldNotBeFound("id.notEquals=" + id);

        defaultCandidatShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCandidatShouldNotBeFound("id.greaterThan=" + id);

        defaultCandidatShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCandidatShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCandidatsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where nom equals to DEFAULT_NOM
        defaultCandidatShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the candidatList where nom equals to UPDATED_NOM
        defaultCandidatShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where nom not equals to DEFAULT_NOM
        defaultCandidatShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the candidatList where nom not equals to UPDATED_NOM
        defaultCandidatShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultCandidatShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the candidatList where nom equals to UPDATED_NOM
        defaultCandidatShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where nom is not null
        defaultCandidatShouldBeFound("nom.specified=true");

        // Get all the candidatList where nom is null
        defaultCandidatShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllCandidatsByNomContainsSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where nom contains DEFAULT_NOM
        defaultCandidatShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the candidatList where nom contains UPDATED_NOM
        defaultCandidatShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where nom does not contain DEFAULT_NOM
        defaultCandidatShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the candidatList where nom does not contain UPDATED_NOM
        defaultCandidatShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllCandidatsByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where prenom equals to DEFAULT_PRENOM
        defaultCandidatShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the candidatList where prenom equals to UPDATED_PRENOM
        defaultCandidatShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where prenom not equals to DEFAULT_PRENOM
        defaultCandidatShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the candidatList where prenom not equals to UPDATED_PRENOM
        defaultCandidatShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultCandidatShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the candidatList where prenom equals to UPDATED_PRENOM
        defaultCandidatShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where prenom is not null
        defaultCandidatShouldBeFound("prenom.specified=true");

        // Get all the candidatList where prenom is null
        defaultCandidatShouldNotBeFound("prenom.specified=false");
    }
                @Test
    @Transactional
    public void getAllCandidatsByPrenomContainsSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where prenom contains DEFAULT_PRENOM
        defaultCandidatShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the candidatList where prenom contains UPDATED_PRENOM
        defaultCandidatShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllCandidatsByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where prenom does not contain DEFAULT_PRENOM
        defaultCandidatShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the candidatList where prenom does not contain UPDATED_PRENOM
        defaultCandidatShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }


    @Test
    @Transactional
    public void getAllCandidatsByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where telephone equals to DEFAULT_TELEPHONE
        defaultCandidatShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the candidatList where telephone equals to UPDATED_TELEPHONE
        defaultCandidatShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where telephone not equals to DEFAULT_TELEPHONE
        defaultCandidatShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the candidatList where telephone not equals to UPDATED_TELEPHONE
        defaultCandidatShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultCandidatShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the candidatList where telephone equals to UPDATED_TELEPHONE
        defaultCandidatShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where telephone is not null
        defaultCandidatShouldBeFound("telephone.specified=true");

        // Get all the candidatList where telephone is null
        defaultCandidatShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllCandidatsByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where telephone contains DEFAULT_TELEPHONE
        defaultCandidatShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the candidatList where telephone contains UPDATED_TELEPHONE
        defaultCandidatShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where telephone does not contain DEFAULT_TELEPHONE
        defaultCandidatShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the candidatList where telephone does not contain UPDATED_TELEPHONE
        defaultCandidatShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllCandidatsByDateNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where dateNaissance equals to DEFAULT_DATE_NAISSANCE
        defaultCandidatShouldBeFound("dateNaissance.equals=" + DEFAULT_DATE_NAISSANCE);

        // Get all the candidatList where dateNaissance equals to UPDATED_DATE_NAISSANCE
        defaultCandidatShouldNotBeFound("dateNaissance.equals=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByDateNaissanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where dateNaissance not equals to DEFAULT_DATE_NAISSANCE
        defaultCandidatShouldNotBeFound("dateNaissance.notEquals=" + DEFAULT_DATE_NAISSANCE);

        // Get all the candidatList where dateNaissance not equals to UPDATED_DATE_NAISSANCE
        defaultCandidatShouldBeFound("dateNaissance.notEquals=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByDateNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where dateNaissance in DEFAULT_DATE_NAISSANCE or UPDATED_DATE_NAISSANCE
        defaultCandidatShouldBeFound("dateNaissance.in=" + DEFAULT_DATE_NAISSANCE + "," + UPDATED_DATE_NAISSANCE);

        // Get all the candidatList where dateNaissance equals to UPDATED_DATE_NAISSANCE
        defaultCandidatShouldNotBeFound("dateNaissance.in=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByDateNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where dateNaissance is not null
        defaultCandidatShouldBeFound("dateNaissance.specified=true");

        // Get all the candidatList where dateNaissance is null
        defaultCandidatShouldNotBeFound("dateNaissance.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatsByLieuNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where lieuNaissance equals to DEFAULT_LIEU_NAISSANCE
        defaultCandidatShouldBeFound("lieuNaissance.equals=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the candidatList where lieuNaissance equals to UPDATED_LIEU_NAISSANCE
        defaultCandidatShouldNotBeFound("lieuNaissance.equals=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByLieuNaissanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where lieuNaissance not equals to DEFAULT_LIEU_NAISSANCE
        defaultCandidatShouldNotBeFound("lieuNaissance.notEquals=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the candidatList where lieuNaissance not equals to UPDATED_LIEU_NAISSANCE
        defaultCandidatShouldBeFound("lieuNaissance.notEquals=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByLieuNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where lieuNaissance in DEFAULT_LIEU_NAISSANCE or UPDATED_LIEU_NAISSANCE
        defaultCandidatShouldBeFound("lieuNaissance.in=" + DEFAULT_LIEU_NAISSANCE + "," + UPDATED_LIEU_NAISSANCE);

        // Get all the candidatList where lieuNaissance equals to UPDATED_LIEU_NAISSANCE
        defaultCandidatShouldNotBeFound("lieuNaissance.in=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByLieuNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where lieuNaissance is not null
        defaultCandidatShouldBeFound("lieuNaissance.specified=true");

        // Get all the candidatList where lieuNaissance is null
        defaultCandidatShouldNotBeFound("lieuNaissance.specified=false");
    }
                @Test
    @Transactional
    public void getAllCandidatsByLieuNaissanceContainsSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where lieuNaissance contains DEFAULT_LIEU_NAISSANCE
        defaultCandidatShouldBeFound("lieuNaissance.contains=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the candidatList where lieuNaissance contains UPDATED_LIEU_NAISSANCE
        defaultCandidatShouldNotBeFound("lieuNaissance.contains=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByLieuNaissanceNotContainsSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where lieuNaissance does not contain DEFAULT_LIEU_NAISSANCE
        defaultCandidatShouldNotBeFound("lieuNaissance.doesNotContain=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the candidatList where lieuNaissance does not contain UPDATED_LIEU_NAISSANCE
        defaultCandidatShouldBeFound("lieuNaissance.doesNotContain=" + UPDATED_LIEU_NAISSANCE);
    }


    @Test
    @Transactional
    public void getAllCandidatsByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where age equals to DEFAULT_AGE
        defaultCandidatShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the candidatList where age equals to UPDATED_AGE
        defaultCandidatShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where age not equals to DEFAULT_AGE
        defaultCandidatShouldNotBeFound("age.notEquals=" + DEFAULT_AGE);

        // Get all the candidatList where age not equals to UPDATED_AGE
        defaultCandidatShouldBeFound("age.notEquals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where age in DEFAULT_AGE or UPDATED_AGE
        defaultCandidatShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the candidatList where age equals to UPDATED_AGE
        defaultCandidatShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where age is not null
        defaultCandidatShouldBeFound("age.specified=true");

        // Get all the candidatList where age is null
        defaultCandidatShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    public void getAllCandidatsByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where age is greater than or equal to DEFAULT_AGE
        defaultCandidatShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the candidatList where age is greater than or equal to UPDATED_AGE
        defaultCandidatShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where age is less than or equal to DEFAULT_AGE
        defaultCandidatShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the candidatList where age is less than or equal to SMALLER_AGE
        defaultCandidatShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where age is less than DEFAULT_AGE
        defaultCandidatShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the candidatList where age is less than UPDATED_AGE
        defaultCandidatShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where age is greater than DEFAULT_AGE
        defaultCandidatShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the candidatList where age is greater than SMALLER_AGE
        defaultCandidatShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }


    @Test
    @Transactional
    public void getAllCandidatsByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where adresse equals to DEFAULT_ADRESSE
        defaultCandidatShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the candidatList where adresse equals to UPDATED_ADRESSE
        defaultCandidatShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where adresse not equals to DEFAULT_ADRESSE
        defaultCandidatShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the candidatList where adresse not equals to UPDATED_ADRESSE
        defaultCandidatShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultCandidatShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the candidatList where adresse equals to UPDATED_ADRESSE
        defaultCandidatShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where adresse is not null
        defaultCandidatShouldBeFound("adresse.specified=true");

        // Get all the candidatList where adresse is null
        defaultCandidatShouldNotBeFound("adresse.specified=false");
    }
                @Test
    @Transactional
    public void getAllCandidatsByAdresseContainsSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where adresse contains DEFAULT_ADRESSE
        defaultCandidatShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the candidatList where adresse contains UPDATED_ADRESSE
        defaultCandidatShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllCandidatsByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList where adresse does not contain DEFAULT_ADRESSE
        defaultCandidatShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the candidatList where adresse does not contain UPDATED_ADRESSE
        defaultCandidatShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCandidatShouldBeFound(String filter) throws Exception {
        restCandidatMockMvc.perform(get("/api/candidats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)));

        // Check, that the count call also returns 1
        restCandidatMockMvc.perform(get("/api/candidats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCandidatShouldNotBeFound(String filter) throws Exception {
        restCandidatMockMvc.perform(get("/api/candidats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCandidatMockMvc.perform(get("/api/candidats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCandidat() throws Exception {
        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidat() throws Exception {
        // Initialize the database
        candidatService.save(candidat);

        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // Update the candidat
        Candidat updatedCandidat = candidatRepository.findById(candidat.getId()).get();
        // Disconnect from session so that the updates on updatedCandidat are not directly saved in db
        em.detach(updatedCandidat);
        updatedCandidat
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .age(UPDATED_AGE)
            .adresse(UPDATED_ADRESSE);

        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandidat)))
            .andExpect(status().isOk());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCandidat.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCandidat.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCandidat.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testCandidat.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testCandidat.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testCandidat.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidat() throws Exception {
        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCandidat() throws Exception {
        // Initialize the database
        candidatService.save(candidat);

        int databaseSizeBeforeDelete = candidatRepository.findAll().size();

        // Delete the candidat
        restCandidatMockMvc.perform(delete("/api/candidats/{id}", candidat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
