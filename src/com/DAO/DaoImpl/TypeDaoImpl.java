package com.DAO.DaoImpl;

import com.DAO.BaseDaoInterface;
import com.model.Type;
import com.util.HibernateUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TypeDaoImpl implements BaseDaoInterface<Type, Integer> {

    @Override
    public int add(Type type) {
        int result = 0;
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            result = (Integer)session.save(type);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null){
                session.close();
            }
            return result;
        }
    }

    @Override
    public void update(Type updatedType) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();

            Type type = session.load(Type.class, updatedType.getId());
            Hibernate.initialize(type);
            type.update(updatedType);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Type findById(Integer id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Type type = session.load(Type.class, id);
        Hibernate.initialize(type);
        session.close();
        return type;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Type type = session.load(Type.class, id);
            session.delete(type);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Type ");
        List types = query.list();
        session.close();
        return types;
    }

    @Override
    public void deleteAll() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Type ");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
