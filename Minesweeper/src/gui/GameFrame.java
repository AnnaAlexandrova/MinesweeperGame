package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameFrame extends JFrame {
    private JFrame gameFrame;
    private int width;
    private int height;
    private JPanel gameField;
    private TimerLabel timerLabel;
    private MinesCountLabel minesCountLabel;
    private GameMenu gameMenu;
    private final int CELL_SIZE = 35;

    GameFrame(int colsCount, int rowsCount, int minesCount, GameView gameView) {
        this.gameFrame = new JFrame("Сапёр");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.width = colsCount * CELL_SIZE + 30;
        this.height = rowsCount * CELL_SIZE + 65;

        gameFrame.setSize(width, height);
        gameFrame.setMinimumSize(new Dimension(300, 335));
        gameFrame.setResizable(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        gameFrame.setBounds(x, y, width, height);

        this.gameField = new JPanel(new GridLayout(rowsCount, colsCount));
        gameField.setSize(gameFrame.getWidth(), gameFrame.getHeight());
        gameField.setBorder(new EmptyBorder(1, 1, 1, 1));

        gameFrame.add(gameField, BorderLayout.CENTER);

        JPanel timerField = new JPanel(new GridBagLayout());
        timerField.setSize(gameFrame.getWidth(), 50);
        timerField.setBorder(new EmptyBorder(1, 1, 1, 1));
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(20, 10, 20, 10);

        this.timerLabel = new TimerLabel();
        timerField.add(timerLabel, c);

        this.minesCountLabel = new MinesCountLabel();
        minesCountLabel.setText(Integer.toString(minesCount));
        timerField.add(minesCountLabel, c);

        gameFrame.add(timerField, BorderLayout.PAGE_START);

        gameMenu = new GameMenu(gameView);
        gameFrame.setJMenuBar(gameMenu);
        gameFrame.setVisible(true);

        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");
        UIManager.put("OptionPane.cancelButtonText", "Отмена");
        UIManager.put("OptionPane.okButtonText", "Ок");
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    JPanel getGameField() {
        return gameField;
    }

    TimerLabel getTimerLabel() {
        return timerLabel;
    }

    MinesCountLabel getMinesCountLabel() {
        return minesCountLabel;
    }

    GameMenu getGameMenu() {
        return gameMenu;
    }

    void setWidth(int colsCount) {
        this.width = colsCount * CELL_SIZE + 30;
    }

    void setHeight(int rowsCount) {
        this.height = rowsCount * CELL_SIZE + 65;
    }

    void setNewGame(int colsCount, int rowsCount) {
        gameFrame.remove(gameField);

        this.width = colsCount * CELL_SIZE + 30;
        this.height = rowsCount * CELL_SIZE + 65;

        gameFrame.setSize(width, height);
        gameFrame.setMinimumSize(new Dimension(300, 400));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        gameFrame.setBounds(x, y, width, height);

        JPanel newGame = new JPanel(new GridLayout(rowsCount, colsCount));
        gameField.setSize(gameFrame.getWidth(), gameFrame.getHeight());
        gameField.setBorder(new EmptyBorder(1, 1, 1, 1));

        gameFrame.add(newGame, BorderLayout.CENTER);

        this.gameField = newGame;

        gameFrame.repaint();
        gameFrame.revalidate();
    }
}
