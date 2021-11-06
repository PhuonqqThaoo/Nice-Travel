package com.nicetravel.custom;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Provider;
import com.nicetravel.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AccountRepository repo;

    public void processOAuthPostLogin(String username) {
        Account existUser = repo.getUserByUsername(username);

        if (existUser == null) {
            Account newUser = new Account();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setIsEnable(true);

            repo.save(newUser);
        }

    }

    public void processOAuthFBLogin(String username) {
        Account existUser = repo.getUserByUsername(username);

        if (existUser == null) {
            Account newUser = new Account();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setIsEnable(true);

            repo.save(newUser);
        }

    }
}
