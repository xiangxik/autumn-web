package com.autumn.modules.core.entity;

import com.autumn.support.data.jpa.TreeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_department")
public class Department extends TreeEntity<Department, Long> {

    @NotNull
    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
