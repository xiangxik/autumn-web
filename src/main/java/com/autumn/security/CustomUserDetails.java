package com.autumn.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    public static final String CORP_CODE_REQUEST_PARAMETER = "corpCode";
    public static final String CORP_CODE_COOKIE = "corp_code";
    private static final long serialVersionUID = 8063484673226426535L;
    private final Long id;

    private final String corpCode;

    public CustomUserDetails(String corpCode, Long id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.id = id;
        this.corpCode = corpCode;
    }

    public Long getId() {
        return id;
    }

    public String getCorpCode() {
        return corpCode;
    }
}
