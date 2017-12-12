package com.dao.daoImpl;

import com.domain.Person;
import com.util.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PersonDaoImpl extends GenericDaoImpl<Person, Integer> {

    public PersonDaoImpl(Class<Person> type) {
        super(type);
    }

    public Person getByPassId(Integer passId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = builder.createQuery(Person.class);


        Root<Person> personRoot = criteria.from(Person.class);
        criteria.select(personRoot);

        criteria.where(builder.equal(personRoot.get("passId"), passId));
        Person result;
        try {
            result = session.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            result = null;
        } finally {
            session.close();
        }
        return result;
    }



}
