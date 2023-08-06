package com.maman14q2.maman14q2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HelloController {
    private final ObservableList<Months> months = FXCollections.observableArrayList(Months.January, Months.February, Months.March, Months.April, Months.May, Months.June, Months.July, Months.August, Months.September, Months.October, Months.November, Months.December);
    private final ObservableList<Integer> years = FXCollections.observableArrayList(2023, 2024, 2025, 2026, 2027, 2028, 2028, 2030);
    private DataBase data;
    private String dataFile;
    @FXML
    private ComboBox<Integer> dayComBox;

    @FXML
    private ComboBox<Months> monthComBox;

    @FXML
    private ComboBox<Integer> yearComBox;

    @FXML
    private TextArea reminder;

    @FXML
    void monthChosen(ActionEvent event) {
        if (monthComBox.getValue() != null)
            dayComBox.setItems(FXCollections.observableArrayList(monthComBox.getValue().getDays()));
    }

    @FXML
    void saveBtnPressed(ActionEvent event) {
        if (checkDateValues()) {
            data.add(new MyDate(dayComBox.getValue(), monthComBox.getValue(), yearComBox.getValue()), reminder.getText());
            updateReminders(dataFile);
            dayComBox.setValue(null);
            monthComBox.setValue(null);
            yearComBox.setValue(null);
            reminder.setText(null);
        }
    }

    @FXML
    void findBtnPressed(ActionEvent event) {
        if (checkDateValues())
            reminder.setText(data.getReminder(new MyDate(dayComBox.getValue(), monthComBox.getValue(), yearComBox.getValue())));
    }

    @FXML
    void deleteBtnPressed(ActionEvent event) {
        if (checkDateValues()) {
            try {
                data.delete(new MyDate(dayComBox.getValue(), monthComBox.getValue(), yearComBox.getValue()));
            } catch (NullPointerException ignored) {
            }
            updateReminders(dataFile);
            dayComBox.setValue(null);
            monthComBox.setValue(null);
            yearComBox.setValue(null);
            reminder.setText(null);
        }
    }

    public void initialize() {
        monthComBox.setItems(months);
        yearComBox.setItems(years);
        data = loadData(readFile("ProgramData.txt"));
    }

    private DataBase loadData(Scanner scan) {
        if (scan.hasNextLine())
            dataFile = scan.nextLine();
        else {
            dataFile = getFileName() + ".xml";
            createFile(dataFile);
            writeToFile("ProgramData.txt", dataFile);
        }
        scan.close();
        DataBase dataBaseToReturn = new DataBase();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(dataFile));
            dataBaseToReturn = (DataBase) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException ignored) {
        }
        return dataBaseToReturn;
    }

    private Scanner readFile(String filePath) {
        try {
            return new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            createFile(filePath);
            try {
                return readFile(filePath);
            } catch (Exception e2) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't Create file" + new File(filePath).getAbsolutePath() + "\nProgram Cannot Resume", ButtonType.OK);
                alert.showAndWait();
                System.exit(-1);
                return null;
            }
        }
    }

    private void createFile(String filePath) {
        try {
            Formatter output = new Formatter(filePath);
            output.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't Create file" + new File(filePath).getAbsolutePath() + "\nProgram Cannot Resume", ButtonType.OK);
            alert.showAndWait();
            System.exit(-1);
        }
    }

    private String getFileName() {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
        inputDialog.setContentText("Please Enter a File Name\nPlease do not give an extension!");
        inputDialog.setHeaderText("No Reminder DB Found");
        inputDialog.showAndWait();
        if (inputDialog.getEditor().getText().contains("."))
            return getFileName();
        return inputDialog.getEditor().getText();
    }

    private void writeToFile(String filePath, String text) {
        try {
            Formatter output = new Formatter(filePath);
            try {
                output.format(text);
            } catch (NoSuchElementException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't write to file" + new File(filePath).getAbsolutePath(), ButtonType.OK);
                alert.showAndWait();
            }
            output.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't Create file" + new File(filePath).getAbsolutePath() + "\nProgram Cannot Resume", ButtonType.OK);
            alert.showAndWait();
            System.exit(-1);
        }
    }

    private void updateReminders(String filePath) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath));
            outputStream.writeObject(data);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't Write to " + new File(filePath).getAbsolutePath() + "\nProgram Cannot Resume", ButtonType.OK);
            alert.showAndWait();
            System.exit(-1);
        }
    }

    private boolean checkDateValues() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Missing Date Parameters!", ButtonType.OK);
        if (dayComBox.getValue() == null || monthComBox.getValue() == null || yearComBox.getValue() == null) {
            alert.showAndWait();
            return false;
        }
        return true;
    }
}