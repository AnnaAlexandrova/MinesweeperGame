package text;

import game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TextGameRunner {
    private Game game;

    public void play() {
        initializedGame();
        TextField textField = new TextField(game);

        int x = 0;
        int y = 0;

        while (true) {
            if (game.getIsEnd() && game.getIsWin()) {
                System.out.println("Вы победили!");
                textField.gameEnd();
                break;
            } else if (game.getIsEnd() && !game.getIsWin()) {
                System.out.println("Вы проиграли!");
                textField.gameEnd();
                break;
            } else if (!game.getIsEnd()) {
                try {
                    Scanner scannerX = new Scanner(System.in);
                    System.out.println("Введите координату x по вертикали");

                    try {
                        x = scannerX.nextInt();

                        Scanner scannerY = new Scanner(System.in);

                        System.out.println("Введите координату y по горизонтали");
                        y = scannerY.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Нужно ввести число координату на игровом поле");
                    }
                    x--;
                    y--;

                    game.openCell(x, y);
                    game.isVictory();
                    game.calcMines(x, y);
                    textField.update();
                } catch (IndexOutOfBoundsException e) {
                    if ((x < 0 || x > game.getHeight()) || (y < 0 || y > game.getWidth())) {
                        System.out.println("Данные координаты не входят в границы поля");
                    }
                }
            }
        }
    }

    private void initializedGame() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userAnswer = "";

        System.out.println("Желаете выбрать параметры игры? (Да / Нет)");
        try {
            userAnswer = reader.readLine();
        } catch (IOException e) {
            System.out.println("Ошибка чтения");
        }

        if (userAnswer.equalsIgnoreCase("Да")) {
            System.out.println("Введите размеры игрового поля и количество мин");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Высота");
            int height = scanner.nextInt();

            System.out.println("Ширина");
            int width = scanner.nextInt();

            System.out.println("Количество мин");
            int minesCount = scanner.nextInt();

            this.game = new Game(height, width, minesCount);
        } else {
            this.game = new Game();
        }
    }
}
