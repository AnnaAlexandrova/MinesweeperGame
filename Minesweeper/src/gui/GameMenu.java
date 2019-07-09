package gui;

import game.AboutGame;
import game.HighScoresTable;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

class GameMenu extends JMenuBar {
    private GameView game;

    GameMenu(GameView game) {
        this.game = game;
        JMenu menu = new JMenu("Меню");

        JMenuItem newGame = new JMenuItem("Новая игра");
        newGame.addActionListener(e -> startNewGame());
        menu.add(newGame);

        JMenuItem about = new JMenuItem("Справка");
        about.addActionListener(e -> showGameInfo());
        menu.add(about);

        JMenuItem highScores = new JMenuItem("Лучшие результаты");
        highScores.addActionListener(e -> showHighScoresTable());
        menu.add(highScores);

        JMenuItem exit = new JMenuItem("Выход");
        exit.addActionListener(e -> gameExit());
        menu.add(exit);

        add(menu);
    }

    private void startNewGame() {
        int userAnswer = JOptionPane.showConfirmDialog(this, "Начать новую игру?",
                "Окно подтверждения", JOptionPane.YES_NO_OPTION);
        if (userAnswer == JOptionPane.YES_OPTION) {
            game.startNewGame();
        }
    }

    private void showGameInfo() {
        AboutGame aboutGame = new AboutGame();
        try {
            for (int i = 0; i < aboutGame.showInfo().size(); i++) {
                JTextArea textArea = new JTextArea(aboutGame.showInfo().get(i), 1, 50);
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setOpaque(false);
                textArea.setBorder(null);
                textArea.setEditable(false);
                textArea.setFocusable(false);

                int userAnswer = JOptionPane.showConfirmDialog(this, textArea,
                        "About game", JOptionPane.OK_CANCEL_OPTION);

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
