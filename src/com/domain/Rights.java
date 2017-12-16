package com.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "rights", schema = "trainsdb")
public class Rights {
    private Integer id;
    private String type;
    private Collection<Person> peopleById;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 45)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rights rights = (Rights) o;
        return Objects.equals(id, rights.id) &&
                Objects.equals(type, rights.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type);
    }

    @OneToMany(mappedBy = "rightsByRightsId")
    public Collection<Person> getPeopleById() {
        return peopleById;
    }

    public void setPeopleById(Collection<Person> peopleById) {
        this.peopleById = peopleById;
    }
}
