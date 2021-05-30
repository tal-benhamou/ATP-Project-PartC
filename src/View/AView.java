package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.media.AudioClip;

public abstract class AView implements IView{

    protected MyViewModel viewModel;
    static protected AudioClip Melody;
    static boolean muteMusic = false;
    static boolean muteSound = false;

    public void PicaAction(ActionEvent actionEvent) {

    }
    public void menuNewPressed(ActionEvent actionEvent) {

    }

    public void menuLoadPressed(ActionEvent actionEvent) {

    }

    public void muteSound(ActionEvent actionEvent) {
    }

    public void muteMusic(ActionEvent actionEvent) {
    }

    public void ExitApp(ActionEvent actionEvent) {
    }

    public void getHelp(ActionEvent actionEvent) {
    }

    public void getAbout(ActionEvent actionEvent) {
    }
    public void getProperties(ActionEvent actionEvent) {
    }
}
