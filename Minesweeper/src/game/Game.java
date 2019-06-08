package game;

import java.util.Random;

public class Game {
    private int height;
    private int width;
    private int[][] cells;
    private boolean[][] isOpened;
    private int minesCount;
    private boolean isEnd;
    private boolean isWin;
    private int closedCellsCount;
    private GameTimer timer;


    public Game() {
        this.height = 9;
        this.width = 9;
        this.minesCount = 10;
        this.closedCellsCount = height * width;

        this.cells = new int[height][width];
        this.isOpened = new boolean[height][width];

        this.isEnd = false;
        this.isWin = false;

        this.timer = new GameTimer(this);

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = 0;
                isOpened[i][j] = false;
            }
        }
        generateMines(minesCount);
        timer.start();
    }

    public Game(int height, int width, int minesCount) {
        this.height = height;
        this.width = width;
        this.minesCount = minesCount;
        this.closedCellsCount = height * width;

        this.cells = new int[height][width];
        this.isOpened = new boolean[height][width];

        this.isEnd = false;
        this.isWin = false;

        this.timer = new GameTimer(this);

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = 0;
                isOpened[i][j] = false;
            }
        }
        generateMines(minesCount);
        timer.start();
    }

    public int getHeight() {
        return cells.length;
    }

    public int getWidth() {
        return cells[0].length;
    }

    public boolean isOpened(int down, int across) {
        return isOpened[down][across];
    }

    public boolean isEnd() {
        return isEnd;
    }

    public boolean isWin() {
        return isWin;
    }

    public int getCell(int down, int across) {
        return cells[down][across];
    }

    public void setCell(int down, int across, int value) {
        if (value < 0 || value > 1) {
            return;
        }
        this.cells[down][across] = value;
    }

    public void generateMines(int minesCount) {
        Random randDown = new Random();
        Random randAcross = new Random();
        for (int i = 1; i <= minesCount; i++) {
            int down = randDown.nextInt(height);
            int across = randAcross.nextInt(width);
            if (cells[down][across] == 1) {
                i--;
            } else {
                cells[down][across] = 1;
            }
        }
    }

    private boolean isOutOfBounds(int down, int across) {
        return (down < 0 || across < 0) || (down >= height || across >= width);
    }

    public void openCell(int down, int across) {
        if (isOutOfBounds(down, across)) {
            return;
        }
        if (isOpened[down][across]) {
            return;
        }
        isOpened[down][across] = true;
        closedCellsCount--;

        if (cells[down][across] == 1) {
            isEnd = true;
            isWin = false;
            System.out.println("BANG!");
            timer.stop();
            return;
        }
        if (calcMines(down, across) > 0) {
            return;
        }

        openCell(down - 1, across - 1);
        openCell(down + 1, across - 1);
        openCell(down - 1, across + 1);
        openCell(down + 1, across + 1);

        openCell(down - 1, across);
        openCell(down, across - 1);
        openCell(down + 1, across);
        openCell(down, across + 1);
    }

    public int calcMines(int down, int across) {
        if (cells[down][across] == 1) {
            return 0;
        }
        if (isOutOfBounds(down, across)) {
            return 0;
        }
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!isOutOfBounds(down + i, across + j)) {
                    if (cells[i + down][j + across] == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void isVictory(int down, int across) {
        if (cells[down][across] == 1) {
            return;
        }
        if (closedCellsCount - minesCount != 0) {
            isWin = false;
        } else {
            isWin = true;
            isEnd = true;
            timer.stop();
        }
    }

    public String getGameTime() {
        return timer.toString();
    }
}

