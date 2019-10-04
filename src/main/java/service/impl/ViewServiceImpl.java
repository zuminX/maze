package service.impl;

import domain.Maze;
import domain.MazeViewButtons;
import domain.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import service.ViewService;
import utils.BaseHolder;
import utils.Information;
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
@Service("viewService")
@PropertySource(value = "classpath:mazeConfig.properties", encoding = "utf-8")
public class ViewServiceImpl implements ViewService {
    /**
     * 文件的后缀必须为.maze
     */
    @Value("${mazePattern}")
    private String pattern;

    /**
     * 创建迷宫按钮组
     *
     * @param maze 迷宫对象
     *
     * @return 迷宫按钮组
     */
    @Override
    public MazeViewButtons newMazeViewButtons(Maze maze) {
        //迷宫的原始数据必须存在
        if (maze == null || maze.getMazeOriginalData() == null) {
            return null;
        }

        MazeViewButtons mazeViewButtons = BaseHolder.getBean("mazeViewButtons", MazeViewButtons.class);
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
     *
     */
    @Override
    public void changeStartAndEndPoint(MazeViewButtons mazeViewButtons, Maze maze, JButton button) {
        //确保数据不为空
        if (maze == null || mazeViewButtons == null) {
            throw new RuntimeException(Information.missingMazeData);
        }
        //寻找该点在迷宫按钮组的位置
        final Point point = mazeViewButtons.findLocationFromButtons(button);

        final Point start = maze.getStart();
        final Point end = maze.getEnd();

        if (point.equals(start)) {
            //取消起点
            maze.setStart(null);
            button.setBackground(Color.white);
        } else if (point.equals(end)) {
            //取消终点
            maze.setEnd(null);
            button.setBackground(Color.white);
        } else if (start == null) {
            //设置起点
            maze.setStart(point);
            button.setBackground(Color.blue);
        } else if (end == null) {
            //设置终点
            maze.setEnd(point);
            button.setBackground(Color.blue);
        } else {
            //若为其他情况，则抛出异常
            throw new RuntimeException(Information.StartAndEndPointsOnlyOne);
        }
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

        new Thread(() -> {
            //每隔0.1s显示路径上的一个点
            for (Point point : mazePathPoints) {
                buttons[point.getI()][point.getJ()].setBackground(Color.green);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //异常中断时，抛出异常
                    throw new RuntimeException(Information.showPathError);
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
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.showDialog(new JLabel(), Information.selectMazeFile);

        File file = jFileChooser.getSelectedFile();
        //没有选择文件，则不执行检验操作
        if (file == null) {
            return;
        }

        //获取绝对路径
        String path = file.getAbsolutePath();
        boolean isMatch = Pattern.matches(pattern, path);

        if (isMatch) {
            maze.setMazeFilePath(path);
        } else {
            //不匹配则抛出异常
            throw new RuntimeException(Information.selectMazeFileNoMatch);
        }
    }

    /**
     * 显示错误信息
     */
    @Override
    public void showErrorInformation(String err) {
        JOptionPane.showMessageDialog(null, err, "错误", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 打开迷宫文件
     *
     * @param filePath 文件路径
     *
     */
    @Override
    public void openMazeFile(String filePath) {
        File file = new File(filePath);
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            throw new RuntimeException(Information.openMazeFileError);
        }
    }

    /**
     * 加载按钮的图片
     *
     * @param mazeViewButtons 迷宫按钮组
     */
    @Override
    public void loadButtonsIcon(MazeViewButtons mazeViewButtons) {
        final JButton[][] buttons = mazeViewButtons.getButtons();
        BufferedImage image = null;
        //读取迷宫墙的图片
        try {
            image = ImageIO.read(new FileInputStream(getClass().getResource("/wall.png").getPath()));
        } catch (IOException e) {
            throw new RuntimeException(Information.loadingImageError);
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
        JOptionPane.showMessageDialog(null, Information.mazeHelpInformation, "帮助", JOptionPane.INFORMATION_MESSAGE);
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
