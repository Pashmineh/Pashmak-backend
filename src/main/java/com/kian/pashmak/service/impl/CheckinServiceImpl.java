package com.kian.pashmak.service.impl;

import com.kian.pashmak.service.CheckinService;
import com.kian.pashmak.domain.Checkin;
import com.kian.pashmak.repository.CheckinRepository;
import com.kian.pashmak.service.dto.CheckinDTO;
import com.kian.pashmak.service.mapper.CheckinMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Checkin.
 */
@Service
@Transactional
public class CheckinServiceImpl implements CheckinService {

    private final Logger log = LoggerFactory.getLogger(CheckinServiceImpl.class);

    private final CheckinRepository checkinRepository;

    private final CheckinMapper checkinMapper;

    public CheckinServiceImpl(CheckinRepository checkinRepository, CheckinMapper checkinMapper) {
        this.checkinRepository = checkinRepository;
        this.checkinMapper = checkinMapper;
    }

    /**
     * Save a checkin.
     *
     * @param checkinDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CheckinDTO save(CheckinDTO checkinDTO) {
        log.debug("Request to save Checkin : {}", checkinDTO);
        Checkin checkin = checkinMapper.toEntity(checkinDTO);
        checkin = checkinRepository.save(checkin);
        return checkinMapper.toDto(checkin);
    }

    /**
     * Get all the checkins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CheckinDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Checkins");
        return checkinRepository.findByUserIsCurrentUser(pageable)
            .map(checkinMapper::toDto);
    }


    /**
     * Get one checkin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CheckinDTO> findOne(Long id) {
        log.debug("Request to get Checkin : {}", id);
        return checkinRepository.findById(id)
            .map(checkinMapper::toDto);
    }

    /**
     * Delete the checkin by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Checkin : {}", id);
        checkinRepository.deleteById(id);
    }
}
