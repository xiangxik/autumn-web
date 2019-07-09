package com.autumn.support.data.jpa;

import com.autumn.support.data.CorpSupport;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class CorpEntity<I extends Serializable> extends DataEntity<I> implements CorpSupport {

    private String corpCode;

    @Override
    public String getCorpCode() {
        return corpCode;
    }

    @Override
    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }
}
