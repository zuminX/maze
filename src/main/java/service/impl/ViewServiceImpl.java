package service.impl;

import domain.Maze;
import domain.MazeViewButtons;
import domain.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ViewService;
import view.MainWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 视图业务层
 * 接收控制层controller的数据
 * 返回数据给controller层
 */

public class ViewServiceImpl implements ViewService {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewServiceImpl.class);

    /**
     * 创建迷宫按钮组
     *
     * @param maze 迷宫对象
     * @return 迷宫按钮组
     */
    @Override
    public MazeViewButtons newMazeViewButtons(Maze maze) {
        //迷宫的原始数据必须存在
        if (maze == null || maze.getMazeOriginalData() == null) {
            return null;
        }

        MazeViewButtons mazeViewButtons = new MazeViewButtons();
        JButton[][] buttons = new JButton[maze.getMazeRow()][];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton[maze.getMazeColumn()];
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                //若该位置为墙，则设置不可用且颜色为灰色
                if (maze.isWall(i, j)) {
                    buttons[i][j].setEnabled(false);
                    buttons[i][j].setBackground(new Color(153, 153, 153));
                } else {
                    //否则设置颜色为白色
                    buttons[i][j].setBackground(Color.white);
                }
                //设置按钮的初始大小
                buttons[i][j].setSize(50, 50);
                buttons[i][j].setBorder(null);
            }
        }
        mazeViewButtons.setButtons(buttons);
        return mazeViewButtons;
    }

    /**
     * 改变迷宫的起、终点
     *
     * @param mazeViewButtons 迷宫按钮组
     * @param maze            迷宫对象
     * @param button          按钮
     * @return 异常信息
     */
    @Override
    public String changeStartAndEndPoint(MazeViewButtons mazeViewButtons, Maze maze, JButton button) {
        //确保数据不为空
        if (maze == null || mazeViewButtons == null) {
            return "未加载迷宫数据";
        }
        //寻找该点在迷宫按钮组的位置
        final Point point = mazeViewButtons.findLocationFromButtons(button);

        final Point start = maze.getStart();
        final Point end = maze.getEnd();

        //取消起点
        if (point.equals(start)) {
            maze.setStart(null);
            button.setBackground(Color.white);
            return null;
        }
        //取消终点
        if (point.equals(end)) {
            maze.setEnd(null);
            button.setBackground(Color.white);
            return null;
        }
        //设置起点
        if (start == null) {
            maze.setStart(point);
            button.setBackground(Color.blue);
            return null;
        }
        //设置终点
        if (end == null) {
            maze.setEnd(point);
            button.setBackground(Color.blue);
            return null;
        }

        return "迷宫只能设置一个起点和一个终点";
    }

    /**
     * 动态显示迷宫路径
     *
     * @param mazeViewButtons 迷宫按钮组
     * @param mazePathPoints  组成迷宫路径的点
     */
    @Override
    public void displayPathAnimation(MazeViewButtons mazeViewButtons, final Point[] mazePathPoints) {
        final JButton[][] buttons = mazeViewButtons.getButtons();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //每隔0.1s显示路径上的一个点
                for (Point point : mazePathPoints) {
                    buttons[point.getI()][point.getJ()].setBackground(Color.green);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        LOGGER.error("", e);
                    }
                }
            }
        }).start();
    }

    /**
     * 移除迷宫路径显示
     *
     * @param mazeViewButtons 迷宫按钮组
     * @param mazePathPoints  组成迷宫路径的点
     */
    @Override
    public void removePathAnimation(MazeViewButtons mazeViewButtons, final Point[] mazePathPoints) {
        final JButton[][] buttons = mazeViewButtons.getButtons();
        for (Point point : mazePathPoints) {
            buttons[point.getI()][point.getJ()].setBackground(Color.white);
        }
    }

    /**
     * 改变文件位置
     *
     * @param maze 迷宫对象
     */
    @Override
    public void changeFileLocation(Maze maze) {
        //创建文件选择器
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.showDialog(new JLabel(), "选择后缀名为.maze的迷宫文件");

        File file = jFileChooser.getSelectedFile();
        if (file == null) {
            return;
        }

        //文件的后缀必须为.maze
        final String pattern = ".*\\.maze";
        String path = file.getAbsolutePath();
        boolean isMatch = Pattern.matches(pattern, path);

        if (isMatch) {
            maze.setMazeFilePath(path);
        } else {
            showErrorInformation("迷宫数据必须为后缀名为.maze的文件");
        }
    }

    /**
     * 显示错误信息
     *
     * @param err 错误信息
     */
    @Override
    public void showErrorInformation(String err) {
        JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 打开迷宫文件
     *
     * @param filePath 文件路径
     * @return 异常信息
     */
    @Override
    public String openMazeFile(String filePath) {
        File file = new File(filePath);
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            LOGGER.error("", e);
            return "迷宫数据文件打开失败";
        }
        return null;
    }

    /**
     * 加载按钮的图片
     *
     * @param mazeViewButtons 迷宫按钮组
     */
    @Override
    //TODO 待优化
    public void loadButtonsIcon(MazeViewButtons mazeViewButtons) {
        final JButton[][] buttons = mazeViewButtons.getButtons();
        BufferedImage image = null;
        //读取迷宫墙的图片
        try {
            image = ImageIO.read(new FileInputStream(getClass().getResource("/wall.png").getPath()));
        } catch (IOException e) {
            LOGGER.error("", e);
            showErrorInformation("加载图片失败");
        }

        //根据按钮的大小设置图片的大小
        final Image wallImage = image.getScaledInstance(buttons[0][0].getWidth(), buttons[0][0].getHeight(), Image.SCALE_DEFAULT);
        for (JButton[] button : buttons) {
            for (JButton b : button) {
                //为迷宫墙添加图片
                if (!b.isEnabled()) {
                    b.setIcon(new ImageIcon(wallImage));
                }
            }
        }
    }

    /**
     * 显示帮助信息
     */
    @Override
    public void showHelpInformation() {
        String information = "1.此程序可以在矩形迷宫寻找从起点到终点的出路;\n" + "2.你可以修改迷宫文件的位置，文件的后缀名必须为.maze且文件必须可读;\n" +
                             "3.你可以修改迷宫文件的数据，迷宫数据应为一个数字矩形，其中0代表可以无障碍的通路，-1代表不能行走的墙。每个数字必须用空格分开、每行数据必须隔行显示;\n" + "4.点击加载数据将会读取迷宫文件的数据并进行显示;\n" +
                             "5.你可以点击空路进行设置/取消迷宫起点和终点，然后点击最短路径对迷宫进行路径求解。";
        JOptionPane.showMessageDialog(null, information, "帮助", JOptionPane.INFORMATION_MESSAGE);
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
        //设置迷宫窗口的大小随迷宫的大小的变化而改变
        //限制迷宫窗口过大
        int width = Math.min(maze.getMazeColumn() * 50, (int) (screenSize.getWidth() / 1.5));
        int height = Math.min(maze.getMazeRow() * 50, (int) (screenSize.getHeight() / 1.5));
        mainWindow.setSize(width, height);
    }
}
