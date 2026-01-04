package ui;

import service.MatchingService;
import model.JobSeeker;
import model.EducationLevel;

import java.util.Scanner;
import java.util.List;
import java.util.UUID;

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
                    handleSeekers();
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

    // --- Seeker Management ---
    private void handleSeekers() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Manage Job Seekers ---");
            System.out.println("1. List All Seekers");
            System.out.println("2. Add new Seeker");
            System.out.println("3. Find Seeker by Name");
            System.out.println("4. Update Seeker");
            System.out.println("5. Delete Seeker");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = readIntInput();
            switch (choice) {
                case 1:
                    listSeekers();
                    break;
                case 2:
                    addSeeker();
                    break;
                case 3:
                    findSeeker();
                    break;
                case 4:
                    updateSeeker();
                    break;
                case 5:
                    deleteSeeker();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void listSeekers() {
        System.out.println("\n--- List of Seekers ---");
        List<JobSeeker> seekers = matchingService.getAllSeekers();
        if (seekers.isEmpty()) {
            System.out.println("No job seekers found.");
        } else {
            for (JobSeeker seeker : seekers) {
                System.out.println(seeker);
            }
        }
    }

    private void addSeeker() {
        System.out.println("\n--- Add New Seeker ---");
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();

        System.out.println("Select Education Level:");
        for (EducationLevel level : EducationLevel.values()) {
            System.out.println(level.ordinal() + ". " + level);
        }
        System.out.print("Choice: ");
        int eduChoice = readIntInput();
        EducationLevel eduLevel = EducationLevel.NONE;
        if (eduChoice >= 0 && eduChoice < EducationLevel.values().length) {
            eduLevel = EducationLevel.values()[eduChoice];
        }

        System.out.print("Years of Experience: ");
        int experience = readIntInput();

        System.out.print("Enter Work Area / Skills: ");
        String workArea = scanner.nextLine();

        JobSeeker newSeeker = new JobSeeker(name, eduLevel, experience, workArea);
        matchingService.addSeeker(newSeeker);
        System.out.println("Job Seeker added successfully!");
    }

    private void findSeeker() {
        System.out.println("\n--- Find Seeker by Name ---");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        List<JobSeeker> results = matchingService.findSeekersByName(name);
        if (results.isEmpty()) {
            System.out.println("No seekers found.");
        } else {
            for (JobSeeker js : results) {
                System.out.println(js);
            }
        }
    }

    private void updateSeeker() {
        System.out.println("\n--- Update Seeker ---");
        System.out.print("Enter ID of seeker to update: ");
        String idStr = scanner.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            // Ideally we check if it exists first, but saving will just overwrite or add.
            // Let's prompt for new details.
            System.out.print("Enter New Full Name: ");
            String name = scanner.nextLine();

            System.out.println("Select New Education Level:");
            for (EducationLevel level : EducationLevel.values()) {
                System.out.println(level.ordinal() + ". " + level);
            }
            System.out.print("Choice: ");
            int eduChoice = readIntInput();
            EducationLevel eduLevel = EducationLevel.NONE;
            if (eduChoice >= 0 && eduChoice < EducationLevel.values().length) {
                eduLevel = EducationLevel.values()[eduChoice];
            }

            System.out.print("New Years of Experience: ");
            int experience = readIntInput();

            System.out.print("New Work Area / Skills: ");
            String workArea = scanner.nextLine();

            JobSeeker updatedSeeker = new JobSeeker(id, name, eduLevel, experience, workArea);
            matchingService.updateSeeker(updatedSeeker);
            System.out.println("Job Seeker updated!");

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private void deleteSeeker() {
        System.out.println("\n--- Delete Seeker ---");
        System.out.print("Enter ID of seeker to delete: ");
        String idStr = scanner.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            matchingService.deleteSeeker(id);
            System.out.println("Seeker deleted (if ID existed).");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID format.");
        }
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
