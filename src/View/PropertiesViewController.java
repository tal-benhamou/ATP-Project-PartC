package View;
import Server.Configurations;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class PropertiesViewController extends AView implements IView {
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
        System.out.println(conf.getProperty("mazeSearchingAlgorithm"));
        System.out.println(conf.getProperty("mazeGeneratingAlgorithm"));
        System.out.println(conf.getProperty("threadPoolSize"));
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

}
