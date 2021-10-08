package com.nicetravel.repository;

import com.nicetravel.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
//    @Query("select DISTINCT ar.username from Account ar WHERE ar.roleId.id IN ('ADMIN','STAFF')")
//    List<Account> getAdministrators();
    Account findAccountsByUsername(String username);
}
