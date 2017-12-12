package com.controller;

import com.dao.daoImpl.GenericDaoImpl;
import com.domain.TrainTrip;
import com.domain.TrainTripStopping;
import com.domain.Trip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class TripsController {


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
        GenericDaoImpl<Trip, Integer> tripDao = new GenericDaoImpl<Trip, Integer>(Trip.class);
        List<Trip> trips = tripDao.list();
        tripList.clear();
        for (Trip trip : trips) {
            tripList.add(new TrainTrip(trip));
        }
    }

    private void setRowFactory() {
        tripTable.setRowFactory( tv -> {
            TableRow<TrainTrip> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    selectedTrainTrip = row.getItem();
                    stoppingsList.setAll(selectedTrainTrip.getStoppings());
                }
            });
            return row;
        });
    }

    @FXML
    private void buyButton(ActionEvent actionEvent) {
        if (selectedTrainTrip != null) {
            showBuyingDialog();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("You have not selected anything.");
            alert.showAndWait();
        }

    }

    private void showBuyingDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sample/templates/buying.fxml"));
            Parent root = (Parent)loader.load();
            BuyingController controller = loader.<BuyingController>getController();
            controller.initData(this.selectedTrainTrip);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Buying");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}