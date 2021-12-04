package com.nicetravel.service;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Provider;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    Page<Account> findAllByStaffPage(int page, int size);

    List<Account> findAllByStaff();
    List<Account> findByUser();

    Page<Account> findAllByUser(int page, int size);

    void update(Account account) throws Exception;

    void delete(String username) throws Exception;

	Account save(Account userRequest);
	
	Account saveStaff(Account userRequest);
	
	Integer getTotalUsers(); 
	
	// so với tháng trước
	Double comparedLastMonth();

    void updateProviderType(String username, Provider provider);

    void changePassword(Account account, String newPassword, PasswordEncoder passwordEncoder) throws Exception;
}