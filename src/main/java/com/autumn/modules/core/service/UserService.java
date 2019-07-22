package com.autumn.modules.core.service;

import com.autumn.modules.core.entity.User;
import com.autumn.support.service.CrudService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CrudService<User, Long> {

    public User createAdmin(String username, String encodedPassword, String corpCode) {
        User admin = new User();
        admin.setName("管理员");
        admin.setUsername(username);
        admin.setPassword(encodedPassword);
        admin.setLocked(true);
        admin.setCorpCode(corpCode);
        return getRepository().save(admin);
    }

}
