package com.kian.pashmak.service.impl;

import com.kian.pashmak.domain.Poll;
import com.kian.pashmak.domain.Vote;
import com.kian.pashmak.repository.PollRepository;
import com.kian.pashmak.repository.VoteRepository;
import com.kian.pashmak.security.SecurityUtils;
import com.kian.pashmak.service.PollService;
import com.kian.pashmak.service.UserService;
import com.kian.pashmak.service.dto.VoteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Event.
 */
@Service
@Transactional
public class PollServiceImpl implements PollService {

    private final Logger log = LoggerFactory.getLogger(PollServiceImpl.class);

    private final PollRepository pollRepository;

    private final UserService userService;

    private final VoteRepository voteRepository;


    public PollServiceImpl(PollRepository pollRepository, UserService userService, VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.userService = userService;
        this.voteRepository = voteRepository;
    }


    @Override
    public Poll save(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    @Override
    public Optional<Poll> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public VoteDTO vote(VoteDTO voteDTO) {
        Optional<Poll> poll=pollRepository.findById(voteDTO.getPoll());
        Vote vote= new Vote();
        vote.setPoll(poll.get());
        vote.setPollItem(poll
            .get().getPollItemSet().stream().filter(p->p.getId().equals(voteDTO.getItem())).findFirst().get());
        vote.setUser(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get());

        voteRepository.save(vote);
        return null;
    }
}
