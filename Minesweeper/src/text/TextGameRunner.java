package text;

import game.AboutGame;
import game.Game;
import game.HighScoresTable;
import game.ScoreRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TextGameRunner {
    private Game game;
    private HighScoresTable table = new HighScoresTable();
    private final String EXIT = "Exit";
    private final String NEW_GAME = "New game";
    private final String ABOUT = "About";
    private final String HIGH_SCORES = "High scores";

    public void play() {
        initializeGame();
        TextField textField = new TextField(game);

        System.out.println("Сделайте первый ход, он всегда беспроигрышный.");
        firstMove();
        textField.update();

        int down = 0;
        int across = 0;

        System.out.println("Дальше нужно открывать ячейки, где нет мины, пока не откроете все поле.");
        System.out.println("Возможные команды:");
        System.out.println("Exit - выход");
        System.out.println("New game - новая игра");
        System.out.println("About - справка");
        System.out.println("High scores - таблица рекордов");

        while (true) {
            if (game.isEnd() && game.isWin()) {
                System.out.println("Вы победили!");
                textField.gameEnd();
                break;
            } else if (game.isEnd() && !game.isWin()) {
                System.out.println("BANG!");
                System.out.println("Вы проиграли!");
                textField.gameEnd();
                break;
            } else if (!game.isEnd()) {
                try {
                    Scanner scannerAcross = new Scanner(System.in);
                    System.out.println("Введите координату по горизонтали");
                    String acrossScan = scannerAcross.nextLine();

                    try {
                        if (acrossScan.equalsIgnoreCase(EXIT)) {
                            return;
                        } else if (acrossScan.equalsIgnoreCase(NEW_GAME)) {
                            TextGameRunner newGame = new TextGameRunner();
                            newGame.play();
                        } else if (acrossScan.equalsIgnoreCase(ABOUT)) {
                            AboutGame about = new AboutGame();
                            about.aboutGame();
                            System.out.println();
                            continue;
                        } else if (acrossScan.equalsIgnoreCase(HIGH_SCORES)) {
                            table.print();
                            continue;
                        } else {
                            across = Integer.parseInt(acrossScan);
                        }

                        Scanner scannerDown = new Scanner(System.in);
                        System.out.println("Введите координату по вертикали");
                        String downScan = scannerDown.nextLine();

                        if (downScan.equalsIgnoreCase(EXIT)) {
                            return;
                        } else if (downScan.equalsIgnoreCase(NEW_GAME)) {
                            TextGameRunner newGame = new TextGameRunner();
                            newGame.play();
                        } else if (downScan.equalsIgnoreCase(ABOUT)) {
                            AboutGame about = new AboutGame();
                            about.aboutGame();
                            System.out.println();
                            continue;
                        } else if (downScan.equalsIgnoreCase(HIGH_SCORES)) {
                            table.print();
                            continue;
                        } else {
                            down = Integer.parseInt(downScan);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Нужно ввести число координату на игровом поле");
                        continue;
                    } catch (FileNotFoundException e) {
                        System.out.println("Файл не найден");
                    }
                    down--;
                    across--;

                    game.openCell(down, across);
                    game.isVictory(down, across);
                    game.calcMines(down, across);
                    textField.update();
                } catch (IndexOutOfBoundsException e) {
                    if ((down < 0 || down > game.getHeight()) || (across < 0 || across > game.getWidth())) {
                        System.out.println("Данные координаты не входят в границы поля");
                    }
                }
            }
        }
        if (game.isWin()) {
            rememberRecord();
        }
    }

    private void initializeGame() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userAnswer = "";

        System.out.println("Желаете выбрать параметры игры? (Yes / No)");
        try {
            userAnswer = reader.readLine();
        } catch (IOException e) {
            System.out.println("Ошибка чтения");
        }

        if (userAnswer.equalsIgnoreCase("Yes")) {
            System.out.println("Введите размеры игрового поля и количество мин");

            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Высота");
                int height = scanner.nextInt();

                System.out.println("Ширина");
                int width = scanner.nextInt();

                System.out.println("Количество мин");
                int minesCount = scanner.nextInt();

                this.game = new Game(height, width, minesCount);
            } catch (InputMismatchException e) {
                System.out.println("Введено не число, параметры игры будут по умолчанию");
                this.game = new Game();
            }
        } else {
            this.game = new Game();
        }
        game.startTimer();
    }

    private void firstMove() {
        int down = 0;
        int across = 0;

        while (!game.isOpened(down, across)) {
            try {
                Scanner scannerAcross = new Scanner(System.in);
                System.out.println("Введите координату по горизонтали");
                try {
                    across = Integer.parseInt(scannerAcross.nextLine());

                    Scanner scannerDown = new Scanner(System.in);
                    System.out.println("Введите координату по вертикали");
                    down = Integer.parseInt(scannerDown.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Нужно ввести число координату на игровом поле");
                    continue;
                }
                down--;
                across--;

                if (game.isMine(down, across)) {
                    game.generateMines(1);
                    game.setMine(down, across, false);
                }

                game.openCell(down, across);
                game.calcMines(down, across);
            } catch (IndexOutOfBoundsException e) {
                if ((down < 0 || down > game.getHeight()) || (across < 0 || across > game.getWidth())) {
                    System.out.println("Данные координаты не входят в границы поля");
                }
            }
        }
    }

    private void rememberRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваше имя");
        String userName = scanner.nextLine();

        table.addRecord(new ScoreRecord(userName, game.getGameTime()));
    }
}