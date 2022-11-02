package org.ne.concours.web.rest;

import org.ne.concours.ConcoursFonctionPubliqueApp;
import org.ne.concours.domain.MinisterConcerner;
import org.ne.concours.repository.MinisterConcernerRepository;
import org.ne.concours.service.MinisterConcernerService;
import org.ne.concours.service.dto.MinisterConcernerCriteria;
import org.ne.concours.service.MinisterConcernerQueryService;

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
 * Integration tests for the {@link MinisterConcernerResource} REST controller.
 */
@SpringBootTest(classes = ConcoursFonctionPubliqueApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MinisterConcernerResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAINE = "AAAAAAAAAA";
    private static final String UPDATED_DOMAINE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MinisterConcernerRepository ministerConcernerRepository;

    @Autowired
    private MinisterConcernerService ministerConcernerService;

    @Autowired
    private MinisterConcernerQueryService ministerConcernerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMinisterConcernerMockMvc;

    private MinisterConcerner ministerConcerner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MinisterConcerner createEntity(EntityManager em) {
        MinisterConcerner ministerConcerner = new MinisterConcerner()
            .nom(DEFAULT_NOM)
            .domaine(DEFAULT_DOMAINE)
            .description(DEFAULT_DESCRIPTION);
        return ministerConcerner;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MinisterConcerner createUpdatedEntity(EntityManager em) {
        MinisterConcerner ministerConcerner = new MinisterConcerner()
            .nom(UPDATED_NOM)
            .domaine(UPDATED_DOMAINE)
            .description(UPDATED_DESCRIPTION);
        return ministerConcerner;
    }

    @BeforeEach
    public void initTest() {
        ministerConcerner = createEntity(em);
    }

    @Test
    @Transactional
    public void createMinisterConcerner() throws Exception {
        int databaseSizeBeforeCreate = ministerConcernerRepository.findAll().size();
        // Create the MinisterConcerner
        restMinisterConcernerMockMvc.perform(post("/api/minister-concerners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ministerConcerner)))
            .andExpect(status().isCreated());

        // Validate the MinisterConcerner in the database
        List<MinisterConcerner> ministerConcernerList = ministerConcernerRepository.findAll();
        assertThat(ministerConcernerList).hasSize(databaseSizeBeforeCreate + 1);
        MinisterConcerner testMinisterConcerner = ministerConcernerList.get(ministerConcernerList.size() - 1);
        assertThat(testMinisterConcerner.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMinisterConcerner.getDomaine()).isEqualTo(DEFAULT_DOMAINE);
        assertThat(testMinisterConcerner.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMinisterConcernerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ministerConcernerRepository.findAll().size();

        // Create the MinisterConcerner with an existing ID
        ministerConcerner.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMinisterConcernerMockMvc.perform(post("/api/minister-concerners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ministerConcerner)))
            .andExpect(status().isBadRequest());

        // Validate the MinisterConcerner in the database
        List<MinisterConcerner> ministerConcernerList = ministerConcernerRepository.findAll();
        assertThat(ministerConcernerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMinisterConcerners() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList
        restMinisterConcernerMockMvc.perform(get("/api/minister-concerners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ministerConcerner.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getMinisterConcerner() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get the ministerConcerner
        restMinisterConcernerMockMvc.perform(get("/api/minister-concerners/{id}", ministerConcerner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ministerConcerner.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.domaine").value(DEFAULT_DOMAINE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getMinisterConcernersByIdFiltering() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        Long id = ministerConcerner.getId();

        defaultMinisterConcernerShouldBeFound("id.equals=" + id);
        defaultMinisterConcernerShouldNotBeFound("id.notEquals=" + id);

        defaultMinisterConcernerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMinisterConcernerShouldNotBeFound("id.greaterThan=" + id);

        defaultMinisterConcernerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMinisterConcernerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMinisterConcernersByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where nom equals to DEFAULT_NOM
        defaultMinisterConcernerShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the ministerConcernerList where nom equals to UPDATED_NOM
        defaultMinisterConcernerShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where nom not equals to DEFAULT_NOM
        defaultMinisterConcernerShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the ministerConcernerList where nom not equals to UPDATED_NOM
        defaultMinisterConcernerShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByNomIsInShouldWork() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultMinisterConcernerShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the ministerConcernerList where nom equals to UPDATED_NOM
        defaultMinisterConcernerShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where nom is not null
        defaultMinisterConcernerShouldBeFound("nom.specified=true");

        // Get all the ministerConcernerList where nom is null
        defaultMinisterConcernerShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllMinisterConcernersByNomContainsSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where nom contains DEFAULT_NOM
        defaultMinisterConcernerShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the ministerConcernerList where nom contains UPDATED_NOM
        defaultMinisterConcernerShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByNomNotContainsSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where nom does not contain DEFAULT_NOM
        defaultMinisterConcernerShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the ministerConcernerList where nom does not contain UPDATED_NOM
        defaultMinisterConcernerShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllMinisterConcernersByDomaineIsEqualToSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where domaine equals to DEFAULT_DOMAINE
        defaultMinisterConcernerShouldBeFound("domaine.equals=" + DEFAULT_DOMAINE);

        // Get all the ministerConcernerList where domaine equals to UPDATED_DOMAINE
        defaultMinisterConcernerShouldNotBeFound("domaine.equals=" + UPDATED_DOMAINE);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByDomaineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where domaine not equals to DEFAULT_DOMAINE
        defaultMinisterConcernerShouldNotBeFound("domaine.notEquals=" + DEFAULT_DOMAINE);

        // Get all the ministerConcernerList where domaine not equals to UPDATED_DOMAINE
        defaultMinisterConcernerShouldBeFound("domaine.notEquals=" + UPDATED_DOMAINE);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByDomaineIsInShouldWork() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where domaine in DEFAULT_DOMAINE or UPDATED_DOMAINE
        defaultMinisterConcernerShouldBeFound("domaine.in=" + DEFAULT_DOMAINE + "," + UPDATED_DOMAINE);

        // Get all the ministerConcernerList where domaine equals to UPDATED_DOMAINE
        defaultMinisterConcernerShouldNotBeFound("domaine.in=" + UPDATED_DOMAINE);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByDomaineIsNullOrNotNull() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where domaine is not null
        defaultMinisterConcernerShouldBeFound("domaine.specified=true");

        // Get all the ministerConcernerList where domaine is null
        defaultMinisterConcernerShouldNotBeFound("domaine.specified=false");
    }
                @Test
    @Transactional
    public void getAllMinisterConcernersByDomaineContainsSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where domaine contains DEFAULT_DOMAINE
        defaultMinisterConcernerShouldBeFound("domaine.contains=" + DEFAULT_DOMAINE);

        // Get all the ministerConcernerList where domaine contains UPDATED_DOMAINE
        defaultMinisterConcernerShouldNotBeFound("domaine.contains=" + UPDATED_DOMAINE);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByDomaineNotContainsSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where domaine does not contain DEFAULT_DOMAINE
        defaultMinisterConcernerShouldNotBeFound("domaine.doesNotContain=" + DEFAULT_DOMAINE);

        // Get all the ministerConcernerList where domaine does not contain UPDATED_DOMAINE
        defaultMinisterConcernerShouldBeFound("domaine.doesNotContain=" + UPDATED_DOMAINE);
    }


    @Test
    @Transactional
    public void getAllMinisterConcernersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where description equals to DEFAULT_DESCRIPTION
        defaultMinisterConcernerShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the ministerConcernerList where description equals to UPDATED_DESCRIPTION
        defaultMinisterConcernerShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where description not equals to DEFAULT_DESCRIPTION
        defaultMinisterConcernerShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the ministerConcernerList where description not equals to UPDATED_DESCRIPTION
        defaultMinisterConcernerShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMinisterConcernerShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the ministerConcernerList where description equals to UPDATED_DESCRIPTION
        defaultMinisterConcernerShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where description is not null
        defaultMinisterConcernerShouldBeFound("description.specified=true");

        // Get all the ministerConcernerList where description is null
        defaultMinisterConcernerShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMinisterConcernersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where description contains DEFAULT_DESCRIPTION
        defaultMinisterConcernerShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the ministerConcernerList where description contains UPDATED_DESCRIPTION
        defaultMinisterConcernerShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMinisterConcernersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        ministerConcernerRepository.saveAndFlush(ministerConcerner);

        // Get all the ministerConcernerList where description does not contain DEFAULT_DESCRIPTION
        defaultMinisterConcernerShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the ministerConcernerList where description does not contain UPDATED_DESCRIPTION
        defaultMinisterConcernerShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMinisterConcernerShouldBeFound(String filter) throws Exception {
        restMinisterConcernerMockMvc.perform(get("/api/minister-concerners?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ministerConcerner.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].domaine").value(hasItem(DEFAULT_DOMAINE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restMinisterConcernerMockMvc.perform(get("/api/minister-concerners/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMinisterConcernerShouldNotBeFound(String filter) throws Exception {
        restMinisterConcernerMockMvc.perform(get("/api/minister-concerners?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMinisterConcernerMockMvc.perform(get("/api/minister-concerners/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMinisterConcerner() throws Exception {
        // Get the ministerConcerner
        restMinisterConcernerMockMvc.perform(get("/api/minister-concerners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMinisterConcerner() throws Exception {
        // Initialize the database
        ministerConcernerService.save(ministerConcerner);

        int databaseSizeBeforeUpdate = ministerConcernerRepository.findAll().size();

        // Update the ministerConcerner
        MinisterConcerner updatedMinisterConcerner = ministerConcernerRepository.findById(ministerConcerner.getId()).get();
        // Disconnect from session so that the updates on updatedMinisterConcerner are not directly saved in db
        em.detach(updatedMinisterConcerner);
        updatedMinisterConcerner
            .nom(UPDATED_NOM)
            .domaine(UPDATED_DOMAINE)
            .description(UPDATED_DESCRIPTION);

        restMinisterConcernerMockMvc.perform(put("/api/minister-concerners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMinisterConcerner)))
            .andExpect(status().isOk());

        // Validate the MinisterConcerner in the database
        List<MinisterConcerner> ministerConcernerList = ministerConcernerRepository.findAll();
        assertThat(ministerConcernerList).hasSize(databaseSizeBeforeUpdate);
        MinisterConcerner testMinisterConcerner = ministerConcernerList.get(ministerConcernerList.size() - 1);
        assertThat(testMinisterConcerner.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMinisterConcerner.getDomaine()).isEqualTo(UPDATED_DOMAINE);
        assertThat(testMinisterConcerner.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMinisterConcerner() throws Exception {
        int databaseSizeBeforeUpdate = ministerConcernerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMinisterConcernerMockMvc.perform(put("/api/minister-concerners")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ministerConcerner)))
            .andExpect(status().isBadRequest());

        // Validate the MinisterConcerner in the database
        List<MinisterConcerner> ministerConcernerList = ministerConcernerRepository.findAll();
        assertThat(ministerConcernerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMinisterConcerner() throws Exception {
        // Initialize the database
        ministerConcernerService.save(ministerConcerner);

        int databaseSizeBeforeDelete = ministerConcernerRepository.findAll().size();

        // Delete the ministerConcerner
        restMinisterConcernerMockMvc.perform(delete("/api/minister-concerners/{id}", ministerConcerner.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MinisterConcerner> ministerConcernerList = ministerConcernerRepository.findAll();
        assertThat(ministerConcernerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
