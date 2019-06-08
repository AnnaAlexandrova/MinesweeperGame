package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AboutGame {
    public void aboutGame() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(
                new FileInputStream(
                        "C:/Users/fytyr/IdeaProjects/MinesweeperGame/Minesweeper/src/resources/about.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        }
    }
}
