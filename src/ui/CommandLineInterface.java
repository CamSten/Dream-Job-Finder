package ui;

import service.MatchingService;

import java.util.Scanner;

public class CommandLineInterface {
    private final MatchingService matchingService;
    private final Scanner scanner;

    public CommandLineInterface(MatchingService matchingService) {
        this.matchingService = matchingService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to Dream Job Finder!");
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readIntInput();

            switch (choice) {
                case 1:
                    // TODO: handle seekers
                    System.out.println("Manage Job Seekers (Coming Soon)");
                    break;
                case 2:
                    // TODO: handle jobs
                    System.out.println("Manage Job Openings (Coming Soon)");
                    break;
                case 3:
                    // TODO: handle matching
                    System.out.println("Match Jobs (Coming Soon)");
                    break;
                case 4:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Manage Job Seekers");
        System.out.println("2. Manage Job Openings");
        System.out.println("3. Match Jobs");
        System.out.println("4. Exit");
        System.out.print("Enter choice: ");
    }

    // helper to read int safely
    private int readIntInput() {
        try {
            String line = scanner.nextLine();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
