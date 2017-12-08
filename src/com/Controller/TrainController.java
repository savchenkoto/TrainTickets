package com.Controller;

import com.DAO.DaoImpl.TrainDaoImpl;
import com.model.Train;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class TrainController {

    private ObservableList<Train> trainList = FXCollections.observableArrayList();

    @FXML
    private TableView<Train> trainTable;

    @FXML
    private TableColumn<Train, String> numberColumn;

    @FXML
    private TableColumn<Train, String> departureColumn;

    @FXML
    private TableColumn<Train, String> destinationColumn;


    @FXML
    private TextField numberField;

    @FXML
    private TextField departureField;

    @FXML
    private TextField destinationField;


    @FXML
    private void initialize() {
        initData();

        numberColumn.setCellValueFactory(new PropertyValueFactory<Train, String>("number"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<Train, String>("departure"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<Train, String>("destination"));

        trainTable.setItems(trainList);
    }

    private void initData() {
        TrainDaoImpl trainDao = new TrainDaoImpl();
        trainList.addAll(trainDao.findAll());
        setRowFactory();
    }

    private void setRowFactory() {
        trainTable.setRowFactory( tv -> {
            TableRow<Train> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    numberField.setText(row.getItem().getNumber());
                    departureField.setText(row.getItem().getDeparture());
                    destinationField.setText(row.getItem().getDestination());
                    selectedTrain = row.getItem();

                } else {
                    numberField.setText("");
                    departureField.setText("");
                    destinationField.setText("");
                    selectedTrain = null;
                }

            });
            return row;
        });
    }

    private Train selectedTrain;


    @FXML
    public void addTrain(ActionEvent actionEvent) {

        if ((numberField.getText() == null || numberField.getText().trim().isEmpty()) ||
                        (departureField.getText() == null || departureField.getText().trim().isEmpty()) ||
                        (destinationField.getText() == null || destinationField.getText().trim().isEmpty())) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("The data haven't added");
            error.setContentText("All fields must be filled out");
            error.showAndWait();

        } else {
            Train train = new Train(
                    numberField.getText(),
                    departureField.getText(),
                    destinationField.getText()
            );
            TrainDaoImpl trainDao = new TrainDaoImpl();
            train.setId(trainDao.add(train));
            trainList.add(train);

            numberField.setText("");
            departureField.setText("");
            destinationField.setText("");
            selectedTrain = null;
        }

    }

    @FXML
    public void deleteTrain(ActionEvent actionEvent) {

        Train train = trainTable.getSelectionModel().getSelectedItem();
        if (train != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confitmation");
            alert.setHeaderText(null);
            alert.setContentText("Do you really want to delete it?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                TrainDaoImpl trainDao = new TrainDaoImpl();
                trainDao.deleteById(train.getId());
                trainList.remove(train);
            } else {
                alert.close();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("The train haven't selected");
            alert.setContentText("Please select the train.");
            alert.showAndWait();

        }

    }

    @FXML
    public void updateTrain(ActionEvent actionEvent) {
        try {
            TrainDaoImpl trainDao = new TrainDaoImpl();
            selectedTrain.setNumber(numberField.getText());
            selectedTrain.setDeparture(departureField.getText());
            selectedTrain.setDestination(destinationField.getText());
            trainDao.update(selectedTrain);
            trainTable.refresh();
        } catch (NullPointerException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Nothing to update");
            error.setContentText("Please select the train.");
            error.showAndWait();
        }

    }

}
