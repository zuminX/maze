package controller;

import domain.Maze;

public interface MazeController {
    String getPathData(Maze maze);
    String getMazeOriginalData(Maze maze);
}
