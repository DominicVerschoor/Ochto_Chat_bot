package com.example.ochto;

import java.io.File;
import java.net.URL;
import java.util.*;

import com.example.logic.CSVHandler;
import com.example.logic.Skill;
import com.example.logic.Slot;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import com.example.ochto.ControllerSkillOverview;

public class ControllerSkillEditor implements Initializable {

    @FXML
    private VBox actionVBox;
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
    private Skill skill;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialized");

        questionVBox1.getChildren().clear();
        slotVBox.getChildren().clear();
        actionVBox.getChildren().clear();

        questionTable();
        slotActionTable();
    }

    public void questionTable() {
        String question = skill.getQuestion();
        Text input = new Text(question);
        input.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));

        input.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                // create a new TextField and set its initial value to the Text object's text
                TextField textField = new TextField(input.getText());

                // replace the Text object with the TextField in the VBox
                VBox parent = (VBox) input.getParent();
                parent.getChildren().set(parent.getChildren().indexOf(input), textField);

                // request focus and select all text in the TextField
                textField.requestFocus();
                textField.selectAll();

                // set an action listener on the TextField to handle the text input
                textField.setOnAction(actionEvent -> {
                    input.setText(textField.getText());
                    parent.getChildren().set(parent.getChildren().indexOf(textField), input);
                });
            }
        });

        questionVBox1.getChildren().addAll(input);
    }

    public void slotActionTable() {
        HashMap<String, String> hashAns = skill.getActions();
        Set<String> keys = hashAns.keySet();

        for (String key : keys) {
            Text slotInput = new Text(skill.translateKeys(key));
            Text actionInput = new Text(hashAns.get(key) + "\n");

            slotInput.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
            actionInput.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));

            slotInput.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    // create a new TextField and set its initial value to the Text object's text
                    TextField textField = new TextField(slotInput.getText());

                    // replace the Text object with the TextField in the VBox
                    VBox parent = (VBox) slotInput.getParent();
                    parent.getChildren().set(parent.getChildren().indexOf(slotInput), textField);

                    // request focus and select all text in the TextField
                    textField.requestFocus();
                    textField.selectAll();

                    // set an action listener on the TextField to handle the text input
                    textField.setOnAction(actionEvent -> {
                        slotInput.setText(textField.getText() + "\n");
                        parent.getChildren().set(parent.getChildren().indexOf(textField), slotInput);
                    });
                }
            });

            actionInput.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    // create a new TextField and set its initial value to the Text object's text
                    TextField textField = new TextField(actionInput.getText());

                    // replace the Text object with the TextField in the VBox
                    VBox parent = (VBox) actionInput.getParent();
                    parent.getChildren().set(parent.getChildren().indexOf(actionInput), textField);

                    // request focus and select all text in the TextField
                    textField.requestFocus();
                    textField.selectAll();

                    // set an action listener on the TextField to handle the text input
                    textField.setOnAction(actionEvent -> {
                        actionInput.setText(textField.getText() + "\n");
                        parent.getChildren().set(parent.getChildren().indexOf(textField), actionInput);
                    });
                }
            });

            slotVBox.getChildren().addAll(slotInput);
            actionVBox.getChildren().addAll(actionInput);
        }
    }

//    public void drawTables2(int i){
//        ArrayList<String> array = currentQuestion.get(i);
//
//        ArrayList<EditButtonInfo> buttonInfoList = new ArrayList<>();
//        for (int j = 0; j < array.size(); j++){
//            String itemName = array.get(j);
//            EditButtonInfo buttonInfo = new EditButtonInfo(itemName, j);
//            buttonInfoList.add(buttonInfo);
//        }
//
//
//        for (EditButtonInfo buttonInfo : buttonInfoList){
//            Text input = new Text(buttonInfo.getString());
//            input.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
//            Button editButton = new Button("Edit");
//            editButton.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event){
//                    try{
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("view6.fxml"));
//
//                        ControllerNewInput controller = new ControllerNewInput();
//                        int index = buttonInfo.getIndex();
//                        controller.setPrevious(array.get(index), i, currentQuestion);
//                        controller.setAllQ(allQuestions);
//                        loader.setController(controller); //initialize
//                        Parent root = loader.load();
//
//                        Scene scene = new Scene(root);
//                        stage5.setScene(scene);
//                        stage5.setTitle("Edit");
//                        stage5.setResizable(false);
//                        stage5.centerOnScreen();
//                        stage5.show();
//                        Stage stage = (Stage) editButton.getScene().getWindow();
//                        stage.close();
//                        System.out.println("stage closed");
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    }
//            });
//
//            switch(i){
//                case 0:
//                    questionVBox1.getChildren().addAll(input);
//                    break;
//                case 1:
//                    slotVBox.getChildren().addAll(input);
//                    break;
//            case 2:
//                    actionVBox.getChildren().addAll(input);
//                    break;
//
//            }
//        }
//    }

    public void setQuestion(ArrayList<ArrayList<String>> inputQ, String oldInputQ) {
        currentQuestion = inputQ;
        previousQuestion = oldInputQ;
        System.out.println("Questions received");
        System.out.println("previous question si " + previousQuestion);
        System.out.println("new question is " + currentQuestion.get(0).get(0));
    }

    public void setAllQ(ArrayList<ArrayList<ArrayList<String>>> allQ) {
        allQuestions = allQ;
        System.out.println("size: " + allQuestions.size());
        System.out.println("AllQ received in editor");
    }

    @FXML
    void onSaveButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view4.fxml"));

            ControllerSkillOverview controller = new ControllerSkillOverview();
            loader.setController(controller); //initialize
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage7.setScene(scene);
            stage7.setTitle("Skill Overview");
            stage7.setResizable(false);
            stage7.centerOnScreen();
            stage7.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFile(File filePath) {
        skill = CSVHandler.readSkill(filePath);
    }
}
