package view;

import controller.MazeController;
import domain.Maze;
import domain.MazeViewButtons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.BaseHolder;
import utils.SkipLoad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;

/**
 * 视图层，进行程序的显示和与用户交互的界面
 * 将数据传递给controller层实现指定方法
 * 获取controller层返回的数据并进行操作
 *
 * @author zumin
 */
@Component("viewWindow")
@SuppressWarnings("all")
public class MainWindow extends JFrame {

    /**
     * 迷宫控制层对象
     */
    @Autowired
    private MazeController mazeController;

    /**
     * 封装迷宫数据的对象
     */
    @Autowired
    private Maze maze;

    /**
     * 封装进行显示迷宫的按钮组对象
     */
    @Autowired
    private MazeViewButtons mazeViewButtons;

    /**
     * 按钮监听器
     */
    @Autowired
    private ButtonActionListener buttonActionListener;

    /**
     * 迷宫面板
     */
    private JPanel mazePanel;

    /**
     * 跳过加载对象
     */
    @Autowired
    private SkipLoad skipLoad;

    /**
     * MainWindow类的无参构造函数
     * 调用initComponents()方法进行初始化显示的组件
     */
    public MainWindow() {
        initComponents();
    }

    /**
     * 监听更改迷宫文件位置的菜单项目
     * 传递迷宫对象给viewController对象
     */
    private void changeFileLocationActionPerformed() {
        mazeController.changeFileLocation(maze);
    }

    /**
     * 监听打开迷宫文件的菜单项目
     * 传递迷宫文件路径给viewController对象
     */
    private void openMazeFileActionPerformed() {
        mazeController.openMazeFile(maze.getMazeFilePath());
    }

    /**
     * 监听加载迷宫数据的菜单项目
     * 若不能跳过加载，则读取文件数据重新初始化
     */
    private void loadingMazeDataActionPerformed() {
        if (!cleanData()) {
            if (mazeController.getMazeOriginalData(maze)) {
                return;
            }
            //迷宫面板初始化
            mazePanel.removeAll();
            mazePanel.setLayout(new GridLayout(maze.getMazeRow(), maze.getMazeColumn()));

            //创建迷宫按钮组并增加监听器添加到面板中
            mazeViewButtons = mazeController.newMazeViewButtons(maze);
            addButtonsToPanel();
            addButtonsActionListener();

            //绘制迷宫面板
            mazePanel.updateUI();
            mazePanel.repaint();

            //设置窗口合适大小
            mazeController.setWindowSize(this, maze, Toolkit.getDefaultToolkit().getScreenSize());

            //加载迷宫按钮图片
            mazeController.loadButtonsIcon(mazeViewButtons);

            //将窗口定位到屏幕中心
            setLocationRelativeTo(null);
        }

    }

    /**
     * 监听寻找迷宫路径的菜单项目
     * 传递迷宫对象给viewController对象，计算出迷宫路径
     * 调用viewController对象进行动态路径显示
     */
    private void findMazeMinPathActionPerformed() {
        if (mazeController.getPathData(maze)) {
            return;
        }
        mazeController.displayPathAnimation(mazeViewButtons, maze.getMazePathPoints());

        //寻找路径成功则移除迷宫按钮的监听器
        removeButtonsListener();
    }

    /**
     * 监听改变按钮的起、终点的位置的事件
     *
     * @param e 事件
     */
    private void changeStartAndEndPointActionPerformed(ActionEvent e) {
        mazeController.changeStartAndEndPoint(mazeViewButtons, maze, e.getSource());
    }

    /**
     * 添加迷宫按钮组到迷宫面板中
     */
    private void addButtonsToPanel() {
        JButton[][] buttons = mazeViewButtons.getButtons();
        for (JButton[] button : buttons) {
            for (JButton b : button) {
                mazePanel.add(b);
            }
        }
    }

    /**
     * 为迷宫按钮组增加监听器
     */
    private void addButtonsActionListener() {
        JButton[][] buttons = mazeViewButtons.getButtons();
        for (JButton[] button : buttons) {
            for (JButton b : button) {
                b.addActionListener(buttonActionListener);
            }
        }
    }

    /**
     * 移除迷宫按钮组的监听器
     */
    private void removeButtonsListener() {
        JButton[][] buttons = mazeViewButtons.getButtons();
        for (JButton[] button : buttons) {
            for (JButton b : button) {
                b.removeActionListener(buttonActionListener);
            }
        }
    }

    /**
     * 清理迷宫对象、迷宫按钮组对象的数据
     *
     * @return true->可以跳过加载 false->不可跳过加载
     */
    private boolean cleanData() {
        //获取当前迷宫文件的位置和修改时间
        final String nowMazeFilePath = maze.getMazeFilePath();
        final long nowFileChangeTime = new File(nowMazeFilePath).lastModified();
        //若能懒加载
        if (skipLoad.canLazyLoad(nowMazeFilePath, nowFileChangeTime)) {
            //若已经进行了迷宫最短路径的寻找
            if (skipLoad.hasFindMazePath(maze)) {
                //清理迷宫对象与最短路径相关的数据
                mazeController.removePathAnimation(mazeViewButtons, maze.getMazePathPoints());
                maze.setStart(null);
                maze.setEnd(null);
                maze.setMazeStepData(null);
                maze.setMazePathPoints(null);
                //将移除的监听器重新进行监听
                addButtonsActionListener();
            }
            return true;
        }
        //若不能懒加载，则设置当前迷宫文件的位置和修改时间
        skipLoad.setFilePath(nowMazeFilePath);
        skipLoad.setFileChangeTime(nowFileChangeTime);
        //重新创建迷宫对象和迷宫按钮组对象
        maze = BaseHolder.getBean("maze");
        maze.setMazeFilePath(nowMazeFilePath);
        mazeViewButtons = BaseHolder.getBean("mazeViewButtons");
        return false;
    }

    /**
     * 显示帮助信息
     */
    private void helpTipsPerformed() {
        mazeController.showHelpInformation();
    }

    /**
     * 初始化面板
     */
    private void initComponents() {
        JMenuBar menuBar1 = new JMenuBar();
        JMenu menu1 = new JMenu();
        JMenuItem menuItem2 = new JMenuItem();
        JMenuItem menuItem3 = new JMenuItem();
        JMenu menu2 = new JMenu();
        JMenuItem menuItem5 = new JMenuItem();
        JMenuItem menuItem6 = new JMenuItem();
        JMenu menu3 = new JMenu();
        JMenuItem menuItem1 = new JMenuItem();
        mazePanel = new JPanel();

        //======== this ========
        setTitle("Maze");
        setIconImage(new ImageIcon(getClass().getResource("/mazeIcon.png")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("\u4fee\u6539");
                menu1.setPreferredSize(new Dimension(40, 30));
                menu1.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 12));

                //---- menuItem2 ----
                menuItem2.setText("\u8ff7\u5bab\u6570\u636e");
                menuItem2.addActionListener(e -> openMazeFileActionPerformed());

                menu1.add(menuItem2);
                menu1.addSeparator();

                //---- menuItem3 ----
                menuItem3.setText("\u6587\u4ef6\u4f4d\u7f6e");
                menuItem3.addActionListener(e -> changeFileLocationActionPerformed());
                menu1.add(menuItem3);
            }
            menuBar1.add(menu1);

            //======== menu2 ========
            {
                menu2.setText("\u8fd0\u884c");
                menu2.setPreferredSize(new Dimension(40, 21));
                menu2.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 12));

                //---- menuItem5 ----
                menuItem5.setText("\u52a0\u8f7d\u6570\u636e");
                menuItem5.addActionListener(e -> loadingMazeDataActionPerformed());
                menu2.add(menuItem5);
                menu2.addSeparator();

                //---- menuItem6 ----
                menuItem6.setText("\u6700\u77ed\u8def\u5f84");
                menuItem6.setToolTipText("\u5c1d\u8bd5\u6c42\u51fa\u4e24\u70b9\u4e4b\u95f4\u6700\u77ed\u7684\u8def\u7ebf");
                menuItem6.addActionListener(e -> findMazeMinPathActionPerformed());
                menu2.add(menuItem6);
            }
            menuBar1.add(menu2);

            //======== menu3 ========
            {
                menu3.setText("\u5e2e\u52a9");
                menu3.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 12));
                menu3.setMinimumSize(new Dimension(40, 21));
                menu3.setMaximumSize(new Dimension(40, 32767));
                menu3.setPreferredSize(new Dimension(40, 21));

                //---- menuItem1 ----
                menuItem1.setText("\u663e\u793a\u4fe1\u606f");
                menuItem1.addActionListener(e -> helpTipsPerformed());
                menu3.add(menuItem1);
            }
            menuBar1.add(menu3);
        }
        setJMenuBar(menuBar1);

        {
            mazePanel.setPreferredSize(new Dimension(37, 21));
            mazePanel.setLayout(new GridLayout());
        }
        contentPane.add(mazePanel, BorderLayout.CENTER);
        setSize(280, 350);
        setLocationRelativeTo(null);

        //开启间隔1s的定时任务
        new Timer(1000, new TimerListener()).start();
    }

    /**
     * 按钮事件的监听器
     */
    @Component
    public class ButtonActionListener implements ActionListener {

        /**
         * 若点击按钮，则执行改变起、终点的方法
         *
         * @param e 事件
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            changeStartAndEndPointActionPerformed(e);
        }
    }

    /**
     * 定时任务监听器
     */
    private class TimerListener implements ActionListener {

        /**
         * 初始按钮大小
         */
        private Dimension dimension = new Dimension(50, 50);

        /**
         * 监听按钮大小变化
         *
         * @param e 事件
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            final JButton[][] buttons = mazeViewButtons.getButtons();
            //若按钮组不为空且按钮的大小与初始化小不符
            if (buttons != null && !buttons[0][0].getSize().equals(dimension)) {
                //刷新按钮图片
                mazeController.loadButtonsIcon(mazeViewButtons);
                dimension = buttons[0][0].getSize();
            }
        }
    }
}
