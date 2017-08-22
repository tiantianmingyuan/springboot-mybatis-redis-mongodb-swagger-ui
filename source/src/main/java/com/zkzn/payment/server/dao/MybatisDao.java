package com.zkzn.payment.server.dao;

import java.io.Serializable;
import java.util.List;



public interface MybatisDao<T, ID extends Serializable> {

    T findOne(ID id);

    List<T> findAll();

    List<T> findAllByIds(Iterable<ID> ids);

    List<T> find(T bean);

    boolean exists(ID id);

    long countAll();

    int save(T entity);

    int saveBatch(List<T> entities);

    int deleteById(ID id);

    int delete(T entity);

    int deleteBatch(List<? extends T> entities);

    int deleteAll();

    int update(T entity);
}
