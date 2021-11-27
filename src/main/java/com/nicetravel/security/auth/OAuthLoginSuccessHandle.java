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
    @Autowired
    UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    private PasswordEncoder passwordEncoder;

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
        System.out.println("accountByEmail: " + accountByEmail);

        if(accountByEmail != null){
            System.out.println("user account already exists in database");
        }else{
            System.out.println("new user. Add to database");
            Account newAccount = new Account();
            if (oauth2ClientName.equals("Github")){
                newAccount.setUsername(username);
            }
            else {
                newAccount.setUsername(email);
            }
            newAccount.setEmail(email);
            newAccount.setFullname(fullname);
            newAccount.setPassword("");
            newAccount.setRole_Id(roleService.findByRoleName("USER"));
            newAccount.setProvider(Provider.valueOf(oauth2ClientName.toUpperCase()));
            newAccount.setIsEnable(false);
            newAccount.setTravelLikes(null);
            accountService.createAccount(newAccount);
        }


        userService.updateAuthenticationType(username, oauth2ClientName);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
