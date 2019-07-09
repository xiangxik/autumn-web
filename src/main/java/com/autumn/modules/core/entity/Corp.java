package com.autumn.modules.core.entity;


import com.autumn.support.data.jpa.DataEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_corp")
public class Corp extends DataEntity<Long> {

    @NotNull
    @Column(length = 100)
    private String name;

    @NotNull
    @Column(unique = true, length = 100)
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
