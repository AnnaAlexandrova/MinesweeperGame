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


    public Game() {
        this.height = 9;
        this.width = 9;
        this.minesCount = 10;

        this.cells = new int[height][width];
        this.isOpened = new boolean[height][width];

        this.isEnd = false;
        this.isWin = false;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = 0;
                isOpened[i][j] = false;
            }
        }
        generateMines(minesCount);
    }

    public Game(int height, int width, int minesCount) {
        this.height = height;
        this.width = width;
        this.minesCount = minesCount;

        this.cells = new int[height][width];
        this.isOpened = new boolean[height][width];

        this.isEnd = false;
        this.isWin = false;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = 0;
                isOpened[i][j] = false;
            }
        }
        generateMines(minesCount);
    }

    public int getHeight() {
        return cells.length;
    }

    public int getWidth() {
        return cells[0].length;
    }

    public boolean getIsOpened(int x, int y) {
        return isOpened[x][y];
    }

    public boolean getIsEnd() {
        return isEnd;
    }

    public boolean getIsWin() {
        return isWin;
    }

    public int getCell(int x, int y) {
        return cells[x][y];
    }

    private void generateMines(int minesCount) {
        Random randX = new Random();
        Random randY = new Random();
        for (int i = 1; i <= minesCount; i++) {
            int x = randX.nextInt(height);
            int y = randY.nextInt(width);
            if (cells[x][y] == 1) {
                i--;
            } else {
                cells[x][y] = 1;
            }
        }
    }

    private boolean isOutOfBounds(int x, int y) {
        return (x < 0 || y < 0) || (x >= height || y >= width);
    }

    public void openCell(int x, int y) {
        if (isOutOfBounds(x, y)) {
            return;
        }
        if (isOpened[x][y]) {
            return;
        }
        isOpened[x][y] = true;

        if (cells[x][y] == 1) {
            isEnd = true;
            isWin = false;
            System.out.println("BANG!");
            return;
        }
        if (calcMines(x, y) > 0) {
            return;
        }

        openCell(x - 1, y - 1);
        openCell(x + 1, y - 1);
        openCell(x - 1, y + 1);
        openCell(x + 1, y + 1);

        openCell(x - 1, y);
        openCell(x, y - 1);
        openCell(x + 1, y);
        openCell(x, y + 1);
    }

    public int calcMines(int x, int y) {
        if (isOutOfBounds(x, y)) {
            return 0;
        }
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!isOutOfBounds(x + i, y + j)) {
                    if (cells[i + x][j + y] == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void isVictory() {
        int closedCellsCount = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!isOpened[i][j]) {
                    closedCellsCount++;
                }
            }
            if (closedCellsCount - minesCount != 0) {
                isWin = false;
            } else {
                isWin = true;
                isEnd = true;
            }
        }
    }
}
