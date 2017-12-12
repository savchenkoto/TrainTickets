package com.domain;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "stations", schema = "ticketsdb")
public class Station {
    private Integer id;
    private String name;
    private Collection<Stopping> stoppingsById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (id != null ? !id.equals(station.id) : station.id != null) return false;
        if (name != null ? !name.equals(station.name) : station.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "stationByStationId", cascade = CascadeType.ALL)
    public Collection<Stopping> getStoppingsById() {
        return stoppingsById;
    }

    public void setStoppingsById(Collection<Stopping> stoppingsById) {
        this.stoppingsById = stoppingsById;
    }
}
