package gui;

import api.*;
import di.ApiComponent;
import di.DaggerApiComponent;
import gui.controller.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.User;

import java.io.IOException;

public class App extends Application {

    Stage primaryStage;

    private ApiComponent component = DaggerApiComponent.create();
    private GroupApi groupApi;
    private HumanApi humanApi;
    private MarkApi markApi;
    private SubjectApi subjectApi;
    private AuthApi authApi;
    private User user;
    private String token;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        groupApi = component.provideGroupApi();
        humanApi = component.provideHumanApi();
        markApi = component.provideMarkApi();
        subjectApi = component.provideSubjectApi();
        authApi = component.provideAuthApi();
        user = new User("user", "pwd");
        authApi.singIn(user).subscribe(response -> {
            if (response.isSuccessful()) {
                token = "Bearer " + response.body();
                showMainWindow();
                primaryStage.setResizable(false);
                primaryStage.show();
            } else {
                showErrorAlert("Error!. Code = " + response.code());
            }
        });
    }

    private MainWindowController controller;

    public void showMainWindow() {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_window.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setTitle("Client");

            controller = loader.getController();
            controller.provideApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stage markStage;

    public void showMarkWindow(Operation operation) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/mark_window.fxml"));
            markStage = new Stage();
            markStage.setX((primaryStage.getX() + primaryStage.getWidth()) / 3 + 50);
            markStage.setY((primaryStage.getY() + primaryStage.getHeight()) / 3);
            markStage.setScene(new Scene(loader.load()));
            markStage.setTitle("Human");
            markStage.show();

            MarkController controller = loader.getController();
            controller.provideApp(this, operation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeMarkWindow() {
        markStage.close();
    }

    private Stage humanStage;

    public void showHumanWindow(Operation operation) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/human_window.fxml"));
            humanStage = new Stage();
            humanStage.setX((primaryStage.getX() + primaryStage.getWidth()) / 3 + 50);
            humanStage.setY((primaryStage.getY() + primaryStage.getHeight()) / 3);
            humanStage.setScene(new Scene(loader.load()));
            humanStage.setTitle("Human");
            humanStage.show();

            final HumanController humanController = loader.getController();
            humanController.provideApp(this, operation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeHumanWindow() {
        humanStage.close();
    }

    private Stage groupStage;

    public void showGroupWindow(Operation operation) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/group_window.fxml"));
            groupStage = new Stage();
            groupStage.setX((primaryStage.getX() + primaryStage.getWidth()) / 3 + 50);
            groupStage.setY((primaryStage.getY() + primaryStage.getHeight()) / 3);
            groupStage.setScene(new Scene(loader.load()));
            groupStage.setTitle("Group");
            groupStage.show();

            final GroupController groupController = loader.getController();
            groupController.provideApp(this, operation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeGroupWindow() {
        groupStage.close();
    }

    private Stage subjectStage;

    public void showSubjectWindow(Operation operation) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/subject_window.fxml"));
            subjectStage = new Stage();
            subjectStage.setX((primaryStage.getX() + primaryStage.getWidth()) / 3 + 50);
            subjectStage.setY((primaryStage.getY() + primaryStage.getHeight()) / 3);
            subjectStage.setScene(new Scene(loader.load()));
            subjectStage.setTitle("Subject");
            subjectStage.show();

            final SubjectController subjectController = loader.getController();
            subjectController.provideApp(this, operation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSubjectWindow() {
        subjectStage.close();
    }

    public void refreshData() {
        controller.refreshData();
    }


    public void showErrorAlert(String message) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong. Please read information below.");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public GroupApi getGroupApi() {
        return groupApi;
    }

    public HumanApi getHumanApi() {
        return humanApi;
    }

    public MarkApi getMarkApi() {
        return markApi;
    }

    public SubjectApi getSubjectApi() {
        return subjectApi;
    }

    public AuthApi getAuthApi() {
        return authApi;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
