package com.kian.pashmak.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kian.pashmak.domain.Poll;
import com.kian.pashmak.service.EventService;
import com.kian.pashmak.service.PollService;
import com.kian.pashmak.service.dto.EventDTO;
import com.kian.pashmak.service.dto.PollDTO;
import com.kian.pashmak.service.dto.VoteDTO;
import com.kian.pashmak.web.rest.errors.BadRequestAlertException;
import com.kian.pashmak.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Event.
 */
@RestController
@RequestMapping("/api")
public class PollResource {

    private final Logger log = LoggerFactory.getLogger(PollResource.class);

    private static final String ENTITY_NAME = "poll";

    private final PollService pollService;

    public PollResource(PollService pollService) {
        this.pollService = pollService;
    }



    /**
     * GET  /events : get all the events.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of events in body
     */
    @GetMapping("/polls")
    @Timed
    public List<Poll> getAllPolls() {
        log.debug("REST request to get all Events");
        return pollService.findAll();
    }


    @PostMapping("/polls/vote")
    @Timed
    public VoteDTO vote(@RequestBody VoteDTO voteDTO) {
        return pollService.vote(voteDTO);
    }

    /**
     * GET  /events/:id : get the "id" event.
     *
     * @param id the id of the eventDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventDTO, or with status 404 (Not Found)
     */

}
