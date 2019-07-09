package com.autumn.support.view;

import java.util.List;

public class MenuItemVo {

    private List<MenuItemVo> children;

    private Integer sortNo;

    private String name;
    private String code;
    private String iconCls;
    private String href;
    private String parameters;

    public MenuItemVo(String name, String code, String iconCls, String href, String parameters) {
        this.name = name;
        this.code = code;
        this.iconCls = iconCls;
        this.href = href;
        this.parameters = parameters;
    }

    public static MenuItemVo root(String name, String code) {
        return create(name, code, null, null, null);
    }

    public static MenuItemVo create(String name, String code, String iconCls, String href, String parameters) {
        MenuItemVo menuItem = new MenuItemVo(name, code, iconCls, href, parameters);
        return menuItem;
    }

    public List<MenuItemVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItemVo> children) {
        this.children = children;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getIconCls() {
        return iconCls;
    }

    public String getHref() {
        return href;
    }

    public String getParameters() {
        return parameters;
    }

    public boolean isLeaf() {
        return children == null || children.size() == 0;
    }
}
