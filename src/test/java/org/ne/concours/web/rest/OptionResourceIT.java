package org.ne.concours.web.rest;

import org.ne.concours.ConcoursFonctionPubliqueApp;
import org.ne.concours.domain.Option;
import org.ne.concours.repository.OptionRepository;
import org.ne.concours.service.OptionService;
import org.ne.concours.service.dto.OptionCriteria;
import org.ne.concours.service.OptionQueryService;

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
 * Integration tests for the {@link OptionResource} REST controller.
 */
@SpringBootTest(classes = ConcoursFonctionPubliqueApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OptionResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private OptionService optionService;

    @Autowired
    private OptionQueryService optionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionMockMvc;

    private Option option;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Option createEntity(EntityManager em) {
        Option option = new Option()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return option;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Option createUpdatedEntity(EntityManager em) {
        Option option = new Option()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return option;
    }

    @BeforeEach
    public void initTest() {
        option = createEntity(em);
    }

    @Test
    @Transactional
    public void createOption() throws Exception {
        int databaseSizeBeforeCreate = optionRepository.findAll().size();
        // Create the Option
        restOptionMockMvc.perform(post("/api/options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(option)))
            .andExpect(status().isCreated());

        // Validate the Option in the database
        List<Option> optionList = optionRepository.findAll();
        assertThat(optionList).hasSize(databaseSizeBeforeCreate + 1);
        Option testOption = optionList.get(optionList.size() - 1);
        assertThat(testOption.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOption.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optionRepository.findAll().size();

        // Create the Option with an existing ID
        option.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionMockMvc.perform(post("/api/options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(option)))
            .andExpect(status().isBadRequest());

        // Validate the Option in the database
        List<Option> optionList = optionRepository.findAll();
        assertThat(optionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOptions() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList
        restOptionMockMvc.perform(get("/api/options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(option.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getOption() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get the option
        restOptionMockMvc.perform(get("/api/options/{id}", option.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(option.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }


    @Test
    @Transactional
    public void getOptionsByIdFiltering() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        Long id = option.getId();

        defaultOptionShouldBeFound("id.equals=" + id);
        defaultOptionShouldNotBeFound("id.notEquals=" + id);

        defaultOptionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOptionShouldNotBeFound("id.greaterThan=" + id);

        defaultOptionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOptionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOptionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where code equals to DEFAULT_CODE
        defaultOptionShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the optionList where code equals to UPDATED_CODE
        defaultOptionShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllOptionsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where code not equals to DEFAULT_CODE
        defaultOptionShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the optionList where code not equals to UPDATED_CODE
        defaultOptionShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllOptionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where code in DEFAULT_CODE or UPDATED_CODE
        defaultOptionShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the optionList where code equals to UPDATED_CODE
        defaultOptionShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllOptionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where code is not null
        defaultOptionShouldBeFound("code.specified=true");

        // Get all the optionList where code is null
        defaultOptionShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllOptionsByCodeContainsSomething() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where code contains DEFAULT_CODE
        defaultOptionShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the optionList where code contains UPDATED_CODE
        defaultOptionShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllOptionsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where code does not contain DEFAULT_CODE
        defaultOptionShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the optionList where code does not contain UPDATED_CODE
        defaultOptionShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllOptionsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where libelle equals to DEFAULT_LIBELLE
        defaultOptionShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the optionList where libelle equals to UPDATED_LIBELLE
        defaultOptionShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllOptionsByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where libelle not equals to DEFAULT_LIBELLE
        defaultOptionShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the optionList where libelle not equals to UPDATED_LIBELLE
        defaultOptionShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllOptionsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultOptionShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the optionList where libelle equals to UPDATED_LIBELLE
        defaultOptionShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllOptionsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where libelle is not null
        defaultOptionShouldBeFound("libelle.specified=true");

        // Get all the optionList where libelle is null
        defaultOptionShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllOptionsByLibelleContainsSomething() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where libelle contains DEFAULT_LIBELLE
        defaultOptionShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the optionList where libelle contains UPDATED_LIBELLE
        defaultOptionShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllOptionsByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList where libelle does not contain DEFAULT_LIBELLE
        defaultOptionShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the optionList where libelle does not contain UPDATED_LIBELLE
        defaultOptionShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOptionShouldBeFound(String filter) throws Exception {
        restOptionMockMvc.perform(get("/api/options?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(option.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restOptionMockMvc.perform(get("/api/options/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOptionShouldNotBeFound(String filter) throws Exception {
        restOptionMockMvc.perform(get("/api/options?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOptionMockMvc.perform(get("/api/options/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOption() throws Exception {
        // Get the option
        restOptionMockMvc.perform(get("/api/options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOption() throws Exception {
        // Initialize the database
        optionService.save(option);

        int databaseSizeBeforeUpdate = optionRepository.findAll().size();

        // Update the option
        Option updatedOption = optionRepository.findById(option.getId()).get();
        // Disconnect from session so that the updates on updatedOption are not directly saved in db
        em.detach(updatedOption);
        updatedOption
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restOptionMockMvc.perform(put("/api/options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOption)))
            .andExpect(status().isOk());

        // Validate the Option in the database
        List<Option> optionList = optionRepository.findAll();
        assertThat(optionList).hasSize(databaseSizeBeforeUpdate);
        Option testOption = optionList.get(optionList.size() - 1);
        assertThat(testOption.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOption.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingOption() throws Exception {
        int databaseSizeBeforeUpdate = optionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionMockMvc.perform(put("/api/options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(option)))
            .andExpect(status().isBadRequest());

        // Validate the Option in the database
        List<Option> optionList = optionRepository.findAll();
        assertThat(optionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOption() throws Exception {
        // Initialize the database
        optionService.save(option);

        int databaseSizeBeforeDelete = optionRepository.findAll().size();

        // Delete the option
        restOptionMockMvc.perform(delete("/api/options/{id}", option.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Option> optionList = optionRepository.findAll();
        assertThat(optionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
