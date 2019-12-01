package com.service;

import com.domain.Maze;

/**
 * 迷宫业务层的接口
 * 接收控制层的数据
 * 调用dao层获得数据
 * 返回数据给控制层
 */
public interface MazeDataService {
    /**
     * 获得迷宫的路径数据
     *
     * @param maze 迷宫对象
     * @return 有异常返回null,无异常返回false
     */
    Boolean getPath(Maze maze);

    /**
     * 获得迷宫的原始数据
     *
     * @param maze 迷宫对象
     * @return 有异常返回null,无异常返回false
     */
    Boolean getMazeOriginalData(Maze maze);

}
