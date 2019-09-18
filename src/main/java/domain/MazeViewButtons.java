package domain;

import javax.swing.*;

public class MazeViewButtons {
    private JButton[][] buttons;

    public JButton[][] getButtons() {
        return buttons;
    }

    public void setButtons(JButton[][] buttons) {
        this.buttons = buttons;
    }

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
