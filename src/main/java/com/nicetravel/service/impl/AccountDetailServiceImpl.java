package com.nicetravel.service.impl;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.AccountDetail;
import com.nicetravel.repository.AccountDetailRepository;
import com.nicetravel.service.AccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDetailServiceImpl implements AccountDetailService {

    AccountDetailRepository accountDetailRepository;

    @Autowired
    public AccountDetailServiceImpl(AccountDetailRepository accountDetailRepository) {
        this.accountDetailRepository = accountDetailRepository;
    }

    @Override
    public List<AccountDetail> getAllAccountDetail() {
        return accountDetailRepository.findAll();
    }

    @Override
    public AccountDetail findAccountDetailById(Integer id) {
        return accountDetailRepository.findById(id).get();
    }

    @Override
    public AccountDetail createAccountDetail(AccountDetail accountDetail) {
        return accountDetailRepository.save(accountDetail);
    }

    @Override
    public AccountDetail updateAccountDetail(AccountDetail accountDetail) {
        return accountDetailRepository.save(accountDetail);
    }

    @Override
    public void deleteAccountDetail(Integer id) {
        accountDetailRepository.deleteById(id);
    }

//    @Override
//    public AccountDetail findAccountDetailByUsername(String username) {
//        return accountDetailRepository.findAccountDetailByUsername(username);
//    }
}
