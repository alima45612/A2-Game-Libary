public class Game {
    private String title;
    private String platform;
    private String status;
    private String gameType;

    public Game(String title, String platform, String status, String gameType) {
        this.title = title;
        this.platform = platform;
        this.status = status;
        this.gameType = gameType;
    }

    public String getTitle() {
        return title;
    }

    public String getPlatform() {
        return platform;
    }

    public String getStatus() {
        return status;
    }

    public String getGameType() {
        return gameType;
    }

    @Override
    public String toString() {
        return "Title: " + title + " | Platform: " + platform + " | Status: " + status + " | Type: " + gameType;
    }
}

