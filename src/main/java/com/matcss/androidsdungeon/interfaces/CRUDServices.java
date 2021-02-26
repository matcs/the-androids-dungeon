package com.matcss.androidsdungeon.interfaces;

import java.util.List;

public interface CRUDServices<T> {
    List<T> findAll();
    T findById(int id);
    T create(T obj);
    T update(int id, T obj);
    void delete(int id);
}
