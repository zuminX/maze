package app;

import org.slf4j.LoggerFactory;
import view.MainWindow;

import javax.swing.*;

/**
 * 迷宫程序运行入口
 *
 * @author zumin
 */

public class MainApp {

    /**
     * 创建MainWindowSwing窗口进行显示
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        //使用本机默认皮肤
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            LoggerFactory.getLogger(MainApp.class).error("", e);
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
}
