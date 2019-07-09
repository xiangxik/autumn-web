package com.autumn.support.view;

import com.autumn.modules.core.entity.MenuItem;
import com.autumn.modules.core.entity.User;
import com.autumn.modules.core.service.MenuItemService;
import com.google.common.collect.Lists;
import org.springframework.data.domain.AuditorAware;

import java.util.List;
import java.util.Optional;

public class ViewVariable {

    private MenuItemService menuItemService;
    private AuditorAware<User> auditorAware;

    public ViewVariable(MenuItemService menuItemService, AuditorAware<User> auditorAware) {
        this.menuItemService = menuItemService;
        this.auditorAware = auditorAware;
    }

    public UserInfo getCurrentUser() {
        UserInfo userInfo = new UserInfo("");
        Optional<User> currentAuditor = auditorAware.getCurrentAuditor();
        if (currentAuditor.isPresent()) {
            User user = currentAuditor.get();
            userInfo.setName(user.getName());
        }
        return userInfo;
    }

    public List<? extends MenuItemVo> getMenuRoots() {
        return Lists.transform(menuItemService.findRoot(), this::convert);
    }

    private MenuItemVo convert(MenuItem menuItem) {
        MenuItemVo vo = new MenuItemVo(menuItem.getName(), menuItem.getCode(), menuItem.getIconCls(), menuItem.getHref(), menuItem.getParameters());
        vo.setChildren(Lists.transform(menuItem.getChildren(), this::convert));
        return vo;
    }

}
