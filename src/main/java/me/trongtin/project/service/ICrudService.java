package me.trongtin.project.service;

import java.util.List;

public interface ICrudService<T, ID> {

    T get(ID id);
    List<T> getAll();
    T add(T t);
    T update(ID id, T t);
    void delete(ID id);

}
