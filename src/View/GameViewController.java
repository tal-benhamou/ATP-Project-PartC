package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class GameViewController extends AView implements Observer, Initializable {
    public TextField textField_mazeRows;
    public TextField textField_mazeCols;
    public MyMazeGenerator generator;
    public mazeDisplayer mazeDisplayer;

    @Override
    public void setViewModel(MyViewModel viewModel) {

    }

    @Override
    public void update(Observable o, Object arg) {
        String change = (String)arg;
        switch (change){
            case "Maze Generated":
                Mazegenerate();
                break;
            case "Maze Solution":
                drawSolution();
                break;
            case "Get Goal":
                getGoal();
                break;
            case "Player Moved":
                playerMoved();
                break;
        }


    }

    private void playerMoved() {
        mazeDisplayer.setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }

    private void getGoal() {

    }

    private void drawSolution() {
        mazeDisplayer.setSol(viewModel.getSolution());
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    private void Mazegenerate() {

        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void GenerateMaze(ActionEvent actionEvent) {
        int rows = Integer.parseInt(textField_mazeRows.getText());
        int cols = Integer.parseInt(textField_mazeCols.getText());
        if (rows < 2 || cols < 2){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please write a vaild dimensions: (2X2).");
            alert.show();
        }
        else
            viewModel.generateMaze(rows, cols);
    }

    public void SolveMaze(ActionEvent actionEvent) {

    }
}
