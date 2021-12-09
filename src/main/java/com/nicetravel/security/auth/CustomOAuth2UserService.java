package com.nicetravel.security.auth;

import com.nicetravel.security.auth.CustomOAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String clientName = userRequest.getClientRegistration().getClientName();
        System.out.println("clientName: " + clientName);
        OAuth2User user =  super.loadUser(userRequest);
        System.out.println("user: " + user);
        return new CustomOAuth2User(user, clientName);
    }

}
