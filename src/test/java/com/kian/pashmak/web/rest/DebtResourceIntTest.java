package com.kian.pashmak.web.rest;

import com.kian.pashmak.PashmakApp;

import com.kian.pashmak.domain.Debt;
import com.kian.pashmak.repository.DebtRepository;
import com.kian.pashmak.service.DebtService;
import com.kian.pashmak.service.dto.DebtDTO;
import com.kian.pashmak.service.mapper.DebtMapper;
import com.kian.pashmak.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.kian.pashmak.web.rest.TestUtil.sameInstant;
import static com.kian.pashmak.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kian.pashmak.domain.enumeration.PaymentType;
/**
 * Test class for the DebtResource REST controller.
 *
 * @see DebtResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PashmakApp.class)
public class DebtResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_PAYMENT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PAYMENT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final PaymentType DEFAULT_REASON = PaymentType.TAKHIR;
    private static final PaymentType UPDATED_REASON = PaymentType.SHIRINI;

    @Autowired
    private DebtRepository debtRepository;


    @Autowired
    private DebtMapper debtMapper;
    

    @Autowired
    private DebtService debtService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDebtMockMvc;

    private Debt debt;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DebtResource debtResource = new DebtResource(debtService);
        this.restDebtMockMvc = MockMvcBuilders.standaloneSetup(debtResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Debt createEntity(EntityManager em) {
        Debt debt = new Debt()
            .amount(DEFAULT_AMOUNT)
            .paymentTime(DEFAULT_PAYMENT_TIME)
            .reason(DEFAULT_REASON);
        return debt;
    }

    @Before
    public void initTest() {
        debt = createEntity(em);
    }

    @Test
    @Transactional
    public void createDebt() throws Exception {
        int databaseSizeBeforeCreate = debtRepository.findAll().size();

        // Create the Debt
        DebtDTO debtDTO = debtMapper.toDto(debt);
        restDebtMockMvc.perform(post("/api/debts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debtDTO)))
            .andExpect(status().isCreated());

        // Validate the Debt in the database
        List<Debt> debtList = debtRepository.findAll();
        assertThat(debtList).hasSize(databaseSizeBeforeCreate + 1);
        Debt testDebt = debtList.get(debtList.size() - 1);
        assertThat(testDebt.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDebt.getPaymentTime()).isEqualTo(DEFAULT_PAYMENT_TIME);
        assertThat(testDebt.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createDebtWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = debtRepository.findAll().size();

        // Create the Debt with an existing ID
        debt.setId(1L);
        DebtDTO debtDTO = debtMapper.toDto(debt);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDebtMockMvc.perform(post("/api/debts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debtDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Debt in the database
        List<Debt> debtList = debtRepository.findAll();
        assertThat(debtList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDebts() throws Exception {
        // Initialize the database
        debtRepository.saveAndFlush(debt);

        // Get all the debtList
        restDebtMockMvc.perform(get("/api/debts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debt.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentTime").value(hasItem(sameInstant(DEFAULT_PAYMENT_TIME))))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }
    

    @Test
    @Transactional
    public void getDebt() throws Exception {
        // Initialize the database
        debtRepository.saveAndFlush(debt);

        // Get the debt
        restDebtMockMvc.perform(get("/api/debts/{id}", debt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(debt.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paymentTime").value(sameInstant(DEFAULT_PAYMENT_TIME)))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDebt() throws Exception {
        // Get the debt
        restDebtMockMvc.perform(get("/api/debts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDebt() throws Exception {
        // Initialize the database
        debtRepository.saveAndFlush(debt);

        int databaseSizeBeforeUpdate = debtRepository.findAll().size();

        // Update the debt
        Debt updatedDebt = debtRepository.findById(debt.getId()).get();
        // Disconnect from session so that the updates on updatedDebt are not directly saved in db
        em.detach(updatedDebt);
        updatedDebt
            .amount(UPDATED_AMOUNT)
            .paymentTime(UPDATED_PAYMENT_TIME)
            .reason(UPDATED_REASON);
        DebtDTO debtDTO = debtMapper.toDto(updatedDebt);

        restDebtMockMvc.perform(put("/api/debts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debtDTO)))
            .andExpect(status().isOk());

        // Validate the Debt in the database
        List<Debt> debtList = debtRepository.findAll();
        assertThat(debtList).hasSize(databaseSizeBeforeUpdate);
        Debt testDebt = debtList.get(debtList.size() - 1);
        assertThat(testDebt.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDebt.getPaymentTime()).isEqualTo(UPDATED_PAYMENT_TIME);
        assertThat(testDebt.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingDebt() throws Exception {
        int databaseSizeBeforeUpdate = debtRepository.findAll().size();

        // Create the Debt
        DebtDTO debtDTO = debtMapper.toDto(debt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restDebtMockMvc.perform(put("/api/debts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debtDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Debt in the database
        List<Debt> debtList = debtRepository.findAll();
        assertThat(debtList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDebt() throws Exception {
        // Initialize the database
        debtRepository.saveAndFlush(debt);

        int databaseSizeBeforeDelete = debtRepository.findAll().size();

        // Get the debt
        restDebtMockMvc.perform(delete("/api/debts/{id}", debt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Debt> debtList = debtRepository.findAll();
        assertThat(debtList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Debt.class);
        Debt debt1 = new Debt();
        debt1.setId(1L);
        Debt debt2 = new Debt();
        debt2.setId(debt1.getId());
        assertThat(debt1).isEqualTo(debt2);
        debt2.setId(2L);
        assertThat(debt1).isNotEqualTo(debt2);
        debt1.setId(null);
        assertThat(debt1).isNotEqualTo(debt2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebtDTO.class);
        DebtDTO debtDTO1 = new DebtDTO();
        debtDTO1.setId(1L);
        DebtDTO debtDTO2 = new DebtDTO();
        assertThat(debtDTO1).isNotEqualTo(debtDTO2);
        debtDTO2.setId(debtDTO1.getId());
        assertThat(debtDTO1).isEqualTo(debtDTO2);
        debtDTO2.setId(2L);
        assertThat(debtDTO1).isNotEqualTo(debtDTO2);
        debtDTO1.setId(null);
        assertThat(debtDTO1).isNotEqualTo(debtDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(debtMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(debtMapper.fromId(null)).isNull();
    }
}
