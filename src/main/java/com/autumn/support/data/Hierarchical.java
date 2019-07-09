package com.autumn.support.data;

import java.util.List;

public interface Hierarchical<T extends Hierarchical<T>> extends Sortable {
    T getParent();

    void setParent(T parent);

    List<T> getChildren();

    void setChildren(List<T> children);
}
