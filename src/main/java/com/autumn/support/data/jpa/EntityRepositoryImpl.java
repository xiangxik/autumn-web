package com.autumn.support.data.jpa;

import com.autumn.support.data.CorpDetection;
import com.autumn.support.data.CorpSupport;
import com.autumn.support.data.Lockedable;
import com.autumn.support.data.LogicDeleteable;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;

public class EntityRepositoryImpl<T, I extends Serializable> extends QuerydslJpaRepository<T, I> implements EntityRepository<T, I> {
    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;
    private final EntityPath<T> path;
    private final PathBuilder<T> builder;
    private final Querydsl querydsl;
    private final JpaEntityInformation<T, I> entityInformation;
    private final EntityManager entityManager;
    private final CorpDetection corpDetection;

    public EntityRepositoryImpl(CorpDetection corpDetection, JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        this(corpDetection, entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
    }

    public EntityRepositoryImpl(CorpDetection corpDetection, JpaEntityInformation<T, I> entityInformation, EntityManager entityManager, EntityPathResolver resolver) {
        super(entityInformation, entityManager, resolver);

        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
        this.corpDetection = corpDetection;

        this.path = resolver.createPath(entityInformation.getJavaType());
        this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
        this.querydsl = new Querydsl(entityManager, builder);
    }

    @Override
    public <S extends T> S save(S entity) {
        if (entity instanceof CorpSupport && Strings.isNullOrEmpty(((CorpSupport) entity).getCorpCode())) {
            ((CorpSupport) entity).setCorpCode(corpDetection.getCurrentCorpCode());
        }
        return super.save(entity);
    }

    @Override
    public void delete(T entity) {
        if (entity instanceof Lockedable) {
            if (((Lockedable) entity).isLocked()) {
                throw new RuntimeException("cannot delete the locked entity.");
            }
        } else if (entity instanceof LogicDeleteable) {
            ((LogicDeleteable) entity).setDeleted(true);
            super.save(entity);
        } else {
            super.delete(entity);
        }
    }

    @Override
    protected JPQLQuery<?> createQuery(Predicate... predicate) {
        JPQLQuery<?> query = super.createQuery(predicate);
        makePredicate(query);
        makeSortable(query);
        return query;
    }

    @Override
    protected JPQLQuery<?> createCountQuery(Predicate... predicate) {
        JPQLQuery<?> query = super.createCountQuery(predicate);
        makePredicate(query);
        return query;
    }

    @Override
    protected <S extends T> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort) {
        // 排序实体
        if (ClassUtils.isAssignable(SortEntity.class, domainClass)) {

            Sort sortNoAsc = new Sort(Sort.Direction.ASC, "sortNo");
            if (sort == null) {
                sort = sortNoAsc;
            } else {
                if (!Iterables.tryFind(sort, s -> {
                    return Objects.equal(s.getProperty(), "sortNo");
                }).isPresent()) {
                    sort.and(sortNoAsc);
                }

            }
        }

        // 数据默认按创建时间倒序排序
        if (ClassUtils.isAssignable(AbstractAuditable.class, domainClass)) {
            Sort createdDateDesc = new Sort(Sort.Direction.DESC, "createdDate");

            if (sort == null) {
                sort = createdDateDesc;
            } else {
                if (!Iterables.tryFind(sort, s -> {
                    return Objects.equal(s.getProperty(), "createdDate");
                }).isPresent()) {
                    sort.and(createdDateDesc);
                }
            }
        }
        return super.getQuery(makeSpecification(spec, domainClass), domainClass, sort);
    }

    @Override
    protected <S extends T> TypedQuery<Long> getCountQuery(Specification<S> spec, Class<S> domainClass) {
        return super.getCountQuery(makeSpecification(spec, domainClass), domainClass);
    }

    protected <S extends T> Specification makeSpecification(Specification<S> spec, Class<S> domainClass) {
        if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
            spec = Specification.where(spec).and((root, query, cb) -> cb.isFalse(root.get("deleted")));
        }
        if (ClassUtils.isAssignable(CorpSupport.class, domainClass)) {
            spec = Specification.where(spec).and((root, query, cb) -> cb.equal(root.get("corpCode"), corpDetection.getCurrentCorpCode()));
        }
        return spec;
    }

    protected void makePredicate(JPQLQuery<?> query) {
        Class<T> domainClass = getDomainClass();
        if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
            BeanPath<T> beanPath = new BeanPath<>(domainClass, StringUtils.uncapitalize(domainClass.getSimpleName()));
            BooleanExpression deletedPropertyIsFalse = Expressions.booleanPath(PathMetadataFactory.forProperty(beanPath, "deleted"))
                    .isFalse();
            query.where(deletedPropertyIsFalse);
        }
        if (ClassUtils.isAssignable(CorpSupport.class, domainClass)) {
            BeanPath<T> beanPath = new BeanPath<>(domainClass, StringUtils.uncapitalize(domainClass.getSimpleName()));
            BooleanExpression equalsCurrentCorpCode = Expressions.comparablePath(String.class, PathMetadataFactory.forProperty(beanPath, "corpCode")).eq(corpDetection.getCurrentCorpCode());
            query.where(equalsCurrentCorpCode);
        }
    }

    protected void makeSortable(JPQLQuery<?> query) {
        Class<T> domainClass = getDomainClass();
        if (ClassUtils.isAssignable(SortEntity.class, domainClass)) {
            Sort sortNoAsc = new Sort(Sort.Direction.ASC, "sortNo");
            query = querydsl.applySorting(sortNoAsc, query);
        }
        if (ClassUtils.isAssignable(AbstractAuditable.class, domainClass)) {
            Sort createdDateDesc = new Sort(Sort.Direction.DESC, "createdDate");
            query = querydsl.applySorting(createdDateDesc, query);
        }
    }


}
