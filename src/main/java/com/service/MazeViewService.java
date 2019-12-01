package com.service;

import com.domain.Maze;
import com.domain.MazeViewButtons;
import com.domain.Point;
import com.view.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * 处理迷宫页面的业务层的接口
 * 接收控制层的数据
 * 返回数据给控制层
 */
public interface MazeViewService {
    /**
     * 创建迷宫按钮组
     *
     * @param maze 迷宫对象
     * @return 迷宫按钮组
     */
    MazeViewButtons newMazeViewButtons(Maze maze);

    /**
     * 改变迷宫的起、终点
     *
     * @param mazeViewButtons 迷宫按钮组
     * @param maze            迷宫对象
     * @param button          按钮
     */
    void changeStartAndEndPoint(MazeViewButtons mazeViewButtons, Maze maze, JButton button);

    /**
     * 动态显示迷宫路径
     *
     * @param mazeViewButtons 迷宫按钮组
     * @param mazePathPoints  组成迷宫路径的点
     */
    void displayPathAnimation(MazeViewButtons mazeViewButtons, Point[] mazePathPoints);

    /**
     * 移除迷宫路径显示
     *
     * @param mazeViewButtons 迷宫按钮组
     * @param mazePathPoints  组成迷宫路径的点
     */
    void removePathAnimation(MazeViewButtons mazeViewButtons, Point[] mazePathPoints);

    /**
     * 改变文件位置
     *
     * @param maze 迷宫对象
     */
    void changeFileLocation(Maze maze);

    /**
     * 显示错误信息
     *
     * @param err 错误信息
     */
    void showErrorInformation(String err);

    /**
     * 打开迷宫文件
     *
     * @param filePath 文件路径
     */
    void openMazeFile(String filePath);

    /**
     * 加载按钮的图片
     *
     * @param mazeViewButtons 迷宫按钮组
     */
    void loadButtonsIcon(MazeViewButtons mazeViewButtons);

    /**
     * 显示帮助信息
     */
    void showHelpInformation();

    /**
     * 修改窗口大小
     *
     * @param mainWindow 迷宫窗口对象
     * @param maze       迷宫对象
     * @param screenSize 窗口大小
     */
    void setWindowSize(MainWindow mainWindow, Maze maze, Dimension screenSize);
}
