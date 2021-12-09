package com.nicetravel.security.auth;

import com.nicetravel.custom.UserService;
import com.nicetravel.entity.Account;
import com.nicetravel.entity.Provider;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.security.SecurityConfig;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.RoleService;
import net.bytebuddy.utility.RandomString;
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

    PasswordEncoder passwordEncoder;

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

        String random = RandomString.make(10);

        System.out.println("oauth: " + oauth2User.getAttributes());


        String oauth2ClientName = oauth2User.getOauth2ClientName();
        String username = oauth2User.getName();
        String email = oauth2User.getEmail();
        String fullname = oauth2User.getFullName();

        Account accountByEmail = accountService.findByEmail(email);

        System.out.println("accountByEmail: " + accountByEmail);

        if (accountByEmail != null) {
            System.out.println("user account already exists in database");
        }



        else {
            System.out.println("new user. Add to database");
            Account newAccount = new Account();
            if (oauth2ClientName.equalsIgnoreCase("GitHub")) {
                String usernameGit = oauth2User.getAttribute("login");
                newAccount.setUsername(usernameGit);
                newAccount.setEmail("");
            }
            else if(oauth2ClientName.equals("Facebook")){
                String usernameFb = oauth2User.getAttribute("id");
                newAccount.setUsername(usernameFb);
                newAccount.setEmail(email);
            }
            else {
                newAccount.setUsername(email);
                newAccount.setEmail(email);
            }

            newAccount.setFullname(fullname);
            newAccount.setPassword("$2a$10$2O1pD5FCB4V7.VUJCTTMJePLUjm3Qj.84DcqbG2wcQIAaVbQLczUm");
            newAccount.setGender(false);
            newAccount.setAddress("");
            newAccount.setPhone("");
            newAccount.setImg("");
            newAccount.setId_Card("Vui lòng nhập lại ID Card " + random);
            newAccount.setRole_Id(roleService.findByRoleName("USER"));
            newAccount.setVerificationCode("");
            newAccount.setProvider(Provider.valueOf(oauth2ClientName.toUpperCase()));
            newAccount.setIsEnable(false);
//        newAccount.setTravelLikes(null);
            accountService.createAccount(newAccount);


            userService.updateAuthenticationType(username, oauth2ClientName);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
