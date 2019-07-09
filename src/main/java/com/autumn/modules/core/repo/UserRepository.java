package com.autumn.modules.core.repo;

import com.autumn.modules.core.entity.User;
import com.autumn.support.data.jpa.EntityRepository;

public interface UserRepository extends EntityRepository<User, Long> {

    User findByUsernameAndCorpCode(String username, String corpCode);

}
