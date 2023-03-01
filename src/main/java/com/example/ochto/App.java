package com.example.ochto;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setBackground(Background.fill(Color.DARKGRAY));
        stage.setTitle("OCTO");
        Image image = new Image("C:\\Project_2-2\\src\\main\\resources\\com\\example\\ochto\\pics\\img_5.png");
        stage.getIcons().add(image);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}