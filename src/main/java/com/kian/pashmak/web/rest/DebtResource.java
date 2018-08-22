package com.kian.pashmak.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kian.pashmak.service.DebtService;
import com.kian.pashmak.web.rest.errors.BadRequestAlertException;
import com.kian.pashmak.web.rest.util.HeaderUtil;
import com.kian.pashmak.web.rest.util.PaginationUtil;
import com.kian.pashmak.service.dto.DebtDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Debt.
 */
@RestController
@RequestMapping("/api")
public class DebtResource {

    private final Logger log = LoggerFactory.getLogger(DebtResource.class);

    private static final String ENTITY_NAME = "debt";

    private final DebtService debtService;

    public DebtResource(DebtService debtService) {
        this.debtService = debtService;
    }

    /**
     * POST  /debts : Create a new debt.
     *
     * @param debtDTO the debtDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new debtDTO, or with status 400 (Bad Request) if the debt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/debts")
    @Timed
    public ResponseEntity<DebtDTO> createDebt(@RequestBody DebtDTO debtDTO) throws URISyntaxException {
        log.debug("REST request to save Debt : {}", debtDTO);
        if (debtDTO.getId() != null) {
            throw new BadRequestAlertException("A new debt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DebtDTO result = debtService.save(debtDTO);
        return ResponseEntity.created(new URI("/api/debts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /debts : Updates an existing debt.
     *
     * @param debtDTO the debtDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated debtDTO,
     * or with status 400 (Bad Request) if the debtDTO is not valid,
     * or with status 500 (Internal Server Error) if the debtDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/debts")
    @Timed
    public ResponseEntity<DebtDTO> updateDebt(@RequestBody DebtDTO debtDTO) throws URISyntaxException {
        log.debug("REST request to update Debt : {}", debtDTO);
        if (debtDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DebtDTO result = debtService.save(debtDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, debtDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /debts : get all the debts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of debts in body
     */
    @GetMapping("/debts")
    @Timed
    public ResponseEntity<List<DebtDTO>> getAllDebts(Pageable pageable) {
        log.debug("REST request to get a page of Debts");
        Page<DebtDTO> page = debtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/debts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /debts/:id : get the "id" debt.
     *
     * @param id the id of the debtDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the debtDTO, or with status 404 (Not Found)
     */
    @GetMapping("/debts/{id}")
    @Timed
    public ResponseEntity<DebtDTO> getDebt(@PathVariable Long id) {
        log.debug("REST request to get Debt : {}", id);
        Optional<DebtDTO> debtDTO = debtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(debtDTO);
    }

    /**
     * DELETE  /debts/:id : delete the "id" debt.
     *
     * @param id the id of the debtDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/debts/{id}")
    @Timed
    public ResponseEntity<Void> deleteDebt(@PathVariable Long id) {
        log.debug("REST request to delete Debt : {}", id);
        debtService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
