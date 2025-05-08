import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String preferredPlatform;
    private List<Game> ownedGames;

    public User(String name, String preferredPlatform) {
        this.name = name;
        this.preferredPlatform = preferredPlatform;
        this.ownedGames = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPreferredPlatform() {
        return preferredPlatform;
    }

    public List<Game> getOwnedGames() {
        return ownedGames;
    }

    public void addGame(Game game) {
        ownedGames.add(game);
    }
}
