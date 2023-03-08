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

public class ControllerSkillAdder implements Initializable{

    @FXML
    private Button slotActionButton;
    @FXML
    private Button questionButton;
    @FXML
    private ScrollPane scrollPane1;
    @FXML
    private ScrollPane scrollPane2;
    @FXML
    private ScrollPane scrollPane3;
    @FXML
    private TextField text_field1;
    @FXML
    private TextField text_field2;
    @FXML
    private TextField text_field3;
    @FXML
    private VBox vBox1;
    @FXML
    private VBox vBox2;
    @FXML
    private VBox vBox3;

    public ArrayList<String> questions;
    public ArrayList<String> slots;
    public ArrayList<String> actions;

    @FXML
    void onQuestionButton(ActionEvent event) {
        String input = text_field1.getText();
        if (!input.isEmpty()){
            addInput(input, vBox1, text_field1);
            questions.add(input);
        }
        else{
            System.out.println("Input is Empty");
        }
    }

    @FXML
    void onSlotButton(ActionEvent event) {
        String input = text_field2.getText();
        if (!input.isEmpty()){
            addInput(input, vBox2, text_field2);
            slots.add(input);
        }
        else{
            System.out.println("Input is Empty");
        }
    }

    @FXML
    void onActionButton(ActionEvent event) {
        String input = text_field3.getText();
        if (!input.isEmpty()){
            addInput(input, vBox3, text_field3);
            actions.add(input);
        }
        else{
            System.out.println("Input is Empty");
        }
    }

    @FXML
    void onSLotActionButton(ActionEvent event){
        System.out.println("Button Clicked");
        String slotInput = text_field2.getText();
        String actionInput = text_field3.getText();
        if (!slotInput.isEmpty() && !actionInput.isEmpty()){
            System.out.println("inputs valid");

            addInput(slotInput, vBox2, text_field2);
            slots.add(slotInput);
            addInput(actionInput, vBox3, text_field3);
            actions.add(actionInput);

        } else if (!slotInput.isEmpty() && actionInput.isEmpty()){
            System.out.println("Action input is empty");
        } else if (slotInput.isEmpty() && !actionInput.isEmpty()){
            System.out.println("Slot input is empty");
        } else{
            System.out.println("Inputs are Empty");
        }
    }

    public void addInput(String input, VBox vbox, TextField textfield){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(0,0,0,5));
    
        Text text = new Text(input);
        hBox.getChildren().addAll(text);
        vbox.getChildren().add(hBox);
    
        textfield.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questions = new ArrayList<String>();
        actions = new ArrayList<String>();
        slots = new ArrayList<String>();
    }

}
