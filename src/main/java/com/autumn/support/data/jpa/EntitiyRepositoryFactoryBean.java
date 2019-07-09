package com.autumn.support.data.jpa;

import com.autumn.support.data.CorpDetection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class EntitiyRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {
    public EntitiyRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        CorpDetection corpDetection = new SpringLazyCorpDetection();
        return new CustomRepositoryFactory<>(corpDetection, entityManager);
    }

    private static class CustomRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

        private final CorpDetection corpDetection;

        public CustomRepositoryFactory(CorpDetection corpDetection, EntityManager entityManager) {
            super(entityManager);

            this.corpDetection = corpDetection;
        }

        @Override
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            if (isEntityRepository(information.getRepositoryInterface())) {
                if (isTreeRepository(information.getRepositoryInterface())) {
                    return new TreeRepositoryImpl(corpDetection, getEntityInformation(information.getDomainType()), entityManager);
                }
                return new EntityRepositoryImpl<>(corpDetection, getEntityInformation(information.getDomainType()), entityManager);
            }
            return super.getTargetRepository(information, entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            if (isEntityRepository(metadata.getRepositoryInterface())) {
                if (isTreeRepository(metadata.getRepositoryInterface())) {
                    return TreeRepositoryImpl.class;
                }
                return EntityRepositoryImpl.class;
            }
            return super.getRepositoryBaseClass(metadata);
        }

        private boolean isEntityRepository(Class<?> repositoryInterface) {
            return ClassUtils.isAssignable(EntityRepository.class, repositoryInterface);
        }

        private boolean isTreeRepository(Class<?> repositoryInterface) {
            return ClassUtils.isAssignable(TreeRepository.class, repositoryInterface);
        }
    }
}
