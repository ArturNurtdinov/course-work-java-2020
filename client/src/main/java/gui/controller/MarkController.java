package gui.controller;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Human;
import model.Mark;
import model.Subject;

import java.util.stream.Collectors;

public class MarkController {
    private App app;
    private Operation operation;

    @FXML
    private Label idLabel;
    @FXML
    private Label valueLabel;
    @FXML
    private Label subjectLabel;
    @FXML
    private Label teacherLabel;
    @FXML
    private Label studentLabel;

    @FXML
    private TextField idField;
    @FXML
    private TextField valueField;

    @FXML
    private ChoiceBox<Subject> subjectBox;
    @FXML
    private ChoiceBox<Human> teacherBox;
    @FXML
    private ChoiceBox<Human> studentBox;

    @FXML
    private Button addSubjectButton;
    @FXML
    private Button addTeacherButton;
    @FXML
    private Button addStudentButton;
    @FXML
    private Button refreshSubjectsButton;
    @FXML
    private Button refreshTeachersButton;
    @FXML
    private Button refreshStudentsButton;


    public void provideApp(App app, Operation operation) {
        this.app = app;
        this.operation = operation;

        app.getSubjectApi().getSubjects(app.getToken()).subscribe(subjectResponse -> {
            if (subjectResponse.isSuccessful() && subjectResponse.body() != null) {
                subjectBox.getItems().addAll(subjectResponse.body());
            } else {
                app.showErrorAlert("Something went wrong. Code = " + subjectResponse.code());
            }
        });

        app.getHumanApi().getPeople(app.getToken()).subscribe(humanResponse -> {
            if (humanResponse.isSuccessful() && humanResponse.body() != null) {
                humanResponse.body().forEach(it -> {
                    if (it.getType() == 'S') {
                        studentBox.getItems().add(it);
                    } else {
                        teacherBox.getItems().add(it);
                    }
                });
            } else {
                app.showErrorAlert("Something went wrong. Code = " + humanResponse.code());
            }
        });

        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (operation == Operation.DELETE) return;
            try {
                app.getMarkApi().getMarkById(app.getToken(), Long.parseLong(newValue)).subscribe(markResponse -> {
                    if (markResponse.isSuccessful() && markResponse.body() != null) {
                        Mark mark = markResponse.body();
                        valueField.setText(Integer.toString(mark.getValue()));
                        subjectBox.setValue(mark.getSubject());
                        teacherBox.setValue(mark.getTeacher());
                        studentBox.setValue(mark.getStudent());
                    } else {
                        valueField.setText("");
                        subjectBox.setValue(null);
                        teacherBox.setValue(null);
                        studentBox.setValue(null);
                    }
                });
            } catch (Exception ignored) {
            }
        });

        showContentFields(true);
        switch (operation) {
            case ADD:
                idField.setVisible(false);
                idLabel.setVisible(false);
                break;
            case DELETE:
                showContentFields(false);
                break;
            case UPDATE:
                break;
        }
    }

    @FXML
    private void handleOk() {
        switch (operation) {
            case ADD:
                if (valueField.getText().isEmpty() || subjectBox.getValue() == null || teacherBox.getValue() == null || studentBox.getValue() == null) {
                    app.showErrorAlert("Please fill all fields.");
                } else {
                    try {
                        app.getMarkApi().addMark(app.getToken(), new Mark(studentBox.getValue(), teacherBox.getValue(), subjectBox.getValue(),
                                Integer.parseInt(valueField.getText()))).subscribe(response -> {
                            if (response.isSuccessful() && response.body() != null) {
                                app.refreshData();
                                app.closeMarkWindow();
                            } else {
                                app.showErrorAlert("Something went wrong. Code = " + response.code());
                            }
                        });
                    } catch (NumberFormatException e) {
                        app.showErrorAlert("Wrong value. Please provide integer type");
                    }
                }
                break;
            case DELETE:
                if (idField.getText().isEmpty()) {
                    app.showErrorAlert("Id cannot be empty");
                } else {
                    try {
                        long id = Long.parseLong(idField.getText());
                        app.getMarkApi().deleteMark(app.getToken(), id).subscribe(response -> {
                            if (response.isSuccessful()) {
                                app.refreshData();
                                app.closeMarkWindow();
                            } else {
                                app.showErrorAlert("Something went wrong. Code = " + response.code());
                            }
                        });
                    } catch (NumberFormatException e) {
                        app.showErrorAlert("Wrong id");
                    }
                }
                break;
            case UPDATE:
                if (valueField.getText().isEmpty() || subjectBox.getValue() == null || teacherBox.getValue() == null || studentBox.getValue() == null) {
                    app.showErrorAlert("Please fill all fields.");
                } else {
                    try {
                        long id = Long.parseLong(idField.getText());
                        app.getMarkApi().updateMark(app.getToken(), id, new Mark(id, studentBox.getValue(), teacherBox.getValue(), subjectBox.getValue(),
                                Integer.parseInt(valueField.getText()))).subscribe(response -> {
                            if (response.isSuccessful()) {
                                app.refreshData();
                                app.closeMarkWindow();
                            } else {
                                app.showErrorAlert("Something went wrong. Code = " + response.code());
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
        app.closeMarkWindow();
    }

    @FXML
    private void addSubject() {
        app.showSubjectWindow(Operation.ADD);
    }

    @FXML
    private void addTeacher() {
        app.showHumanWindow(Operation.ADD);
    }

    @FXML
    private void addStudent() {
        app.showHumanWindow(Operation.ADD);
    }

    @FXML
    private void refreshSubjects() {
        app.getSubjectApi().getSubjects(app.getToken()).subscribe(response -> {
            if (response.isSuccessful() && response.body() != null) {
                subjectBox.getItems().clear();
                subjectBox.getItems().addAll(response.body());
            } else {
                app.showErrorAlert("Something went wrong. Code = " + response.code());
            }
        });
    }

    @FXML
    private void refreshTeachers() {
        app.getHumanApi().getPeople(app.getToken()).subscribe(response -> {
            if (response.isSuccessful() && response.body() != null) {
                teacherBox.getItems().clear();
                teacherBox.getItems().addAll(response.body().stream().filter(it -> it.getType() == 'T').collect(Collectors.toList()));
            } else {
                app.showErrorAlert("Something went wrong. Code = " + response.code());
            }
        });
    }

    @FXML
    private void refreshStudents() {
        app.getHumanApi().getPeople(app.getToken()).subscribe(response -> {
            if (response.isSuccessful() && response.body() != null) {
                studentBox.getItems().clear();
                studentBox.getItems().addAll(response.body().stream().filter(it -> it.getType() == 'S').collect(Collectors.toList()));
            } else {
                app.showErrorAlert("Something went wrong. Code = " + response.code());
            }
        });
    }

    private void showContentFields(boolean state) {
        valueLabel.setVisible(state);
        valueField.setVisible(state);

        subjectLabel.setVisible(state);
        subjectBox.setVisible(state);
        addSubjectButton.setVisible(state);
        refreshSubjectsButton.setVisible(state);

        teacherLabel.setVisible(state);
        teacherBox.setVisible(state);
        addTeacherButton.setVisible(state);
        refreshTeachersButton.setVisible(state);

        studentLabel.setVisible(state);
        studentBox.setVisible(state);
        addStudentButton.setVisible(state);
        refreshStudentsButton.setVisible(state);
    }
}
