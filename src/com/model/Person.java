package com.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "people", schema = "ticketsdb")
public class Person {
    private Integer id;
    private String name;
    private Integer passId;
    private Collection<Ticket> ticketsById;

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
    public Integer getPassId() {
        return passId;
    }

    public void setPassId(Integer passId) {
        this.passId = passId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (passId != null ? !passId.equals(person.passId) : person.passId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (passId != null ? passId.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<Ticket> getTicketsById() {
        return ticketsById;
    }

    public void setTicketsById(Collection<Ticket> ticketsById) {
        this.ticketsById = ticketsById;
    }
}
