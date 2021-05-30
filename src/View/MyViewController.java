package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController extends AView implements Observer, Initializable {
    public Button startButton;


    public void StartGame(ActionEvent actionEvent) {
        Scene GameScene = ((Node) actionEvent.getSource()).getScene();
        openNewScene(GameScene);
    }

    protected IView openNewScene(Scene scene) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/GameView.fxml"));
            Parent tableViewParent = null;

            tableViewParent = fxmlLoader.load();

            Stage window = (Stage) scene.getWindow();
            Scene curScene = startButton.getScene();
            Scene tableViewScene = new Scene(tableViewParent, curScene.getWidth(), curScene.getHeight());
            window.setScene(tableViewScene);

            IView view = fxmlLoader.getController();
            view.setViewModel(viewModel);
            window.show();
            return view;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setViewModel(MyViewModel viewModel) {

    }

    public void fileNewPressed(ActionEvent actionEvent) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Melody = new AudioClip(Paths.get("C:\\Users\\Ido\\IdeaProjects\\ATP-Project-PartC\\resources\\sounds\\palletsound.mp3").toUri().toString());
//        Melody.setCycleCount(200);
//        Melody.setVolume(0.2);
//        if(!muteMusic)
//            Melody.play();
    }
}
