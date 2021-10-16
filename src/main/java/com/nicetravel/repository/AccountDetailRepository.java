package com.nicetravel.repository;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Integer> {
    @Query("SELECT acc.username FROM AccountDetail u LEFT JOIN  Account AS acc ON u.id = acc.id  WHERE u.id = ?1")
    AccountDetail findAccountDetailById(Integer id);
}
