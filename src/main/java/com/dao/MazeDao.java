package com.dao;

import java.util.stream.Stream;

/**
 * 迷宫dao层的接口
 * 读取文件数据
 * 返回数据给业务层
 */
public interface MazeDao {
    /**
     * 读取迷宫文件
     *
     * @param lines 文件数据的stream流
     * @return 迷宫文件数据
     */
    int[][] getMazeData(Stream<String> lines) throws Exception;
}
