package com.kian.pashmak.service;

import com.kian.pashmak.service.dto.MessageDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Message.
 */
public interface MessageService {

    /**
     * Save a message.
     *
     * @param messageDTO the entity to save
     * @return the persisted entity
     */
    MessageDTO save(MessageDTO messageDTO);

    /**
     * Get all the messages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MessageDTO> findAll(Pageable pageable);


    @Transactional(readOnly = true)
    List<MessageDTO> findAll();

    /**
     * Get the "id" message.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MessageDTO> findOne(Long id);

    /**
     * Delete the "id" message.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
