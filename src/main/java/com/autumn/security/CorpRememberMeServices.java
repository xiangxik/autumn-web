package com.autumn.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

public class CorpRememberMeServices extends TokenBasedRememberMeServices {

    public CorpRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    @Override
    protected String retrieveUserName(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getCorpCode() + DatabaseUserDetailsService.CORP_SPLIT + ((CustomUserDetails) principal).getUsername();
        }
        return super.retrieveUserName(authentication);
    }
}
