package com.example.ochto;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class ControllerLogic implements Initializable {
    @FXML
    private TextField text_field;
    @FXML
    private VBox vbox_message;
    @FXML
    private ScrollPane scrollPane;
    private final LocalTime time = LocalTime.now();
    private final LocalDate date = LocalDate.now();;
    private final Random random = new Random();
    private String message;
    private final Timer timer = new Timer();
    @FXML
    private final VBox vBox = new VBox();
    @FXML
    Circle circle = new Circle();
    private String[] skills ={"Which lectures are there on DAY at TIME"};
    private final Stage stage2 = new Stage();
    @FXML
    private Button theme_button = new Button();
    private boolean isDarkTheme = true;
    private ImageView dark_theme_ImageView;
    private ImageView light_theme_ImageView;
    private Image dark_theme_background = new Image("file:src\\main\\resources\\com\\example\\ochto\\pics\\img_1.png");
    private Image light_theme_background = new Image("file:src\\main\\resources\\com\\example\\ochto\\pics\\White_full.png");
    @FXML
    private ImageView main_image_view = new ImageView();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vbox_message.heightProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });
        vbox_message.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 30),
                CornerRadii.EMPTY,
                Insets.EMPTY)));

        Image img = new Image("file:src\\main\\resources\\com\\example\\ochto\\pics\\img.png");
        //C:\\Users\\mielg\\OneDrive\\Documenten\\GitHub\\Project_2-2\\src\\main\\resources\\com\\example\\ochto\\pics\\img_5.png
        ImageView view = new ImageView(img);
        view.setFitHeight(23);
        view.setPreserveRatio(true);

        text_field.setStyle("""
                text-field {
                 -fx-border-radius: 7 7 7 7;
                 -fx-background-radius: 7 7 7 7;""");


        Image im = new Image("file:src\\main\\resources\\com\\example\\ochto\\pics\\img_6.png",false);
        //C:\\Users\\mielg\\OneDrive\\Documenten\\GitHub\\Project_2-2\\src\\main\\resources\\com\\example\\ochto\\pics\\img_6.png
        circle.setFill(new ImagePattern(im));
        theme_button.setStyle(
            "-fx-background-radius: 5em; " +
            "-fx-min-width: 50px; " +
            "-fx-min-height: 50px; " +
            "-fx-max-width: 50px; " +
            "-fx-max-height: 50px;"
        );
        Image dark_theme = new Image("file:src\\main\\resources\\com\\example\\ochto\\pics\\dark_theme.png");
        dark_theme_ImageView = new ImageView(dark_theme);
        Image light_theme = new Image("file:src\\main\\resources\\com\\example\\ochto\\pics\\light_theme.png");
        light_theme_ImageView = new ImageView(light_theme);
        dark_theme_ImageView.setFitHeight(50);
        dark_theme_ImageView.setPreserveRatio(true);
        light_theme_ImageView.setFitHeight(50);
        light_theme_ImageView.setPreserveRatio(true);
        theme_button.setGraphic(light_theme_ImageView);
        main_image_view.setImage(dark_theme_background);
    }

    @FXML
    public void handle(ActionEvent newActionEvent) {
        message = text_field.getText();
        addUMessage(message, vbox_message);
        if (!message.isEmpty()) {
            if (message.contains("how are you")) {

                String[] responses = new String[]{"I'm fine !,What about you?","I am good, thanks for asking!","I'm great ,thanks for asking!"};
                message = responses[random.nextInt(3)];

            } else if (message.contains("you") && (message.contains("smart") || message.contains("good"))) {
                message = "Thank you !";

            } else if (message.contains("welcome")) {
                message = "You are so polite.How can i help you ?";

            } else if (message.contains("hi") && message.charAt(0) == 'h' || message.contains("hello") || message.contains("hey")) {
                String[] responses = new String[]{"Hi!","Hello","Hey"};
                message = responses[random.nextInt(3)];

            } else if (message.contains("by")) {
                message = "Byy,See you soon ..!";

            }
            else if (message.contains("i am good") || message.contains("i am great") || message.contains("i am ") && message.contains("fine")) {
                message = "Good to hear.";

            } else if (message.contains("thank")) {
                int num = random.nextInt(3);
                if (num == 0) {
                    message = "Welcome..";
                } else if (num == 1) {
                    message = "My plesure";
                } else {
                    message = "Happy to help";
                }

            } else if (message.contains("what") && message.contains("name")) {
                if (message.contains("your")) {
                    message = "I'm Bot...;";
                }
                if (message.contains("my")) {
                    message = "Your name is .. maybe .. hmmm... IDK :<";
                }

            } else if (message.contains("change")) {
                message = "Sorry,I can't change anything...";

            } else if (message.contains("time")) {
                String ctime = "";
                if (time.getHour() > 12) {
                    int hour = time.getHour() - 12;
                    ctime = ctime + hour + ":" + time.getMinute() + ":" + time.getSecond() + " PM";
                } else {
                    ctime = ctime + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + " AM";
                }

                message = (ctime);

            } else if (message.contains("date") || message.contains("month") || message.contains("year") || message.contains("day")) {
                String cdate = new String();
                cdate = cdate + date.getDayOfWeek() + "," + date.getDayOfMonth() + " " + date.getMonth() + " " + date.getYear();
                message = (cdate);

            } else if (message.contains("good morning")) {
                message = "Good morning,Have a nice day !";

            } else if (message.contains("good night")) {
                message = "Good night,Have a nice dreams !";

            } else if (message.contains("good evening")) {
                message = "Good Evening ...!";

            } else if (message.contains("good") && message.contains("noon")) {
                message = "Good Afternoon ...!";

            } else if (message.contains("clear") && (message.contains("screen") || message.contains("chat"))) {
                message = "wait a few second...";

            } else{
                message = "Sorry, but Im not able to response to this yet!";
            }
        }
        else{
            message = "Input something!";
        }

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    // UI update code goes here
                    addBMessage(message,vbox_message);
                });
            };
        };
        int delay = 1;
        timer.schedule(tt, delay * 2000);
    }

    public void addUMessage(String message, VBox vbox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(message);
        TextFlow TextFlow = new TextFlow(text);
        TextFlow.setStyle("-fx-color: rgb(37,211,102);" +
                "-fx-background-color: rgb(7, 94, 84);" +
                " -fx-background-radius: 20px;");

        TextFlow.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.color(1,1,1));
        text.setFont(Font.font("MathBold", FontWeight.BOLD, FontPosture.REGULAR, 15));

        ImageView imageView = new ImageView(new Image("file:src\\main\\resources\\com\\example\\ochto\\pics\\senderIcon.png"));
        //C:\\Users\\mielg\\OneDrive\\Documenten\\GitHub\\Project_2-2\\src\\main\\resources\\com\\example\\ochto\\pics\\senderIcon.png
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        hBox.getChildren().addAll(TextFlow, imageView);
        HBox.setHgrow(TextFlow, Priority.NEVER);
        HBox.setHgrow(imageView, Priority.NEVER);
        HBox.setMargin(imageView, new Insets(0, 4, 0, 10)); // add some margin between the icon and the text

        vbox.getChildren().add(hBox);

        text_field.clear();
    }

    public static void addBMessage(String message, VBox vbox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(message);
        TextFlow TextFlow = new TextFlow(text);
        TextFlow.setStyle("-fx-color: rgb(37,211,102);" +
                "-fx-background-color: rgb(0, 102, 204);" +
                " -fx-background-radius: 20px;");
        text.setFont(Font.font("MathBold", FontWeight.BOLD, FontPosture.REGULAR, 15));

        ImageView imageView = new ImageView(new Image("file:src\\main\\resources\\com\\example\\ochto\\pics\\octoIconChat.png"));
        //C:\\Users\\mielg\\OneDrive\\Documenten\\GitHub\\Project_2-2\\src\\main\\resources\\com\\example\\ochto\\pics\\octoIconChat.png
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        TextFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(1, 1, 1));

        hBox.getChildren().addAll(imageView, TextFlow);
        HBox.setHgrow(TextFlow, Priority.NEVER);
        HBox.setHgrow(imageView, Priority.NEVER);
        HBox.setMargin(imageView, new Insets(0, 10, 0, 0)); // add some margin between the text and the icon

        vbox.getChildren().add(hBox);
    }

    public void setName(String s){
        addBMessage("Hi "+s.toUpperCase()+"! How can i assist you?",vbox_message);
    }

    @FXML
    public void handleButton(ActionEvent newActionEvent)
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view3.fxml"));
            Parent root = loader.load();

            ControllerSkillEditor controller = new ControllerSkillEditor();
            loader.setController(controller);

            Scene scene = new Scene(root);
            stage2.setScene(scene);
            stage2.setTitle("Skill Editor");
            stage2.setResizable(false);
            stage2.centerOnScreen();
            stage2.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void changeTheme(ActionEvent newActionEvent)
    {
        if (isDarkTheme) {//here we change to light theme
            theme_button.setGraphic(dark_theme_ImageView);
            isDarkTheme = false;
            main_image_view.setImage(light_theme_background);
            vbox_message.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190),
                CornerRadii.EMPTY,
                Insets.EMPTY)));

        }
        else{//here we change to dark theme
            theme_button.setGraphic(light_theme_ImageView);
            isDarkTheme = true;
            main_image_view.setImage(dark_theme_background);
            vbox_message.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 30),
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        }
    }

}



