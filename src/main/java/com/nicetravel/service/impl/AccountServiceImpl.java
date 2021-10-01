package com.nicetravel.service.impl;

import com.nicetravel.entity.Account;
import com.nicetravel.repository.AccountDetailRepository;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Integer id) {
        return accountRepository.findById(id).get();
    }

//    @Override
//    public List<Account> getAdministrators() {
//        return accountRepository.getAdministrators();
//    }

    @Override
    public Account createAccount(Account account) {
        return null;
    }

    @Override
    public Account updateAccount(Account account) {
        return null;
    }

    @Override
    public void deleteAccount(Integer id) {

    }
}
