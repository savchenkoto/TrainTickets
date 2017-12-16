package com.controller;

import com.dao.GenericDao;
import com.dao.daoImpl.GenericDaoImpl;
import com.domain.TrainTrip;
import com.domain.TrainTripStopping;
import com.domain.Trip;
import com.domain.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TripsController {

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

        GenericDaoImpl<Type, Integer> typeDao = new GenericDaoImpl<Type, Integer>(Type.class);
        typeComboBoxList.setAll(typeDao.list());
        typeComboBoxList.add(null);
        typeComboBox.setItems(typeComboBoxList);

        stoppingTable.setItems(stoppingsList);

        tripTable.setItems(tripList);

    }

    private TrainTrip selectedTrainTrip;

    private void initData() {
        setRowFactory();
        GenericDaoImpl<Trip, Integer> tripDao = new GenericDaoImpl<Trip, Integer>(Trip.class);
        initTripList(tripDao.list());
    }

    private void initTripList(List<Trip> trips) {
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

    // search control
    private ObservableList<Type> typeComboBoxList = FXCollections.observableArrayList();

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField fromField;

    @FXML
    private TextField toField;

    @FXML
    public ComboBox<Type> typeComboBox;

    @FXML
    public Button searchButton;

    public void find(ActionEvent actionEvent) {
        tripList.setAll(query(
                toCalendar(datePicker.getValue()),
                typeComboBox.getValue(),
                fromField.getText(),
                toField.getText()
        ));
        disableSelection();
    }

    private void disableSelection(){
        selectedTrainTrip = null;
        stoppingsList.clear();
    }

    private List<TrainTrip> query(Calendar date, Type type, String departure, String destination) {

        GenericDao<Trip, Integer> tripDao = new GenericDaoImpl<Trip, Integer>(Trip.class);
        initTripList(tripDao.list());

        List<TrainTrip> results = new ArrayList<TrainTrip>(tripList);
        if (datePicker.getValue() != null) {
            String queriedDate = new SimpleDateFormat("MMM dd").format(date.getTime());
            results = tripList.stream()
                    .filter(o -> new SimpleDateFormat("MMM dd")
                            .format(toCalendar(o.getTrip().getDate()).getTime())
                            .equals(queriedDate))
                    .collect(Collectors.toList());
        }
        if (type != null) {
            results = results.stream()
                    .filter(o -> o.listCarTypes().contains(type))
                    .collect(Collectors.toList());
        }

        results = results.stream()
                .filter(o -> o.getStoppings().get(0).getStation().getName().contains(departure))
                .collect(Collectors.toList());

        results = results.stream().
                filter(o -> o.getStoppings().stream()
                        .anyMatch(q -> q.getStation().getName().contains(destination)))
                .collect(Collectors.toList());

        return results;
    }

    private Calendar toCalendar(LocalDate localDate) {
        Calendar cal = null;
        if (localDate != null) {
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            cal = Calendar.getInstance();
            cal.setTime(date);
        }
        return cal;
    }

    private Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    @FXML
    public void reset(ActionEvent actionEvent) {

        GenericDao<Trip, Integer> tripDao = new GenericDaoImpl<Trip, Integer>(Trip.class);
        initTripList(tripDao.list());

        datePicker.setValue(null);
        typeComboBox.setValue(null);
        fromField.clear();
        toField.clear();

    }
}