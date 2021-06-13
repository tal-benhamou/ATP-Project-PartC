package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
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
    private final Timeline timeline = new Timeline();


    @Override
    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
        //this.CurrScene = (new Button()).getScene();
        this.CurrScene = solveMazeButton.getScene();
        //gridPaneDisplayer.maxHeightProperty().bind(CurrScene.heightProperty().subtract(menuBar.heightProperty()).subtract(20));
        //gridPaneDisplayer.minHeightProperty().bind(CurrScene.heightProperty().subtract(menuBar.heightProperty()).subtract(20));
        //gridPaneDisplayer.maxHeightProperty().bind(CurrScene.widthProperty().subtract(PaneMenuBar.heightProperty()).subtract(10));
        //gridPaneDisplayer.minHeightProperty().bind(CurrScene.widthProperty().subtract(PaneMenuBar.heightProperty()).subtract(10));
    }

    private final InvalidationListener listener = new InvalidationListener() {
        @Override
        public void invalidated(javafx.beans.Observable observable) {
            try {
                if (viewModel != null && viewModel.getMaze() != null)
                    mazeDisplayer.draw();
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
        // mazeDisplayer.movePlayer(viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }

    private void getGoal() throws IOException {
        isFinish = true;
        getFinish(new ActionEvent());
    }

    private void drawSolution() throws FileNotFoundException {
        //mazeDisplayer.setSol(viewModel.getSolution());
        mazeDisplayer.drawSol(viewModel.getSolution());
        //mazeDisplayer.drawMaze(viewModel.getMaze());
    }

//    private void Mazegenerate() {
//        //viewModel.getMaze().print();
//        //System.out.println(viewModel.getMaze().getMap().length);
//        //System.out.println(viewModel.getMaze().getMap()[0].length);
//       mazeDisplayer.drawMaze(viewModel.getMaze());
//    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mazeDisplayer.widthProperty().bind(gridPaneDisplayer.widthProperty());
        mazeDisplayer.heightProperty().bind(gridPaneDisplayer.heightProperty());
//        mazeDisplayer.widthProperty().bind(gridPaneGame.widthProperty());
//        mazeDisplayer.heightProperty().bind(gridPaneGame.heightProperty());
        menuBar.prefWidthProperty().bind(gridPaneGame.widthProperty());
        mazeDisplayer.widthProperty().addListener(listener);
        mazeDisplayer.heightProperty().addListener(listener);
    }

    public void generateMaze() throws FileNotFoundException {
//        Node node = (Node) actionEvent.getSource();
//        Stage stage = (Stage) node.getScene().getWindow();
//        stage.show();
//        scPane.setContent(node);
//        int rows = Integer.parseInt(rowsTextField.getText());
//        int cols = Integer.parseInt(colsTextField.getText());
//        if (rows < 2 || cols < 2){
//            Alert alert = new Alert(Alert.AlertType.WARNING, "Please write a valid dimensions: Minimum Size - 2X2");
//            alert.show();
//        }
//        else
        CenterCanvas();
        //playerPosition = viewModel.getLocation();
        isFinish = false;
        solveMazeButton.setDisable(false);
        saveMazeButton.setDisable(false);
        mazeDisplayer.drawMaze(viewModel.getMaze());
        Position playerPosition = new Position(viewModel.getPlayerRow(), viewModel.getPlayerCol());
        mazeDisplayer.requestFocus();
        //viewModel.generateMaze(rows, cols);

    }


    public void ExitAppMyView(ActionEvent actionEvent) {
        CurrScene = generateMazeButton.getScene();
        super.ExitApp(actionEvent);
    }

//    private void finish() throws FileNotFoundException {
//        playerPosition = viewModel.getLocation();
//        mazeDisplayer.movePlayer(playerPosition.getRowIndex(),playerPosition.getColumnIndex());
//
//        try {
//            Stage stage = NewStage("FinishView.fxml", "Congratulations");
//            stage.setMinHeight(450);
//            stage.setMinWidth(600);
//            stage.setMaxHeight(450);
//            stage.setMaxWidth(600);
//            stage.getIcons().add(new Image("./images/picachu.png"));
//            solveMazeButton.setDisable(true);
//        }
//        catch (IOException e){
//
//        }
//    }

    public void generateNewMaze(ActionEvent actionEvent) throws FileNotFoundException {
        int rows = Integer.parseInt(rowsTextField.getText());
        int cols = Integer.parseInt(colsTextField.getText());
        if (rows < 2 || cols < 2) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please write a valid dimensions. (Minimum Size - 2X2)");
            alert.show();
        }
        viewModel.generateMaze(rows, cols);
        //mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    public void solveMaze(ActionEvent actionEvent) throws FileNotFoundException {
        viewModel.solveMaze();
        //mazeDisplayer.drawSol(viewModel.getSolution());
    }

    public void fileNewPressed(ActionEvent actionEvent) {
        viewModel.generateMaze(10, 10);
    }

    public void mouseCLicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }


    //    public void menuSavePressed(ActionEvent actionEvent) {
//        FileChooser fileChooser = getFileChooser("Choose Path To Save The Maze");
//        File file = fileChooser.showSaveDialog(solveMazeButton.getScene().getWindow());
//        if (file != null)
//            viewModel.saveMaze(file);
//    }
    private void CenterCanvas() {
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateYProperty(), 0)),
                new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleXProperty(), 1)),
                new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleYProperty(), 1))
        );
        timeline.play();
    }

    public void zoomIn(ScrollEvent event) {
        double zoomfactor = 1;
        if (event.isControlDown()) {
            if (event.getDeltaY() > 0) {
                zoomfactor = 1.4;
            } else {
                zoomfactor = 0.6;
            }
            if (mazeDisplayer.getScaleX() * zoomfactor < 0.9) {
                CenterCanvas();
            } else {
                double factor = (mazeDisplayer.getScaleX() * zoomfactor / mazeDisplayer.getScaleX()) - 1;
                double xPos = (event.getSceneX() - (mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getWidth() / 2 + mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getMinX()));
                double yPos = (event.getSceneY() - (mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getHeight() / 2 + mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getMinY()));
                timeline.getKeyFrames().clear();
                timeline.getKeyFrames().addAll(
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateXProperty(), mazeDisplayer.getTranslateX() - factor * xPos)),
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateYProperty(), mazeDisplayer.getTranslateY() - factor * yPos)),
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleXProperty(), mazeDisplayer.getScaleX() * zoomfactor)),
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleYProperty(), mazeDisplayer.getScaleX() * zoomfactor))
                );
                timeline.play();
                mazeDisplayer.setScaleX(mazeDisplayer.getScaleX() * zoomfactor);
                mazeDisplayer.setScaleY(mazeDisplayer.getScaleY() * zoomfactor);
            }

            event.consume();
        }
    }

    public void undrawSolution(MouseEvent mouseEvent) throws FileNotFoundException {
        mazeDisplayer.drawMaze(viewModel.getMaze());
        mazeDisplayer.requestFocus();
    }


    public void keyPressed(KeyEvent keyEvent) {
        mazeDisplayer.requestFocus();
        if (isFinish == false)
            viewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }

    public void SaveFunc(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PicachuMaze", "*.pmz");
        fileChooser.getExtensionFilters().add(extFilter);
        //Show save file dialog
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
        if (clickPlayer){
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