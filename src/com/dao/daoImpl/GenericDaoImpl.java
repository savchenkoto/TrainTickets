package com.dao.daoImpl;
import com.dao.GenericDao;
import com.util.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    private Class<T> type;

    public GenericDaoImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public PK save(T newInstance) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        PK id = (PK) session.save(newInstance);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    @Override
    public T get(PK id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        T object = session.get(type, id);
        session.close();
        return object;
    }

    @Override
    public void update(T transientObject) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(transientObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(T persistentObject) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(persistentObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<T> list() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);

        Root<T> contactRoot = criteria.from(type);
        criteria.select(contactRoot);

        List<T> objects = session.createQuery(criteria).getResultList();
        session.close();
        return objects;
    }
}
