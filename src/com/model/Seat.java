package com.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "seats", schema = "ticketsdb")
public class Seat {
    private Integer id;
    private Integer seat;
    private Byte isBorrowed;
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
    @Column(name = "is_borrowed", nullable = false)
    public Byte getIsBorrowed() {
        return isBorrowed;
    }

    public void setIsBorrowed(Byte isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat1 = (Seat) o;

        if (id != null ? !id.equals(seat1.id) : seat1.id != null) return false;
        if (seat != null ? !seat.equals(seat1.seat) : seat1.seat != null) return false;
        if (isBorrowed != null ? !isBorrowed.equals(seat1.isBorrowed) : seat1.isBorrowed != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (isBorrowed != null ? isBorrowed.hashCode() : 0);
        return result;
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
}
