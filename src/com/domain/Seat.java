package com.domain;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "seats", schema = "ticketsdb")
public class Seat {
    private Integer id;
    private Integer seat;
    private Byte isEngaged;
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
    @Column(name = "is_engaged", nullable = false)
    public Byte getIsEngaged() {
        return isEngaged;
    }

    public void setIsEngaged(Byte isBorrowed) {
        this.isEngaged = isBorrowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat1 = (Seat) o;

        if (id != null ? !id.equals(seat1.id) : seat1.id != null) return false;
        if (seat != null ? !seat.equals(seat1.seat) : seat1.seat != null) return false;
        if (isEngaged != null ? !isEngaged.equals(seat1.isEngaged) : seat1.isEngaged != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (isEngaged != null ? isEngaged.hashCode() : 0);
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

    public void update(Seat updatedSeat) {
        this.setSeat(updatedSeat.getSeat());
        this.setIsEngaged(updatedSeat.getIsEngaged());
    }
}
