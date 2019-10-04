package domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * 迷宫视图按钮组
 */
@Component
@Scope("prototype")
public class MazeViewButtons {
    /**
     * 二维按钮
     */
    private JButton[][] buttons;

    public JButton[][] getButtons() {
        return buttons;
    }

    public void setButtons(JButton[][] buttons) {
        this.buttons = buttons;
    }

    /**
     * 查找指定按钮在按钮组的位置
     *
     * @param button 按钮
     *
     * @return 按钮的位置
     */
    public Point findLocationFromButtons(JButton button) {
        if (button == null || buttons == null) {
            return null;
        }
        for (int i = 0, rowLength = buttons.length; i < rowLength; i++) {
            for (int j = 0, columnLength = buttons[i].length; j < columnLength; j++) {
                if (buttons[i][j] == button) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }
}
