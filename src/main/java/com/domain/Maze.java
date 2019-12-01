package com.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 迷宫对象
 */
@Component()
@Scope("prototype")
@Getter
@Setter
public class Maze {
    /**
     * 迷宫原始数据
     */
    private int[][] mazeOriginalData;
    /**
     * 迷宫步数数据
     */
    private int[][] mazeStepData;
    /**
     * 组成迷宫路径的点
     */
    private Point[] mazePathPoints;
    /**
     * 迷宫文件路径
     */
    private String mazeFilePath;
    /**
     * 迷宫的行
     */
    private int mazeRow;
    /**
     * 迷宫的列
     */
    private int mazeColumn;
    /**
     * 迷宫的起点
     */
    private Point start;
    /**
     * 迷宫的终点
     */
    private Point end;

    /**
     * Maze的无参构造方法
     */
    public Maze() {
        mazeFilePath = Maze.class.getResource("/mazeData.maze").getPath();
    }

    /**
     * 清除起点、终点，迷宫步骤数据、组成迷宫路径点的数据
     */
    public void cleanData() {
        setStart(null);
        setEnd(null);
        setMazeStepData(null);
        setMazePathPoints(null);
    }

    /**
     * 判断此位置是否为墙
     *
     * @param row    行
     * @param column 列
     * @return 是墙返回true，不是墙返回false
     */
    public boolean isWall(int row, int column) {
        return mazeOriginalData[row][column] == -1;
    }

}
