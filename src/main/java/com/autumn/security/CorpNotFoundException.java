package com.autumn.security;

import org.springframework.security.core.AuthenticationException;

public class CorpNotFoundException extends AuthenticationException {

    public CorpNotFoundException(String msg) {
        super(msg);
    }

    public CorpNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

}
