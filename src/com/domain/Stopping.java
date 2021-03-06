package com.domain;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stoppings", schema = "trainsdb")
public class Stopping  implements Comparable<Stopping> {
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
        return Objects.equals(id, stopping.id) &&
                Objects.equals(stopTime, stopping.stopTime) &&
                Objects.equals(startTime, stopping.startTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, stopTime, startTime);
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

    @Override
    public int compareTo(@NotNull com.domain.Stopping stopping) {
        return startTime.compareTo(stopping.getStartTime());
    }
}
