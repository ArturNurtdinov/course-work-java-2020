package gui.controller;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Mark;
import model.Subject;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SubjectController {
    private App app;
    private Operation operation;

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;

    public void provideApp(App app, Operation operation) {
        this.app = app;
        this.operation = operation;

        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (operation == Operation.DELETE) return;
            try {
                app.getSubjectApi().getSubjectById(app.getToken(), Long.parseLong(newValue)).subscribe(subjectResponse -> {
                    if (subjectResponse.isSuccessful() && subjectResponse.body() != null) {
                        nameField.setText(subjectResponse.body().getName());
                    } else {
                        nameField.setText("");
                    }
                });
            } catch (Exception ignored) {
            }
        });

        idLabel.setVisible(true);
        idField.setVisible(true);
        nameLabel.setVisible(true);
        nameField.setVisible(true);
        switch (operation) {
            case ADD:
                idLabel.setVisible(false);
                idField.setVisible(false);
                break;
            case DELETE:
                nameLabel.setVisible(false);
                nameField.setVisible(false);
            case UPDATE:
                break;
        }
    }

    @FXML
    private void handleOk() {
        switch (operation) {
            case ADD:
                if (nameField.getText().isEmpty()) {
                    app.showErrorAlert("Name cannot be empty");
                } else {
                    app.getSubjectApi().addSubject(app.getToken(), new Subject(nameField.getText())).subscribe(response -> {
                        if (response.isSuccessful()) {
                            app.refreshData();
                            app.closeSubjectWindow();
                        } else {
                            app.showErrorAlert("Something went wrong. Code = " + response.code());
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
                        if (marksResponse.isSuccessful()) {
                            List<Mark> list = marksResponse.body();
                            AtomicBoolean check = new AtomicBoolean(false);
                            list.forEach(it -> {
                                if (it.getSubject().getId() == id) {
                                    app.showErrorAlert("There is a mark refers on this subject. You can't delete it");
                                    app.closeSubjectWindow();
                                    check.set(true);
                                    return;
                                }
                            });
                            if (check.get()) return;
                            app.getSubjectApi().deleteSubjectById(app.getToken(), id).subscribe(response -> {
                                if (response.isSuccessful()) {
                                    app.refreshData();
                                    app.closeSubjectWindow();
                                } else {
                                    app.showErrorAlert("Something went wrong. Code = " + response.code());
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
                String idString = idField.getText();
                String newName = nameField.getText();
                if (idString.isEmpty() || newName.isEmpty()) {
                    app.showErrorAlert("Fields cannot be empty");
                } else {
                    try {
                        long id = Long.parseLong(idString);
                        app.getSubjectApi().updateSubject(app.getToken(), id, new Subject(id, newName)).subscribe(response -> {
                            if (response.isSuccessful()) {
                                app.refreshData();
                                app.closeSubjectWindow();
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
        app.closeSubjectWindow();
    }
}
