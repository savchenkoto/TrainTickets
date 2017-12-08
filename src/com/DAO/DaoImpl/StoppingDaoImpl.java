package com.DAO.DaoImpl;

import com.DAO.BaseDaoInterface;
import com.model.Stopping;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.util.HibernateUtils;

import java.util.List;

public class StoppingDaoImpl implements BaseDaoInterface<Stopping, Integer> {

    @Override
    public int add(Stopping stopping) {
        int result = 0;
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            result = (Integer) session.save(stopping);
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
    public void update(Stopping updatedStopping) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();

            Stopping stopping = session.load(Stopping.class, updatedStopping.getId());
            Hibernate.initialize(stopping);
                
            stopping.update(updatedStopping);
            
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
    public Stopping findById(Integer id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session = HibernateUtils.getSessionFactory().openSession();
        Stopping stopping = session.load(Stopping.class, id);
        Hibernate.initialize(stopping);
        session.close();
        return stopping;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Stopping stopping = session.load(Stopping.class, id);
            session.delete(stopping);
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
        Query query = session.createQuery("from Stopping ");
        List<Stopping> stoppings = query.list();
        session.close();
        return stoppings;
    }

    @Override
    public void deleteAll() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("DELETE from Stopping");
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
