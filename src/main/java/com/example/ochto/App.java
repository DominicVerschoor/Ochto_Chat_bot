package com.example.ochto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.MatOfRect;
import org.opencv.videoio.VideoCapture;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.logic.camDetector.faceDetector;
import static org.opencv.highgui.HighGui.destroyAllWindows;

public class App extends Application {
    private String name;
    @Override
    public void start(Stage stage) throws IOException {
                        //python version face detector
//        Process proc = Runtime.getRuntime().exec("python python/camDetector.py");
//        BufferedReader out = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//        name = out.readLine();
//        System.out.println(name);

                        //java version face detector
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture cam = new VideoCapture(0);
        MatOfRect faces = null;
        if (cam.isOpened()) {
            faces = faceDetector(cam);
        }
        cam.release();
        destroyAllWindows();
        System.out.println(faces.toArray().length);
        if (faces.toArray().length > 0) {
            System.out.println("Face detected");


                        //python version face detector
//        if (!(getName() == null)){

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