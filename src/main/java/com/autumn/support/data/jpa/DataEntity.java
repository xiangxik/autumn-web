package com.autumn.support.data.jpa;

import com.autumn.modules.core.entity.User;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class DataEntity<I extends Serializable> extends AbstractAuditable<User, I> {
}
