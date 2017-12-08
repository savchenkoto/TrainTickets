package com.model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "stoppings", schema = "ticketsdb")
public class Stopping implements Comparable<Stopping> {
    private Integer id;
    private Integer stopTime;
    private Integer startTime;
    private Train trainByTrainId;
    private Station stationByStationId;

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
    @Column(name = "stop_time", nullable = false)
    public Integer getStopTime() {
        return stopTime;
    }

    public void setStopTime(Integer stopTime) {
        this.stopTime = stopTime;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stopping stopping = (Stopping) o;

        if (id != null ? !id.equals(stopping.id) : stopping.id != null) return false;
        if (stopTime != null ? !stopTime.equals(stopping.stopTime) : stopping.stopTime != null) return false;
        if (startTime != null ? !startTime.equals(stopping.startTime) : stopping.startTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (stopTime != null ? stopTime.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "train_id", referencedColumnName = "id", nullable = false)
    public Train getTrainByTrainId() {
        return trainByTrainId;
    }

    public void setTrainByTrainId(Train trainByTrainId) {
        this.trainByTrainId = trainByTrainId;
    }

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id", nullable = false)
    public Station getStationByStationId() {
        return stationByStationId;
    }

    public void setStationByStationId(Station stationByStationId) {
        this.stationByStationId = stationByStationId;
    }

    public void update(Stopping updatedStopping) {
        this.setStartTime(updatedStopping.getStartTime());
        this.setStopTime(updatedStopping.getStopTime());
        this.setStationByStationId(updatedStopping.getStationByStationId());
    }

    @Override
    public int compareTo(@NotNull Stopping stopping) {
        return startTime.compareTo(stopping.getStartTime());
    }
}
