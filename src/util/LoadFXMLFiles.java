package util;

import animatefx.animation.FadeIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class LoadFXMLFiles {
    public static void loadOnTheCurrentPane(String filename, AnchorPane context) throws IOException {
        URL resource = LoadFXMLFiles.class.getResource("../view/" + filename + ".fxml");
        Parent load = FXMLLoader.load(resource);
        context.getChildren().clear();
        context.getChildren().add(load);
        new FadeIn(context).play();
    }

}
