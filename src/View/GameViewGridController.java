package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.awt.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class GameViewGridController extends AView implements Observer, Initializable {
    public TextField textField_mazeRows;
    public TextField textField_mazeCols;
    public mazeDisplayer _mazeDisplayer;

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
        _mazeDisplayer.setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }

    private void getGoal() {

    }

    private void drawSolution() {
        _mazeDisplayer.setSol(viewModel.getSolution());
        _mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    private void Mazegenerate() {

        _mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void GenerateMaze(ActionEvent actionEvent) {
        int rows = Integer.parseInt(textField_mazeRows.getText());
        int cols = Integer.parseInt(textField_mazeCols.getText());
        if (rows < 2 || cols < 2){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please write a valid dimensions: Minimum Size - 2X2");
            alert.show();
        }
        else
            viewModel.generateMaze(rows, cols);
    }

    public void SolveMaze(ActionEvent actionEvent) {

    }

    public void fileNewPressed(ActionEvent actionEvent) {

    }

    public void mouseCLicked(MouseEvent mouseEvent) {

    }


    public void menuSavePressed(ActionEvent actionEvent) {

    }

    public void generateMaze(ActionEvent actionEvent) {

    }


    public void undrawSolution(MouseEvent mouseEvent) {

    }

    public void movePlayer(KeyEvent keyEvent) {

    }

    public void zoomIn(ScrollEvent scrollEvent) {

    }
}
