package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

public class MyModel extends Observable implements IModel {
    private Maze maze;
    private Solution solution;
    private int playerRow;
    private int playerCol;
    private Server serverGenerator;
    private Server serverSolver;

    public MyModel() {
        serverGenerator = new Server(5400,1000, new ServerStrategyGenerateMaze());
        serverSolver = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        serverGenerator.start();
        serverSolver.start();
    }


    @Override
    public void generateMaze(int row, int col) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[])((byte[])fromServer.readObject());
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[row*col+7];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }
    playerRow = maze.getStartPosition().getRowIndex();
    playerCol = maze.getStartPosition().getColumnIndex();
    setChanged();
    notifyObservers("Maze Generated");
    }
        @Override
    public Maze getMaze() {
        return maze;
    }


    @Override
    public void solveMaze() {
        Maze solvedMaze = new Maze(maze.toByteArray());
        solvedMaze.setStart(new Position(playerRow, playerCol));
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze);
                        toServer.flush();
                        solution = (Solution)fromServer.readObject();
                        ArrayList<AState> mazeSolutionSteps = solution.getSolutionPath();

                        for(int i = 0; i < mazeSolutionSteps.size(); ++i) {
                            System.out.println(String.format("%s. %s", i, ((AState)mazeSolutionSteps.get(i)).toString()));
                        }
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }
    setChanged();
    notifyObservers("Maze Solution");
    }

    @Override
    public Solution getSolution() {
        return solution;
    }

    @Override
    public void updatePlayerLocation(int direction) {
        if (direction == -1){
            return;
        }
        int sizeRowMaze = maze.getMap().length - 1;
        int sizeColMaze = maze.getMap()[0].length - 1;
        switch (direction){
            case 1:
                if (playerRow < sizeRowMaze && playerCol > 0){
                    playerRow++;
                    playerCol--;
                    break;
                }
            case 2:
                if (playerRow < sizeRowMaze) {
                    playerRow++;
                    break;
                }
            case 3:
                if (playerRow < sizeRowMaze && playerCol < sizeColMaze){
                    playerRow++;
                    playerCol++;
                    break;
                }
            case 4:
                if (playerCol > 0) {
                    playerCol--;
                    break;
                }
            case 6:
                if (playerCol < sizeColMaze) {
                    playerCol++;
                    break;
                }
            case 7:
                if (playerRow > 0 && playerCol > 0){
                    playerRow--;
                    playerCol--;
                    break;
                }
            case 8:
                if (playerRow > 0) {
                    playerRow--;
                    break;
                }
            case 9:
                if (playerRow > 0 && playerCol < sizeColMaze){
                    playerRow--;
                    playerCol++;
                    break;
                }
        }
        if (playerRow == maze.getGoalPosition().getRowIndex() && playerCol == maze.getGoalPosition().getColumnIndex()){
            setChanged();
            notifyObservers("Get Goal");
            return;
        }
        setChanged();
        notifyObservers("Player Moved");
    }

    @Override
    public int getPlayerRow() {
        return playerRow;
    }

    @Override
    public int getPlayerCol() {
        return playerCol;
    }

    @Override
    public void saveMaze(File file) {

    }

    @Override
    public void loadMaze(File file) {

    }


    @Override
    public String[] getProperties() {
        String[] getProper = new String[3];
        Configurations conf = Configurations.Instance();
        getProper[0] = conf.getProperty("mazeSearchingAlgorithm");
        getProper[1] = conf.getProperty("mazeGeneratingAlgorithm");
        getProper[2] = conf.getProperty("threadPoolSize");
        return  getProper;
    }

    @Override
    public void close() {
        serverGenerator.stop();
        serverSolver.stop();
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }
}
