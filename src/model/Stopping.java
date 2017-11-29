package model;

import javax.persistence.*;

@Entity
@Table(name = "stoppings", schema = "ticketsdb")
public class Stopping {
    private int id;
    private int trainId;
    private int stationId;
    private int stopTime;
    private int startTime;

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
    @Column(name = "train_id")
    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    @Basic
    @Column(name = "station_id")
    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "stop_time")
    public int getStopTime() {
        return stopTime;
    }

    public void setStopTime(int stopTime) {
        this.stopTime = stopTime;
    }

    @Basic
    @Column(name = "start_time")
    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stopping stopping = (Stopping) o;

        if (id != stopping.id) return false;
        if (trainId != stopping.trainId) return false;
        if (stationId != stopping.stationId) return false;
        if (stopTime != stopping.stopTime) return false;
        if (startTime != stopping.startTime) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + trainId;
        result = 31 * result + stationId;
        result = 31 * result + stopTime;
        result = 31 * result + startTime;
        return result;
    }
}
