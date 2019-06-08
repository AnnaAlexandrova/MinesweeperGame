package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import javax.swing.*;

public class GameTimer implements ActionListener {
    private Timer timer;
    private Time time = new Time(0);
    private float gameTime;

    public GameTimer(Game game) {
        this.timer = new Timer(100, this);
        this.gameTime = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameTime = new Time(0).getTime() - time.getTime();
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public String toString() {
        int minutes = (int) gameTime / 1000 / 60;
        int seconds = (int) gameTime / 1000 % 60;
        int milliseconds = (int) gameTime % 1000 / 10;
        return (String.format("%02d:%02d.%02d", minutes, seconds, milliseconds));
    }
}
