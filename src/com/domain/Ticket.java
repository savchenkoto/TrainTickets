package com.domain;

import javax.persistence.*;

@Entity
@Table(name = "tickets", schema = "ticketsdb")
public class Ticket {
    private Integer id;
    private Trip tripByTripId;
    private Seat seatBySeatId;

    public Ticket(Trip tripByTripId, Seat seatBySeatId, Person personByPersonId) {
        this.tripByTripId = tripByTripId;
        this.seatBySeatId = seatBySeatId;
        this.personByPersonId = personByPersonId;
    }

    public Ticket() {

    }

    private Person personByPersonId;

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

        if (id != null ? !id.equals(ticket.id) : ticket.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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

    public void update(Ticket updatedTicket) {
        this.setPersonByPersonId(updatedTicket.getPersonByPersonId());
        this.setTripByTripId(updatedTicket.getTripByTripId());
        this.setSeatBySeatId(updatedTicket.getSeatBySeatId());
    }
}
