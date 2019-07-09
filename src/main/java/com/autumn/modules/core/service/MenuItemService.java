package com.autumn.modules.core.service;

import com.autumn.modules.core.entity.MenuItem;
import com.autumn.modules.core.repo.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public void save(MenuItem menuItem) {
        menuItemRepository.save(menuItem);
    }

    public List<MenuItem> findRoot() {
        return menuItemRepository.findRoots();
    }

    public MenuItem add(MenuItem parent, String name, String code, String href, String iconCls) {
        MenuItem menuItem = new MenuItem();
        menuItem.setParent(parent);
        menuItem.setCode(code);
        menuItem.setName(name);
        menuItem.setHref(href);
        menuItem.setIconCls(iconCls);
        return menuItemRepository.save(menuItem);
    }
}
