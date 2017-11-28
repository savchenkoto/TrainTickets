package DAO;

import model.Train;

import java.util.List;

public interface BaseDaoInterface<T, Id> {

    public void add(T entity);

    public void update(T entity);

    public T findById(Id id);

    public void deleteById(Id id);

    public List findAll();

    public void deleteAll();
}
