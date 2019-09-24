package controller.impl;

import controller.ViewController;
import domain.Maze;
import domain.MazeViewButtons;
import domain.Point;
import service.ViewService;
import service.impl.ViewServiceImpl;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * 迷宫视图控制层
 * 接收视图层的数据,数据传递给service层
 * 接受service返回的数据并返回给视图层
 */
public class ViewControllerImpl implements ViewController {
    /**
     * service对象
     */
    private ViewService viewService = new ViewServiceImpl();

    /**
     * 创建迷宫按钮组
     *
     * @param maze 迷宫对象
     * @return 迷宫按钮组
     */
    @Override
    public MazeViewButtons newMazeViewButtons(Maze maze) {
        return viewService.newMazeViewButtons(maze);
    }

    /**
     * 改变迷宫的起、终点
     *
     * @param mazeViewButtons 迷宫按钮组
     * @param maze            迷宫对象
     * @param source          按钮源
     * @return 异常信息
     */
    @Override
    public String changeStartAndEndPoint(MazeViewButtons mazeViewButtons, Maze maze, Object source) {
        return viewService.changeStartAndEndPoint(mazeViewButtons, maze, (JButton) source);
    }

    /**
     * 动态显示迷宫路径
     *
     * @param mazeViewButtons 迷宫按钮组
     * @param mazePathPoints  组成迷宫路径的点
     */
    @Override
    public void displayPathAnimation(MazeViewButtons mazeViewButtons, Point[] mazePathPoints) {
        viewService.displayPathAnimation(mazeViewButtons, mazePathPoints);
    }

    /**
     * 移除迷宫路径显示
     *
     * @param mazeViewButtons 迷宫按钮组
     * @param mazePathPoints  组成迷宫路径的点
     */
    @Override
    public void removePathAnimation(MazeViewButtons mazeViewButtons, Point[] mazePathPoints) {
        viewService.removePathAnimation(mazeViewButtons, mazePathPoints);
    }

    /**
     * 改变文件位置
     *
     * @param maze 迷宫对象
     */
    @Override
    public void changeFileLocation(Maze maze) {
        viewService.changeFileLocation(maze);
    }

    /**
     * 显示错误信息
     *
     * @param err 错误信息
     */
    @Override
    public void showErrorInformation(String err) {
        viewService.showErrorInformation(err);
    }

    /**
     * 打开迷宫文件
     *
     * @param filePath 文件路径
     * @return 异常信息
     */
    @Override
    public String openMazeFile(String filePath) {
        return viewService.openMazeFile(filePath);
    }

    /**
     * 加载按钮的图片
     *
     * @param mazeViewButtons 迷宫按钮组
     */
    @Override
    public void loadButtonsIcon(MazeViewButtons mazeViewButtons) {
        viewService.loadButtonsIcon(mazeViewButtons);
    }

    /**
     * 显示帮助信息
     */
    @Override
    public void showHelpInformation() {
        viewService.showHelpInformation();
    }

    /**
     * 修改窗口大小
     *
     * @param mainWindow 迷宫窗口对象
     * @param maze       迷宫对象
     * @param screenSize 窗口大小
     */
    @Override
    public void setWindowSize(MainWindow mainWindow, Maze maze, Dimension screenSize) {
        viewService.setWindowSize(mainWindow, maze, screenSize);
    }
}
