package com.autumn.support.data.jpa;

import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface TreeRepository<T extends TreeEntity<T, I>, I extends Serializable> extends EntityRepository<T, I> {

    List<T> findRoots();

    List<T> findAllChildren(T root);
}
