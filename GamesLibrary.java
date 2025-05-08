import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GamesLibrary {
    private static List<User> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "UsersData.txt";
    private static User currentUser = null;

    public static void run() {
        loadUsers();

        boolean running = true;

        while (running) {
            if (currentUser == null) {
                System.out.println("\n=== Welcome ===");
                System.out.println("1. Login");
                System.out.println("2. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    login();
                } else if (choice.equals("2")) {
                    saveUsers();
                    running = false;
                    System.out.println("Goodbye!");
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            } else {
                System.out.println("\n=== " + currentUser.getName() + "'s Library ===");
                System.out.println("1. View Preferred Platform");
                System.out.println("2. View Owned Games");
                System.out.println("3. Add Game");
                System.out.println("4. Delete Game");
                System.out.println("5. Update Game Progress");
                System.out.println("6. Logout");
                System.out.print("Choose an option: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        viewPreferredPlatform();
                        break;
                    case "2":
                        viewOwnedGames();
                        break;
                    case "3":
                        addGame();
                        break;
                    case "4":
                        deleteGame();
                        break;
                    case "5":
                        updateGameProgress();
                        break;
                    case "6":
                        currentUser = null;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        }
    }

    private static void login() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                currentUser = user;
                System.out.println("Welcome back, " + name + "!");
                return;
            }
        }

        System.out.print("New user! Enter your preferred platform: ");
        String platform = scanner.nextLine();
        currentUser = new User(name, platform);
        users.add(currentUser);
        saveUsers();
        System.out.println("User created!");
    }

    private static void viewPreferredPlatform() {
        System.out.println("Preferred Platform: " + currentUser.getPreferredPlatform());
    }

    private static void viewOwnedGames() {
        if (currentUser.getOwnedGames().isEmpty()) {
            System.out.println("No games found.");
        } else {
            for (int i = 0; i < currentUser.getOwnedGames().size(); i++) {
                System.out.println((i + 1) + ". " + currentUser.getOwnedGames().get(i));
            }
        }
    }

    private static void addGame() {
        System.out.print("Enter game title: ");
        String title = scanner.nextLine();

        System.out.print("Enter game platform: ");
        String platform = scanner.nextLine();

        System.out.print("Enter game status (e.g., Completed, In Progress): ");
        String status = scanner.nextLine();

        System.out.print("Enter game type (Singleplayer / Multiplayer / Online): ");
        String gameType = scanner.nextLine().toLowerCase();

        Game newGame = null;

        switch (gameType) {
            case "singleplayer":
                System.out.print("Enter levels completed: ");
                int levels = Integer.parseInt(scanner.nextLine());
                newGame = new SingleplayerGame(title, platform, status, levels);
                break;

            case "multiplayer":
                System.out.print("Enter number of wins: ");
                int wins = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter number of losses: ");
                int losses = Integer.parseInt(scanner.nextLine());
                newGame = new MultiplayerGame(title, platform, status, wins, losses);
                break;

            case "online":
                System.out.print("Enter time played (in minutes): ");
                int timePlayed = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter achievements unlocked: ");
                int achievements = Integer.parseInt(scanner.nextLine());
                newGame = new OnlineGame(title, platform, status, timePlayed, achievements);
                break;

            default:
                System.out.println("Invalid game type.");
                return;
        }

        currentUser.addGame(newGame);
        System.out.println("Game added!");
        saveUsers();
    }

    private static void deleteGame() {
        viewOwnedGames();
        if (currentUser.getOwnedGames().isEmpty()) return;

        System.out.print("Enter the number of the game to delete: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index >= 0 && index < currentUser.getOwnedGames().size()) {
            Game removed = currentUser.getOwnedGames().remove(index);
            System.out.println("Removed: " + removed.getTitle());
            saveUsers();
        } else {
            System.out.println("Invalid number.");
        }
    }

    private static void updateGameProgress() {
        if (currentUser.getOwnedGames().isEmpty()) {
            System.out.println("You don't have any games yet.");
            return;
        }

        System.out.println("\nSelect a game to update progress:");
        for (int i = 0; i < currentUser.getOwnedGames().size(); i++) {
            System.out.println((i + 1) + ". " + currentUser.getOwnedGames().get(i));
        }

        System.out.print("Enter the number of the game to update progress: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index >= 0 && index < currentUser.getOwnedGames().size()) {
            Game game = currentUser.getOwnedGames().get(index);
            game.updateProgress();
            saveUsers();
        } else {
            System.out.println("Invalid number.");
        }
    }

    private static void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                writer.println("USER:" + user.getName() + "," + user.getPreferredPlatform());
                for (Game game : user.getOwnedGames()) {
                    StringBuilder line = new StringBuilder("GAME:");
                    line.append(game.getGameType()).append(",")
                        .append(game.getTitle()).append(",")
                        .append(game.getPlatform()).append(",")
                        .append(game.getStatus());

                    if (game instanceof SingleplayerGame sp) {
                        line.append(",").append(sp.getLevelsCompleted());
                    } else if (game instanceof MultiplayerGame mp) {
                        line.append(",").append(mp.getWins()).append(",").append(mp.getLosses());
                    } else if (game instanceof OnlineGame og) {
                        line.append(",").append(og.getTimePlayed()).append(",").append(og.getAchievementsUnlocked());
                    }

                    writer.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            User current = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("USER:")) {
                    String[] parts = line.substring(5).split(",");
                    current = new User(parts[0], parts[1]);
                    users.add(current);
                } else if (line.startsWith("GAME:") && current != null) {
                    String[] parts = line.substring(5).split(",");
                    String type = parts[0];
                    String title = parts[1];
                    String platform = parts[2];
                    String status = parts[3];

                    Game game = null;

                    switch (type.toLowerCase()) {
                        case "singleplayer":
                            int levels = Integer.parseInt(parts[4]);
                            game = new SingleplayerGame(title, platform, status, levels);
                            break;
                        case "multiplayer":
                            int wins = Integer.parseInt(parts[4]);
                            int losses = Integer.parseInt(parts[5]);
                            game = new MultiplayerGame(title, platform, status, wins, losses);
                            break;
                        case "online":
                            int timePlayed = Integer.parseInt(parts[4]);
                            int achievements = Integer.parseInt(parts[5]);
                            game = new OnlineGame(title, platform, status, timePlayed, achievements);
                            break;
                    }

                    if (game != null) {
                        current.addGame(game);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // It's okay if the file doesn't exist yet
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }
}
