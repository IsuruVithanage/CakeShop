package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.LoadFXMLFiles;

import java.io.IOException;

public class WorkerWindowController {
    public AnchorPane contextWorker;
    public AnchorPane context;
    public JFXButton btnopenPlaceOrder;
    public JFXButton btnOpenUpdateOrder;

    public void initialize() throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("PlaceOrderWindow", context);
    }


    public void logout(ActionEvent actionEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("LoginWindow", contextWorker);

    }

    public void openManageItem(ActionEvent actionEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("ManageIngredient", context);
    }

    public void openPlaceOrder(ActionEvent actionEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("PlaceOrderWindow", context);
    }

    public void openUpdateOrder(ActionEvent actionEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("UpdateOrder", context);
    }

    public void openUnavailable(MouseEvent mouseEvent) throws IOException {
        LoadFXMLFiles.loadOnTheCurrentPane("UnAvailableIngredients", context);
    }
}
