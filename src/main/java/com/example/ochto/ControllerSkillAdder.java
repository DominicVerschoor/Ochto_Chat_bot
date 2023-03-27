package com.example.ochto;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

import com.example.logic.Slot;
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

import com.example.ochto.ControllerLogic;
import javafx.stage.Stage;

public class ControllerSkillAdder implements Initializable {

    @FXML
    private Button slotActionButton;
    @FXML
    private VBox mainVBox;
    @FXML
    private Button questionButton;
    @FXML
    private Button saveButton;
    @FXML
    private ScrollPane scrollPane1;
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
    private HBox lineHBox;
    private VBox currentQuestionVBox;
    private VBox currentActionVBox;
    private VBox currentSlotVBox;

    @FXML
    void onQuestionButton(ActionEvent event) {
        String input = question_textfield.getText();
        if (!input.isEmpty()) {
            lineHBox = new HBox();
            currentHBox = new HBox();
            mainVBox.getChildren().add(lineHBox);
            mainVBox.getChildren().add(currentHBox);

            Text line = new Text("____________________________________________________________________________________________________________________________________________________________________________________");
            lineHBox.getChildren().addAll(line);

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
        } else {
            System.out.println("Input is Empty");
        }
    }

    @FXML
    void onSLotActionButton(ActionEvent event) {
        if (allQuestions.isEmpty()) {
            System.out.println("Enter a Question first");
        } else {
            String slotInput = slot_textfield.getText();
            String actionInput = action_textfield.getText();
            if (!slotInput.isEmpty() && !actionInput.isEmpty()) {
                Text slotText = new Text(slotInput);
                currentSlotVBox.getChildren().addAll(slotText);
                slots.add(slotInput);
                slot_textfield.clear();

                Text actionText = new Text(actionInput);
                currentActionVBox.getChildren().addAll(actionText);
                actions.add(actionInput);
                action_textfield.clear();

            } else if (!slotInput.isEmpty() && actionInput.isEmpty()) {
                System.out.println("Action Input is Empty");
            } else if (slotInput.isEmpty() && !actionInput.isEmpty()) {
                System.out.println("Slot Input is Empty");
            } else {
                System.out.println("Slot and Action Inputs are Empty");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allQuestions = new ArrayList<>();
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

        Text questionText = new Text("What lecture do we have on <DAY> at <TIME>?");
        currentQuestionVBox.getChildren().addAll(questionText);
        Text slotText1 = new Text("<DAY> Monday <TIME> 11:00");
        currentSlotVBox.getChildren().addAll(slotText1);
        Text slotText2 = new Text("<DAY> Tuesday <TIME> 13:00");
        currentSlotVBox.getChildren().addAll(slotText2);
        Text actionText1 = new Text("Mathematical Modelling");
        currentActionVBox.getChildren().addAll(actionText1);
        Text actionText2 = new Text("Theoretical Computer Science");
        currentActionVBox.getChildren().addAll(actionText2);
    }

    @FXML
    void onSaveButton(ActionEvent event) {
        for (ArrayList<ArrayList<String>> currQs: allQuestions) {
            createCSV(currQs);
        }

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public ArrayList<String> seperateSlots(ArrayList<String> slots) {
        ArrayList<String> output = new ArrayList<>();
        for (String slot: slots) {
            String[] currentSlots = slot.split("<");

            output.addAll(Arrays.asList(currentSlots).subList(1, currentSlots.length));
        }
        Collections.sort(output);
        output.replaceAll(s -> "<" + s);

        return output;
    }

    public void createCSV(ArrayList<ArrayList<String>> currentQuestion) {
        String fileName = "Questions/Skill" + (fileCount());
        String question = currentQuestion.get(0).get(0);
        ArrayList<String> slots = seperateSlots(currentQuestion.get(1));
        ArrayList<String> actions = currentQuestion.get(2);

        StringBuilder output = new StringBuilder();
        output.append(question).append("\n");
        for (String currentSlot: slots) {
            output.append(currentSlot).append("\n");
        }
        for (String currentAction: actions) {
            output.append("Action ").append(currentAction).append("\n");
        }


        try {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.write(String.valueOf(output));

            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public int fileCount() {
        File folder = new File("Questions/");
        int count = 0;
        File[] files = folder.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                count++;
            }
        }

        return count;
    }
}
