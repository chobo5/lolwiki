package org.example.dao;

import java.util.List;

public interface Dao<T> {
    void add(T t);

    List<T> findAll();

    T findBy(int id);

    T findBy(String keyword);

    int delete(int id);

    int delete(String keyword);

    int update(T t);

}
