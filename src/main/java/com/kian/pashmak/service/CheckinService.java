package com.kian.pashmak.service;

import com.kian.pashmak.service.dto.CheckinDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Checkin.
 */
public interface CheckinService {

    /**
     * Save a checkin.
     *
     * @param checkinDTO the entity to save
     * @return the persisted entity
     */
    CheckinDTO save(CheckinDTO checkinDTO);

    /**
     * Get all the checkins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CheckinDTO> findAll(Pageable pageable);


    /**
     * Get the "id" checkin.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CheckinDTO> findOne(Long id);

    /**
     * Delete the "id" checkin.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
