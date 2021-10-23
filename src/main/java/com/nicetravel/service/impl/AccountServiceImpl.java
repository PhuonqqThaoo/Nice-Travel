package com.nicetravel.service.impl;

import com.nicetravel.entity.Account;

import com.nicetravel.repository.AccountRepository;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    public Account getAccountById(Integer id) {
        return accountRepository.findById(id).get();
    }

//    @Override
//    public List<Account> getAdministrators() {
//        return accountRepository.getAdministrators();
//    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account findAccountsByUsername(String username) {
        return accountRepository.findAccountsByUsername(username);
    }

    @Override
    public Account findByVerificationCode(String code) {
        return accountRepository.findByVerificationCode(code);
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }


    @Override
	public List<Account> findAll() {
		return accountRepository.findAllAvailable();
	}

	@Override
	public List<Account> findAllByStaff() {
		return accountRepository.findAllByStaff();
	}

	
}
