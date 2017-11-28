package model;

import javax.persistence.*;

@Entity
@Table(name = "tickets", schema = "ticketsdb")
public class Ticket {
    private int id;
    private int tripId;
    private int seatId;
    private int personId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "trip_id")
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    @Basic
    @Column(name = "seat_id")
    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    @Basic
    @Column(name = "person_id")
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;
        if (tripId != ticket.tripId) return false;
        if (seatId != ticket.seatId) return false;
        if (personId != ticket.personId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + tripId;
        result = 31 * result + seatId;
        result = 31 * result + personId;
        return result;
    }
}
