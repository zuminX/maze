package controller;

import domain.Maze;

/**
 * 迷宫控制层的接口
 * <p>
 * 接收视图层的数据,数据传递给service层
 * 接受service返回的数据并返回给视图层
 */
public interface MazeController {

    /**
     * 获得迷宫的路径数据
     *
     * @param maze 迷宫对象
     * @return 异常信息
     */
    String getPathData(Maze maze);

    /**
     * 获得迷宫的原始数据
     *
     * @param maze 迷宫对象
     * @return 异常信息
     */
    String getMazeOriginalData(Maze maze);
}
