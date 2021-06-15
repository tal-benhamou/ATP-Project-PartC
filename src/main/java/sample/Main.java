package sample;

import Model.IModel;
import Model.MyModel;
import View.IView;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    MyViewModel viewModel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("./View/GameViewGrid.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MyView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("The Pokemon Game");
        primaryStage.getIcons().add(new Image("./View/resources/picachu.png"));
        primaryStage.setScene(new Scene(root, 755, 800));
        primaryStage.show();
        IView my = fxmlLoader.getController();
        IModel model = new MyModel();
        viewModel = new MyViewModel(model);
        //MyViewController my1 = fxmlLoader.getController();
        //GameViewGridController game = fxmlLoader.getController();
        my.setViewModel(viewModel);
        //game.setViewModel(viewModel);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        viewModel.close();
        super.stop();
    }
}
