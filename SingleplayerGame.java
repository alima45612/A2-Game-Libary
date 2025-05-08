import java.util.Scanner;

public class SingleplayerGame extends Game {
    private int levelsCompleted;

    public SingleplayerGame(String title, String platform, String status, int levelsCompleted) {
        super(title, platform, status);
        this.levelsCompleted = levelsCompleted;
    }

    public int getLevelsCompleted() {
        return levelsCompleted;
    }

    @Override
    public String getGameType() {
        return "Singleplayer";
    }

    @Override
    public void updateProgress() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new number of levels completed: ");
        levelsCompleted = Integer.parseInt(scanner.nextLine());
        System.out.println("Progress updated.");
    }

    @Override
    public String toString() {
        return super.toString() + " | Levels completed: " + levelsCompleted;
    }
}

