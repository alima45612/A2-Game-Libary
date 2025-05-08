import java.util.Scanner;

public class OnlineGame extends Game {
    private int timePlayed;
    private int achievementsUnlocked;

    public OnlineGame(String title, String platform, String status, int timePlayed, int achievementsUnlocked) {
        super(title, platform, status);
        this.timePlayed = timePlayed;
        this.achievementsUnlocked = achievementsUnlocked;
    }

    public int getTimePlayed() {
        return timePlayed;
    }

    public int getAchievementsUnlocked() {
        return achievementsUnlocked;
    }

    @Override
    public String getGameType() {
        return "Online";
    }

    @Override
    public void updateProgress() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new time played (minutes): ");
        timePlayed = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new number of achievements unlocked: ");
        achievementsUnlocked = Integer.parseInt(scanner.nextLine());
        System.out.println("Progress updated.");
    }

    @Override
    public String toString() {
        return super.toString() + " | Time played: " + timePlayed + " mins | Achievements: " + achievementsUnlocked;
    }
}
