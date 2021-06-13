package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

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
