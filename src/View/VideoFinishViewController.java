package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class VideoFinishViewController extends AView implements IView{

    public Button closeVideo;

    @Override
    public void setViewModel(MyViewModel viewModel) {

    }

    public void CloseVideo(ActionEvent actionEvent) throws IOException {
        CurrScene = closeVideo.getScene();
        Stage stage = (Stage) CurrScene.getWindow();
        stage.close();
        NewStage("FinishView.fxml", "Congratulation");
    }
}
