package View;

import ViewModel.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController extends AView implements Observer, Initializable {
    public Button startButton;
    public MenuBar menubar1;
    public BorderPane Pane;
    public Menu menufile;
    public Menu optionmenu;


    public void StartGame(ActionEvent actionEvent) {
        Scene GameScene = ((Node) actionEvent.getSource()).getScene();
        openNewScene(GameScene);
    }

    protected void openNewScene(Scene scene) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/GameView.fxml"));
            Parent tableViewParent = fxmlLoader.load();

//            tableViewParent = fxmlLoader.load();

            Stage window = (Stage) scene.getWindow();
            Scene curScene = startButton.getScene();
            Scene tableViewScene = new Scene(tableViewParent, curScene.getWidth(), curScene.getHeight());
            window.setScene(tableViewScene);

            IView view = fxmlLoader.getController();
            view.setViewModel(viewModel);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
        this.CurrScene = startButton.getScene();
    }

    public void fileNewPressed(ActionEvent actionEvent) {
        Scene scene = startButton.getScene();
        openNewScene(scene);
        viewModel.generateMaze(10, 10);
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String s = "resources/sounds/palletsound.mp3";
        Media media = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(200);
        mediaPlayer.setVolume(0.05);
        mediaPlayer.setAutoPlay(true);
    }

    public void ExitAppMyView(ActionEvent actionEvent) {
        CurrScene = startButton.getScene();
        super.ExitApp(actionEvent);
    }

    public void menuClicked(ActionEvent mouseEvent) {

    }

}
