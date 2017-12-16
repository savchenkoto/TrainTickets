package com.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "seats", schema = "trainsdb")
public class Seat {
    private Integer id;
    private Integer seat;
    private Byte isTaken;
    private Car carByCarId;
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
    @Column(name = "seat", nullable = false)
    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    @Basic
    @Column(name = "is_taken", nullable = false)
    public Byte getIsTaken() {
        return isTaken;
    }

    public void setIsTaken(Byte isTaken) {
        this.isTaken = isTaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat1 = (Seat) o;
        return Objects.equals(id, seat1.id) &&
                Objects.equals(seat, seat1.seat) &&
                Objects.equals(isTaken, seat1.isTaken);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, seat, isTaken);
    }

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id", nullable = false)
    public Car getCarByCarId() {
        return carByCarId;
    }

    public void setCarByCarId(Car carByCarId) {
        this.carByCarId = carByCarId;
    }

    @OneToMany(mappedBy = "seatBySeatId")
    public Collection<Ticket> getTicketsById() {
        return ticketsById;
    }

    public void setTicketsById(Collection<Ticket> ticketsById) {
        this.ticketsById = ticketsById;
    }

    @Transient
    public StringProperty isTakenProperty() {
        SimpleStringProperty result = new SimpleStringProperty("");
        if (getIsTaken() == 1) {
            result.set("X");
        }
        return result;
    }
}
