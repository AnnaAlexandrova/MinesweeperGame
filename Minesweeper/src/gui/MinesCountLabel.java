package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class MinesCountLabel extends JLabel {
    MinesCountLabel() {
        Font font = new Font("Verdana", Font.BOLD, 12);
        setFont(font);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);

        setForeground(Color.CYAN);
        setBackground(Color.BLACK);
        setOpaque(true);
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
