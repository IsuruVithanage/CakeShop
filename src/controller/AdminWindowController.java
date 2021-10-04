package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.LoadFXMLFiles;

import java.io.IOException;

public class AdminWindowController {
    public AnchorPane contextAdmin;
    public JFXButton btnopenPlaceOrder;
    public JFXButton btnOpenUpdateOrder;
    public AnchorPane context;

    public void initialize() throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("DashBoard", context);
    }

    public void openPlaceOrder(ActionEvent actionEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("PlaceOrderWindow", context);
    }

    public void openUpdateOrder(ActionEvent actionEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("UpdateOrder", context);
    }

    public void openManageItem(ActionEvent actionEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("ManageIngredient", context);

    }

    public void logout(ActionEvent actionEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("LoginWindow", contextAdmin);
    }

    public void openIncome(MouseEvent mouseEvent) {
    }

    public void openCakeWindow(MouseEvent mouseEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("CakeWindow", context);
    }

    public void openRecipes(MouseEvent mouseEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("RecipeWindow", context);
    }

    public void openWorkers(MouseEvent mouseEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("WorkerDetail", context);
    }

    public void openSalary(MouseEvent mouseEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("SalaryWindow", context);
    }

    public void openUser(MouseEvent mouseEvent) throws IOException {

        LoadFXMLFiles.loadOnTheCurrentPane("UserDetails", context);

    }

    public void openUnavalableLList(MouseEvent mouseEvent) throws IOException {
        LoadFXMLFiles.loadOnTheCurrentPane("UnAvailableIngredients", context);
    }

    public void openDash(ActionEvent actionEvent) throws IOException {
        LoadFXMLFiles.loadOnTheCurrentPane("DashBoard", context);
    }

    public void openInsom(MouseEvent mouseEvent) throws IOException {
        LoadFXMLFiles.loadOnTheCurrentPane("IncomeWindow", context);
    }
}
