package com.example.ochto;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class ControllerLogIn {
    private final Stage stage = new Stage();
    @FXML
    private TextField tf = new TextField();


    @FXML
    public void logIn(ActionEvent e)throws Exception{
        String name = tf.getText();
        if (name.isEmpty() || !name.matches("^[a-zA-Z]+$")) {
            // If tf is empty or contains non-alphabet characters, do not take any action
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view1.fxml"));
        Parent root = loader.load();
        ControllerLogic controller = loader.getController();
        controller.setName(tf.getText());
        stage.setTitle("OCTO");
        Image image = new Image("file:src/main/resources/com/example/ochto/pics/img_5.png");
        //Image image = new Image("C:\\Users\\mobasha\\OneDrive\\Bureaublad\\Documenten\\GitHub\\Project_2-2\\src\\main\\resources\\com\\example\\ochto\\pics\\img_5.png");;
        stage.getIcons().add(image);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        Stage stage = (Stage) tf.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public Stage getStage() {
        return this.stage;
    }

}
