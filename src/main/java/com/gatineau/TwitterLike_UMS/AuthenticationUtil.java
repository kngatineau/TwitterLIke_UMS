package com.gatineau.TwitterLike_UMS;

import com.gatineau.TwitterLike_UMS.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthenticationUtil {
    @Autowired
    OAuth2AuthorizedClientService clientService;

    public User getAuthToken() {
        String githubId = null;
        String name = null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) auth;
            OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            githubId = attributes.get("login").toString();
            name = attributes.get("name").toString();
        }

        assert auth instanceof OAuth2AuthenticationToken;
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) auth;

        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

        User user = new User();
        user.setAuthToken(client.getAccessToken().getTokenValue());
        user.setGitHubID(githubId);
        user.setName(name);
        return user;
    }

    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || AnonymousAuthenticationToken.class.isAssignableFrom(auth.getClass())) {
            return false;
        }
        return auth.isAuthenticated();
    }
}