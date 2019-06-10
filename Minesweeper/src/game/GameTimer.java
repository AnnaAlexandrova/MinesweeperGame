package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;

public class GameTimer implements ActionListener {
    private Timer timer;
    private Date date = new Date();
    private float gameTime;

    GameTimer() {
        this.timer = new Timer(100, this);
        this.gameTime = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameTime = new Date().getTime() - date.getTime();
    }

    float getGameTime() {
        return gameTime;
    }

    void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    public String toString() {
        int minutes = (int) gameTime / 1000 / 60;
        int seconds = (int) gameTime / 1000 % 60;
        int milliseconds = (int) gameTime % 1000 / 10;
        return (String.format("%02d:%02d.%02d", minutes, seconds, milliseconds));
    }
}
