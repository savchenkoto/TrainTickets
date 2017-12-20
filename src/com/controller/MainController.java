package com.controller;

import com.domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;


public class MainController extends AnchorPane {

    @FXML
    private TripsController tripsController;

    @FXML
    private TripsController tripsTable;

    @FXML
    private TabPane tabs;

    @FXML
    private Tab tripTab;

    @FXML
    public Tab archiveTab;

    @FXML
    private Tab editTab;

    public void initialize(){
        if (User.getInstance().getCurrent().getRightsByRightsId().getType().equals("user")) {
            archiveTab.setDisable(true);
            editTab.setDisable(true);
        }
    }








}
