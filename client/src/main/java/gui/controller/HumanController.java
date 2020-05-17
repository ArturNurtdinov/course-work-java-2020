package gui.controller;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Group;
import model.Human;
import model.Mark;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class HumanController {
    private App app;
    private Operation operation;

    @FXML
    private Label idLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label fatherNameLabel;
    @FXML
    private Label groupLabel;
    @FXML
    private Label typeLabel;

    @FXML
    private TextField idField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField fatherNameField;

    @FXML
    private ChoiceBox<Group> groupBox;
    @FXML
    private ChoiceBox<Character> typeBox;

    @FXML
    private Button addGroupButton;
    @FXML
    private Button refreshGroupsButton;

    public void provideApp(App app, Operation operation) {
        this.app = app;
        this.operation = operation;

        app.getGroupApi().getGroups(app.getToken()).subscribe(groupResponse -> {
            if (groupResponse.isSuccessful()) {
                groupBox.getItems().addAll(groupResponse.body());
            } else {
                app.showErrorAlert("Something went wrong. Code = " + groupResponse.code());
            }
        });

        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (operation == Operation.DELETE) return;
            try {
                app.getHumanApi().getHumanById(app.getToken(), Long.parseLong(newValue)).subscribe(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Human human = response.body();
                        firstNameField.setText(human.getFirstName());
                        lastNameField.setText(human.getLastName());
                        fatherNameField.setText(human.getFatherName());
                        groupBox.setValue(human.getGroup());
                        typeBox.setValue(human.getType());
                    } else {
                        firstNameField.setText("");
                        lastNameField.setText("");
                        fatherNameField.setText("");
                        groupBox.setValue(null);
                        typeBox.setValue('S');
                    }
                });
            } catch (Exception ignored) {
            }
        });

        idLabel.setVisible(true);
        idField.setVisible(true);
        typeBox.getItems().add('T');
        typeBox.getItems().add('S');
        showContentFields(true);
        switch (operation) {
            case ADD:
                idLabel.setVisible(false);
                idField.setVisible(false);
                break;
            case DELETE:
                showContentFields(false);
            case UPDATE:
                break;
        }
    }

    @FXML
    private void addGroup() {
        app.showGroupWindow(Operation.ADD);
    }

    @FXML
    private void refreshGroups() {
        app.getGroupApi().getGroups(app.getToken()).subscribe(groupResponse -> {
            if (groupResponse.isSuccessful()) {
                groupBox.getItems().clear();
                groupBox.getItems().addAll(groupResponse.body());
            } else {
                app.showErrorAlert("Something went wrong. Code = " + groupResponse.code());
            }
        });
    }

    @FXML
    private void handleOk() {
        switch (operation) {
            case ADD:
                if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()
                        || fatherNameField.getText().isEmpty() || groupBox.getValue() == null || typeBox.getValue() == null) {
                    app.showErrorAlert("Please fill all fields.");
                } else {
                    app.getHumanApi().addHuman(app.getToken(), new Human(firstNameField.getText(), lastNameField.getText(),
                            fatherNameField.getText(), groupBox.getValue(), typeBox.getValue())).subscribe(humanResponse -> {
                        if (humanResponse.isSuccessful() && humanResponse.body() != null) {
                            app.refreshData();
                            app.closeHumanWindow();
                        } else {
                            app.showErrorAlert("Something went wrong. Code = " + humanResponse.code());
                        }
                    });

                }
                break;
            case DELETE:
                if (idField.getText().isEmpty()) {
                    app.showErrorAlert("Id cannot be empty");
                    return;
                }
                try {
                    long id = Long.parseLong(idField.getText());
                    app.getMarkApi().getMarks(app.getToken()).subscribe(marksResponse -> {
                        if (marksResponse.isSuccessful() && marksResponse.body() != null) {
                            List<Mark> marks = marksResponse.body();
                            AtomicBoolean check = new AtomicBoolean(false);
                            marks.forEach(it -> {
                                if (it.getStudent().getId() == id || it.getTeacher().getId() == id) {
                                    check.set(true);
                                    app.showErrorAlert("There are marks refer to this human. You can't delete it");
                                    app.closeHumanWindow();
                                    return;
                                }
                            });
                            if (check.get()) return;
                            app.getHumanApi().deleteHuman(app.getToken(), id).subscribe(humanResponse -> {
                                if (humanResponse.isSuccessful()) {
                                    app.refreshData();
                                    app.closeHumanWindow();
                                } else {
                                    app.showErrorAlert("Something went wrong. Code = " + humanResponse.code());
                                }
                            });
                        } else {
                            app.showErrorAlert("Something went wrong. Code = " + marksResponse.code());
                        }
                    });
                } catch (NumberFormatException e) {
                    app.showErrorAlert("Wrong id");
                }
                break;
            case UPDATE:
                if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || idField.getText().isEmpty()
                        || fatherNameField.getText().isEmpty() || groupBox.getValue() == null || typeBox.getValue() == null) {
                    app.showErrorAlert("Please fill all fields.");
                } else {
                    try {
                        long id = Long.parseLong(idField.getText());
                        app.getHumanApi().updateHuman(app.getToken(), id, new Human(id, firstNameField.getText(), lastNameField.getText(),
                                fatherNameField.getText(), groupBox.getValue(), typeBox.getValue())).subscribe(humanResponse -> {
                            if (humanResponse.isSuccessful()) {
                                app.refreshData();
                                app.closeHumanWindow();
                            } else {
                                app.showErrorAlert("Something went wrong. Code = " + humanResponse.code());
                            }
                        });
                    } catch (NumberFormatException e) {
                        app.showErrorAlert("Wrong id");
                    }
                }
                break;
        }
    }

    @FXML
    private void handleCancel() {
        app.closeHumanWindow();
    }

    private void showContentFields(boolean state) {
        firstNameLabel.setVisible(state);
        firstNameField.setVisible(state);
        lastNameLabel.setVisible(state);
        lastNameField.setVisible(state);
        fatherNameField.setVisible(state);
        fatherNameLabel.setVisible(state);
        groupLabel.setVisible(state);
        groupBox.setVisible(state);
        typeLabel.setVisible(state);
        typeBox.setVisible(state);
        addGroupButton.setVisible(state);
        refreshGroupsButton.setVisible(state);
    }
}
