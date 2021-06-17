package View;

import Server.Configurations;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class PropertiesViewController extends AView implements IView, Initializable {
    public MenuButton SolveAlg;
    public TextField NumThreads;
    public Button LetsGoButton;
    public MenuButton GenerateAlg;

    @Override
    public void setViewModel(MyViewModel viewModel) {

    }

    public void GoToGameView(ActionEvent actionEvent) {
        Configurations conf = Configurations.Instance();

        switch (SolveAlg.getText()) {
            case "Best First Search" -> conf.setSolveAlg("BEST");
            case "Depth First Search" -> conf.setSolveAlg("DFS");
            case "Breadth First Search" -> conf.setSolveAlg("BFS");
        }
        switch (GenerateAlg.getText()) {
            case "Empty Generator" -> conf.setGenerateAlg("Empty");
            case "My Generator" -> conf.setGenerateAlg("My");
            case "Simple Generator" -> conf.setGenerateAlg("Simple");
        }

        conf.setNumThreads(NumThreads.getText());
        Stage stage = (Stage) LetsGoButton.getScene().getWindow();
        stage.close();
    }


    public void mygen(ActionEvent actionEvent) {
        GenerateAlg.setText("My Generator");
    }

    public void simplegen(ActionEvent actionEvent) {
        GenerateAlg.setText("Simple Generator");
    }

    public void emptygen(ActionEvent actionEvent) {
        GenerateAlg.setText("Empty Generator");
    }

    public void bestsearch(ActionEvent actionEvent) {
        SolveAlg.setText("Best First Search");
    }

    public void breadthsearch(ActionEvent actionEvent) {
        SolveAlg.setText("Breadth First Search");
    }

    public void dephtsearch(ActionEvent actionEvent) {
        SolveAlg.setText("Depth First Search");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Configurations conf = Configurations.Instance();
        switch (conf.getProperty("mazeGeneratingAlgorithm")){
            case "My" -> GenerateAlg.setText("My Generator");
            case "Empty" -> GenerateAlg.setText("Empty Generator");
            case "Simple" -> GenerateAlg.setText("Simple Generator");
        }

        switch (conf.getProperty("mazeSearchingAlgorithm")){
            case "BEST" -> SolveAlg.setText("Best First Search");
            case "DFS" -> SolveAlg.setText("Depth First Search");
            case "BFS" -> SolveAlg.setText("Breadth First Search");
        }

        NumThreads.setText(conf.getProperty("threadPoolSize"));
    }
}
