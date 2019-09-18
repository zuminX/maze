package controller;

import domain.Maze;
import domain.MazeViewButtons;
import domain.Point;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;

public interface ViewController {
    MazeViewButtons newMazeViewButtons(Maze maze);

    String changeStartAndEndPoint(MazeViewButtons mazeViewButtons, Maze maze, Object source);

    void displayPathAnimation(MazeViewButtons mazeViewButtons, final Point[] mazePathPoints);

    void removePathAnimation(MazeViewButtons mazeViewButtons, Point[] mazePathPoints);

    void changeFileLocation(Maze maze);

    void showErrorInformation(String err);

    String openMazeFile(String filePath);

    void loadButtonsIcon(MazeViewButtons mazeViewButtons);

    void showHelpInformation();

    void setWindowSize(MainWindow mainWindow, Maze maze, Dimension screenSize);
}
