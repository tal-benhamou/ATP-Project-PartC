package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("./View/MyView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("The Pokemon Game");
        primaryStage.getIcons().add(new Image("./images/picachu.png"));
        primaryStage.setScene(new Scene(root, 955, 900));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
