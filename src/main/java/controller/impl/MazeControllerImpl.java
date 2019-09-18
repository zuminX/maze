package controller.impl;

import controller.MazeController;
import domain.Maze;
import service.MazeService;
import service.impl.MazeServiceImpl;

public class MazeControllerImpl implements MazeController {
    private MazeService mazeService = new MazeServiceImpl();

    public String getPathData(Maze maze) {
        return mazeService.getPath(maze);
    }

    public String getMazeOriginalData(Maze maze) {
        return mazeService.getMazeOriginalData(maze);
    }

}
