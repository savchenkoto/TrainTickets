package com.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

    PK save(T newInstance);

    T get(PK id);

    void update(T transientObject);

    void delete(T persistentObject);

    List<T> list();

}
