package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
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
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class GameViewGridController extends AView implements Observer, Initializable {
    public MazeDisplayer mazeDisplayer;

    public Button generateMazeButton;
    public Button solveMazeButton;
    public TextField colsTextField;
    public MenuItem saveMazeButton;
    public TextField rowsTextField;
    //public AnchorPane anchorPane;
    public GridPane gridPane;
    private Solution solution;
    boolean isFinish;
    private final Timeline timeline = new Timeline();


    @Override
    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
        this.CurrScene = solveMazeButton.getScene();
      //  anchorPane.minWidthProperty().bind(CurrScene.widthProperty());
        //anchorPane.maxWidthProperty().bind(CurrScene.widthProperty());

        //anchorPane.maxHeightProperty().bind(CurrScene.heightProperty().subtract(vbox.heightProperty()).subtract(menuBar.heightProperty()).subtract(20));
        //anchorPane.minHeightProperty().bind(CurrScene.heightProperty().subtract(vbox.heightProperty()).subtract(menuBar.heightProperty()).subtract(20));

    }

    private final InvalidationListener listener = new InvalidationListener(){
        @Override
        public void invalidated(javafx.beans.Observable observable) {
            try {
                mazeDisplayer.draw();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void update(Observable o, Object arg) {
        String change = (String)arg;
        switch (change){
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
                getGoal();
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
       mazeDisplayer.movePlayer(viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }

    private void getGoal() {

    }

    private void drawSolution() throws FileNotFoundException {
        //mazeDisplayer.setSol(viewModel.getSolution());
        mazeDisplayer.drawSol(solution);
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
        mazeDisplayer.widthProperty().bind(gridPane.widthProperty());
        mazeDisplayer.heightProperty().bind(gridPane.heightProperty());
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
        solveMazeButton.setDisable(false);
        saveMazeButton.setDisable(false);
        mazeDisplayer.drawMaze(viewModel.getMaze());
        Position playerPosition = new Position(viewModel.getPlayerRow(), viewModel.getPlayerCol());
        isFinish = false;
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
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    public void solveMaze(ActionEvent actionEvent) throws FileNotFoundException {
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    public void fileNewPressed(ActionEvent actionEvent) {
        viewModel.generateMaze(10,10);
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
        double zoomfactor=1;
        if (event.isControlDown()) {
            if (event.getDeltaY() > 0) {
                zoomfactor = 1.4;
            }
            else{
                zoomfactor = 0.6;
            }
            if(mazeDisplayer.getScaleX()*zoomfactor < 0.9){
                CenterCanvas();
            }
            else
            {
                double factor = (mazeDisplayer.getScaleX()*zoomfactor / mazeDisplayer.getScaleX()) - 1;
                double xPos = (event.getSceneX() - (mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getWidth() / 2 + mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getMinX()));
                double yPos = (event.getSceneY() - (mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getHeight() / 2 + mazeDisplayer.localToScene(mazeDisplayer.getBoundsInLocal()).getMinY()));
                timeline.getKeyFrames().clear();
                timeline.getKeyFrames().addAll(
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateXProperty(), mazeDisplayer.getTranslateX() - factor * xPos)),
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.translateYProperty(), mazeDisplayer.getTranslateY() - factor * yPos)),
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleXProperty(), mazeDisplayer.getScaleX()*zoomfactor)),
                        new KeyFrame(Duration.millis(100), new KeyValue(mazeDisplayer.scaleYProperty(), mazeDisplayer.getScaleX()*zoomfactor))
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

    public void movePlayer(KeyEvent keyEvent) {
        if (isFinish == false)
            viewModel.movePlayer(keyEvent);
    }



    public void keyPressed(KeyEvent keyEvent) {
        if(isFinish == false)
            viewModel.movePlayer(keyEvent);
//        catch (IllegalStateException e) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText("Game is over! Create a new game to play");
//            alert.show();
//        }
        keyEvent.consume();
    }



}