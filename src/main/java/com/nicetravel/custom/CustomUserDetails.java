package com.nicetravel.custom;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Role;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private final Account account;

    private final AccountService accountService;

    @Autowired
    public CustomUserDetails(Account account, AccountService accountService) {
        this.account = account;
        this.accountService = accountService;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<Role> roles = account.getRoles();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//        }
        return null;
    }

//
//    public boolean hasRole(String roleName) {
//        return this.account.hasRole(roleName);
//    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.getIsEnable();
    }

    public String getName() {
        return account.getUsername();
    }

    public Account getAccount(){
        return account;
    }


}
