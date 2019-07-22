package com.autumn.support.web;

import com.autumn.modules.core.entity.User;
import com.autumn.security.UserAuditorAware;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {
    @Autowired
    private UserAuditorAware userAuditorAware;

    public User getCurrentUser() {
        return userAuditorAware.getCurrentAuditor().orElse(null);
    }
}
