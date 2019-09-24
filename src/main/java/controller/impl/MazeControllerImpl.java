package controller.impl;

import controller.MazeController;
import domain.Maze;
import service.MazeService;
import service.impl.MazeServiceImpl;

/**
 * 迷宫控制层
 * 接收视图层的数据,数据传递给service层
 * 接受service返回的数据并返回给视图层
 */
public class MazeControllerImpl implements MazeController {
    /**
     * service对象
     */
    private MazeService mazeService = new MazeServiceImpl();

    /**
     * 获得迷宫的路径数据
     *
     * @param maze 迷宫对象
     * @return 异常信息
     */
    @Override
    public String getPathData(Maze maze) {
        return mazeService.getPath(maze);
    }

    /**
     * 获得迷宫的原始数据
     *
     * @param maze 迷宫对象
     * @return 异常信息
     */
    @Override
    public String getMazeOriginalData(Maze maze) {
        return mazeService.getMazeOriginalData(maze);
    }

}
