package service;

import domain.Maze;
import domain.Point;

public interface MazeService {
    String getPath(Maze maze);
    String getPathData(Maze maze);
    String getMazeOriginalData(Maze maze);
    Integer getPointValueByMaze(Point point, Integer[][] grid);
}
