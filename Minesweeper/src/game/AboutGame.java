package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AboutGame {
    private String pathToFile = "./Minesweeper/src/resources/";

    public void aboutGame() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(
                new FileInputStream(pathToFile + "about.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        }
    }

    public ArrayList<String> showInfo() throws FileNotFoundException {
        ArrayList<String> info = new ArrayList<>();
        try (Scanner scanner = new Scanner(
                new FileInputStream(pathToFile + "aboutGUI.txt"))) {
            while (scanner.hasNextLine()) {
                info.add(scanner.nextLine());
            }
        }
        return info;
    }
}
