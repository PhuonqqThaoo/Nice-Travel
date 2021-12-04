package com.nicetravel.security.auth;

import com.nicetravel.custom.UserService;
import com.nicetravel.entity.Account;
import com.nicetravel.entity.Provider;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.security.SecurityConfig;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuthLoginSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    private final AccountService accountService;

    private final RoleService roleService;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public OAuthLoginSuccessHandle(UserService userService, AccountService accountService, RoleService roleService) {
        this.userService = userService;
        this.accountService = accountService;
        this.roleService = roleService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        String oauth2ClientName = oauth2User.getOauth2ClientName();
        String username = oauth2User.getName();
        System.out.println("username: " + username);
        String email = oauth2User.getEmail();
        String fullname = oauth2User.getFullName();

        System.out.println("clientName: " + oauth2ClientName);
        System.out.println("Oauth2 username: " + oauth2User.getName());
        System.out.println("Oauth2 email: " + oauth2User.getEmail());

        Account accountByEmail = accountService.findByEmail(email);

        if (accountByEmail != null) {
            System.out.println("user account already exists in database");
        }
        System.out.println("new user. Add to database");
        Account newAccount = new Account();
        if (oauth2ClientName.equalsIgnoreCase("GitHub")) {
            newAccount.setUsername(username);
            newAccount.setEmail("");
        }
        else {
            newAccount.setUsername(email);
            newAccount.setEmail(email);
        }
//            System.out.println("new user. Add to database");
//            Account newAccount = new Account();

        newAccount.setFullname(fullname);
        newAccount.setPassword("");
        newAccount.setGender(false);
        newAccount.setAddress("");
        newAccount.setPhone("");
        newAccount.setImg("");
        newAccount.setRole_Id(roleService.findByRoleName("USER"));
        newAccount.setVerificationCode("");
        newAccount.setProvider(Provider.valueOf(oauth2ClientName.toUpperCase()));
        newAccount.setIsEnable(false);
//        newAccount.setTravelLikes(null);
        accountService.createAccount(newAccount);


        userService.updateAuthenticationType(username, oauth2ClientName);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
