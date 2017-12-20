package com.domain;

import com.dao.daoImpl.PersonDaoImpl;

public final class User {

    private static User instance = null;

    private User() {}
    private Person current;

    public static synchronized User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public Person getCurrent() {
        PersonDaoImpl personDao = new PersonDaoImpl(Person.class);
        return personDao.get(this.current.getId());
    }

    public void setCurrent(Person current) {
        this.current = current;
    }
}
