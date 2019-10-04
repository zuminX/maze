package controller.impl;

import controller.MazeController;
import domain.Maze;
import domain.MazeViewButtons;
import domain.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.MazeService;
import service.ViewService;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * 控制层
 * 接收视图层的数据,数据传递给service层
 * 接受service返回的数据并返回给视图层
 */
@Controller("mazeController")
public class MazeControllerImpl implements MazeController {
    /**
     * ViewService对象
     */
    @Autowired
    private ViewService viewService;

    /**
     * MazeService对象
     */
    @Autowired
    private MazeService mazeService;

    /**
     * 获得迷宫的路径数据
     *
     * @param maze 迷宫对象
     * @return 有异常->true 无异常->false
     */
    @Override
    public boolean getPathData(Maze maze) {
        final String err = mazeService.getPath(maze);
        if (err != null) {
            viewService.showErrorInformation(err);
            return true;
        }
        return false;
    }

    /**
     * 获得迷宫的原始数据
     *
     * @param maze 迷宫对象
     * @return 有异常->true 无异常->false
     */
    @Override
    public boolean getMazeOriginalData(Maze maze) {
        final String err = mazeService.getMazeOriginalData(maze);
        if (err != null) {
            viewService.showErrorInformation(err);
            return true;
        }
        return false;
    }

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
     * @return 有异常->true 无异常->false
     */
    @Override
    public boolean changeStartAndEndPoint(MazeViewButtons mazeViewButtons, Maze maze, Object source) {
        final String err = viewService.changeStartAndEndPoint(mazeViewButtons, maze, (JButton) source);
        if (err != null) {
            viewService.showErrorInformation(err);
            return true;
        }
        return false;
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

    @Override
    public void showErrorInformation(String err) {
        viewService.showErrorInformation(err);
    }

    /**
     * 显示帮助信息
     */
    @Override
    public void showHelpInformation() {
        viewService.showHelpInformation();
    }

    /**
     * 打开迷宫文件
     *
     * @param filePath 文件路径
     * @return 有异常->true 无异常->false
     */
    @Override
    public boolean openMazeFile(String filePath) {
        final String err = viewService.openMazeFile(filePath);
        if (err != null) {
            viewService.showErrorInformation(err);
            return true;
        }
        return false;
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
