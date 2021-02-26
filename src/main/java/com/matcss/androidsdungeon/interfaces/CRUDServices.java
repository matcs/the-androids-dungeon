package com.matcss.androidsdungeon.interfaces;

import java.util.List;

public interface CRUDServices<T> {
    public List<T> findAll();
    public T findById(int id);
    public T create(T obj);
    public T update(int id, T obj);
    public void delete(int id);
}
