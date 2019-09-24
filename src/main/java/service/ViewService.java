package service;

import domain.Maze;
import domain.MazeViewButtons;
import domain.Point;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * 视图业务层的接口
 * 接收控制层controller的数据
 */
public interface ViewService {
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
     * @return 异常信息
     */
    String changeStartAndEndPoint(MazeViewButtons mazeViewButtons, Maze maze, JButton button);

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
     * @return 异常信息
     */
    String openMazeFile(String filePath);

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
