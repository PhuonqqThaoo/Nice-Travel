package com.nicetravel.security;

import com.nicetravel.entity.Role;
import com.nicetravel.security.auth.CustomOAuth2User;
import com.nicetravel.security.auth.CustomOAuth2UserService;
import com.nicetravel.custom.CustomUserDetailsService;
import com.nicetravel.custom.UserService;
import com.nicetravel.entity.Account;
import com.nicetravel.security.login.LoginSuccessHandler;
import com.nicetravel.service.AccountService;
import lombok.SneakyThrows;
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
    private CustomOAuth2UserService oauthUserService;
    private LoginSuccessHandler LoginSuccessHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(getPasswordEncoder());
//
//        return authProvider;
//    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/login", "/oauth/**").permitAll()
                .antMatchers("/booking/**").authenticated()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/customer").hasAnyRole( "STAFF", "USER")
                .antMatchers("/api/v1/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/account/login")
//                .defaultSuccessUrl("/", false)
                .successHandler(LoginSuccessHandler)
                .failureUrl("/login/error")
                .usernameParameter("username")
                .passwordParameter("password");

        http.rememberMe();

        http.logout()
                .logoutUrl("/logoff")
                .logoutSuccessUrl("/logoff/success")
                .deleteCookies("JSESSIONID");

//        http.exceptionHandling()
//                .accessDeniedPage("/error");
//        http.oauth2Login()
//                .loginPage("/login")
//                .defaultSuccessUrl("/", true)
//                .failureUrl("/login/error")
//                .authorizationEndpoint()
//                .baseUri("/oauth2/authorization");
        http.oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauthUserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {

                    @SneakyThrows
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) {

                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                        userService.processOAuthPostLogin(oauthUser.getName(), oauthUser.getEmail());

                        response.sendRedirect("/");
                    }
                });


    }

    @Autowired UserService userService;

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
                return User.withUsername(username).password((password)).roles(roles).build();
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
