package com.kian.pashmak.service.impl;

import com.google.common.collect.Lists;
import com.kian.pashmak.domain.Poll;
import com.kian.pashmak.domain.PollItem;
import com.kian.pashmak.domain.User;
import com.kian.pashmak.domain.Vote;
import com.kian.pashmak.repository.PollItemRepository;
import com.kian.pashmak.repository.PollRepository;
import com.kian.pashmak.repository.VoteRepository;
import com.kian.pashmak.security.SecurityUtils;
import com.kian.pashmak.service.PollService;
import com.kian.pashmak.service.UserService;
import com.kian.pashmak.service.dto.PollDTO;
import com.kian.pashmak.service.dto.PollItemDTO;
import com.kian.pashmak.service.dto.VoteDTO;
import com.kian.pashmak.service.dto.push.Notification;
import com.kian.pashmak.service.dto.push.Push;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
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

    private final PollItemRepository pollItemRepository;
    private final RestTemplate restTemplate;


    public PollServiceImpl(PollRepository pollRepository, UserService userService, VoteRepository voteRepository, PollItemRepository pollItemRepository, RestTemplate restTemplate) {
        this.pollRepository = pollRepository;
        this.userService = userService;
        this.voteRepository = voteRepository;
        this.pollItemRepository = pollItemRepository;
        this.restTemplate = restTemplate;
    }


    @Override
    public Poll save(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    public List<PollDTO> findAll() {
        List<Poll> polls= pollRepository.findAll();
        List<PollDTO> pollDTOS= new ArrayList<>();
        for (Poll poll : polls) {
            PollDTO pollDTO= new PollDTO();
            pollDTO.setId(poll.getId());
            pollDTO.setAnonymous(poll.getAnonymous());
            pollDTO.setAnswerLimit(poll.getAnswerLimit());
            pollDTO.setImgsrc(poll.getImgsrc());
            pollDTO.setQuestion(poll.getQuestion());
            pollDTO.setTotalVote(poll.getTotalVote());
            List<PollItemDTO> pollItemDTOS= new ArrayList<>();
            for (PollItem pollItem : poll.getPollItemSet()) {
                PollItemDTO itemDTO= new PollItemDTO();
                itemDTO.setId(pollItem.getId());
                itemDTO.setImgsrc(pollItem.getImgsrc());
                itemDTO.setNumber(pollItem.getNumber());
                itemDTO.setTitle(pollItem.getTitle());
                Vote v = voteRepository.findByUser_LoginAndPoll_IdAndPollItem_Id(SecurityUtils.getCurrentUserLogin().get(), poll.getId(), pollItem.getId());
                itemDTO.setVoted(v != null);
                pollItemDTOS.add(itemDTO);
            }
            pollDTO.setItemDTOS(pollItemDTOS);
            pollDTOS.add(pollDTO);
        }

        return pollDTOS;
    }

    @Override
    public Optional<Poll> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Vote vote(VoteDTO voteDTO) {
        Poll poll=pollRepository.findById(voteDTO.getPoll()).get();
        Vote vote= new Vote();
        vote.setPoll(poll);
        vote.setPollItem(poll.getPollItemSet().stream().filter(p->p.getId().equals(voteDTO.getItem())).findFirst().get());
        vote.setUser(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get());

        PollItem item = vote.getPollItem();
        item.setNumber(item.getNumber()+1);
        pollItemRepository.save(item);
        voteRepository.save(vote);
        poll.setTotalVote(poll.getTotalVote()+1);
        pollRepository.save(poll);


        Optional<User> user=userService.getUserWithAuthorities();
        Push push= new Push();
        List<Notification> notif= new ArrayList<>();
        Notification notification= new Notification();
        notification.setTokens(Lists.newArrayList(user.get().getPushToken()));
        notification.setPlatform(user.get().getPlatform().equals("IOS")?1:2);
        notification.setPriority("high");
        notification.setTitle("vote");
        notification.setTopic("com.pashmak.app");
        notification.setMutableContent(true);

        notif.add(notification);
        push.setNotifications(notif);
        restTemplate.postForEntity(URI.create("http://178.62.20.28:8088/api/push"),push,Object.class);

        return vote;
    }

    @Override
    public Vote deleteVote(VoteDTO voteDTO) {
        Vote vote= voteRepository.findByUser_LoginAndPoll_IdAndPollItem_Id(SecurityUtils.getCurrentUserLogin().get(),voteDTO.getPoll(),voteDTO.getItem());

        Optional<User> user=userService.getUserWithAuthorities();
        Push push= new Push();
        List<Notification> notif= new ArrayList<>();
        Notification notification= new Notification();
        notification.setTokens(Lists.newArrayList(user.get().getPushToken()));
        notification.setPlatform(user.get().getPlatform().equals("IOS")?1:2);
        notification.setPriority("high");
        notification.setTitle("vote");
        notification.setTopic("com.pashmak.app");
        notification.setMutableContent(true);

        notif.add(notification);
        push.setNotifications(notif);
        restTemplate.postForEntity(URI.create("http://178.62.20.28:8088/api/push"),push,Object.class);

        voteRepository.delete(vote);

        PollItem item = vote.getPollItem();
        item.setNumber(item.getNumber()-1);
        pollItemRepository.save(item);
        voteRepository.save(vote);
        Poll poll = vote.getPoll();
        poll.setTotalVote(poll.getTotalVote()-1);
        pollRepository.save(poll);
        return vote;
    }
}
