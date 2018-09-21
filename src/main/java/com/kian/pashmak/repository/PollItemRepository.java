package com.kian.pashmak.repository;

import com.kian.pashmak.domain.PollItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PollItemRepository extends JpaRepository<PollItem, Long> {

}
