package com.nicetravel.repository;

import com.nicetravel.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
//    @Query("select DISTINCT ar.username from Account ar WHERE ar.roleId.id IN ('ADMIN','STAFF')")
//    List<Account> getAdministrators();
    Account findAccountsByUsername(String username);



    @Query("SELECT u FROM Account u WHERE u.email = ?1")
    public Account findByEmail(String email);

    @Query("SELECT u FROM Account u WHERE u.verificationCode = ?1")
    public Account findByVerificationCode(String code);
}
