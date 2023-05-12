package com.example.ochto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
public class App extends Application {
    private String name;
    @Override
    public void start(Stage stage) throws IOException {
        Process proc = Runtime.getRuntime().exec("python python/camDetector.py");
        BufferedReader out = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        name = out.readLine();
        System.out.println(name);
        if (!(getName().isEmpty())){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view1.fxml"));
            Parent root = loader.load();
            ControllerLogic controller = loader.getController();
            controller.setName("User");
            stage.setTitle("OCTO");
            Image image = new Image("file:src/main/resources/com/example/ochto/pics/img_5.png");
            stage.getIcons().add(image);
            stage.setResizable(false);
            Scene scene = new Scene(root);

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