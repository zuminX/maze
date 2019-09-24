package service;

import domain.Maze;
import domain.Point;

/**
 * 迷宫业务层的接口
 * 接收控制层controller的数据
 * 调用dao层获得数据
 */
public interface MazeService {
    /**
     * 获得迷宫的路径数据
     *
     * @param maze 迷宫对象
     * @return 异常信息
     */
    String getPath(Maze maze);

    /**
     * 获得迷宫的原始数据
     *
     * @param maze 迷宫对象
     * @return 异常信息
     */
    String getMazeOriginalData(Maze maze);

}
