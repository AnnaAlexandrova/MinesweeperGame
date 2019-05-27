package text;

import game.Game;

class TextField {
    private String[][] textField;
    private String mine = "*";
    private String empty = " ";
    private String unknown = "?";
    private Game game;

    TextField(Game game) {
        this.game = game;
        this.textField = new String[game.getHeight()][game.getWidth()];
        for (int i = 0; i < textField.length; i++) {
            for (int j = 0; j < textField[0].length; j++) {
                textField[i][j] = unknown;
            }
        }
        update();
    }

    private static void displayTextField(String[][] textField) {
        System.out.print("   |");
        for (int i = 1; i <= textField[0].length; i++) {
            System.out.printf("%3d", i);
        }
        System.out.printf("%n---|---");

        String s = "---";
        int k = 1;
        while (k < textField[0].length) {
            k++;
            System.out.print(s);
        }
        System.out.println();

        for (int i = 0; i < textField.length; i++) {
            System.out.printf("%3d|", i + 1);
            for (int j = 0; j < textField[0].length; j++) {
                System.out.printf("%3s", textField[i][j]);
            }
            System.out.println();
        }
    }

    void update() {
        for (int x = 0; x < game.getHeight(); x++) {
            for (int y = 0; y < game.getWidth(); y++) {
                if (game.getIsOpened(x, y) && textField[x][y].equals(unknown)) {
                    if (game.getCell(x, y) == 1) {
                        textField[x][y] = mine;
                    } else {
                        int minesCount = game.calcMines(x, y);
                        if (minesCount > 0) {
                            textField[x][y] = Integer.toString(minesCount);
                        } else {
                            textField[x][y] = empty;
                        }
                    }
                }
            }
        }
        displayTextField(textField);
        System.out.println();
    }

    void gameEnd() {
        for (int x = 0; x < game.getHeight(); x++) {
            for (int y = 0; y < game.getWidth(); y++) {
                if (game.getCell(x, y) == 0) {
                    textField[x][y] = empty;
                } else {
                    textField[x][y] = mine;
                }
            }
        }
        update();
    }
}
