package com.example.ochto;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import com.example.ochto.ControllerSkillOverview;

import javafx.fxml.Initializable;

public class ControllerNewInput implements Initializable{

    public String previousInput;
    public int column;
    @FXML
    private TextField newInputField;
    @FXML
    private TextField previousInputField;
    @FXML
    private Button confirmButton;
    private ArrayList<ArrayList<String>> currentQuestion;
    private final Stage stage4 = new Stage();
    public ArrayList<ArrayList<ArrayList<String>>> allQuestions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        previousInputField.setText(previousInput);
    }

    public void setPrevious(String input, int i,ArrayList<ArrayList<String>> inputQ){
        previousInput = input;
        column = i;
        currentQuestion = inputQ;
        System.out.println("previous input: " + previousInput);
    }

    public void setAllQ(ArrayList<ArrayList<ArrayList<String>>> allQ){
        allQuestions = allQ;
        System.out.println("AllQ received in input");
    }
    @FXML
    void onConfirmButton(ActionEvent event){
        String newInput = newInputField.getText();
        System.out.println("Updating input");
        System.out.println("Old Input: " + previousInput);
        System.out.println("New Input: " + newInput);
        ArrayList<String> updatedArray = currentQuestion.get(column);
        for (int j = 0; j < updatedArray.size(); j++){
            if (updatedArray.get(j) == previousInput){
                System.out.println("Old Input found: " + updatedArray.get(j));
                updatedArray.set(j,newInput);
                System.out.println("Replaced by " + updatedArray.get(j));
            }
        }

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view5.fxml"));

            ControllerSkillEditor controller = new ControllerSkillEditor();
            controller.setQuestion(currentQuestion, previousInput);
            controller.setAllQ(allQuestions);
            loader.setController(controller); //initialize
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage =(Stage) confirmButton.getScene().getWindow();
            System.out.println("Closing Stage");
            stage.close();
            stage4.setScene(scene);
            stage4.setTitle("Skill Editor");
            stage4.setResizable(false);
            stage4.centerOnScreen();
            stage4.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
