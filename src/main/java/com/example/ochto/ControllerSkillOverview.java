package com.example.ochto;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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
    private Button saveButton;
    private final Stage stage4 = new Stage();
    public int innerI;

    @Override
    public void initialize(URL location, ResourceBundle resources) { 
        // ControllerLogic questionGetter = new ControllerLogic();
        // allQuestions = questionGetter.getQuestionList();
        createQuestionList2();
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


    public HashMap<String, String> getAllQuestions(){
        String folderPath = "Questions";
        HashMap<String, String> allFiles = new HashMap<>();

        // Get a list of all the files in the folder
        File folder = new File(folderPath);
        File[] fileList = folder.listFiles();

        // Loop over each file in the folder and read the first line
        for (File file : fileList) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String firstLine = br.readLine();
                allFiles.put(firstLine, file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return allFiles;
    }

    public void createQuestionList2(){
        HashMap<String, String> allQuestions = getAllQuestions(); // <Qs, path>

        if (allQuestions.isEmpty()){
            System.out.println("No Questions Implemented");
        }
        else{
            Text explanation = new Text("Double Click on a Question to Edit it");
            explanation.setFont(Font.font("verdana", FontWeight.BLACK, FontPosture.REGULAR, 21));
            mainVBox.getChildren().addAll(explanation);
            Text line = new Text("____________________________________________________________________________________________________");
            explanation.setFont(Font.font("verdana", FontWeight.BLACK, FontPosture.REGULAR, 21));
            mainVBox.getChildren().addAll(line);

            for (String currentQuestion : allQuestions.keySet()){
                Text input = new Text(currentQuestion);
                input.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                        try{
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("view5.fxml"));

                            ControllerSkillEditor controller = new ControllerSkillEditor();
                            controller.setFile(allQuestions.get(currentQuestion));

                            loader.setController(controller); //initialize
                            Parent root = loader.load();

                            Scene scene = new Scene(root);
                            stage4.setScene(scene);
                            stage4.setTitle("Skill Editor");
                            stage4.setResizable(false);
                            stage4.centerOnScreen();
                            stage4.show();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                input.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
                mainVBox.getChildren().addAll(input);
            }
        }
    }

    @FXML
    void onSaveButton(ActionEvent event) {
        System.out.println("save button clickeD");
    }
}
