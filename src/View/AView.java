package View;

import ViewModel.MyViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

public abstract class AView implements IView {

    protected MyViewModel viewModel;
    protected Scene CurrScene;
    public static MediaPlayer picaSound;
    public static MediaPlayer mediaPlayer;
    static boolean musicPlay = true;
    public Button picaButton;
    static boolean soundPlay = true;


    public void PicaAction(ActionEvent actionEvent) throws InterruptedException {

        String s = "resources/sounds/pikachuSounds_Trim.mp3";
        Media media = new Media(Paths.get(s).toUri().toString());
        picaSound = new MediaPlayer(media);
        picaSound.setVolume(0.8);
        if (soundPlay && musicPlay)
            mediaPlayer.pause();
        if (soundPlay)
            picaSound.play();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                if (musicPlay)
                    mediaPlayer.play();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void menuLoadPressed(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PicachuMaze", "*.pmz");
        fileChooser.getExtensionFilters().add(extFilter);
        //Show save file dialog
        fileChooser.setTitle("Choose Your Picachu File");
        File file = fileChooser.showOpenDialog(CurrScene.getWindow());
        if (file != null) {
            viewModel.loadMaze(file);
        }
    }
    public void muteSound(ActionEvent actionEvent) {
        String s = "resources/sounds/pikachuSounds_Trim.mp3";
        Media media = new Media(Paths.get(s).toUri().toString());
        picaSound = new MediaPlayer(media);
        picaSound.setVolume(0.8);
        if (soundPlay) {
            picaSound.stop();
            soundPlay = false;
        } else {
            soundPlay = true;
        }
    }

    public void muteMusic(ActionEvent actionEvent) {
        if (musicPlay) {
            mediaPlayer.stop();
            musicPlay = false;
        } else {
            musicPlay = true;
            mediaPlayer.play();
        }
    }

    public void ExitApp(ActionEvent actionEvent) {
        if (mediaPlayer.isAutoPlay()) {
            mediaPlayer.pause();
        }
        Stage stage = (Stage) CurrScene.getWindow();
        stage.close();
    }

    public void getAbout(ActionEvent actionEvent) throws IOException {
        Stage stage = NewStage("AboutView.fxml", "About");
        stage.setMinHeight(450);
        stage.setMaxHeight(450);
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

    protected void getFinish(ActionEvent actionEvent) throws IOException {
        if (musicPlay)
            muteMusic(actionEvent);
        Stage videostage = null;
        videostage = new Stage();
        videostage.setTitle("Congratulation");
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("VideoFinishView.fxml"));
        // Pane p = new Pane( 433, 703);
        Parent p = Loader.load();
        Scene s = new Scene(p);
        IView view = Loader.getController();
        view.setViewModel(viewModel);
        videostage.setScene(s);
        //videostage = NewStage("VideoFinishView.fxml","Congratulation");
        videostage.setMinHeight(423);
        videostage.setMaxHeight(423);
        videostage.setMinWidth(700.0);
        videostage.setMaxWidth(700.0);
        videostage.getIcons().add(new Image("./images/congradulation.png"));


        File mediaFile = new File("resources/images/videoFinish.mp4");
        Media media = null;
        try {
            media = new Media(mediaFile.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        assert media != null;
        MediaPlayer mediaVideo = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaVideo);

        //fitting the dimensions of the video
        DoubleProperty mvw = mediaView.fitWidthProperty();
        DoubleProperty mvh = mediaView.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.setPreserveRatio(true);
        Button b = new Button();
        b.setMinHeight(30);
        b.setMinWidth(80);
        b.setStyle("-fx-font-weight: bold;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-background-color: linear-gradient(#c0c0c0, #fc5f5f);\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        b.setText("Close");
        b.setId("closeVideo");
        b.setLayoutY(340);
        b.setLayoutX(575);
        b.setOnAction((e)->{
            CurrScene = b.getScene();
            Stage stage = (Stage) CurrScene.getWindow();
            stage.close();
            try {
                mediaVideo.stop();
                muteMusic(actionEvent);
                NewStage("FinishView.fxml", "Congratulation");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        videostage.setScene(new Scene(new AnchorPane(mediaView,b), 425, 650));
        videostage.show();
        mediaVideo.play();
    }

}
