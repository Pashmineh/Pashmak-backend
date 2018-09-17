package com.kian.pashmak.service;

import com.kian.pashmak.domain.Poll;
import com.kian.pashmak.service.dto.EventDTO;
import com.kian.pashmak.service.dto.VoteDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Event.
 */
public interface PollService {

    /**
     * Save a event.
     *
     * @param poll the entity to save
     * @return the persisted entity
     */
    Poll save(Poll poll);

    /**
     * Get all the events.
     *
     * @return the list of entities
     */
    List<Poll> findAll();


    /**
     * Get the "id" event.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Poll> findOne(Long id);

    /**
     * Delete the "id" event.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    VoteDTO vote(VoteDTO voteDTO);
}
