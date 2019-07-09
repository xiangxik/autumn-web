package com.autumn.support.data.jpa;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity<I extends Serializable> extends AbstractPersistable<I> {

}
