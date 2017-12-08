package com.Controller;

import com.DAO.DaoImpl.TripDaoImpl;
import com.model.TrainTrip;
import com.model.TrainTripStopping;
import com.model.Trip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TripsTableController {


    // stations
    private ObservableList<TrainTripStopping> stoppingsList = FXCollections.observableArrayList();

    @FXML
    private TableView<TrainTripStopping> stoppingTable;

    @FXML
    private TableColumn<TrainTripStopping, String> stopTimeColumn;

    @FXML
    private TableColumn<TrainTripStopping, String> stationColumn;

    @FXML
    private TableColumn<TrainTripStopping, String> startTimeColumn;


    // trips
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

    @FXML
    private void initialize() {
        initData();

        numberColumn.setCellValueFactory(new PropertyValueFactory<TrainTrip, String>("train"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<TrainTrip, String>("departureTime"));
        departureStationColumn.setCellValueFactory(new PropertyValueFactory<TrainTrip, String>("departureStation"));
        destinationTimeColumn.setCellValueFactory(new PropertyValueFactory<TrainTrip, String>("destinationTime"));
        destinationStationColumn.setCellValueFactory(new PropertyValueFactory<TrainTrip, String>("destinationStation"));

        stopTimeColumn.setCellValueFactory(new PropertyValueFactory<TrainTripStopping, String>("stopTime"));
        stationColumn.setCellValueFactory(new PropertyValueFactory<TrainTripStopping, String>("station"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<TrainTripStopping, String>("startTime"));

        stoppingTable.setItems(stoppingsList);

        tripTable.setItems(tripList);

    }

    private TrainTrip selectedTrainTrip;

    private void initData() {
        setRowFactory();
        TripDaoImpl tripDao = new TripDaoImpl();
        List<Trip> trips = tripDao.findAll();
        tripList.clear();
        for (Trip trip : trips) {
            tripList.add(new TrainTrip(trip));
        }
    }

    private void setRowFactory() {
        tripTable.setRowFactory( tv -> {
            TableRow<TrainTrip> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    selectedTrainTrip = row.getItem();
                    stoppingsList.setAll(selectedTrainTrip.getStoppings());
                }
            });
            return row;
        });
    }

}