package com.kian.pashmak.service;

import com.kian.pashmak.service.dto.DebtDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Debt.
 */
public interface DebtService {

    /**
     * Save a debt.
     *
     * @param debtDTO the entity to save
     * @return the persisted entity
     */
    DebtDTO save(DebtDTO debtDTO);

    /**
     * Get all the debts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DebtDTO> findAll(Pageable pageable);


    /**
     * Get the "id" debt.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DebtDTO> findOne(Long id);

    /**
     * Delete the "id" debt.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
