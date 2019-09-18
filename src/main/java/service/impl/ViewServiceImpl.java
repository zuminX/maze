package service.impl;

import domain.Maze;
import domain.MazeViewButtons;
import domain.Point;
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

public class ViewServiceImpl implements ViewService {

    @Override
    public MazeViewButtons newMazeViewButtons(Maze maze) {
        if (maze == null) {
            return null;
        }

        MazeViewButtons mazeViewButtons = new MazeViewButtons();
        JButton[][] buttons = new JButton[maze.getMazeRow()][];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton[maze.getMazeColumn()];
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                if (maze.isWall(i, j)) {
                    buttons[i][j].setEnabled(false);
                    buttons[i][j].setBackground(new Color(153, 153, 153));
                } else {
                    buttons[i][j].setBackground(Color.white);
                }
                buttons[i][j].setSize(50, 50);
                buttons[i][j].setBorder(null);
            }
        }
        mazeViewButtons.setButtons(buttons);
        return mazeViewButtons;
    }

    @Override
    public String changeStartAndEndPoint(MazeViewButtons mazeViewButtons, Maze maze, JButton button) {
        if (maze == null || mazeViewButtons == null) {
            return "未加载迷宫数据";
        }
        final Point point = mazeViewButtons.findLocationFromButtons(button);

        final Point start = maze.getStart();
        final Point end = maze.getEnd();

        if (point.equals(start)) {
            maze.setStart(null);
            button.setBackground(Color.white);
            return null;
        }
        if (point.equals(end)) {
            maze.setEnd(null);
            button.setBackground(Color.white);
            return null;
        }
        if (start == null) {
            maze.setStart(point);
            button.setBackground(Color.blue);
            return null;
        }
        if (end == null) {
            maze.setEnd(point);
            button.setBackground(Color.blue);
            return null;
        }

        return "迷宫只能设置一个起点和一个终点";
    }

    @Override
    public void displayPathAnimation(MazeViewButtons mazeViewButtons, final Point[] mazePathPoints) {
        final JButton[][] buttons = mazeViewButtons.getButtons();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Point point : mazePathPoints) {
                    buttons[point.getI()][point.getJ()].setBackground(Color.green);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void removePathAnimation(MazeViewButtons mazeViewButtons, final Point[] mazePathPoints) {
        final JButton[][] buttons = mazeViewButtons.getButtons();
        for (Point point : mazePathPoints) {
            buttons[point.getI()][point.getJ()].setBackground(Color.white);
        }
    }

    @Override
    public void changeFileLocation(Maze maze) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser.showDialog(new JLabel(), "选择后缀名为.maze的迷宫文件");
        File file = jFileChooser.getSelectedFile();

        final String pattern = ".*\\.maze";
        if (file == null) {
            return;
        }
        String path = file.getAbsolutePath();
        boolean isMatch = Pattern.matches(pattern, path);

        if (isMatch) {
            maze.setMazeFilePath(path);
        } else {
            showErrorInformation("迷宫数据必须为后缀名为.maze的文件");
        }
    }

    @Override
    public void showErrorInformation(String err) {
        JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public String openMazeFile(String filePath) {
        File file = new File(filePath);
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "迷宫数据文件打开失败";
        }
        return null;
    }

    //TODO 待优化
    @Override
    public void loadButtonsIcon(MazeViewButtons mazeViewButtons) {
        final JButton[][] buttons = mazeViewButtons.getButtons();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream(getClass().getResource("/wall.png").getPath()));
        } catch (IOException e) {
            e.printStackTrace();
            showErrorInformation("加载图片失败");
        }

        final Image wallImage = image.getScaledInstance(buttons[0][0].getWidth(), buttons[0][0].getHeight(), Image.SCALE_DEFAULT);
        for (JButton[] button : buttons) {
            for (JButton b : button) {
                if (!b.isEnabled()) {
                    b.setIcon(new ImageIcon(wallImage));
                }
            }
        }
    }

    @Override
    public void showHelpInformation() {
        String information = "1.此程序可以在矩形迷宫寻找从起点到终点的出路;\n"
                + "2.你可以修改迷宫文件的位置，文件的后缀名必须为.maze且文件必须可读;\n"
                + "3.你可以修改迷宫文件的数据，迷宫数据应为一个数字矩形，其中0代表可以无障碍的通路，-1代表不能行走的墙。每个数字必须用空格分开、每行数据必须隔行显示;\n"
                + "4.点击加载数据将会读取迷宫文件的数据并进行显示;\n"
                + "5.你可以点击空路进行设置/取消迷宫起点和终点，然后点击最短路径对迷宫进行路径求解。";
        JOptionPane.showMessageDialog(null, information, "帮助", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setWindowSize(MainWindow mainWindow, Maze maze, Dimension screenSize) {
        int width = Math.min(maze.getMazeColumn() * 50, (int) (screenSize.getWidth() / 1.5));
        int height = Math.min(maze.getMazeRow() * 50, (int) (screenSize.getHeight() / 1.5));
        mainWindow.setSize(width, height);
    }
}
