package com.controller;

import com.dao.daoImpl.TripDaoImpl;
import com.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ArchiveController {

    private ObservableList<TrainTrip> tripList = FXCollections.observableArrayList();

    @FXML
    private TableView<TrainTrip> tripTable;

    @FXML
    private TableColumn<TrainTrip, String> numberColumn;

    @FXML
    private TableColumn<TrainTrip, String> departureTimeColumn;

    @FXML
    private TableColumn<TrainTrip, String> departureStationColumn;

    @FXML
    private TableColumn<TrainTrip, String> destinationTimeColumn;

    @FXML
    private TableColumn<TrainTrip, String> destinationStationColumn;


    private ObservableList<Seat> passengersList = FXCollections.observableArrayList();

    @FXML
    private TableView<Seat> passengersTable;

    @FXML
    private TableColumn<Seat, String> carColumn;

    @FXML
    private TableColumn<Seat, String> typeColumn;

    @FXML
    private TableColumn<Seat, Integer> seatColumn;

    @FXML
    private TableColumn<Seat, String> passIdColumn;


    @FXML
    private void initialize() {

        initData();

        numberColumn.setCellValueFactory(new PropertyValueFactory<TrainTrip, String>("train"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<TrainTrip, String>("departureTime"));
        departureStationColumn.setCellValueFactory(new PropertyValueFactory<TrainTrip, String>("departureStation"));
        destinationTimeColumn.setCellValueFactory(new PropertyValueFactory<TrainTrip, String>("destinationTime"));
        destinationStationColumn.setCellValueFactory(new PropertyValueFactory<TrainTrip, String>("destinationStation"));

        carColumn.setCellValueFactory(new PropertyValueFactory<Seat, String>("car"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Seat, String>("type"));
        seatColumn.setCellValueFactory(new PropertyValueFactory<Seat, Integer>("seat"));
        passIdColumn.setCellValueFactory(new PropertyValueFactory<Seat, String>("passId"));

        tripTable.setItems(tripList);
        passengersTable.setItems(passengersList);

    }

    private TrainTrip selectedTrainTrip;

    private void initData() {
        setRowFactory();
        TripDaoImpl tripDao = new TripDaoImpl(Trip.class);
        initTrainTripList(tripDao.getDepartedTrains());

    }

    private void initTrainTripList(List<Trip> trips) {
        this.tripList.clear();
        for (Trip trip : trips) {
            this.tripList.add(new TrainTrip(trip));
        }
    }

    private void setRowFactory() {
        tripTable.setRowFactory( tv -> {
            TableRow<TrainTrip> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    selectedTrainTrip = row.getItem();
                    if (selectedTrainTrip != null) {
                        passengersList.setAll(selectedTrainTrip.getSeats());
                    }
                }
            });
            return row;
        });
    }


}
