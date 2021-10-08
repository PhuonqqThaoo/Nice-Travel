package com.nicetravel.security;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Role;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    BCryptPasswordEncoder pe;

    @Autowired
    AccountService accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
//                .antMatchers("/order/**").authenticated()
//                .antMatchers("/admin/**").hasAnyRole("STAF", "DIRE")
//                .antMatchers("/rest/authorities").hasRole("DIRE")
                .antMatchers("/travel/**").hasRole("ADMIN")
                .antMatchers("/api/v1/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        http.formLogin()
                .loginPage("/security/login/form")
                .loginProcessingUrl("/security/login")
                .defaultSuccessUrl("/security/login/success", false)
                .failureUrl("/security/login/error");

//        http.rememberMe()
//                .tokenValiditySeconds(86400);

//        http.exceptionHandling()
//                .accessDeniedPage("/security/anauthoried");

        http.logout()
                .logoutUrl("/security/logoff")
                .logoutSuccessUrl("/security/logoff/success");

//        //      OAuth2 - ĐĂNG NHẬP TỪ MẠNG XÃ HỘI
//        http.oauth2Login()
//                .loginPage("/security/login/form")
//                .defaultSuccessUrl("/oauth2/login/success", true)
//                .failureUrl("/security/login/error")
//                .authorizationEndpoint()
//                .baseUri("/oauth2/authorization");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            try {
                Account account = accountService.findAccountsByUsername(username);
                String password = account.getPassword();
                String [] roles = {account.getRole_Id().getRole()};
                String role = Arrays.toString(roles);
                System.out.println(role);
                return User.withUsername(username).password(getPasswordEncoder().encode(password)).roles(role).build();
            } catch (NoSuchElementException e) {
                throw new UsernameNotFoundException(username + "Not Found !");
            }
        });
    }

    //	Cơ chế mã hóa mật khẩu
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //	Cho phép truy xuất REST API từ bên ngoài (domain khác)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
