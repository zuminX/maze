package com.controller.impl;

import com.controller.MazeController;
import com.domain.Maze;
import com.domain.MazeViewButtons;
import com.domain.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.service.MazeService;
import com.service.ViewService;
import com.view.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * 控制层
 * 接收视图层的数据,数据传递给service层
 * 接受service返回的数据并返回给视图层
 */
@Controller("mazeController")
@SuppressWarnings("all")
public class MazeControllerImpl implements MazeController {
    @Autowired
    private ViewService viewService;

    @Autowired
    private MazeService mazeService;

    /**
     * 获得迷宫的路径数据
     *
     * @param maze 迷宫对象
     *
     * @return 有异常->true 无异常->false
     */
    @Override
    public boolean getPathData(Maze maze) {
        return mazeService.getPath(maze);
    }

    /**
     * 获得迷宫的原始数据
     *
     * @param maze 迷宫对象
     *
     * @return 有异常->true 无异常->false
     */
    @Override
    public boolean getMazeOriginalData(Maze maze) {
        return mazeService.getMazeOriginalData(maze);
    }

    /**
     * 创建迷宫按钮组
     *
     * @param maze 迷宫对象
     *
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
     *
     */
    @Override
    public void changeStartAndEndPoint(MazeViewButtons mazeViewButtons, Maze maze, Object source) {
        viewService.changeStartAndEndPoint(mazeViewButtons, maze, (JButton) source);
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
     *
     */
    @Override
    public void openMazeFile(String filePath) {
        viewService.openMazeFile(filePath);
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
