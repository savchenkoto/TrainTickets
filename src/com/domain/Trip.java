package com.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "trips", schema = "ticketsdb")
public class Trip {
    private Integer id;
    private Date date;
    private Collection<Ticket> ticketsById;
    private Train trainByTrainId;

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
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        if (id != null ? !id.equals(trip.id) : trip.id != null) return false;
        if (date != null ? !date.equals(trip.date) : trip.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "tripByTripId", cascade = CascadeType.ALL)
    public Collection<Ticket> getTicketsById() {
        return ticketsById;
    }

    public void setTicketsById(Collection<Ticket> ticketsById) {
        this.ticketsById = ticketsById;
    }

    @ManyToOne
    @JoinColumn(name = "train_id", referencedColumnName = "id", nullable = false)
    public Train getTrainByTrainId() {
        return trainByTrainId;
    }

    public void setTrainByTrainId(Train trainByTrainId) {
        this.trainByTrainId = trainByTrainId;
    }

    public void update(Trip updatedTrip) {
        this.setDate(updatedTrip.getDate());
        this.setTrainByTrainId(updatedTrip.getTrainByTrainId());
    }

    @Transient
    public StringProperty trainByTrainIdProperty() {
        return new SimpleStringProperty(trainByTrainId.getNumber());
    }


}
