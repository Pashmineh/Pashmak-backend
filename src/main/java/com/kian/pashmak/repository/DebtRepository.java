package com.kian.pashmak.repository;

import com.kian.pashmak.domain.Debt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Debt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {

    @Query("select debt from Debt debt where debt.user.login = ?#{principal.username}")
    List<Debt> findByUserIsCurrentUser();

}
