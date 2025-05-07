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
    private static List<Game> library = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "GameLibrary.txt";

    public static void run() {
        loadGames();  // Load saved games on startup

        boolean running = true;

        while (running) {
            System.out.println("\n=== Games Library ===");
            System.out.println("1. Add Game");
            System.out.println("2. View Games");
            System.out.println("3. Delete Game");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addGame();
                    break;
                case "2":
                    viewGames();
                    break;
                case "3":
                    deleteGame();
                    break;
                case "4":
                    saveGames();  // Save games when exiting
                    running = false;
                    System.out.println("Exiting the library. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addGame() {
        System.out.print("Enter game title: ");
        String title = scanner.nextLine();

        System.out.print("Enter platform: ");
        String platform = scanner.nextLine();

        System.out.print("Enter status (e.g. Completed, Playing, Wishlist): ");
        String status = scanner.nextLine();

        library.add(new Game(title, platform, status));
        System.out.println("Game added!");
        saveGames();  // Save games after adding
    }

    private static void viewGames() {
        if (library.isEmpty()) {
            System.out.println("Your library is empty.");
        } else {
            System.out.println("\nYour Games:");
            for (int i = 0; i < library.size(); i++) {
                System.out.println((i + 1) + ". " + library.get(i));
            }
        }
    }

    private static void deleteGame() {
        viewGames();
        if (library.isEmpty()) return;

        System.out.print("Enter the number of the game to delete: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < library.size()) {
                library.remove(index);
                System.out.println("Game deleted.");
                saveGames();  // Save games after deletion
            } else {
                System.out.println("Invalid number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private static void loadGames() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String title = parts[0].trim();
                    String platform = parts[1].trim();
                    String status = parts[2].trim();
                    library.add(new Game(title, platform, status));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous game library found, starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading game library: " + e.getMessage());
        }
    }

    private static void saveGames() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Game game : library) {
                writer.write(game.getTitle() + " | " + game.getPlatform() + " | " + game.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving game library: " + e.getMessage());
        }
    }
}
