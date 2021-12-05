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

    Page<Account> findAllByStaffPageActive(int page, int size);

    Page<Account> findAllByStaffPage(int page, int size);

    Page<Account> findAllByStaffPageNoActive(int page, int size);

    List<Account> findAllByStaff();

    List<Account> findByUser();

    // user đang hoạt động
    Page<Account> findAllByUserActivate(int page, int size);

    // user tất cả
    Page<Account> getAllUser(int page, int size);

    // user khong hoạt động
    Page<Account> findAllByUserNoActivate(int page, int size);

    void update(Account account) throws Exception;

    void delete(String username) throws Exception;

	Account save(Account userRequest);
	
	Account saveStaff(Account userRequest);
	
	Integer getTotalUsers(); 
	
	// so với tháng trước
	Double comparedLastMonth();

    void updateProviderType(String username, Provider provider);

    void changePassword(Account account, String newPassword, PasswordEncoder passwordEncoder) throws Exception;

    Account getIdByUser(String user);
}