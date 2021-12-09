package com.nicetravel.security;

import com.nicetravel.entity.Role;
import com.nicetravel.security.auth.CustomOAuth2User;
import com.nicetravel.security.auth.CustomOAuth2UserService;
import com.nicetravel.custom.CustomUserDetailsService;
import com.nicetravel.custom.UserService;
import com.nicetravel.entity.Account;
import com.nicetravel.security.auth.DatabaseLoginSuccessHandler;
import com.nicetravel.security.auth.OAuthLoginSuccessHandle;
import com.nicetravel.service.AccountService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
//
    @Autowired
    AccountService accountService;

    @Autowired
    private CustomOAuth2UserService oauth2UserService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Autowired
    private OAuthLoginSuccessHandle oauthLoginSuccessHandler;

    @Autowired
    private DatabaseLoginSuccessHandler databaseLoginSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/login", "/oauth/**").permitAll()
                .antMatchers("/booking/**", "/travel/like/**").authenticated()
                .antMatchers("/thanh-toan/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/staff/**").hasRole("STAFF")
                .antMatchers("/customer/**").hasRole( "USER")
                .antMatchers("/api/v1/**", "/api/v2/**").hasAnyRole("ADMIN", "STAFF")
                .anyRequest().permitAll();

        http.formLogin().permitAll()
                .loginPage("/login")
                .loginProcessingUrl("/account/login")
                .failureUrl("/login/error")
                .usernameParameter("username")
                .passwordParameter("password")
//                .successHandler(databaseLoginSuccessHandler)
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauth2UserService)
                .and()
                .successHandler(oauthLoginSuccessHandler)
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");

        http.rememberMe();

        http.logout()
                .logoutUrl("/logoff")
                .logoutSuccessUrl("/logoff/success")
                .deleteCookies("JSESSIONID");


    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            try {
                Account account = accountService.findAccountsByUsername(username);
                String password = account.getPassword();
                System.out.println(username);
                System.out.println(password);
                String roles = account.getRole_Id().getRole();
//                String [] roles = account.getRoles().stream()
//                        .map(Role::getRole).toArray(String[]::new);
//                System.out.println(Arrays.toString(roles));
                return User.withUsername(username).password(password).roles(roles).build();
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
