package DAO.DaoImpl;

import DAO.BaseDaoInterface;
import model.Station;
import model.Trip;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtils;

import java.util.List;

public class StationDaoImpl implements BaseDaoInterface<Station, Integer> {
    @Override
    public void add(Station station) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(station);
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
    public void update(Station updatedStation) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();

            Station station = session.load(Station.class, updatedStation.getId());
            Hibernate.initialize(station);
            station.setName(updatedStation.getName());

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
    public Station findById(Integer id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session = HibernateUtils.getSessionFactory().openSession();
        Station station = session.load(Station.class, id);
        Hibernate.initialize(station);
        session.close();
        return station;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Station station = session.load(Station.class, id);
            session.delete(station);
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
        Query query = session.createQuery("from Station");
        List<Station> stations = query.list();
        session.close();
        return stations;
    }

    @Override
    public void deleteAll() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("DELETE from Station");
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
