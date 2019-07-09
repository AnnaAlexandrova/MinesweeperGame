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
        for (int k = 1; k < textField[0].length; k++) {
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
        for (int down = 0; down < game.getHeight(); down++) {
            for (int across = 0; across < game.getWidth(); across++) {
                if (game.isOpened(down, across) && textField[down][across].equals(unknown)) {
                    if (game.isMine(down, across)) {
                        textField[down][across] = mine;
                    } else {
                        int minesCount = game.calcMines(down, across);
                        if (minesCount > 0) {
                            textField[down][across] = Integer.toString(minesCount);
                        } else {
                            textField[down][across] = empty;
                        }
                    }
                }
            }
        }
        displayTextField(textField);
        System.out.println();
    }

    void gameEnd() {
        for (int down = 0; down < game.getHeight(); down++) {
            for (int across = 0; across < game.getWidth(); across++) {
                if (!game.isMine(down, across)) {
                    textField[down][across] = empty;
                } else {
                    textField[down][across] = mine;
                }
            }
        }
        game.stopTimer();
        update();
        System.out.println(game.printGameTime());
    }
}
