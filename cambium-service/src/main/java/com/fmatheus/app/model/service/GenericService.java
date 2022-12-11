package com.fmatheus.app.model.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    T save(T t);

    void deleteById(Integer id);
}
