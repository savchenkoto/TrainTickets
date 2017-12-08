package com.DAO.DaoImpl;

import com.DAO.BaseDaoInterface;
import com.model.Stopping;
import com.model.Train;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.query.Query;
import com.util.HibernateUtils;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;

import java.util.List;

public class TrainDaoImpl implements BaseDaoInterface<Train, Integer> {

    @Override
    public int add(Train train) {
        Integer result = 0;
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            result = (Integer)session.save(train);
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
    public void update(Train updatedTrain) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();

            Train train = session.load(Train.class, updatedTrain.getId());
            Hibernate.initialize(train);

            train.update(updatedTrain);

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
    public Train findById(Integer id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Train train = session.load(Train.class, id);
        Hibernate.initialize(train);
        session.close();
        return train;
    }

       @Override
    public void deleteById(Integer id) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Train train = session.load(Train.class, id);
            session.delete(train);
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
        Query query = session.createQuery("from Train ");
        List<Stopping> trains = query.list();
        session.close();
        return trains;
    }

    @Override
    public void deleteAll() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("DELETE from Train");
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
