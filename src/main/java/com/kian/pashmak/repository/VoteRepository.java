package com.kian.pashmak.repository;

import com.kian.pashmak.domain.Poll;
import com.kian.pashmak.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

       Vote findByUser_LoginAndPoll_IdAndPollItem_Id(String login,Long polId,Long itemId);

    Optional<Vote> findByPollAndUser_Login(Poll poll, String login);


}
