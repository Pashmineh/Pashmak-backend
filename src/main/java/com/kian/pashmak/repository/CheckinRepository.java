package com.kian.pashmak.repository;

import com.kian.pashmak.domain.Checkin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Checkin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long> {

    @Query("select checkin from Checkin checkin where checkin.user.login = ?#{principal.username}")
    List<Checkin> findByUserIsCurrentUser();

}
