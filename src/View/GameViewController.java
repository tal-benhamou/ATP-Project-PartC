package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class GameViewController extends AView implements Observer, Initializable {

    public MyMazeGenerator generator;

    @Override
    public void setViewModel(MyViewModel viewModel) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
