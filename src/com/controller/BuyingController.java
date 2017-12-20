package com.controller;

import com.dao.daoImpl.GenericDaoImpl;
import com.dao.daoImpl.PersonDaoImpl;
import com.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class BuyingController {

    private Car selectedCar;

    private Trip selectedTrip;

    private ObservableList<Car> carList = FXCollections.observableArrayList();

    private ObservableList<Seat> seatList = FXCollections.observableArrayList();

    // Car table
    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, Integer> carNumberColumn;

    @FXML
    private TableColumn<Car, Type> carTypeColumn;


    // Seat table
    @FXML
    private TableView<Seat> seatTable;

    @FXML
    private TableColumn<Seat, Integer> seatColumn;

    @FXML
    private TableColumn<Seat, String> isTakenColumn;

    @FXML
    public void initialize() {

        carNumberColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("number"));
        carTypeColumn.setCellValueFactory(new PropertyValueFactory<Car, Type>("typeByTypeId"));
        seatColumn.setCellValueFactory(new PropertyValueFactory<Seat, Integer>("seat"));
        isTakenColumn.setCellValueFactory(new PropertyValueFactory<Seat, String>("isTaken"));

        carTable.setItems(carList);
        seatTable.setItems(seatList);

    }

    private void setCarsRowFactory() {
        carTable.setRowFactory(tv -> {
            TableRow<Car> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    selectedCar = row.getItem();
                    seatList.setAll(selectedCar.getSeatsById());
                }
            });
            return row;
        });
    }

    public void initData(TrainTrip selectedTrip) {
        this.selectedTrip = selectedTrip.getTrip();
        setCarsRowFactory();
        GenericDaoImpl<Train, Integer> trainDao = new GenericDaoImpl<Train, Integer>(Train.class);
        carList.setAll(trainDao.get(this.selectedTrip.getTrainByTrainId().getId()).getCarsById());
    }

    public void buyButton(ActionEvent actionEvent) {
        if (
                (seatTable.getSelectionModel().getSelectedItem() == null) ||
                (seatTable.getSelectionModel().getSelectedItem().getIsTaken() == 1)) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText(null);
            error.setContentText("Please, select the car and unoccupied seat");
            error.showAndWait();
        } else {
            PersonDaoImpl personDao = new PersonDaoImpl(Person.class);
            if (User.getInstance().getCurrent().hasTicketsOnTheTrip(selectedTrip)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("You have already bought the ticket on this train.");
                alert.showAndWait();
            } else {
                Seat selectedSeat = seatTable.getSelectionModel().getSelectedItem();
                GenericDaoImpl<Ticket, Integer> ticketDao = new GenericDaoImpl<Ticket, Integer>(Ticket.class);
                ticketDao.save(
                        new Ticket(
                                selectedTrip,
                                selectedSeat,
                                personDao.get(User.getInstance().getCurrent().getId())
                        )
                );
                selectedSeat.setIsTaken((byte) 1);
                GenericDaoImpl<Seat, Integer> seatDao = new GenericDaoImpl<Seat, Integer>(Seat.class);
                seatDao.update(selectedSeat);
                seatTable.refresh();
            }

        }


    }

    private void makeNumeric(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

}
