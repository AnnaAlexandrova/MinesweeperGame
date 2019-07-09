package gui;

import game.Game;
import game.HighScoresTable;
import game.ScoreRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameView {
    private Game game;
    private GameFrame gameFrame;
    private GameMouseClickListener mouseClickListener = new GameMouseClickListener();
    private int rowsCount;
    private int colsCount;
    private JButton[][] cells;
    private HighScoresTable table = new HighScoresTable();
    private String path = "./Minesweeper/src/resources/";

    public GameView() {
        SwingUtilities.invokeLater(this::run);
    }

    private void run() {
        initGame();

        this.colsCount = game.getWidth();
        this.rowsCount = game.getHeight();
        this.gameFrame = new GameFrame(colsCount, rowsCount, game.getMinesCount(), this);

        this.cells = new JButton[rowsCount][colsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                JButton cell = new JButton();
                cell.setMaximumSize(new Dimension(30, 30));
                cell.addMouseListener(mouseClickListener);
                gameFrame.getGameField().add(cell);
                cells[i][j] = cell;
            }
        }
    }

    private class GameMouseClickListener implements MouseListener {
        private int down;
        private int across;
        private int prevDown;
        private int prevAcross;
        private int button;

        private void receiveCellCoordinates(MouseEvent e) {
            JButton cell = (JButton) e.getSource();
            down = 0;
            across = 0;
            for (int i = 0; i < rowsCount; ++i) {
                for (int j = 0; j < colsCount; ++j) {
                    if (cell == cells[i][j]) {
                        down = i;
                        across = j;
                        break;
                    }
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            receiveCellCoordinates(e);

            MinesCountLabel minesCount = gameFrame.getMinesCountLabel();
            TimerLabel timerLabel = gameFrame.getTimerLabel();

            if (SwingUtilities.isRightMouseButton(e)) {
                if (!game.isOpened(down, across)) {
                    game.addFlag(down, across);
                    minesCount.setText(Integer.toString(game.getMinesCount() - game.getFlagsCount()));
                    update();
                }
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                if (game.getClosedCellsCount() == rowsCount * colsCount) {
                    timerLabel.start();
                    if (game.isMine(down, across)) {
                        game.generateMines(1);
                        game.setMine(down, across, false);
                    }
                }

                if (game.isMine(down, across)) {
                    gameEnd();
                    youLose();
                } else {
                    game.openCell(down, across);
                    game.isVictory(down, across);

                    if (game.isWin()) {
                        gameEnd();
                        youWin();
                    }
                    game.calcMines(down, across);
                    update();
                }
            } else if (SwingUtilities.isMiddleMouseButton(e)) {
                if (game.isOpened(down, across) && game.calcMines(down, across) > 0) {
                    findNeighboringFlags(down, across);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            receiveCellCoordinates(e);

            if (!SwingUtilities.isMiddleMouseButton(e)) {
                button = e.getButton();
            }

            if (SwingUtilities.isLeftMouseButton(e)) {
                if (prevDown == down && prevAcross == across) {
                    if (game.isOpened(down, across) && game.calcMines(down, across) > 0) {
                        findNeighboringFlags(down, across);
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            receiveCellCoordinates(e);
            if (SwingUtilities.isLeftMouseButton(e)) {
                prevDown = down;
                prevAcross = across;
            }
            if (!SwingUtilities.isMiddleMouseButton(e)) {
                if (e.getButton() != button) {
                    if (game.isOpened(down, across) && game.calcMines(down, across) > 0) {
                        findNeighboringFlags(down, across);
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private void initGame() {
        int result = JOptionPane.showConfirmDialog(gameFrame, "Хотите ввести параметры игры?",
                "Параметры игры", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            Object[] size = new Object[30];
            int x = 0;
            for (int i = 0; i < size.length; i++) {
                x += 1;
                size[i] = Integer.toString(x);
            }

            String width = (String) JOptionPane.showInputDialog(gameFrame,
                    "Выберите количество ячеек по горизонтали", "Введите параметры игры",
                    JOptionPane.QUESTION_MESSAGE, null, size, size[0]);

            String height = (String) JOptionPane.showInputDialog(gameFrame,
                    "Выберите количество ячеек по вертикали", "Введите параметры игры",
                    JOptionPane.QUESTION_MESSAGE, null, size, size[0]);

            Object[] mines = new Object[Integer.parseInt(height) * Integer.parseInt(width) / 2];
            int y = 0;
            for (int i = 0; i < mines.length; i++) {
                y += 1;
                mines[i] = Integer.toString(y);
            }

            String minesCount = (String) JOptionPane.showInputDialog(gameFrame,
                    "Выберите количество мин", "Введите параметры игры",
                    JOptionPane.QUESTION_MESSAGE, null, mines, mines[0]);

            game = new Game(Integer.parseInt(height), Integer.parseInt(width), Integer.parseInt(minesCount));
        } else {
            game = new Game();
        }
    }

    private void update() {
        Color color = new Color(195, 195, 195);

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                if (game.isOpened(i, j)) {
                    cells[i][j].setBackground(color);

                    if (game.isMine(i, j)) {
                        cells[i][j].setIcon(new ImageIcon(path + "bomb.png"));
                    } else {
                        switch (game.calcMines(i, j)) {
                            case 0:
                                cells[i][j].setIcon(new ImageIcon(path + "empty.png"));
                                break;
                            case 1:
                                cells[i][j].setIcon(new ImageIcon(path + "1.png"));
                                break;
                            case 2:
                                cells[i][j].setIcon(new ImageIcon(path + "2.png"));
                                break;
                            case 3:
                                cells[i][j].setIcon(new ImageIcon(path + "3.png"));
                                break;
                            case 4:
                                cells[i][j].setIcon(new ImageIcon(path + "4.png"));
                                break;
                            case 5:
                                cells[i][j].setIcon(new ImageIcon(path + "5.png"));
                                break;
                            case 6:
                                cells[i][j].setIcon(new ImageIcon(path + "6.png"));
                                break;
                            case 7:
                                cells[i][j].setIcon(new ImageIcon(path + "7.png"));
                                break;
                            case 8:
                                cells[i][j].setIcon(new ImageIcon(path + "8.png"));
                                break;
                        }
                    }
                } else {
                    if (game.isFlag(i, j)) {
                        cells[i][j].setIcon(new ImageIcon(path + "flag.png"));
                    } else {
                        cells[i][j].setIcon(null);
                    }
                }
            }
        }
    }

    private void gameEnd() {
        int foundedMines = 0;
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                if (game.isFlag(i, j)) {
                    if (game.isMine(i, j)) {
                        cells[i][j].setIcon(new ImageIcon(path + "flag.png"));
                        foundedMines++;
                    } else {
                        cells[i][j].setIcon(new ImageIcon(path + "brokenFag.png"));
                    }
                } else if (game.isMine(i, j)) {
                    cells[i][j].setIcon(new ImageIcon(path + "bomb.png"));
                } else {
                    cells[i][j].setIcon(new ImageIcon(path + "empty.png"));
                }
            }
        }
        gameFrame.getMinesCountLabel().setText(Integer.toString(foundedMines));
        gameFrame.getTimerLabel().stop();
    }

    private void findNeighboringFlags(int down, int across) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!game.isOutOfBounds(i + down, j + across)) {
                    if (game.isMine(i + down, j + across)) {
                        if (game.isFlag(i + down, j + across)) {
                            count++;
                        } else {
                            gameEnd();
                            youLose();
                            break;
                        }
                    }
                }
            }
        }
        if (count == game.calcMines(down, across)) {
            game.openNeighboringFlagCells(down, across);
            game.isVictory(down, across);
            if (game.isWin()) {
                gameEnd();
                youWin();
            }
            game.calcMines(down, across);
            update();
        }
    }

    private void youWin() {
        JOptionPane.showMessageDialog(gameFrame,
                "Вы победили!",
                "YOU WIN!", JOptionPane.INFORMATION_MESSAGE);
        rememberRecord();
    }

    private void youLose() {
        JOptionPane.showMessageDialog(gameFrame,
                "Вы проиграли!",
                "BANG!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void rememberRecord() {
        String userName = JOptionPane.showInputDialog(gameFrame, "<html><h2>Введите ваше имя");

        table.addRecord(new ScoreRecord(userName, gameFrame.getTimerLabel().getTime()));
    }

    void startNewGame() {
        SwingUtilities.invokeLater(() -> {
            gameFrame.getTimerLabel().stop();
            initGame();

            this.colsCount = game.getWidth();
            this.rowsCount = game.getHeight();
            this.gameFrame = new GameFrame(colsCount, rowsCount, game.getMinesCount(), this);

            this.cells = new JButton[rowsCount][colsCount];
            for (int i = 0; i < rowsCount; i++) {
                for (int j = 0; j < colsCount; j++) {
                    JButton cell = new JButton();
                    cell.setMaximumSize(new Dimension(30, 30));
                    cell.addMouseListener(mouseClickListener);
                    gameFrame.getGameField().add(cell);
                    cells[i][j] = cell;
                }
            }
        });
    }
}

