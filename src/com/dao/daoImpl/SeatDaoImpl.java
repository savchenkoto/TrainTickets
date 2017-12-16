package com.dao.daoImpl;

import com.domain.Car;
import com.domain.Seat;
import com.util.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SeatDaoImpl extends GenericDaoImpl<Seat, Integer> {

    public SeatDaoImpl(Class<Seat> type) {
        super(type);
    }

    public List<Seat> listUnengagedSeatsOfCar(Car car) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Seat> criteria = builder.createQuery(Seat.class);

        Root<Seat> root = criteria.from(Seat.class);
        criteria.where(builder.and(
                builder.equal(root.get("isTaken"), 0),
                builder.equal(root.get("carByCarId"), car)
        ));

        List<Seat> results = session.createQuery(criteria).getResultList();
        session.close();
        return results;
    }
}
