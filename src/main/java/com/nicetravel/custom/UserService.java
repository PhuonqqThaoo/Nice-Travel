package com.nicetravel.custom;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Provider;
import com.nicetravel.entity.Role;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
public class UserService {
    private final AccountService repo;
    @Autowired RoleService repoRoleService;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(AccountService repo, RoleService roleService) {
        this.repo = repo;
    }

//    public void registerDefaultUser(Account account) {
//        Role roleUser = repoRoleService.findByRoleName("User");
//        account.addRole(roleUser);
//
//        repo.save(account);
//    }

    public void processOAuthPostLogin(String username, String email) {
        Account existUser = repo.findAccountsByUsername(username);


        if (existUser == null) {
            Account newUser = new Account();
//            String encodedPassword = passwordEncoder.encode("123");

            newUser.setEmail(email);
            newUser.setUsername(email);
            newUser.setPassword((""));
//            newUser.setRole_Id(roleService.findByRoleName("USER"));
            newUser.setProvider(Provider.GOOGLE);
            newUser.setIsEnable(true);

            repo.createAccount(newUser);
        }

    }

    public void processOAuthFBLogin(String username) {
        Account existUser = repo.findAccountsByUsername(username);
        System.out.println(existUser);

        if (existUser == null) {
            Account newUser = new Account();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setIsEnable(true);

            repo.save(newUser);
        }

    }
}
