package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.File;
import java.util.Observer;

public interface IModel  {
    void generateMaze(int row, int col);
    Maze getMaze();
    void solveMaze();
    Solution getSolution();
    void updatePlayerLocation(int direction);
    int getPlayerRow();
    int getPlayerCol();
    void saveMaze(File file);
    void loadMaze(File file);
    String[] getProperties();
    void close();
    void assignObserver(Observer o);
}
