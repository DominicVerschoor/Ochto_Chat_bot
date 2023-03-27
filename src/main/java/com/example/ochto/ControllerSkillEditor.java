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
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import com.example.ochto.ControllerSkillOverview;

public class ControllerSkillEditor implements Initializable{

    @FXML
    private VBox actionVBox;
    @FXML
    private VBox editActionVBox;
    @FXML
    private VBox editQuestionVBox;
    @FXML
    private VBox editSlotVBox;
    @FXML
    private Label label;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private VBox questionVBox1;
    @FXML
    private ScrollPane scrollPane1;
    @FXML
    private ScrollPane scrollPane11;
    @FXML
    private ScrollPane scrollPane12;
    @FXML
    private VBox slotVBox;

    public ArrayList<ArrayList<ArrayList<String>>> allQuestions;    
    public ArrayList<ArrayList<String>> currentQuestion;
    public String previousQuestion;
    private Button editButton;
    private Button saveButton = new Button();
    private final Stage stage5 = new Stage();
    private final Stage stage7 = new Stage();

    public int innerJ;
    public ControllerSkillOverview overview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialized");

        questionVBox1.getChildren().clear();
        editQuestionVBox.getChildren().clear();
        slotVBox.getChildren().clear();
        editSlotVBox.getChildren().clear();
        actionVBox.getChildren().clear();
        editActionVBox.getChildren().clear();

        drawTables2(0);
        drawTables2(1);
        drawTables2(2);
    }

    public void drawTables2(int i){
        ArrayList<String> array = currentQuestion.get(i);

        ArrayList<EditButtonInfo> buttonInfoList = new ArrayList<>();
        for (int j = 0; j < array.size(); j++){
            String itemName = array.get(j);
            EditButtonInfo buttonInfo = new EditButtonInfo(itemName, j);
            buttonInfoList.add(buttonInfo);
        }
        

        for (EditButtonInfo buttonInfo : buttonInfoList){
            Text input = new Text(buttonInfo.getString());
            input.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
            Button editButton = new Button("Edit");
            editButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("view6.fxml"));

                        ControllerNewInput controller = new ControllerNewInput();
                        int index = buttonInfo.getIndex();
                        controller.setPrevious(array.get(index), i, currentQuestion);
                        controller.setAllQ(allQuestions);
                        loader.setController(controller); //initialize
                        Parent root = loader.load();

                        Scene scene = new Scene(root);
                        stage5.setScene(scene);
                        stage5.setTitle("Edit");
                        stage5.setResizable(false);
                        stage5.centerOnScreen();
                        stage5.show();
                        Stage stage = (Stage) editButton.getScene().getWindow();
                        stage.close();
                        System.out.println("stage closed");
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    }
            });

            switch(i){
                case 0:
                    questionVBox1.getChildren().addAll(input);
                    editQuestionVBox.getChildren().addAll(editButton);
                    break;
                case 1:
                    slotVBox.getChildren().addAll(input);
                    editSlotVBox.getChildren().addAll(editButton);
                    break;
            case 2:
                    actionVBox.getChildren().addAll(input);
                    editActionVBox.getChildren().addAll(editButton);
                    break;

            }
        }
    }

    public void setQuestion(ArrayList<ArrayList<String>> inputQ, String oldInputQ){
        currentQuestion = inputQ;
        previousQuestion = oldInputQ;
        System.out.println("Questions received");
        System.out.println("previous question si " + previousQuestion);
        System.out.println("new question is " + currentQuestion.get(0).get(0));
    }

    public void setAllQ(ArrayList<ArrayList<ArrayList<String>>> allQ){
        allQuestions = allQ;
        System.out.println("size: " + allQuestions.size());
        System.out.println("AllQ received in editor");
    }

    @FXML
    void onSaveButton(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view4.fxml"));

            ControllerSkillOverview controller = new ControllerSkillOverview();
            controller.setQuestions(allQuestions);
            loader.setController(controller); //initialize
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage7.setScene(scene);
            stage7.setTitle("Skill Overview");
            stage7.setResizable(false);
            stage7.centerOnScreen();
            stage7.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setFile(String filePath){

    }
}
