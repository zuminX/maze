package dao;

import java.io.BufferedReader;

/**
 * dao层的接口
 * 读取数据
 */
public interface MazeDao {
    /**
     * 读取迷宫文件
     *
     * @param br 带缓冲的字符输入流
     *
     * @return 迷宫文件数据
     *
     * @throws Exception 异常
     */
    int[][] getMazeData(BufferedReader br) throws Exception;
}
