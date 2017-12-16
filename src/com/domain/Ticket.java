package com.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tickets", schema = "trainsdb")
public class Ticket {
    private Integer id;
    private Trip tripByTripId;
    private Seat seatBySeatId;
    private Person personByPersonId;

    public Ticket(Trip trip, Seat seat, Person person) {
        this.setTripByTripId(trip);
        this.setSeatBySeatId(seat);
        this.setPersonByPersonId(person);
    }

    public Ticket() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id", nullable = false)
    public Trip getTripByTripId() {
        return tripByTripId;
    }

    public void setTripByTripId(Trip tripByTripId) {
        this.tripByTripId = tripByTripId;
    }

    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id", nullable = false)
    public Seat getSeatBySeatId() {
        return seatBySeatId;
    }

    public void setSeatBySeatId(Seat seatBySeatId) {
        this.seatBySeatId = seatBySeatId;
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }
}
