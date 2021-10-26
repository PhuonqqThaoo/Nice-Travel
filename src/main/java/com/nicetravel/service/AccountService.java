package com.nicetravel.service;

import com.nicetravel.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccount();

    Account getAccountById(Integer id);

//    List<Account> getAdministrators();

    Account createAccount(Account account);

    Account updateAccount(Account account);

    void deleteAccount(Integer id);

    Account findAccountsByUsername(String username);

    Account findByVerificationCode(String code);

    Account findByEmail(String email);

    List<Account> findAll();

    List<Account> findAllByStaff();
<<<<<<< HEAD
    
    void update(Account account) throws Exception;
     
    void delete(String username) throws Exception;
=======

>>>>>>> ed0dfc4dd0265ca028919d1b6a0aab252773bc59
}
