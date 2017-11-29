package model;

import javax.persistence.*;

@Entity
@Table(name = "trains", schema = "ticketsdb")
public class Train {
    private int id;
    private String number;
    private String departure;
    private String destination;

    public Train(String number, String departure, String destination) {
        this.number = number;
        this.departure = departure;
        this.destination = destination;
    }

    public Train() {

    }

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
    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "departure")
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    @Basic
    @Column(name = "destination")
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

        if (id != train.id) return false;
        if (number != null ? !number.equals(train.number) : train.number != null) return false;
        if (departure != null ? !departure.equals(train.departure) : train.departure != null) return false;
        if (destination != null ? !destination.equals(train.destination) : train.destination != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }
}
