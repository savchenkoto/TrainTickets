package com.controller;

import com.dao.daoImpl.GenericDaoImpl;
import com.domain.Train;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class TrainController {

    private ObservableList<Train> trainList = FXCollections.observableArrayList();

    @FXML
    protected TableView<Train> trainTable;

    @FXML
    protected TableColumn<Train, String> trainNumberColumn;

    @FXML
    protected TableColumn<Train, String> departureColumn;

    @FXML
    protected TableColumn<Train, String> destinationColumn;


    @FXML
    protected TextField trainNumberField;

    @FXML
    protected TextField departureField;

    @FXML
    protected TextField destinationField;

    @FXML
    public Parent carEditor;

    @FXML
    private CarController carEditorController;

    @FXML
    private void initialize() {

        initData();

        trainNumberColumn.setCellValueFactory(new PropertyValueFactory<Train, String>("number"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<Train, String>("departure"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<Train, String>("destination"));

        trainTable.setItems(trainList);

    }

    private void initData() {
        GenericDaoImpl<Train, Integer> trainDao = new GenericDaoImpl<Train, Integer>(Train.class);
        trainList.addAll(trainDao.list());
        setTrainRowFactory();
    }

    private void setTrainRowFactory() {
        trainTable.setRowFactory(tv -> {
            TableRow<Train> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    trainNumberField.setText(row.getItem().getNumber());
                    departureField.setText(row.getItem().getDeparture());
                    destinationField.setText(row.getItem().getDestination());
                    selectedTrain = row.getItem();
                } else {
                    trainNumberField.setText("");
                    departureField.setText("");
                    destinationField.setText("");
                }
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    selectedTrain = row.getItem();
                    carEditorController.getCarList().setAll(selectedTrain.getCarsById());

                }
            });
            return row;
        });
    }


    private Train selectedTrain;


    @FXML
    public void addTrain(ActionEvent actionEvent) {

        if ((trainNumberField.getText() == null || trainNumberField.getText().trim().isEmpty()) ||
                (departureField.getText() == null || departureField.getText().trim().isEmpty()) ||
                (destinationField.getText() == null || destinationField.getText().trim().isEmpty())) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Данные не добавлены");
            error.setContentText("Все поля должны быть заполнены!");
            error.showAndWait();

        } else {
            Train train = new Train(
                    trainNumberField.getText(),
                    departureField.getText(),
                    destinationField.getText()
            );
            GenericDaoImpl<Train, Integer> trainDao = new GenericDaoImpl<Train, Integer>(Train.class);
            train.setId(trainDao.save(train));
            trainList.add(trainDao.get(train.getId()));

            trainNumberField.setText("");
            departureField.setText("");
            destinationField.setText("");
        }

    }

    @FXML
    public void deleteTrain(ActionEvent actionEvent) {

        Train train = trainTable.getSelectionModel().getSelectedItem();
        if (train != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText(null);
            alert.setContentText("Вы действительно хотите удалить этот поезд?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                GenericDaoImpl<Train, Integer> trainDao = new GenericDaoImpl<Train, Integer>(Train.class);
                trainDao.delete(train);
                trainList.remove(train);
            } else {
                alert.close();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Поезд не выбран");
            alert.setContentText("Пожалуйста, выберите поезд");
            alert.showAndWait();

        }

    }

    @FXML
    public void updateTrain(ActionEvent actionEvent) {
        try {
            GenericDaoImpl<Train, Integer> trainDao = new GenericDaoImpl<Train, Integer>(Train.class);
            selectedTrain.setNumber(trainNumberField.getText());
            selectedTrain.setDeparture(departureField.getText());
            selectedTrain.setDestination(destinationField.getText());
            trainDao.update(selectedTrain);
            trainTable.refresh();
        } catch (NullPointerException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Нечего обновлять");
            error.setContentText("Пожалуйста, выберите поезд");
            error.showAndWait();
        }

    }

    protected Train getSelectedTrain() {
        return selectedTrain;
    }

}
