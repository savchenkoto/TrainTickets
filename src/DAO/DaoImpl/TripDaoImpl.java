package DAO.DaoImpl;

import DAO.BaseDaoInterface;
import model.Trip;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtils;

import java.util.List;

public class TripDaoImpl implements BaseDaoInterface<Trip, Integer> {
    @Override
    public void add(Trip trip) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(trip);
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
        }
    }

    @Override
    public void update(Trip updatedTrip) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();

            Trip trip = session.load(Trip.class, updatedTrip.getId());
            Hibernate.initialize(trip);
            trip.setDate(updatedTrip.getDate());
            trip.setTrainId(updatedTrip.getTrainId());

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
    public Trip findById(Integer id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session = HibernateUtils.getSessionFactory().openSession();
        Trip trip = session.load(Trip.class, id);
        Hibernate.initialize(trip);
        session.close();
        return trip;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Trip trip = session.load(Trip.class, id);
            session.delete(trip);
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
        Query query = session.createQuery("from Trip ");
        List<Trip> trips = query.list();
        session.close();
        return trips;
    }

    @Override
    public void deleteAll() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("DELETE from Trip");
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
