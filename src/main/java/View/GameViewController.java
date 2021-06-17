package View;

import ViewModel.MyViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class GameViewController extends AView implements Observer, Initializable {
    public MazeDisplayer mazeDisplayer;

    public Button generateMazeButton;
    public Button solveMazeButton;
    public TextField colsTextField;
    public MenuItem saveMazeButton;
    public TextField rowsTextField;
    public MenuBar menuBar;
    public Pane gridPaneDisplayer;
    public GridPane gridPaneGame;
    public Pane PaneMenuBar;
    public Pane VBox;
    private boolean clickPlayer;
    boolean isFinish;
    private final Timeline timeLine = new Timeline();
    private boolean DisableSolveButton = true;
    private boolean isGoalPosion = false;


    @Override
    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
        this.CurrScene = solveMazeButton.getScene();
    }

    private final InvalidationListener listener = new InvalidationListener() {
        @Override
        public void invalidated(javafx.beans.Observable observable) {
            try {
               if (viewModel != null && viewModel.getMaze() != null) {
                   mazeDisplayer.draw();
               }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change) {
            case "Maze Generated":
                try {
                    generateMaze();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "Maze Solution":
                try {
                    drawSolution();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "Get Goal":
                try {
                    getGoal();
                    isGoalPosion = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Player Moved":
                try {
                    playerMoved();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }


    }

    private void playerMoved() throws FileNotFoundException {
        mazeDisplayer.setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }

    private void getGoal() throws IOException {
        isFinish = true;
        getFinish(new ActionEvent());
    }

    private void drawSolution() throws FileNotFoundException {
        mazeDisplayer.setSolution(viewModel.getSolution());
        mazeDisplayer.drawSol(viewModel.getSolution());
        mazeDisplayer.requestFocus();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mazeDisplayer.widthProperty().bind(gridPaneDisplayer.widthProperty());
        mazeDisplayer.heightProperty().bind(gridPaneDisplayer.heightProperty());
        menuBar.prefWidthProperty().bind(gridPaneGame.widthProperty());
        gridPaneGame.prefHeightProperty().bind(menuBar.heightProperty());
        mazeDisplayer.widthProperty().addListener(listener);
        mazeDisplayer.heightProperty().addListener(listener);
        if (!musicPlayBol)
            musicGameView.setSelected(true);
        if (!soundPlayBol)
            soundGameView.setSelected(true);
    }

    public void generateMaze() throws FileNotFoundException {
        centerCanvas();
        isFinish = false;
        solveMazeButton.setDisable(false);
        saveMazeButton.setDisable(false);
        mazeDisplayer.drawMaze(viewModel.getMaze());
        mazeDisplayer.requestFocus();
    }


    public void ExitAppMyView(ActionEvent actionEvent) {
        CurrScene = generateMazeButton.getScene();
        super.ExitApp(actionEvent);
    }

    public void generateNewMaze(ActionEvent actionEvent) throws FileNotFoundException {
        int rows = 0,cols = 0;
        if (rowsTextField.getText().toString().equals("")) {
            rows = 10;
        }
        else {
            try {
                rows = Integer.parseInt(rowsTextField.getText().toString());
            } catch (NumberFormatException e) {
                rows = 10;
            }
        }
        if (colsTextField.getText().toString().equals(""))
            cols = 10;
        else {
            try {
                cols = Integer.parseInt(colsTextField.getText());
            } catch (NumberFormatException e) {
                cols = 10;
            }
        }
        if (rows < 2 || cols < 2) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please write a valid dimensions. (Minimum Size - 2X2)");
            alert.show();
            return;
        }
        viewModel.generateMaze(rows, cols);
        DisableSolveButton = false;
    }

    public void solveMaze(ActionEvent actionEvent) throws FileNotFoundException {
        if (!DisableSolveButton)
            viewModel.solveMaze();
    }

    public void fileNewPressed(ActionEvent actionEvent) {
        viewModel.generateMaze(10, 10);
    }

    private void centerCanvas() {
        timeLine.getKeyFrames().clear();
        timeLine.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateYProperty(), 0)),
                new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleXProperty(), 1)),
                new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleYProperty(), 1))
        );
        timeLine.play();
    }

    public void zoomIn(ScrollEvent event) {
        double zoom = 1;
        if (event.isControlDown()) {
            if (event.getDeltaY() > 0) {
                zoom = 1.6;
            } else {
                zoom = 0.5;
            }
            if (mazeDisplayer.getScaleX() * zoom < 0.9) {
                centerCanvas();
            } else {
                double factor = (mazeDisplayer.getScaleX() * zoom / mazeDisplayer.getScaleX()) - 1;
                double xPos = (event.getSceneX() - (mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getWidth() / 2 + mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getMinX()));
                double yPos = (event.getSceneY() - (mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getHeight() / 2 + mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getMinY()));
                timeLine.getKeyFrames().clear();
                timeLine.getKeyFrames().addAll(
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateXProperty(), mazeDisplayer.getTranslateX() - factor * xPos)),
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateYProperty(), mazeDisplayer.getTranslateY() - factor * yPos)),
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleXProperty(), mazeDisplayer.getScaleX() * zoom)),
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleYProperty(), mazeDisplayer.getScaleX() * zoom))
                );
                timeLine.play();
                mazeDisplayer.setScaleX(mazeDisplayer.getScaleX() * zoom);
                mazeDisplayer.setScaleY(mazeDisplayer.getScaleY() * zoom);
            }
            event.consume();
        }
    }


    public void keyPressed(KeyEvent keyEvent) {
        mazeDisplayer.requestFocus();
        if (!isFinish)
            viewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }

    public void SaveFunc(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PicachuMaze", "*.pmz");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save Your PicachuMaze");
        File file = fileChooser.showSaveDialog(CurrScene.getWindow());
        if (file != null) {
            viewModel.saveMaze(file);
        }
    }


    public void mousePreesed(MouseEvent mouseEvent) {
        double cellHeight = mazeDisplayer.getCellHeight();
        double cellWidth = mazeDisplayer.getCellWidth();
        double x = mouseEvent.getSceneX()-gridPaneDisplayer.localToScene(gridPaneDisplayer.getBoundsInLocal()).getMinX();
        double y = mouseEvent.getSceneY()-gridPaneDisplayer.localToScene(gridPaneDisplayer.getBoundsInLocal()).getMinY();
        if ((int)(y/cellHeight) == mazeDisplayer.getPlayerRow() && (int)(x/cellWidth) == mazeDisplayer.getPlayerCol())
            clickPlayer = true;
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        if (clickPlayer && !isGoalPosion){
            mazeDisplayer.requestFocus();
            double cellHeight = mazeDisplayer.getCellHeight();
            double cellWidth = mazeDisplayer.getCellWidth();
            double x = mouseEvent.getSceneX()-gridPaneDisplayer.localToScene(gridPaneDisplayer.getBoundsInLocal()).getMinX();
            double y = mouseEvent.getSceneY()-gridPaneDisplayer.localToScene(gridPaneDisplayer.getBoundsInLocal()).getMinY();
            viewModel.isValidDragg((int)(y/cellHeight), (int)(x/cellWidth));
        }
    }

    public void mouseDraggedReleased(MouseEvent mouseDragEvent) {
        clickPlayer = false;
    }

    public void mouseClick(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }
}