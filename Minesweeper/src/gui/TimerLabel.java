package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TimerLabel extends JLabel implements ActionListener {
    private Timer gameTimer;
    private Date date = new Date();
    private float time;

    TimerLabel() {
        setText("00:00.00");
        Font font = new Font("Verdana", Font.BOLD, 12);
        setFont(font);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);

        setForeground(Color.GREEN);
        setBackground(Color.BLACK);
        setOpaque(true);
        setBorder(new EmptyBorder(5, 5, 5, 5));

        gameTimer = new Timer(100, this);
        time = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time = new Date().getTime() - date.getTime();
        setText(toString());
    }

    float getTime() {
        return time;
    }

    void start() {
        gameTimer.start();
    }

    void stop() {
        gameTimer.stop();
    }

    void startNewGame() {
        date = new Date();
        gameTimer = new Timer(100, this);
        time = 0;
        start();
    }

    @Override
    public String toString() {
        final int MINUTES = (int) time / 1000 / 60;
        final int SECONDS = (int) time / 1000 % 60;
        final int MILLISECONDS = (int) time % 1000 / 10;
        return (String.format("%02d:%02d.%02d", MINUTES, SECONDS, MILLISECONDS));
    }
}
