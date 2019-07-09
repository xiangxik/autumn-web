package com.autumn.support.data.jpa;

import com.autumn.support.data.Sortable;
import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class SortEntity<I extends Serializable> extends CorpEntity<I> implements Sortable, Comparable<SortEntity<I>> {

    private Integer sortNo;

    @Override
    public Integer getSortNo() {
        return sortNo;
    }

    @Override
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    @Override
    public int compareTo(SortEntity<I> o) {
        return new CompareToBuilder().append(getSortNo(), o.getSortNo()).append(getId(), o.getId()).toComparison();
    }
}
