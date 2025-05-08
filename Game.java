public abstract class Game {
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

    public abstract String getGameType();

    public abstract void updateProgress();

    @Override
    public String toString() {
        return title + " (" + platform + ") - " + status;
    }
}

