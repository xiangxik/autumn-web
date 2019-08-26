package com.autumn.support.data.jpa;

import com.autumn.support.data.CorpDetection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class TreeRepositoryImpl<T extends TreeEntity<T, I>, I extends Serializable> extends EntityRepositoryImpl<T, I> implements TreeRepository<T, I> {

    public TreeRepositoryImpl(CorpDetection corpDetection, JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        super(corpDetection, entityInformation, entityManager);
    }

    @Override
    public List<T> findRoots() {
        return getQuery((root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("parent")), (Sort) null).getResultList();
    }

    @Override
    public List<T> findAllChildren(T parent) {
        return getQuery((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("treePath"), parent.getTreePath() + parent.getId() + "%"), (Sort) null).getResultList();
    }
}
