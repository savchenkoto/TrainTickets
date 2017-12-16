package com.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.Transient;
import java.text.SimpleDateFormat;
import java.util.*;

public class TrainTrip {

    private Trip trip;
    private Train train;
    private Calendar departureDate;
    private String departureTime;
    private String departureStation;
    private String destinationTime;
    private String destinationStation;
    private List<TrainTripStopping> stoppings;


    // Cмотреть сюда !!!!

    public TrainTrip(Trip trip) {
        this.trip = trip;
        this.setTrain(trip.getTrainByTrainId());
        List<Stopping> stoppings = (List<Stopping>) this.getTrain().getStoppingsById();
        Collections.sort(stoppings);
        this.setDepartureDate(toCalendar(trip.getDate()));
        this.setDepartureTime(
                new SimpleDateFormat("MMM dd 'at' HH:mm")
                        .format(fromDeparture(stoppings.get(0).getStartTime()).getTime()));
        this.setDepartureStation(stoppings.get(0).getStationByStationId().getName());
        this.setDestinationTime(
                new SimpleDateFormat("MMM dd 'at' HH:mm")
                        .format(fromDeparture(stoppings.get(stoppings.size() - 1).getStartTime())
                                .getTime()));
        this.setDestinationStation
                (stoppings.get(stoppings.size() - 1).getStationByStationId().getName());

        this.updateStoppings(stoppings);
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Calendar getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Calendar departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(String destinationTime) {
        this.destinationTime = destinationTime;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public List<TrainTripStopping> getStoppings() {
        return stoppings;
    }

    public void setStoppings(List<TrainTripStopping> stoppings) {
        this.stoppings = stoppings;
    }

    private void updateStoppings(List<Stopping> stoppings) {
        this.stoppings = new ArrayList<TrainTripStopping>();
        for (Stopping stopping : stoppings) {
            if (!stopping.getStopTime().equals(stopping.getStartTime())) {
                this.stoppings.add(new TrainTripStopping(
                        stopping.getStationByStationId(),
                        new SimpleDateFormat("HH:mm").format(fromDeparture(stopping.getStopTime()).getTime()),
                        new SimpleDateFormat("HH:mm").format(fromDeparture(stopping.getStartTime()).getTime())
                ));
            } else {
                if (stopping.getStopTime() == 0) {
                    this.stoppings.add(new TrainTripStopping(
                            stopping.getStationByStationId(),
                            new SimpleDateFormat("MMM dd").format(fromDeparture(stopping.getStopTime()).getTime()),
                            new SimpleDateFormat("HH:mm").format(fromDeparture(stopping.getStartTime()).getTime())
                    ));
                } else {
                    this.stoppings.add(new TrainTripStopping(
                            stopping.getStationByStationId(),
                            new SimpleDateFormat("HH:mm").format(fromDeparture(stopping.getStopTime()).getTime()),
                            new SimpleDateFormat("MMM dd").format(fromDeparture(stopping.getStartTime()).getTime())
                    ));
                }

            }

        }
    }

    @Transient
    public StringProperty trainProperty() {
        return new SimpleStringProperty(train.getNumber());
    }

    private Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private Calendar fromDeparture(int minutes) {
        Calendar cal = (Calendar) this.getDepartureDate().clone();
        cal.add(Calendar.MINUTE, minutes);
        return cal;
    }

    public HashSet<Type> listCarTypes() {
        HashSet<Type> result = new HashSet<Type>();
        List<Car> cars = (List<Car>) this.getTrip().getTrainByTrainId().getCarsById();
        for (Car car : cars) {
            if (!result.contains(car.getTypeByTypeId()) &&
                    car.getSeatsById().stream().anyMatch(o -> o.getIsTaken() == 0))

                result.add(car.getTypeByTypeId());
        }
        return result;
    }




}
