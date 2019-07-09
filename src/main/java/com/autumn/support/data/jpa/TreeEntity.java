package com.autumn.support.data.jpa;

import com.autumn.support.data.Hierarchical;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@MappedSuperclass
public class TreeEntity<T extends TreeEntity<T, I>, I extends Serializable> extends SortEntity<I> implements Hierarchical<T> {

    @Column(length = 500)
    private String treePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private T parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @OrderBy("sortNo asc")
    private List<T> children;

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    @Override
    public T getParent() {
        return parent;
    }

    @Override
    public void setParent(T parent) {
        this.parent = parent;
    }

    @Override
    public List<T> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<T> children) {
        this.children = children;
    }
}
