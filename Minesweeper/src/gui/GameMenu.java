package gui;

import game.AboutGame;
import game.HighScoresTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

class GameMenu extends JMenuBar {
    private GameView game;

    GameMenu(GameView game) {
        this.game = game;
        JMenu menu = new JMenu("Меню");

        JMenuItem newGame = new JMenuItem("Новая игра");
        newGame.addActionListener(e -> startNewGame());
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        menu.add(newGame);

        JMenuItem options = new JMenuItem("Настройки");
        options.addActionListener(e -> changeParameters());
        options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        menu.add(options);

        JMenuItem about = new JMenuItem("Справка");
        about.addActionListener(e -> showGameInfo());
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menu.add(about);

        JMenuItem highScores = new JMenuItem("Лучшие результаты");
        highScores.addActionListener(e -> showHighScoresTable());
        highScores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        menu.add(highScores);

        JMenuItem exit = new JMenuItem("Выход");
        exit.addActionListener(e -> gameExit());
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        menu.add(exit);

        add(menu);
    }

   private void startNewGame() {
        int userAnswer = JOptionPane.showConfirmDialog(this, "Начать новую игру?",
                "Окно подтверждения", JOptionPane.YES_NO_OPTION);
        if (userAnswer == JOptionPane.YES_OPTION) {
            int colsCount = 9;
            int rowsCount = 9;
            int minesCount = 10;

            game.startNewGame(colsCount, rowsCount, minesCount);
        }
    }

    private void changeParameters() {
        int result = JOptionPane.showConfirmDialog(this, "Хотите изменить параметры игры?",
                "Параметры игры", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            Object[] fieldWidth = new Object[30];
            int x = 0;
            for (int i = 0; i < fieldWidth.length; i++) {
                x++;
                fieldWidth[i] = Integer.toString(x);
            }

            String width = (String) JOptionPane.showInputDialog(this,
                    "Выберите количество ячеек по горизонтали", "Выберите параметры игры",
                    JOptionPane.QUESTION_MESSAGE, null, fieldWidth, fieldWidth[0]);

            Object[] fieldHeight = new Object[16];
            int y = 0;
            for (int i = 0; i < fieldHeight.length; i++) {
                y++;
                fieldHeight[i] = Integer.toString(y);
            }

            String height = (String) JOptionPane.showInputDialog(this,
                    "Выберите количество ячеек по вертикали", "Выберите параметры игры",
                    JOptionPane.QUESTION_MESSAGE, null, fieldHeight, fieldHeight[0]);

            Object[] mines = new Object[Integer.parseInt(height) * Integer.parseInt(width) / 2];
            int z = 0;
            for (int i = 0; i < mines.length; i++) {
                z++;
                mines[i] = Integer.toString(z);
            }

            String minesCount = (String) JOptionPane.showInputDialog(this,
                    "Выберите количество мин", "Выберите параметры игры",
                    JOptionPane.QUESTION_MESSAGE, null, mines, mines[0]);

            game.startNewGame(Integer.parseInt(width), Integer.parseInt(height), Integer.parseInt(minesCount));
        }
    }

   private void showGameInfo() {
        AboutGame aboutGame = new AboutGame();
        UIManager.put("OptionPane.okButtonText", "Далее");

        try {
            for (int i = 0; i < aboutGame.showInfo().size(); i++) {
                JTextArea textArea = new JTextArea(aboutGame.showInfo().get(i), 1, 80);
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setOpaque(false);
                textArea.setBorder(null);
                textArea.setEditable(false);
                textArea.setFocusable(false);

                int userAnswer = JOptionPane.showConfirmDialog(this, textArea,
                        "Справка", JOptionPane.OK_CANCEL_OPTION);

                if (userAnswer == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

  private void showHighScoresTable() {
        JTable table = new JTable(5, 1);
        table.setShowGrid(false);
        Font font = new Font("Verdana", Font.BOLD, 12);
        table.setFont(font);

        try {
            HighScoresTable highScoresTable = new HighScoresTable();
            for (int i = 0; i < highScoresTable.showHighScores().size(); i++) {
                table.setValueAt(highScoresTable.showHighScores().get(i), i, 0);
            }
            JOptionPane.showMessageDialog(this, table,
                    "Лучшие результаты", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "В этой игре еще никто не побеждал");
        }
    }

    private void gameExit() {
        int userAnswer = JOptionPane.showConfirmDialog(this, "Выйти из игры?",
                "Окно подтверждения", JOptionPane.YES_NO_OPTION);
        if (userAnswer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
