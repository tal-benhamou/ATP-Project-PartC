package ViewModel;

import Model.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private final IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this);
    }

    public void generateMaze(int row, int col){
        model.generateMaze(row, col);
    }

    public void solveMaze(){
        model.solveMaze();
    }

    public Maze getMaze(){
        return model.getMaze();
    }

    public Solution getSolution(){
        return model.getSolution();
    }
    public int getPlayerRow(){
        return model.getPlayerRow();
    }
    public int getPlayerCol(){
        return model.getPlayerCol();
    }

    public void movePlayer(KeyEvent keyEvent){
        int direction = -1;
        switch (keyEvent.getCode()){
            case DIGIT1:
            case NUMPAD1:
                direction = 1;
                break;
            case DIGIT2:
            case NUMPAD2:
                direction = 2;
                break;
            case DIGIT3:
            case NUMPAD3:
                direction = 3;
                break;
            case DIGIT4:
            case NUMPAD4:
                direction = 4;
                break;
            case DIGIT6:
            case NUMPAD6:
                direction = 6;
                break;
            case DIGIT7:
            case NUMPAD7:
                direction = 7;
                break;
            case DIGIT8:
            case NUMPAD8:
                direction = 8;
                break;
            case DIGIT9:
            case NUMPAD9:
                direction = 9;
                break;
        }
        model.updatePlayerLocation(direction);
    }
    public void saveMaze(File file){
        model.saveMaze(file);
    }
    public void loadMaze(File file) throws FileNotFoundException {
        model.loadMaze(file);
    }
    public String[] getProperties(){
        return model.getProperties();
    }
    public void close(){
        model.close();
    }
    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public void isValidDragg(int i, int j) {
        int rowMove = i - getPlayerRow();
        int columnMove = j - getPlayerCol();
        if (rowMove == 1 && columnMove == -1)
            model.updatePlayerLocation(1);
        else if (rowMove == 1 && columnMove == 0)
            model.updatePlayerLocation(2);
        else if (rowMove == 1 && columnMove == 1)
            model.updatePlayerLocation(3);
        else if (rowMove == 0 && columnMove == -1)
            model.updatePlayerLocation(4);
        else if (rowMove == 0 && columnMove == 1)
            model.updatePlayerLocation(6);
        else if (rowMove == -1 && columnMove == -1)
            model.updatePlayerLocation(7);
        else if (rowMove == -1 && columnMove == 0)
            model.updatePlayerLocation(8);
        else if (rowMove == -1 && columnMove == 1)
            model.updatePlayerLocation(9);
    }
}
