package org.mce.gestionedeleghe.web.rest;

import org.mce.gestionedeleghe.Application;
import org.mce.gestionedeleghe.domain.Delega;
import org.mce.gestionedeleghe.repository.DelegaRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DelegaResource REST controller.
 *
 * @see DelegaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DelegaResourceTest {

    private static final String DEFAULT_NOME = "SAMPLE_TEXT";
    private static final String UPDATED_NOME = "UPDATED_TEXT";
    private static final String DEFAULT_COGNOME = "SAMPLE_TEXT";
    private static final String UPDATED_COGNOME = "UPDATED_TEXT";
    private static final String DEFAULT_CODICE_FISCALE = "SAMPLE_TEXT";
    private static final String UPDATED_CODICE_FISCALE = "UPDATED_TEXT";

    @Inject
    private DelegaRepository delegaRepository;

    private MockMvc restDelegaMockMvc;

    private Delega delega;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DelegaResource delegaResource = new DelegaResource();
        ReflectionTestUtils.setField(delegaResource, "delegaRepository", delegaRepository);
        this.restDelegaMockMvc = MockMvcBuilders.standaloneSetup(delegaResource).build();
    }

    @Before
    public void initTest() {
        delegaRepository.deleteAll();
        delega = new Delega();
        delega.setNome(DEFAULT_NOME);
        delega.setCognome(DEFAULT_COGNOME);
        delega.setCodiceFiscale(DEFAULT_CODICE_FISCALE);
    }

    @Test
    public void createDelega() throws Exception {
        int databaseSizeBeforeCreate = delegaRepository.findAll().size();

        // Create the Delega
        restDelegaMockMvc.perform(post("/api/delegas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(delega)))
                .andExpect(status().isCreated());

        // Validate the Delega in the database
        List<Delega> delegas = delegaRepository.findAll();
        assertThat(delegas).hasSize(databaseSizeBeforeCreate + 1);
        Delega testDelega = delegas.get(delegas.size() - 1);
        assertThat(testDelega.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDelega.getCognome()).isEqualTo(DEFAULT_COGNOME);
        assertThat(testDelega.getCodiceFiscale()).isEqualTo(DEFAULT_CODICE_FISCALE);
    }

    @Test
    public void getAllDelegas() throws Exception {
        // Initialize the database
        delegaRepository.save(delega);

        // Get all the delegas
        restDelegaMockMvc.perform(get("/api/delegas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(delega.getId())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME.toString())))
                .andExpect(jsonPath("$.[*].codiceFiscale").value(hasItem(DEFAULT_CODICE_FISCALE.toString())));
    }

    @Test
    public void getDelega() throws Exception {
        // Initialize the database
        delegaRepository.save(delega);

        // Get the delega
        restDelegaMockMvc.perform(get("/api/delegas/{id}", delega.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(delega.getId()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME.toString()))
            .andExpect(jsonPath("$.codiceFiscale").value(DEFAULT_CODICE_FISCALE.toString()));
    }

    @Test
    public void getNonExistingDelega() throws Exception {
        // Get the delega
        restDelegaMockMvc.perform(get("/api/delegas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDelega() throws Exception {
        // Initialize the database
        delegaRepository.save(delega);
		
		int databaseSizeBeforeUpdate = delegaRepository.findAll().size();

        // Update the delega
        delega.setNome(UPDATED_NOME);
        delega.setCognome(UPDATED_COGNOME);
        delega.setCodiceFiscale(UPDATED_CODICE_FISCALE);
        restDelegaMockMvc.perform(put("/api/delegas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(delega)))
                .andExpect(status().isOk());

        // Validate the Delega in the database
        List<Delega> delegas = delegaRepository.findAll();
        assertThat(delegas).hasSize(databaseSizeBeforeUpdate);
        Delega testDelega = delegas.get(delegas.size() - 1);
        assertThat(testDelega.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDelega.getCognome()).isEqualTo(UPDATED_COGNOME);
        assertThat(testDelega.getCodiceFiscale()).isEqualTo(UPDATED_CODICE_FISCALE);
    }

    @Test
    public void deleteDelega() throws Exception {
        // Initialize the database
        delegaRepository.save(delega);
		
		int databaseSizeBeforeDelete = delegaRepository.findAll().size();

        // Get the delega
        restDelegaMockMvc.perform(delete("/api/delegas/{id}", delega.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Delega> delegas = delegaRepository.findAll();
        assertThat(delegas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
