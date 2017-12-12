package com.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TrainTripStopping {

    private Station station;
    private String stopTime;
    private String startTime;

    public TrainTripStopping(Station station, String stopTime, String startTime) {
        this.station = station;
        this.stopTime = stopTime;
        this.startTime = startTime;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public StringProperty stationProperty() {
        return new SimpleStringProperty(station.getName());
    }


}
