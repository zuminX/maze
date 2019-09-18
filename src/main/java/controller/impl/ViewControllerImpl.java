package controller.impl;

import controller.ViewController;
import domain.Maze;
import domain.MazeViewButtons;
import domain.Point;
import service.ViewService;
import service.impl.ViewServiceImpl;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;

public class ViewControllerImpl implements ViewController {
    private ViewService viewService = new ViewServiceImpl();

    @Override
    public MazeViewButtons newMazeViewButtons(Maze maze) {
        return viewService.newMazeViewButtons(maze);
    }

    @Override
    public String changeStartAndEndPoint(MazeViewButtons mazeViewButtons, Maze maze, Object source) {
        return viewService.changeStartAndEndPoint(mazeViewButtons, maze, (JButton) source);
    }

    @Override
    public void displayPathAnimation(MazeViewButtons mazeViewButtons, Point[] mazePathPoints) {
        viewService.displayPathAnimation(mazeViewButtons, mazePathPoints);
    }

    @Override
    public void removePathAnimation(MazeViewButtons mazeViewButtons, Point[] mazePathPoints) {
        viewService.removePathAnimation(mazeViewButtons, mazePathPoints);
    }

    @Override
    public void changeFileLocation(Maze maze) {
        viewService.changeFileLocation(maze);
    }

    @Override
    public void showErrorInformation(String err) {
        viewService.showErrorInformation(err);
    }

    @Override
    public String openMazeFile(String filePath) {
        return viewService.openMazeFile(filePath);
    }

    @Override
    public void loadButtonsIcon(MazeViewButtons mazeViewButtons) {
        viewService.loadButtonsIcon(mazeViewButtons);
    }

    @Override
    public void showHelpInformation() {
        viewService.showHelpInformation();
    }

    @Override
    public void setWindowSize(MainWindow mainWindow, Maze maze, Dimension screenSize) {
        viewService.setWindowSize(mainWindow, maze, screenSize);
    }
}
