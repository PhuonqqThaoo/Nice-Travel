package com.nicetravel.custom;

import com.nicetravel.entity.Account;
import com.nicetravel.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
//        String dBuserName = account.getUsername();
        if (account == null) {
            throw new UsernameNotFoundException("Email not found");
        }

//        GrantedAuthority authority = new SimpleGrantedAuthority(account.getRole_Id().getRole());
//        UserDetails userDetails = (UserDetails)new Account(dBuserName,
//                account.getPassword(), Arrays.asList(authority));

        return new CustomUserDetails(account);
    }
}
