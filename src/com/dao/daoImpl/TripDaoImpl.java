package com.dao.daoImpl;

import com.domain.Trip;
import com.util.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TripDaoImpl extends GenericDaoImpl<Trip, Integer> {

    public TripDaoImpl(Class<Trip> type) {
        super(type);
    }

    public List<Trip> getDepartedTrains() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Trip> criteria = builder.createQuery(Trip.class);

        Root<Trip> trips = criteria.from(Trip.class);
        criteria.select(trips);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        java.sql.Date sqlDate = new java.sql.Date(now.getTime());
        criteria.where(builder.lessThan(trips.get("date"), sqlDate));

        List<Trip> results = session.createQuery(criteria).getResultList();
        session.close();
        return results;
    }

    public List<Trip> getFutureTrains() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Trip> criteria = builder.createQuery(Trip.class);

        Root<Trip> trips = criteria.from(Trip.class);
        criteria.select(trips);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        java.sql.Date sqlDate = new java.sql.Date(now.getTime());
        criteria.where(builder.greaterThan(trips.get("date"), sqlDate));

        List<Trip> results = session.createQuery(criteria).getResultList();
        session.close();
        return results;
    }



}
