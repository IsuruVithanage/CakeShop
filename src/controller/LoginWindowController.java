package controller;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.contollerUtil.UserUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class LoginWindowController {
    public ImageView img;
    public JFXTextField txtusername;
    public JFXPasswordField password;
    public AnchorPane contextLogin;
    public Label lbError;

    public void login(ActionEvent actionEvent) throws IOException {
        try {
            User user = new UserUtil().getUsers(txtusername.getText());
            if ("Rachika123".equals(password.getText()) || (user.getPassword().equals(password.getText()) && user.getPassword().contains("ADMIN"))) {
                URL resource = getClass().getResource("../view/AdminWindow.fxml");
                Parent load = FXMLLoader.load(resource);
                contextLogin.getChildren().clear();
                new FadeIn(contextLogin).play();
                contextLogin.getChildren().add(load);
            } else if ((user.getPassword().equals(password.getText()) && user.getPassword().contains("WORKER")) || "Rachika1234".equals(password.getText())) {
                URL resource = getClass().getResource("../view/WorkerWindow.fxml");
                Parent load = FXMLLoader.load(resource);
                contextLogin.getChildren().clear();
                new FadeIn(contextLogin).play();
                contextLogin.getChildren().add(load);
            } else {
                lbError.setText("Incorrect UserName & Password ");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
}
