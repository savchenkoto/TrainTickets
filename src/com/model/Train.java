package com.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "trains", schema = "ticketsdb")
public class Train {
    private Integer id;
    private String number;
    private String departure;
    private String destination;
    private Collection<Car> carsById;
    private Collection<Stopping> stoppingsById;
    private Collection<Trip> tripsById;

    public Train(String number, String departure, String destination) {
        this.number = number;
        this.departure = departure;
        this.destination = destination;
    }

    public Train() { }

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

        if (id != null ? !id.equals(train.id) : train.id != null) return false;
        if (number != null ? !number.equals(train.number) : train.number != null) return false;
        if (departure != null ? !departure.equals(train.departure) : train.departure != null) return false;
        if (destination != null ? !destination.equals(train.destination) : train.destination != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }


    @OneToMany(mappedBy = "trainByTrainId", cascade = CascadeType.ALL)
    public Collection<Car> getCarsById() {
        return carsById;
    }

    public void setCarsById(Collection<Car> carsById) {
        this.carsById = carsById;
    }

    @OneToMany(mappedBy = "trainByTrainId", cascade = CascadeType.ALL)
    public Collection<Stopping> getStoppingsById() {
        return stoppingsById;
    }

    public void setStoppingsById(Collection<Stopping> stoppingsById) {
        this.stoppingsById = stoppingsById;
    }

    @OneToMany(mappedBy = "trainByTrainId", cascade = CascadeType.ALL)
    public Collection<Trip> getTripsById() {
        return tripsById;
    }

    public void setTripsById(Collection<Trip> tripsById) {
        this.tripsById = tripsById;
    }

    public void update(Train updatedTrain) {
        this.setNumber(updatedTrain.getNumber());
        this.setDeparture(updatedTrain.getDeparture());
        this.setDestination(updatedTrain.getDestination());
    }

    @Override
    public String toString() {
        return number;
    }

}
