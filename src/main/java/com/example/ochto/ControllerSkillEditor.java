package com.example.ochto;

import java.io.*;
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
    @FXML
    private Button saveButton;
    private final Stage stage5 = new Stage();
    private final Stage stage7 = new Stage();

    public int innerJ;
    public ControllerSkillOverview overview;
    private Skill skill;
    private String fileName;
    private String newQuestion;
    private ArrayList<String> newSlots = new ArrayList<>();
    private ArrayList<String> newAction = new ArrayList<>();
    private ControllerLogic logic = new ControllerLogic();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionVBox1.getChildren().clear();
        slotVBox.getChildren().clear();
        actionVBox.getChildren().clear();
        saveButton = new Button();

        newQuestion = skill.getQuestion();
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
                    skill.setQuestion(textField.getText());
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
                        ArrayList<String> tempText = seperateSlots(textField.getText());
                        List<Slot> tempSlots = skill.untralateKeys(key);

                        if (tempSlots.size() != tempText.size()) {
                            System.out.println("fuck off, do it right");
                        } else {
                            for (int i = 0; i < tempSlots.size(); i++) {
                                tempSlots.get(i).setBoth(tempText.get(i));
                            }
                        }

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
                        skill.setActions(key, textField.getText());

                        parent.getChildren().set(parent.getChildren().indexOf(textField), actionInput);
                    });
                }
            });

            slotVBox.getChildren().addAll(slotInput);
            actionVBox.getChildren().addAll(actionInput);
        }
    }

    public ArrayList<String> seperateSlots(String slots) {
        String[] currentSlots = slots.split("<");
        ArrayList<String> output = new ArrayList<>(Arrays.asList(currentSlots).subList(1, currentSlots.length));
        output.replaceAll(s -> "<" + s);
        return output;
    }

    @FXML
    void onSaveButton(ActionEvent event) {
        CSVHandler.writeSkill(skill, fileName);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view4.fxml"));

            ControllerSkillOverview controller = new ControllerSkillOverview();
            loader.setController(controller); //initialize
            Parent root = loader.load();
            controller.setLogic(logic);

            Scene scene = new Scene(root);
            Stage stage = (Stage) actionVBox.getScene().getWindow();
            stage.close();

            stage7.setScene(scene);
            stage7.setTitle("Skill Overview");
            stage7.setResizable(false);
            stage7.centerOnScreen();
            stage7.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLogic(ControllerLogic logic) {
        this.logic = logic;
    }

    public void setFile(File filePath) {
        fileName = filePath.getName();
        skill = CSVHandler.readSkill(filePath);
    }
}
