package com.autumn;

import com.autumn.modules.core.entity.Corp;
import com.autumn.security.CurrentCorpDetection;
import com.autumn.modules.core.service.CorpService;
import com.autumn.modules.core.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CorpService corpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MenuItemService menuItemService;

    private volatile boolean neverExecuted = true;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("refreshed");
        if (neverExecuted) {
            synchronized (this) {
                if (neverExecuted) {
                    neverExecuted = false;

                    doInitialize();
                }
            }
        }
    }

    protected void doInitialize() {
        if (!corpService.existCorp()) {
            Corp corp = corpService.createNewCorp("广州当凌信息科技有限公司", "wl000", "admin", passwordEncoder.encode("qwe123"));
            CurrentCorpDetection.setCorpCode(corp.getCode());

            menuItemService.add(null, "租户", "corp", "/corp", "fa fa-dashboard");
            menuItemService.add(null, "部门", "dept", "/dept", "fa fa-group");
            menuItemService.add(null, "用户", "user", "/user", "fa fa-user");
            menuItemService.add(null, "角色", "role", "/role", "fa fa-th");
            menuItemService.add(null, "菜单", "menu", "/menu", "fa fa-list");
            menuItemService.add(null, "数据库", "h2", "/h2", "fa fa-database");
            CurrentCorpDetection.cleanup();


            corp = corpService.createNewCorp("广州微禹信息科技有限公司", "wy000", "admin1", "qwe123");
            CurrentCorpDetection.setCorpCode(corp.getCode());

            menuItemService.add(null, "部门", "dept", "/dept", "fa fa-group");
            menuItemService.add(null, "用户", "user", "/user", "fa fa-user");
            menuItemService.add(null, "角色", "role", "/role", "fa fa-th");
            menuItemService.add(null, "菜单", "menu", "/menu", "fa fa-list");
            menuItemService.add(null, "数据库", "h2", "/h2", "fa fa-database");
            CurrentCorpDetection.cleanup();
        }

    }
}
