package model;

import javax.persistence.*;

@Entity
@Table(name = "seats", schema = "ticketsdb")
public class Seat {
    private int id;
    private int carId;
    private int seat;
    private byte isBorrowed;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "car_id")
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Basic
    @Column(name = "seat")
    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    @Basic
    @Column(name = "is_borrowed")
    public byte getIsBorrowed() {
        return isBorrowed;
    }

    public void setIsBorrowed(byte isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat1 = (Seat) o;

        if (id != seat1.id) return false;
        if (carId != seat1.carId) return false;
        if (seat != seat1.seat) return false;
        if (isBorrowed != seat1.isBorrowed) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + carId;
        result = 31 * result + seat;
        result = 31 * result + (int) isBorrowed;
        return result;
    }
}
