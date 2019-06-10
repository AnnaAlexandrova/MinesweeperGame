package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class HighScoresTable {
    private ArrayList<ScoreRecord> table;
    private String path = "./Minesweeper/src/resources/HighScoresTable.txt";

    public HighScoresTable() {
        this.table = new ArrayList<>();
        readFile();
    }

    public void addRecord(ScoreRecord record) {
        table.add(record);
        table.sort((o1, o2) -> Float.compare(o1.getTime(), o2.getTime()));
        if (table.size() > 5) {
            table.remove(5);
        }
        writeFile();
    }

    private void writeFile() {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(path))) {
            for (ScoreRecord record : table) {
                writer.println(record.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void readFile() {
        try (Scanner scanner = new Scanner(new FileInputStream(path))) {
            while (scanner.hasNextLine()) {
                String record = scanner.nextLine();

                String name = record.substring(0, record.indexOf(":"));
                String time = record.substring(name.length() + 2);
                table.add(new ScoreRecord(name, toFloat(time)));
            }
        } catch (FileNotFoundException e) {
            System.out.println();
        }
    }

    private static float toFloat(String time) {
        int minutes = Integer.parseInt(time.substring(0, time.indexOf(":")));
        int seconds = Integer.parseInt(time.substring(time.indexOf(":") + 1, time.indexOf(".")));
        int milliseconds = Integer.parseInt(time.substring(time.indexOf(".") + 1));

        return minutes * 60000 + seconds * 1000 + milliseconds * 10;
    }

    public void print() {
        try (Scanner scanner = new Scanner(new FileInputStream(path))) {
            while (scanner.hasNextLine()) {
                String record = scanner.nextLine();
                System.out.println(record);
            }
        } catch (FileNotFoundException e) {
            System.out.println("В этой игре еще никто не побеждал");
        }
    }
}
