package com.autumn.support.view;

import com.autumn.modules.core.entity.User;
import com.autumn.modules.core.service.MenuItemService;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Component
public class ViewInitializer implements SmartInitializingSingleton {

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private AuditorAware<User> auditorAware;

    @Autowired
    private MenuItemService menuItemService;

    @Override
    public void afterSingletonsInstantiated() {
        ViewVariable viewVariable = new ViewVariable(menuItemService, auditorAware);

        thymeleafViewResolver.addStaticVariable("vv", viewVariable);
    }

}
