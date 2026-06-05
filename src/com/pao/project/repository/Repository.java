package com.pao.project.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    void save(T obiect);

    Optional<T> findById(ID id);

    List<T> findAll();

    void update(T obiect);

    void delete(ID id);
}
