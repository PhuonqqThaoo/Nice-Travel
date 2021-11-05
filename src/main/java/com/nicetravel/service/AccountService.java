package com.nicetravel.service;

import com.nicetravel.entity.Account;

import java.util.List;

import javax.validation.Valid;

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

    void update(Account account) throws Exception;
     
    void delete(String username) throws Exception;

	Account save(Account userRequest);
	
	Account saveStaff(Account userRequest);
	
	Integer getTotalUsers(); 
}
