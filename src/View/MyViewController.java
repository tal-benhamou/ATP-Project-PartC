package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MyViewController extends AView implements IView {
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
}
