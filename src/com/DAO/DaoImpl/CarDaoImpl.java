package com.DAO.DaoImpl;

import com.DAO.BaseDaoInterface;
import com.model.Car;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.util.HibernateUtils;

import java.util.List;

public class CarDaoImpl implements BaseDaoInterface<Car, Integer> {

    @Override
    public int add(Car car) {
        int result = 0;
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            result = (Integer)session.save(car);
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
    public void update(Car updatedCar) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();

            Car car = session.load(Car.class, updatedCar.getId());
            Hibernate.initialize(car);
            car.update(updatedCar);

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
    public Car findById(Integer id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Car car = session.load(Car.class, id);
        Hibernate.initialize(car);
        session.close();
        return car;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Car car = session.load(Car.class, id);
            session.delete(car);
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
        Query query = session.createQuery("FROM Car ");
        List cars = query.list();
        session.close();
        return cars;
    }

    @Override
    public void deleteAll() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Car ");
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
