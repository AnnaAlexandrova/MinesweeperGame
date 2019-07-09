package game;

import java.util.Random;

public class Game {
    private int height;
    private int width;
    private Cell[][] cells;
    private int minesCount;
    private int flagsCount;
    private boolean isEnd;
    private boolean isWin;
    private int closedCellsCount;
    private GameTimer timer;


    public Game() {
        this.height = 9;
        this.width = 9;
        this.minesCount = 10;
        this.flagsCount = 0;
        this.closedCellsCount = height * width;

        this.cells = new Cell[height][width];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell();
            }
        }

        this.isEnd = false;
        this.isWin = false;

        this.timer = new GameTimer();

        generateMines(minesCount);
    }

    public Game(int height, int width, int minesCount) {
        this.height = height;
        this.width = width;
        this.minesCount = minesCount;
        this.flagsCount = 0;
        this.closedCellsCount = height * width;

        this.cells = new Cell[height][width];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell();
            }
        }

        this.isEnd = false;
        this.isWin = false;

        this.timer = new GameTimer();

        generateMines(minesCount);
    }

    public int getHeight() {
        return cells.length;
    }

    public int getWidth() {
        return cells[0].length;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public int getFlagsCount() {
        return flagsCount;
    }

    public boolean isOpened(int down, int across) {
        return cells[down][across].isOpen();
    }

    public boolean isFlag(int down, int across) {
        return cells[down][across].isFlag();
    }

    public boolean isEnd() {
        return isEnd;
    }

    public boolean isWin() {
        return isWin;
    }

    public boolean isMine(int down, int across) {
        return cells[down][across].isMine();
    }

    public void setMine(int down, int across, boolean isMine) {
        this.cells[down][across].setMine(isMine);
    }

    public int getClosedCellsCount() {
        return closedCellsCount;
    }

    public void generateMines(int minesCount) {
        Random randDown = new Random();
        Random randAcross = new Random();
        for (int i = 1; i <= minesCount; i++) {
            int down = randDown.nextInt(height);
            int across = randAcross.nextInt(width);
            if (cells[down][across].isMine()) {
                i--;
            } else {
                cells[down][across].setMine(true);
            }
        }
    }

    public boolean isOutOfBounds(int down, int across) {
        return (down < 0 || across < 0) || (down >= height || across >= width);
    }

    public void openCell(int down, int across) {
        if (isOutOfBounds(down, across)) {
            return;
        }
        if (cells[down][across].isOpen()) {
            return;
        }
        cells[down][across].setOpen(true);
        closedCellsCount--;

        if (cells[down][across].isMine()) {
            isEnd = true;
            isWin = false;
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

    public void openNeighboringFlagCells(int down, int across) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!isOutOfBounds(i + down, j + across)) {
                    if (!cells[i + down][j + across].isFlag()) {
                        openCell(i + down, j + across);
                    }
                }
            }
        }
    }

    public int calcMines(int down, int across) {
        if (cells[down][across].isMine()) {
            return 0;
        }
        if (isOutOfBounds(down, across)) {
            return 0;
        }
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!isOutOfBounds(down + i, across + j)) {
                    if (cells[i + down][j + across].isMine()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void addFlag(int down, int across) {
        if (cells[down][across].isFlag()) {
            cells[down][across].setFlag(false);
            flagsCount--;
        } else {
            cells[down][across].setFlag(true);
            flagsCount++;
        }
    }

    public void isVictory(int down, int across) {
        if (cells[down][across].isMine()) {
            return;
        }
        if (closedCellsCount - minesCount != 0) {
            isWin = false;
        } else {
            isWin = true;
            isEnd = true;
        }
    }

    public String printGameTime() {
        return timer.toString();
    }

    public float getGameTime() {
        return timer.getGameTime();
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }
}

