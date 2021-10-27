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
    
    void update(Account account) throws Exception;
     
    void delete(String username) throws Exception;
>>>>>>> b58e0e9b13b45dc67263a1b19064263464f45b3c
}
