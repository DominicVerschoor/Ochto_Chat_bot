package com.example.ochto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App extends Application {
    private String name;
    @Override
    public void start(Stage stage) throws IOException{
        Process proc = Runtime.getRuntime().exec("python Python_facial_recognition/model_1/detector.py");
        BufferedReader out = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        name = out.readLine();
        System.out.println(name);
        if (!(getName() == null)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view1.fxml"));
            Parent root = loader.load();
            ChatScreen controller = loader.getController();
            controller.setName("User");
            stage.setTitle("OCTO");
            Image image = new Image("file:src/main/resources/com/example/ochto/pics/img_5.png");
            stage.getIcons().add(image);
            stage.setResizable(false);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view2.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setBackground(Background.fill(Color.DARKGRAY));
            stage.setTitle("OCTO");
            Image image = new Image("file:src/main/resources/com/example/ochto/pics/img_5.png");
            stage.getIcons().add(image);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
    }
    public String getName() {
        String output = name;
        return output;
    }
    public static void main(String[] args){
        launch();
    }
}