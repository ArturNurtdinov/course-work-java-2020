package gui.controller;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Group;
import model.Human;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GroupController {
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
                app.getGroupApi().getGroupById(app.getToken(), Long.parseLong(newValue)).subscribe(groupResponse -> {
                    if (groupResponse.isSuccessful() && groupResponse.body() != null) {
                        nameField.setText(groupResponse.body().getName());
                    } else {
                        nameField.setText("");
                    }
                });
            } catch (Exception ignored) { }
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
                    app.getGroupApi().addGroup(app.getToken(), new Group(nameField.getText())).subscribe(response -> {
                        if (response.isSuccessful()) {
                            app.refreshData();
                            app.closeGroupWindow();
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
                    app.getHumanApi().getPeople(app.getToken()).subscribe(peopleResponse -> {
                        if (peopleResponse.isSuccessful()) {
                            List<Human> people = peopleResponse.body();
                            AtomicBoolean check = new AtomicBoolean(false);
                            people.forEach(it -> {
                                if (it.getGroup().getId() == id) {
                                    app.showErrorAlert("There are people refer on this group. You can't delete it");
                                    app.closeGroupWindow();
                                    check.set(true);
                                    return;
                                }
                            });
                            if (check.get()) return;
                            app.getGroupApi().deleteGroup(app.getToken(), id).subscribe(response -> {
                                if (response.isSuccessful()) {
                                    app.refreshData();
                                    app.closeGroupWindow();
                                } else {
                                    app.showErrorAlert("Something went wrong. Code = " + response.code());
                                }
                            });
                        } else {
                            app.showErrorAlert("Something went wrong. Code = " + peopleResponse.code());
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
                        app.getGroupApi().updateGroup(app.getToken(), id, new Group(id, newName)).subscribe(response -> {
                            if (response.isSuccessful()) {
                                app.refreshData();
                                app.closeGroupWindow();
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
        app.closeGroupWindow();
    }
}
