package gui.controller;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Group;
import model.Human;
import model.Mark;
import model.Subject;

public class MainWindowController {
    private App app;
    @FXML
    private TableView<Group> groupTable;
    @FXML
    private TableColumn<?, ?> groupIdColumn;
    @FXML
    private TableColumn<?, ?> groupNameColumn;

    @FXML
    private TableView<Human> humanTable;
    @FXML
    private TableColumn<?, ?> humanIdColumn;
    @FXML
    private TableColumn<?, ?> humanFirstNameColumn;
    @FXML
    private TableColumn<?, ?> humanLastNameColumn;
    @FXML
    private TableColumn<?, ?> humanFatherNameColumn;
    @FXML
    private TableColumn<?, ?> humanTypeColumn;
    @FXML
    private TableColumn<?, ?> humanGroupColumn;

    @FXML
    private TableView<Subject> subjectTable;
    @FXML
    private TableColumn<?, ?> subjectIdColumn;
    @FXML
    private TableColumn<?, ?> subjectNameColumn;

    @FXML
    private TableView<Mark> markTable;
    @FXML
    private TableColumn<?, ?> markIdColumn;
    @FXML
    private TableColumn<?, ?> markStudentColumn;
    @FXML
    private TableColumn<?, ?> markTeacherColumn;
    @FXML
    private TableColumn<?, ?> markSubjectColumn;
    @FXML
    private TableColumn<?, ?> markValueColumn;

    public void provideApp(App app) {
        this.app = app;
        groupIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        humanIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        humanFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        humanLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        humanFatherNameColumn.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        humanGroupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        humanTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        subjectIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        markIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        markStudentColumn.setCellValueFactory(new PropertyValueFactory<>("student"));
        markTeacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        markSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        markValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        refreshData();
    }

    public void refreshData() {
        markTable.getItems().clear();
        groupTable.getItems().clear();
        subjectTable.getItems().clear();
        humanTable.getItems().clear();
        loadPeople();
        loadGroups();
        loadSubjects();
        loadMarks();
    }

    private void loadMarks() {
        app.getMarkApi().getMarks(app.getToken()).subscribe(markResponse -> {
            if (markResponse.isSuccessful() && markResponse.body() != null) {
                markTable.getItems().addAll(markResponse.body());
            } else {
                app.showErrorAlert(markResponse.errorBody().string());
            }
        });
    }

    private void loadGroups() {
        app.getGroupApi().getGroups(app.getToken()).subscribe(groupResponse -> {
            if (groupResponse.isSuccessful() && groupResponse.body() != null) {
                groupTable.getItems().addAll(groupResponse.body());
            } else {
                app.showErrorAlert(groupResponse.errorBody().string());
            }
        });
    }

    private void loadPeople() {
        app.getHumanApi().getPeople(app.getToken()).subscribe(humanResponse -> {
            if (humanResponse.isSuccessful() && humanResponse.body() != null) {
                humanTable.getItems().addAll(humanResponse.body());
            } else {
                app.showErrorAlert(humanResponse.errorBody().string());
            }
        });
    }

    private void loadSubjects() {
        app.getSubjectApi().getSubjects(app.getToken()).subscribe(subjectResponse -> {
            if (subjectResponse.isSuccessful() && subjectResponse.body() != null) {
                subjectTable.getItems().addAll(subjectResponse.body());
            } else {
                app.showErrorAlert(subjectResponse.errorBody().string());
            }
        });
    }

    @FXML
    private void groupAdd() {
        app.showGroupWindow(Operation.ADD);
    }

    @FXML
    private void groupDelete() {
        app.showGroupWindow(Operation.DELETE);
    }

    @FXML
    private void groupUpdate() {
        app.showGroupWindow(Operation.UPDATE);
    }

    @FXML
    private void subjectAdd() {
        app.showSubjectWindow(Operation.ADD);
    }

    @FXML
    private void subjectDelete() {
        app.showSubjectWindow(Operation.DELETE);
    }

    @FXML
    private void subjectUpdate() {
        app.showSubjectWindow(Operation.UPDATE);
    }

    @FXML
    private void humanAdd() {
        app.showHumanWindow(Operation.ADD);
    }

    @FXML
    private void humanDelete() {
        app.showHumanWindow(Operation.DELETE);
    }

    @FXML
    private void humanUpdate() {
        app.showHumanWindow(Operation.UPDATE);
    }

    @FXML
    private void markAdd() {
        app.showMarkWindow(Operation.ADD);
    }

    @FXML
    private void markDelete() {
        app.showMarkWindow(Operation.DELETE);
    }

    @FXML
    private void markUpdate() {
        app.showMarkWindow(Operation.UPDATE);
    }

    @FXML
    private void refreshPeople() {
        humanTable.getItems().clear();
        loadPeople();
    }

    @FXML
    private void refreshGroups() {
        groupTable.getItems().clear();
        loadGroups();
    }

    @FXML
    private void refreshSubjects() {
        subjectTable.getItems().clear();
        loadSubjects();
    }

    @FXML
    private void refreshMarks() {
        markTable.getItems().clear();
        loadMarks();
    }
}
