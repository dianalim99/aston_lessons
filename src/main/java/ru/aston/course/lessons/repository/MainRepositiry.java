package ru.aston.course.lessons.repository;

import java.util.List;

public interface MainRepositiry<T, K> {
    T findById(K id);

    boolean deleteById(K id);

    List<T> findAll();

    T save(T t);

    T update(T t);
}
