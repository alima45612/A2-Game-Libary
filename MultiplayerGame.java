import java.util.Scanner;

public class MultiplayerGame extends Game {
    private int wins;
    private int losses;

    public MultiplayerGame(String title, String platform, String status, int wins, int losses) {
        super(title, platform, status);
        this.wins = wins;
        this.losses = losses;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    @Override
    public String getGameType() {
        return "Multiplayer";
    }

    @Override
    public void updateProgress() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new number of wins: ");
        wins = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new number of losses: ");
        losses = Integer.parseInt(scanner.nextLine());
        System.out.println("Progress updated.");
    }

    @Override
    public String toString() {
        return super.toString() + " | W-L: " + wins + "-" + losses;
    }
}

