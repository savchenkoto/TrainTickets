package com.domain;

import com.dao.daoImpl.GenericDaoImpl;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "people", schema = "trainsdb")
public class Person {
    private Integer id;
    private String name;
    private String passId;
    private Rights rightsByRightsId;
    private Collection<Ticket> ticketsById;

    public Person(String name, String passId) {
        this.setName(name);
        this.setPassId(passId);
        GenericDaoImpl<Rights, Integer> rightsDao = new GenericDaoImpl<Rights, Integer>(Rights.class);
        this.setRightsByRightsId(rightsDao.get(1));
    }

    public Person() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "pass_id", nullable = false)
    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) &&
                Objects.equals(passId, person.passId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, passId);
    }

    @ManyToOne
    @JoinColumn(name = "rights_id", referencedColumnName = "id", nullable = false)
    public Rights getRightsByRightsId() {
        return rightsByRightsId;
    }

    public void setRightsByRightsId(Rights rightsByRightsId) {
        this.rightsByRightsId = rightsByRightsId;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Ticket> getTicketsById() {
        return ticketsById;
    }

    public void setTicketsById(Collection<Ticket> ticketsById) {
        this.ticketsById = ticketsById;
    }

    public boolean hasTicketsOnTheTrip(Trip trip) {

        List<Ticket> tickets = (List<Ticket>) this.getTicketsById();
        if (tickets != null) {
            return tickets.stream().filter(o -> o.getTripByTripId().equals(trip)).findFirst().isPresent();
        }
        return false;
    }
}
