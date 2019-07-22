package com.autumn.modules.core.controller;

import com.autumn.modules.core.entity.User;
import com.autumn.support.web.CrudController;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController extends CrudController<User, Long> {

    @Override
    public Page<User> doPage(Predicate predicate, Pageable pageable) {
        return super.doInternalPage(predicate, pageable);
    }
}
