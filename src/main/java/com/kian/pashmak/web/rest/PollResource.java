package com.kian.pashmak.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kian.pashmak.service.PollService;
import com.kian.pashmak.service.dto.PollDTO;
import com.kian.pashmak.service.dto.VoteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<PollDTO> getAllPolls() {
        log.debug("REST request to get all Events");
        return pollService.findAll();
    }


    @PostMapping("/polls/vote")
    @Timed
    public List<PollDTO> vote(@RequestBody VoteDTO voteDTO) {
         pollService.vote(voteDTO);
        return getAllPolls();

    }

    @DeleteMapping("/polls/vote")
    @Timed
    public List<PollDTO> deleteVote(@RequestBody VoteDTO voteDTO) {
         pollService.deleteVote(voteDTO);
         return getAllPolls();
    }

    /**
     * GET  /events/:id : get the "id" event.
     *
     * @param id the id of the eventDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventDTO, or with status 404 (Not Found)
     */

}
