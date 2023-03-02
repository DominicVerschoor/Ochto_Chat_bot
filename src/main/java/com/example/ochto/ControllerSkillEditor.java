package com.example.ochto;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Insets;

public class ControllerSkillEditor implements Initializable{

    @FXML
    private Button actionButton;
    @FXML
    private VBox mainVBox;
    @FXML
    private Button questionButton;
    @FXML
    private ScrollPane scrollPane1;
    @FXML
    private Button slotButton;
    @FXML
    private TextField question_textfield;
    @FXML
    private TextField action_textfield;
    @FXML
    private TextField slot_textfield;
    
    public ArrayList<ArrayList<ArrayList<String>>> allQuestions;
    public ArrayList<ArrayList<String>> currentQuestion;
    public ArrayList<String> question;
    public ArrayList<String> slots;
    public ArrayList<String> actions;

    private HBox currentHBox;
    private VBox currentQuestionVBox;
    private VBox currentActionVBox;
    private VBox currentSlotVBox;

    @FXML
    void onQuestionButton(ActionEvent event) {
        String input = question_textfield.getText();
        if (!input.isEmpty()){
            currentHBox = new HBox();
            mainVBox.getChildren().add(currentHBox);

            currentQuestionVBox = new VBox();
            currentQuestionVBox.setPrefWidth(300);
            currentActionVBox = new VBox();
            currentActionVBox.setPrefWidth(300);
            currentSlotVBox = new VBox();
            currentSlotVBox.setPrefWidth(300);
            currentHBox.getChildren().addAll(currentQuestionVBox);
            currentHBox.getChildren().addAll(currentSlotVBox);
            currentHBox.getChildren().addAll(currentActionVBox);

            Text text = new Text(input);
            currentQuestionVBox.getChildren().addAll(text);

            currentQuestion = new ArrayList<>();
            question = new ArrayList<>();
            actions = new ArrayList<>();
            slots = new ArrayList<>();
            question.add(input);
            currentQuestion.add(question);
            currentQuestion.add(slots);
            currentQuestion.add(actions);
            allQuestions.add(currentQuestion);

            question_textfield.clear();
        }
        else{
            System.out.println("Input is Empty");
        }
    }

    @FXML
    void onActionButton(ActionEvent event) {
        if (allQuestions.isEmpty()){
            System.out.println("Enter a Question first");
        }
        else{
            String input = action_textfield.getText();
        if (!input.isEmpty()){
            Text text = new Text(input);
            currentActionVBox.getChildren().addAll(text);
            actions.add(input);
            action_textfield.clear();
        }
        else{
            System.out.println("Input is Empty");
        }
        }
    }

    @FXML
    void onSlotButton(ActionEvent event) {
        if (allQuestions.isEmpty()){
            System.out.println("Enter a Question first");
        }
        else{
            String input = slot_textfield.getText();
            if (!input.isEmpty()){
                Text text = new Text(input);
                currentSlotVBox.getChildren().addAll(text);
                slots.add(input);
                slot_textfield.clear();
            }
            else{
                System.out.println("Input is Empty");
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allQuestions = new ArrayList<>();
    }

}
