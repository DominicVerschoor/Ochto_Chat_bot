package com.example.ochto;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;


public class ControllerSkillOverview implements Initializable{

    @FXML
    private Label label;
    @FXML
    private VBox mainVBox;
    @FXML
    private VBox editVBox;
    @FXML
    private ScrollPane scrollPane1;
    private Button editButton;
    private final Stage stage4 = new Stage();
    public int innerI;

    public ArrayList<ArrayList<ArrayList<String>>> allQuestions;

    @Override
    public void initialize(URL location, ResourceBundle resources) { 
        // ControllerLogic questionGetter = new ControllerLogic();
        // allQuestions = questionGetter.getQuestionList();
        createQuestionList2();
    }

    public ArrayList<ArrayList<ArrayList<String>>> getQuestionList(){
        return allQuestions;
    }

    public void setQuestions(ArrayList<ArrayList<ArrayList<String>>> allQuestions){
        this.allQuestions = allQuestions;
    }

    public void printFullQuestion(ArrayList<ArrayList<String>> question){
        System.out.println("FULL QUESTION");
        for (int f = 0; f < question.size(); f++){
            for (int x = 0; x < question.get(f).size(); x++){
                System.out.println(question.get(f).get(x) + " ");
            }
            System.out.println();
        }
    }

    public void createQuestionList2(){
        if (allQuestions.isEmpty()){
            System.out.println("No Questions Implemented");
        }
        else{
            ArrayList<EditButtonInfo> buttonInfoList = new ArrayList<>();
            for (int i = 0; i < allQuestions.size(); i++){
                String itemName = allQuestions.get(i).get(0).get(0);
                EditButtonInfo buttonInfo = new EditButtonInfo(itemName, i);
                buttonInfoList.add(buttonInfo);
            }

            for (EditButtonInfo buttonInfo : buttonInfoList){
                Button editButton = new Button("Edit");
                editButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event){
                        try{
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("view5.fxml"));

                            ControllerSkillEditor controller = new ControllerSkillEditor();
                            int index = buttonInfo.getIndex();
                            controller.setQuestion(allQuestions.get(index),"TEMP");
                            controller.setAllQ(allQuestions);
                            printFullQuestion(allQuestions.get(index));     

                            loader.setController(controller); //initialize
                            Parent root = loader.load();

                            Scene scene = new Scene(root);
                            stage4.setScene(scene);
                            stage4.setTitle("Skill Editor");
                            stage4.setResizable(false);
                            stage4.centerOnScreen();
                            stage4.show();
                            Stage stage = (Stage) editButton.getScene().getWindow();
                            stage.close();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                Text input = new Text(buttonInfo.getString());
                mainVBox.getChildren().addAll(input);
                editVBox.getChildren().addAll(editButton);
            }
        }
    }
}
