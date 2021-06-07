package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public abstract class AView implements IView{

    protected MyViewModel viewModel;
    protected Scene CurrScene;
    public static MediaPlayer picaSound;
    public static MediaPlayer mediaPlayer;
    static boolean musicPlay = true;
    public Button picaButton;



    public void PicaAction(ActionEvent actionEvent) {
        if (picaSound == null){
            String s = "resources/sounds/pikachuSounds_Trim.mp4";
            Media media = new Media(Paths.get(s).toUri().toString());
            picaSound = new MediaPlayer(media);
            picaSound.setVolume(0.3);
            picaSound.setAutoPlay(true);
            while(picaSound.isAutoPlay()){}
        }
        else{
            picaSound.setAutoPlay(true);
            while(picaSound.isAutoPlay()){}
        }
        Stage stage = (Stage)picaButton.getScene().getWindow();
        stage.close();
    }

    public void menuNewPressed(ActionEvent actionEvent) {

    }

    public void menuLoadPressed(ActionEvent actionEvent) {

    }

    public void muteSound(ActionEvent actionEvent) {
    }

    public void muteMusic(ActionEvent actionEvent) {
        boolean curr = true;
        if (musicPlay){
            mediaPlayer.pause();
            curr = false;
        }
        else{
            mediaPlayer.play();
        }
        musicPlay = curr;
    }

    public void ExitApp(ActionEvent actionEvent) {
        if (mediaPlayer.isAutoPlay()){
            mediaPlayer.pause();
        }
        Stage stage = (Stage)CurrScene.getWindow();
        stage.close();
    }

    public void getAbout(ActionEvent actionEvent) throws IOException {
        Stage stage = NewStage("AboutView.fxml", "About");
        stage.setMinHeight(397);
        stage.setMaxHeight(397);
        stage.setMinWidth(703);
        stage.setMaxWidth(703);
        stage.getIcons().add(new Image(("./images/picachu.png")));
    }

    public void getHelp(ActionEvent actionEvent) throws IOException {
        Stage stage = NewStage("HelpView.fxml", "Help");
        stage.setMinHeight(436);
        stage.setMaxHeight(436);
        stage.setMinWidth(703);
        stage.setMaxWidth(703);
        stage.getIcons().add(new Image(("./images/picachu.png")));
    }

    public void getProperties(ActionEvent actionEvent) throws IOException {
        Stage stage = NewStage("PropertiesView.fxml", "Get Your Properties");
        stage.setMinHeight(500);
        stage.setMaxHeight(500);
        stage.setMinWidth(955);
        stage.setMaxWidth(955);
        stage.getIcons().add(new Image(("./images/picachu.png")));
    }

    protected Stage NewStage(String pathFXML, String text) throws IOException {
        Stage newWindow = new Stage();
        newWindow.setTitle(text);
        FXMLLoader Loader = new FXMLLoader(getClass().getResource(pathFXML));
        Parent p = Loader.load();
        Scene s = new Scene(p);
        IView view = Loader.getController();
        view.setViewModel(viewModel);
        newWindow.setScene(s);
        newWindow.show();
        return newWindow;
    }

}
