package com.nicetravel.service.impl;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Provider;
import com.nicetravel.entity.Role;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    private RoleService roleService;

    private static final String ROLE_USER = "user";
    private static final String ROLE_STAFF = "staff";

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
    public Account findAccountByIDCard(String id_Card) {
        return accountRepository.findAccountByIDCard(id_Card);
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
    public Page<Account> findAllByStaffPageActive(int page, int size) {
        return accountRepository.findAllByStaffPageActive(PageRequest.of(page, size));
    }

    @Override
    public Page<Account> findAllByStaffPage(int page, int size) {
        return accountRepository.findAllByStaffPage(PageRequest.of(page, size));
    }

    @Override
    public Page<Account> findAllByStaffPageNoActive(int page, int size) {
        return accountRepository.findAllByStaffPageNoActive(PageRequest.of(page, size));
    }

    @Override
    public List<Account> findAllByStaff() {
        return accountRepository.findAllByStaff();
    }

    @Override
    public List<Account> findByUser() {
        return accountRepository.findByUser();
    }

    @Override
    @Transactional
    public Page<Account> findAllByUserActivate(int page, int size) {
        return accountRepository.findAllByUserActivate(PageRequest.of(page, size));
    }

    @Override
    public Page<Account> findAllByUserActivateInGetDate(int page, int size) {
        return accountRepository.findAllByUserActivateInGetDate(PageRequest.of(page, size));
    }

    @Override
    public Page<Account> findAllByUserActivateInMonth(int page, int size) {
        return accountRepository.findAllByUserActivateInMonth(PageRequest.of(page, size));
    }

    @Override
    public Page<Account> findAllByUserActivateInYear(int page, int size) {
        return accountRepository.findAllByUserActivateInYear(PageRequest.of(page, size));
    }

    @Override
    public Page<Account> getAllUser(int page, int size) {
        return accountRepository.getAllUserAdmin(PageRequest.of(page, size));
    }

    @Override
    public Page<Account> findAllByUserNoActivate(int page, int size) {
        return accountRepository.findAllByUserNoActivate(PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public void update(Account account) throws Exception {
        if (ObjectUtils.isEmpty(account)) {
            throw new Exception("Tài khoản không thể trống");
        }
        //check update non password or update full
        if (ObjectUtils.isEmpty(account.getPassword())) {
            accountRepository.updateNonPass(account.getFullname(), account.getEmail(), account.getPhone(), account.getId_Card(), account.getGender(), account.getAddress(), account.getUsername());
        }
        else if(account.getPassword().length() != 0 || account.getPassword() != null){
            accountRepository.update(account.getFullname(), account.getEmail(), account.getPassword(), account.getPhone(), account.getId_Card(), account.getGender(), account.getAddress(), account.getImg(), account.getUsername());
        }
        else {
            account.setPassword(bcrypt.encode(account.getPassword()));
            accountRepository.update(account.getFullname(), account.getEmail(), account.getPassword(), account.getPhone(), account.getId_Card(), account.getGender(), account.getAddress(), account.getImg(), account.getUsername());
        }
    }

    @Override
    @Transactional
    public void delete(String username) throws Exception {
        if (ObjectUtils.isEmpty(username)) {
            throw new Exception("user cannot be empty");
        }
        accountRepository.deletedUser(username);
        ;

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

    @Override
    public Account saveStaff(Account userRequest) {
        userRequest.setPassword(bcrypt.encode("123"));
        userRequest.setIsEnable(false);
        Role role = roleService.findByRole(ROLE_STAFF);
        userRequest.setRole_Id(role);
        userRequest.setImg("user.png");
        return accountRepository.saveAndFlush(userRequest);
    }

    @Override
    public Integer getTotalUsers() {
        return accountRepository.getTotalUsers();
    }

    @Override
    public Double comparedLastMonth() {
        double currentMonth = accountRepository.getTotalUsers();
        double lastMonth = accountRepository.getTotalUserLastMonth();
        double result = ((currentMonth / lastMonth) * 100) - 100;
//		if (currentMonth > lastMonth) {
//			 result = ((currentMonth / lastMonth) * 100) -100 ;
//		}else {
//			 result = 100 - ((currentMonth / lastMonth) * 100) ;
//		}

        return result;
    }

    @Override
    public void updateProviderType(String username, Provider provider) {
        accountRepository.updateProviderType(username, provider);
    }

    @Override
    public void changePassword(Account account, String newPassword, PasswordEncoder passwordEncoder) throws Exception {
        if (ObjectUtils.isEmpty(account)) {
            throw new Exception("Tài khoản không thể trống");
        }
        //check update non password or update full
        if (ObjectUtils.isEmpty(account.getPassword())) {
            System.out.println("oldpass: " +account.getPassword());
            accountRepository.updateNonPass(account.getFullname(), account.getEmail(), account.getPhone(), account.getId_Card(), account.getGender(), account.getAddress(), account.getUsername());
        } else {
            account.setPassword(bcrypt.encode(newPassword));
//            String encodePassword = passwordEncoder.encode(newPassword);
            accountRepository.update(account.getFullname(), account.getEmail(), account.getPassword(), account.getPhone(), account.getId_Card(), account.getGender(), account.getAddress(), account.getImg(), account.getUsername());
        }
//        account.setPassword(encodePassword);
//        System.out.println(encodePassword);
//        account.setPasswordChangedTime(new Date());

//        accountRepository.save(account);
    }

    @Override
    public Account getIdByUser(String user) {
        return accountRepository.getIdByUser(user);
    }


}