package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class MazeDisplayer extends Canvas {
    private Maze maze;
    private double cellHeight;
    private  double cellWidth;
    public StringProperty imagePlayer = new SimpleStringProperty();
    public StringProperty imageEnd = new SimpleStringProperty();
    public StringProperty imageSolve = new SimpleStringProperty();
    public StringProperty imageWall = new SimpleStringProperty();
    private int playerRow;
    private int playerCol;
    private Solution sol;


    public String getImagePlayer(){return imagePlayer.get();}
    public String getImageEnd(){return imageEnd.get();}
    public String getImageSolve(){return imageSolve.get();}
    public String getImageWall(){return imageWall.get();}

    public void setImagePlayer(String imagePlayer) {this.imagePlayer.set(imagePlayer);}

    public void setImageEnd(String imageEnd) {this.imageEnd.set(imageEnd);}

    public void setImageSolve(String imageSolve) {this.imageSolve.set(imageSolve);}

    public void setImageWall(String imageWall) {this.imageWall.set(imageWall);}

    public void drawMaze(Maze maze) throws FileNotFoundException {
        this.maze = maze;
        this.sol = null;
        this.playerRow = maze.getStartPosition().getRowIndex();
        this.playerCol = maze.getStartPosition().getColumnIndex();
        draw();
        //drawPlayer(maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex(), getGraphicsContext2D());
    }

    private void drawPlayer(int rowIndex, int columnIndex, GraphicsContext graph) throws FileNotFoundException {
        Image player = new Image(new FileInputStream(getImagePlayer()));
        graph.clearRect((playerCol*cellWidth)+0.1, (playerRow*cellHeight)+0.1, cellWidth-0.2,cellHeight-0.2);
        graph.setGlobalAlpha(0.1);
        graph.setFill(Color.WHITE);
        graph.fillRect((cellWidth*playerCol)+0.15, (cellHeight*playerRow)+0.15, cellWidth-0.2, cellHeight-0.18);
        graph.setGlobalAlpha(1);
        graph.drawImage(player, playerCol*cellWidth, playerRow*cellHeight, cellWidth, cellHeight);
    }
    public void setPlayerPosition(int row, int col) throws FileNotFoundException {
        this.playerRow = row;
        this.playerCol = col;
        draw();
    }

    private void drawOld(GraphicsContext graph) {
        double x = playerCol * cellWidth;
        double y = playerRow * cellHeight;
        graph.setFill(Color.WHITE);
        graph.fillRect(x, y, cellWidth, cellHeight);
    }

    public void draw() throws FileNotFoundException {

        double hightCanvas = getHeight();
        double widthCanvas = getWidth();
        int row = maze.getMap().length;
        int column = maze.getMap()[0].length;
        //cellHeight = hightCanvas/row;
        setCellHeight(hightCanvas/row);
        //cellWidth = widthCanvas/column;
        setCellWidth(widthCanvas/column);
        GraphicsContext graph = getGraphicsContext2D();
        //clear canvas
        graph.clearRect(0, 0, widthCanvas, hightCanvas);
        double height, width;
        Image wall = new Image(new FileInputStream(getImageWall()));
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                width = j*cellWidth;
                height = i*cellHeight;
                if (maze.getMap()[i][j] == 1){
                    graph.drawImage(wall, width, height, cellWidth, cellHeight);
                   // graph.fillRect(width, height, cellHeight, cellHeight);
                }
                else {
                    graph.setFill(Color.LIGHTGOLDENRODYELLOW);
                    graph.fillRect(width, height, cellWidth, cellHeight);
                }
            }


        }
        double heightGoalCell = maze.getGoalPosition().getRowIndex();
        double widthGoalCell = maze.getGoalPosition().getColumnIndex();
        Image Goal = new Image(new FileInputStream(getImageEnd()));
        graph.drawImage(Goal, widthGoalCell*cellWidth, heightGoalCell*cellHeight, cellWidth,cellHeight);
        if (sol != null){
            drawSol(sol);
        }
        drawPlayer(playerRow, playerCol, graph);
    }

    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void movePlayer(int row, int col) throws FileNotFoundException {
        if (maze != null)
            drawPlayer(row, col, getGraphicsContext2D());
    }

    public void drawSol(Solution sol) throws FileNotFoundException {
        List<AState> lst = sol.getSolutionPath();
        int curr = 0;
        AState start = lst.get(curr);
        Image solve = new Image(new FileInputStream(getImageSolve()));
        while (((MazeState)start).getPosition().getRowIndex() != playerRow && ((MazeState)start).getPosition().getColumnIndex() != playerCol) {
            curr++;
            start = lst.get(curr);
        }
        for (int i = curr +1  ; i < lst.size() -1 ; i++) {
            GraphicsContext graph = getGraphicsContext2D();
            graph.drawImage(solve,  ((MazeState)(lst.get(i))).getPosition().getColumnIndex()*cellWidth, ((MazeState)lst.get(i)).getPosition().getRowIndex()*cellHeight, cellWidth, cellHeight);
        }
    }

    public double getCellHeight() {
        return cellHeight;
    }

    public double getCellWidth() {
        return cellWidth;
    }

    public void setCellHeight(double cellHeight) {
        this.cellHeight = cellHeight;
    }

    public void setCellWidth(double cellWidth) {
        this.cellWidth = cellWidth;
    }
}
