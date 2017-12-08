package com.Controller;

import com.DAO.DaoImpl.CarDaoImpl;
import com.DAO.DaoImpl.TrainDaoImpl;
import com.DAO.DaoImpl.TypeDaoImpl;
import com.model.Car;
import com.model.Train;
import com.model.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class CarController {

    private ObservableList<Car> carList = FXCollections.observableArrayList();

    private ObservableList<Train> trainComboBoxList = FXCollections.observableArrayList();

    private ObservableList<Type> typeComboBoxList = FXCollections.observableArrayList();

    @FXML
    private TextField numberField;

    @FXML
    public TableView<Car> carTable;

    @FXML
    public TableColumn<Car, String> trainColumn;

    @FXML
    public TableColumn<Car, Integer> numberColumn;

    @FXML
    public TableColumn<Car, String> typeColumn;

    @FXML
    public ComboBox<Train> trainComboBox;

    @FXML
    public ComboBox<Type> typeComboBox;

    @FXML
    private void initialize() {
        initData();

        trainColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("trainByTrainId"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("number"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("typeByTypeId"));

        carTable.setItems(carList);
        trainComboBox.setItems(trainComboBoxList);
        typeComboBox.setItems(typeComboBoxList);
    }

    private void initData() {
        CarDaoImpl carDao = new CarDaoImpl();
        TrainDaoImpl trainDao = new TrainDaoImpl();
        TypeDaoImpl typeDao = new TypeDaoImpl();
        carList.setAll(carDao.findAll());
        trainComboBoxList.setAll(trainDao.findAll());
        typeComboBoxList.setAll(typeDao.findAll());
        setRowFactory();
        makeNumeric(numberField);


    }

    private void makeNumeric(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void setRowFactory() {
        carTable.setRowFactory( tv -> {
            TableRow<Car> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    trainComboBox.setValue(row.getItem().getTrainByTrainId());
                    numberField.setText(String.valueOf(row.getItem().getNumber()));
                    typeComboBox.setValue(row.getItem().getTypeByTypeId());
                    selectedCar = row.getItem();

                } else {
                    trainComboBox.getSelectionModel().clearSelection();
                    numberField.setText("");
                    typeComboBox.getSelectionModel().clearSelection();
                    selectedCar = null;
                }

            });
            return row;
        });
    }

    private Car selectedCar;

    @FXML
    public void addButton(ActionEvent actionEvent) {

        if (
                trainComboBox.getSelectionModel().isEmpty() ||
                numberField.getText().trim().isEmpty()  ||
                typeComboBox.getSelectionModel().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("The data haven't added");
            error.setContentText("All fields must be filled out");
            error.showAndWait();

        } else {

            Car car = new Car(
                    (Train)trainComboBox.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(numberField.getText()),
                    (Type)typeComboBox.getSelectionModel().getSelectedItem()
            );
            CarDaoImpl carDao = new CarDaoImpl();
            car.setId(carDao.add(car));
            carList.add(car);

            trainComboBox.getSelectionModel().clearSelection();
            numberField.setText("");
            typeComboBox.getSelectionModel().clearSelection();
        }
    }

    public void deleteButton(ActionEvent actionEvent) {

        Car car = carTable.getSelectionModel().getSelectedItem();
        if (car != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confitmation");
            alert.setHeaderText(null);
            alert.setContentText("Do you really want to delete it?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                CarDaoImpl carDao = new CarDaoImpl();
                carDao.deleteById(car.getId());
                carList.remove(car);
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

    public void updateButton(ActionEvent actionEvent) {

        try {
            CarDaoImpl carDao = new CarDaoImpl();
            selectedCar.setNumber(Integer.parseInt(numberField.getText()));
            selectedCar.setTrainByTrainId(trainComboBox.getValue());
            selectedCar.setTypeByTypeId(typeComboBox.getValue());
            carDao.update(selectedCar);
            carTable.refresh();
        } catch (NullPointerException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Nothing to update");
            error.setContentText("Please select the car.");
            error.showAndWait();
        }

    }
}
