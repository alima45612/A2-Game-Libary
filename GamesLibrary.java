import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class GamesLibrary {
    private static List<User> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "UsersData.txt";
    private static User currentUser = null;

    public static void run() {
        loadUsers();  // Load users' data from the file on startup

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
                    saveUsers();  // Save users' data when exiting
                    running = false;
                    System.out.println("Goodbye!");
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            } else {
                // Menu for the logged-in user
                System.out.println("\n=== " + currentUser.getName() + "'s Library ===");
                System.out.println("1. View Preferred Platform");
                System.out.println("2. View Owned Games");
                System.out.println("3. Add Game");
                System.out.println("4. Delete Game");
                System.out.println("5. Logout");
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
                        currentUser = null;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        // Check if user exists
        for (User user : users) {
            if (user.getName().equals(username)) {
                currentUser = user;
                System.out.println("Welcome back, " + username + "!");
                return;
            }
        }

        // If the user doesn't exist, create a new user
        System.out.print("Enter preferred platform: ");
        String platform = scanner.nextLine();
        currentUser = new User(username, platform);
        users.add(currentUser);
        System.out.println("New user created! Welcome, " + username + "!");
    }

    private static void viewPreferredPlatform() {
        System.out.println("Preferred Platform: " + currentUser.getPreferredPlatform());
    }

    private static void viewOwnedGames() {
        if (currentUser.getOwnedGames().isEmpty()) {
            System.out.println("You haven't added any games yet.");
        } else {
            System.out.println("\nYour Owned Games:");
            for (Game game : currentUser.getOwnedGames()) {
                System.out.println(game);
            }
        }
    }

    private static void addGame() {
        System.out.print("Enter game title: ");
        String title = scanner.nextLine();

        System.out.print("Enter platform: ");
        String platform = scanner.nextLine();

        System.out.print("Enter status (e.g., Completed, Playing, Wishlist): ");
        String status = scanner.nextLine();

        Game newGame = new Game(title, platform, status);
        currentUser.addGame(newGame);
        System.out.println("Game added!");
        saveUsers();  // Save users' data after adding a game
    }

    private static void deleteGame() {
        viewOwnedGames();
        if (currentUser.getOwnedGames().isEmpty()) return;

        System.out.print("Enter the number of the game to delete: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < currentUser.getOwnedGames().size()) {
                currentUser.removeGame(currentUser.getOwnedGames().get(index));
                System.out.println("Game deleted.");
                saveUsers();  // Save users' data after deleting a game
            } else {
                System.out.println("Invalid number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userParts = line.split("\\|");
                if (userParts.length == 2) {
                    String username = userParts[0].trim();
                    String preferredPlatform = userParts[1].trim();
                    User user = new User(username, preferredPlatform);
                    users.add(user);
                }

                // Read and assign owned games for each user
                line = reader.readLine();
                if (line != null) {
                    String[] gameParts = line.split("\\|");
                    for (String gameData : gameParts) {
                        String[] gameDetails = gameData.split(",");
                        if (gameDetails.length == 3) {
                            String title = gameDetails[0].trim();
                            String platform = gameDetails[1].trim();
                            String status = gameDetails[2].trim();
                            Game game = new Game(title, platform, status);
                            users.get(users.size() - 1).addGame(game);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading users data: " + e.getMessage());
        }
    }

    private static void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                writer.write(user.getName() + " | " + user.getPreferredPlatform());
                writer.newLine();
                for (Game game : user.getOwnedGames()) {
                    writer.write(game.getTitle() + "," + game.getPlatform() + "," + game.getStatus() + "|");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users data: " + e.getMessage());
        }
    }
}
