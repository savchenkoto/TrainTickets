package com.controller;

import com.dao.daoImpl.GenericDaoImpl;
import com.domain.Car;
import com.domain.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class CarController extends TrainController {

    private ObservableList<Car> carList = FXCollections.observableArrayList();

    private ObservableList<Type> typeComboBoxList = FXCollections.observableArrayList();

    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, Integer> carNumberColumn;

    @FXML
    private TableColumn<Car, Type> typeColumn;

    @FXML
    private Spinner<Double> carNumberSpinner;

    @FXML
    private ComboBox<Type> typeComboBox;

    private Car selectedCar;

    @FXML
    private void initialize() {
        initData();

        carNumberColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("number"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Car, Type>("typeByTypeId"));
        typeComboBox.setItems(typeComboBoxList);
        carTable.setItems(carList);
    }

    private void initData() {
        setCarRowFactory();
        GenericDaoImpl<Type, Integer> typeDao = new GenericDaoImpl<Type, Integer>(Type.class);
        typeComboBoxList.setAll(typeDao.list());
    }

    private void setCarRowFactory() {
        carTable.setRowFactory( tv -> {
            TableRow<Car> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    carNumberSpinner.getValueFactory().setValue(1.);
                    typeComboBox.setValue(row.getItem().getTypeByTypeId());
                    selectedCar = row.getItem();


                } else {
                    typeComboBox.getSelectionModel().clearSelection();
                }
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    selectedCar = row.getItem();
                }
            });
            return row;
        });
    }

    @FXML
    public void addCar(ActionEvent actionEvent) {

        if (typeComboBox.getSelectionModel().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Данные не добавлены");
            error.setContentText("Все поля должны быть заполнены!");
            error.showAndWait();

        } else {
            Car car = new Car(
                    super.getSelectedTrain(),
                    carNumberSpinner.getValue().intValue(),
                    typeComboBox.getValue()
            );
            GenericDaoImpl<Car, Integer> carDao = new GenericDaoImpl<Car, Integer>(Car.class);

            car.setId(carDao.save(car));
            carList.add(car);

            carNumberSpinner.getEditor().clear();
            typeComboBox.getSelectionModel().clearSelection();
        }

    }

    @FXML
    public void deleteCar(ActionEvent actionEvent) {

        Car car = carTable.getSelectionModel().getSelectedItem();
        if (car != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText(null);
            alert.setContentText("Вы действительно хотите удалить этот вагон?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                GenericDaoImpl<Car, Integer> carDao = new GenericDaoImpl<Car, Integer>(Car.class);
                carDao.delete(car);
                carList.remove(car);
            } else {
                alert.close();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Вагон не выбран");
            alert.setContentText("Пожалуйста, выберите вагон");
            alert.showAndWait();

        }

    }

    @FXML
    public void updateCar(ActionEvent actionEvent) {
        try {
            GenericDaoImpl<Car, Integer> CarDao = new GenericDaoImpl<Car, Integer>(Car.class);

            CarDao.update(selectedCar);
            carTable.refresh();
        } catch (NullPointerException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Нечего обновлять");
            error.setContentText("Пожалуйста, выберите вагон!");
            error.showAndWait();
        }

    }

    public TableView<Car> getCarTable() {
        return this.carTable;
    }

    public ObservableList<Car> getCarList() {
        return this.carList;
    }

}
