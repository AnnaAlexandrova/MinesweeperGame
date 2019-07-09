package game;

public class ScoreRecord {
    private String name;
    private float time;

    public ScoreRecord(String name, float time) {
        this.name = name;
        this.time = time;
    }

    float getTime() {
        return time;
    }

    public String toString() {
        final int MINUTES = (int) time / 1000 / 60;
        final int SECONDS = (int) time / 1000 % 60;
        final int MILLISECONDS = (int) time % 1000 / 10;
        return (String.format("%s: %02d:%02d.%02d", name, MINUTES, SECONDS, MILLISECONDS));
    }
}
