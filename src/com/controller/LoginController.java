package com.controller;

import com.dao.daoImpl.PersonDaoImpl;
import com.domain.Person;
import com.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField passId;

    @FXML
    private Button loginButton;

    @FXML
    public void login(ActionEvent actionEvent) {
        if (!username.getText().trim().isEmpty()  && !passId.getText().trim().isEmpty()) {

            PersonDaoImpl personDao = new PersonDaoImpl(Person.class);
            Person person = personDao.getByPassId(passId.getText());
            if (person != null) {
                if (person.getName().equals(username.getText())) {
                    User.getInstance().setCurrent(person);
                    goToTheHomepage();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Некорректное имя");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Пользователь не найден");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void register(ActionEvent actionEvent) {
        if (!username.getText().trim().isEmpty() && !passId.getText().trim().isEmpty()) {
            PersonDaoImpl personDao = new PersonDaoImpl(Person.class);
            Person newPerson = personDao.getByPassId(passId.getText());
            if (newPerson == null) {
                newPerson = new Person(username.getText(), passId.getText());
                newPerson.setId(personDao.save(newPerson));
                User.getInstance().setCurrent(newPerson);
                goToTheHomepage();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Пользователь с такими паспортными данными уже зарегистрирован!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void initialize() {
        passId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                passId.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void goToTheHomepage() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/sample.fxml"));
            Parent root = (Parent)loader.load();
            Stage homeStage = new Stage();
            //homeStage.initModality(Modality.APPLICATION_MODAL);
            homeStage.initStyle(StageStyle.DECORATED);
            homeStage.setTitle("Билеты");
            homeStage.setScene(new Scene(root));
            homeStage.show();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

