package com.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "trains", schema = "trainsdb")
public class Train {
    private Integer id;
    private String number;
    private String departure;
    private String destination;
    private Collection<Car> carsById;
    private Collection<Stopping> stoppingsById;
    private Collection<Trip> tripsById;

    public Train(String number, String departure, String destination) {
        this.setNumber(number);
        this.setDeparture(departure);
        this.setDestination(destination);
    }

    public Train() {

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
    @Column(name = "number", nullable = false, length = 45)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "departure", nullable = false, length = 45)
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    @Basic
    @Column(name = "destination", nullable = false, length = 45)
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return Objects.equals(id, train.id) &&
                Objects.equals(number, train.number) &&
                Objects.equals(departure, train.departure) &&
                Objects.equals(destination, train.destination);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, number, departure, destination);
    }

    @OneToMany(mappedBy = "trainByTrainId")
    public Collection<Car> getCarsById() {
        return carsById;
    }

    public void setCarsById(Collection<Car> carsById) {
        this.carsById = carsById;
    }

    @OneToMany(mappedBy = "trainByTrainId")
    public Collection<Stopping> getStoppingsById() {
        return stoppingsById;
    }

    public void setStoppingsById(Collection<Stopping> stoppingsById) {
        this.stoppingsById = stoppingsById;
    }

    @OneToMany(mappedBy = "trainByTrainId")
    public Collection<Trip> getTripsById() {
        return tripsById;
    }

    public void setTripsById(Collection<Trip> tripsById) {
        this.tripsById = tripsById;
    }

    @Override
    public String toString() {
        return number;
    }
}
