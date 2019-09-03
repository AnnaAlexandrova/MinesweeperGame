package gui;

import game.Game;
import game.HighScoresTable;
import game.ScoreRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameView {
    private Game game;
    private GameFrame gameFrame;
    private GameMouseClickListener mouseClickListener = new GameMouseClickListener();
    private GameKeyListener gameKeyListener = new GameKeyListener();
    private int rowsCount;
    private int colsCount;
    private JButton[][] cells;
    private HighScoresTable table = new HighScoresTable();
    private String path = "./Minesweeper/src/resources/";

    public GameView() {
        SwingUtilities.invokeLater(this::run);
    }

    private void run() {
        game = new Game();

        this.colsCount = game.getWidth();
        this.rowsCount = game.getHeight();
        this.gameFrame = new GameFrame(colsCount, rowsCount, game.getMinesCount(), this);

        this.cells = new JButton[rowsCount][colsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                JButton cell = new JButton();
                cell.setMaximumSize(new Dimension(30, 30));
                cell.addMouseListener(mouseClickListener);
                cell.addKeyListener(gameKeyListener);
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
                    update();
                }
            } else if (SwingUtilities.isMiddleMouseButton(e)) {
                if (game.isOpened(down, across) && game.calcMines(down, across) > 0) {
                    findNeighboringFlags(down, across);
                }
            }

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

    private class GameKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            GameMenu gameMenu = gameFrame.getGameMenu();

            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {
                gameMenu.startNewGame();
            } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O) {
                gameMenu.changeParameters();
            } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
                gameMenu.showGameInfo();
            } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R) {
                gameMenu.showHighScoresTable();
            } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E) {
                gameMenu.gameExit();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

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
                        cells[i][j].setBackground(null);
                    }
                }
            }
        }
    }

    private void gameEnd() {
        int foundedMines = 0;
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                cells[i][j].removeMouseListener(mouseClickListener);

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
                    } else if (game.isFlag(i + down, j + across)) {
                        return;
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
            update();
        }
    }

    private void youWin() {
        JOptionPane.showMessageDialog(gameFrame,
                "Вы победили!",
                "Победа!", JOptionPane.INFORMATION_MESSAGE);
        rememberRecord();
    }

    private void youLose() {
        JOptionPane.showMessageDialog(gameFrame,
                "Вы проиграли!",
                "Взрыв!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void rememberRecord() {
        String userName;
        try {
            userName = JOptionPane.showInputDialog(gameFrame, "<html><h2>Введите ваше имя").trim();
        } catch (NullPointerException e) {
            return;
        }
        if (userName.length() > 0) {
            table.addRecord(new ScoreRecord(userName, gameFrame.getTimerLabel().getTime()));
        }
    }

    void startNewGame(int colsCount, int rowsCount, int minesCount) {
        SwingUtilities.invokeLater(() -> {
            this.game = new Game(rowsCount, colsCount, minesCount);

            this.colsCount = colsCount;
            this.rowsCount = rowsCount;

            gameFrame.getTimerLabel().startNewGame();
            gameFrame.getMinesCountLabel().setText(Integer.toString(game.getMinesCount()));

            gameFrame.setWidth(colsCount);
            gameFrame.setHeight(rowsCount);

            gameFrame.setNewGame(colsCount, rowsCount);

            this.cells = new JButton[rowsCount][colsCount];
            for (int i = 0; i < rowsCount; i++) {
                for (int j = 0; j < colsCount; j++) {
                    JButton cell = new JButton();
                    cell.setMaximumSize(new Dimension(30, 30));
                    cell.addMouseListener(mouseClickListener);
                    cell.addKeyListener(gameKeyListener);
                    gameFrame.getGameField().add(cell);
                    cells[i][j] = cell;
                }
            }
            update();
        });
    }
}

