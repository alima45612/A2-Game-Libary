public class Game {
    private String title;
    private String platform;
    private String status;

    public Game(String title, String platform, String status) {
        this.title = title;
        this.platform = platform;
        this.status = status;
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

    public String toString() {
        return "Title: " + title + " | Platform: " + platform + " | Status: " + status;
    }
}
