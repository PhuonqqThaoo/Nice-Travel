package com.nicetravel.service.impl;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Role;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Autowired
    private RoleService roleService;
    
    private static final String ROLE_USER ="user"; 
    
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

	@Override
	@Transactional 
	public void update(Account account) throws Exception {
		if (ObjectUtils.isEmpty(account)) {
			throw new Exception("user cannot be empty");
		}
		//check update non password or update full
		if (ObjectUtils.isEmpty(account.getPassword())) {
			accountRepository.updateNonPass(account.getFullname(), account.getEmail(), account.getPhone(), account.getId_Card(), account.getGender(),account.getAddress(), account.getUsername());
		}else {
			account.setPassword(bcrypt.encode(account.getPassword()));
			accountRepository.update(account.getFullname(), account.getEmail(),account.getPassword(), account.getPhone(), account.getId_Card(), account.getGender(),account.getAddress(), account.getUsername());
		}
	}

	@Override
	@Transactional
	public void delete(String username) throws Exception {
		if (ObjectUtils.isEmpty(username)) {
			throw new Exception("user cannot be empty");
		}
		accountRepository.deletedUser(username);;
		
	}

	@Override
	public Account save(Account account) {
//		account.setPassword(bcrypt.encode(account.getPassword()));
		
		account.setPassword(bcrypt.encode("123"));
		account.setIsEnable(false);
		Role role = roleService.findByRole(ROLE_USER);
		account.setRole_Id(role);
		account.setImg("user.png");
		return accountRepository.saveAndFlush(account);
		
	}

	
}
