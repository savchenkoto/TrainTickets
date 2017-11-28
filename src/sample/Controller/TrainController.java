package sample.Controller;

import DAO.DaoImpl.TrainDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Train;

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
    private void initialize() {
        initData();

        // устанавливаем тип и значение которое должно хранится в колонке
        numberColumn.setCellValueFactory(new PropertyValueFactory<Train, String>("number"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<Train, String>("departure"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<Train, String>("destination"));

        trainTable.setItems(trainList);
    }

    private void initData() {
        TrainDaoImpl trainDao = new TrainDaoImpl();
        trainList.addAll(trainDao.findAll());
    }

    @FXML
    private TextField numberField;

    @FXML
    private TextField departureField;
    @FXML
    private TextField destinationField;

    @FXML
    public void addTrain(ActionEvent event) {

        if (
                (numberField.getText() == null || numberField.getText().trim().isEmpty()) ||
                        (departureField.getText() == null || departureField.getText().trim().isEmpty()) ||
                        (destinationField.getText() == null || destinationField.getText().trim().isEmpty())){
            Alert error= new Alert(Alert.AlertType.INFORMATION);
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
            trainDao.add(train);
            trainList.add(train);

            numberField.setText("");
            departureField.setText("");
            destinationField.setText("");
        }
    }

}
