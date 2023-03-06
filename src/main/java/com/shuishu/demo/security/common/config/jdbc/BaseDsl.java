package com.shuishu.demo.security.common.config.jdbc;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2022-12-31 19:56
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
public class BaseDsl {
    @Autowired
    public JPAQueryFactory jpaQueryFactory;
    @Autowired
    public SQLQueryFactory sqlQueryFactory;
    @Resource
    @PersistenceContext
    public EntityManager entityManager;

    private static final int BATCH_SIZE = 10000;


    @Transactional(rollbackFor = RuntimeException.class)
    public void flush() {
        entityManager.flush();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public <T> T insert(T entity) {
        Assert.notNull(entity, "Entity must not be null!");
        entityManager.persist(entity);
        return entity;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public <T> T insertAndFlush(T entity) {
        T temp = insert(entity);
        flush();
        return temp;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public <T> T update(T entity) {
        Assert.notNull(entity, "Entity must not be null!");
        return entityManager.merge(entity);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public <T> T updateAndFlush(T entity) {
        T temp = update(entity);
        flush();
        return temp;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public <T> List<T> insertBatch(Iterable<T> entities) {
        Assert.notNull(entities, "Entities must not be null!");
        List<T> result = new ArrayList<T>();
        Iterator<T> iterator = entities.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            T entity = iterator.next();
            entityManager.persist(entity);
            result.add(entity);
            index++;
            if (index % BATCH_SIZE == 0) {
                entityManager.flush();
            }
        }
        if (index % BATCH_SIZE != 0) {
            entityManager.flush();
        }
        return result;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public <T> List<T> updateBatch(Iterable<T> entities) {
        Assert.notNull(entities, "Entities must not be null!");
        List<T> result = new ArrayList<T>();
        Iterator<T> iterator = entities.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            result.add(entityManager.merge(iterator.next()));
            index++;
            if (index % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        if (index % BATCH_SIZE != 0) {
            entityManager.flush();
            entityManager.clear();
        }
        return result;
    }
}
