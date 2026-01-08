//package ui;
//
//import service.MatchingService;
//import model.JobSeeker;
//import model.JobOpening;
//import model.EducationLevel;
//import model.MatchResult;
//import strategy.StrategyType;
//
//import java.util.Scanner;
//import java.util.List;
//
//public class CommandLineInterface {
//    private final MatchingService matchingService;
//    private final Scanner scanner;
//
//    public CommandLineInterface(MatchingService matchingService) {
//        this.matchingService = matchingService;
//        this.scanner = new Scanner(System.in);
//    }
//
//    public void start() {
//        System.out.println("Welcome to Dream Job Finder!");
//        boolean running = true;
//
//        while (running) {
//            printMainMenu();
//            int choice = readIntInput();
//
//            switch (choice) {
//                case 1:
//                    handleSeekers();
//                    break;
//                case 2:
//                    handleJobs();
//                    break;
//                case 3:
//                    handleMatching();
//                    break;
//                case 4:
//                    running = false;
//                    System.out.println("Goodbye!");
//                    break;
//                default:
//                    System.out.println("Invalid option, please try again.");
//            }
//        }
//    }
//
//    private void printMainMenu() {
//        System.out.println("\n--- Main Menu ---");
//        System.out.println("1. Manage Job Seekers");
//        System.out.println("2. Manage Job Openings");
//        System.out.println("3. Match Jobs");
//        System.out.println("4. Exit");
//        System.out.print("Enter choice: ");
//    }
//
//    // --- Seeker Management ---
//    private void handleSeekers() {
//        boolean back = false;
//        while (!back) {
//            System.out.println("\n--- Manage Job Seekers ---");
//            System.out.println("1. List All Seekers");
//            System.out.println("2. Add new Seeker");
//            System.out.println("3. Find Seeker by Name");
//            System.out.println("4. Update Seeker");
//            System.out.println("5. Delete Seeker");
//            System.out.println("6. Back to Main Menu");
//            System.out.print("Enter choice: ");
//
//            int choice = readIntInput();
//            switch (choice) {
//                case 1:
//                    listSeekers();
//                    break;
//                case 2:
//                    addSeeker();
//                    break;
//                case 3:
//                    findSeeker();
//                    break;
//                case 4:
//                    updateSeeker();
//                    break;
//                case 5:
//                    deleteSeeker();
//                    break;
//                case 6:
//                    back = true;
//                    break;
//                default:
//                    System.out.println("Invalid option.");
//            }
//        }
//    }
//
//    private void listSeekers() {
//        System.out.println("\n--- List of Seekers ---");
//        List<JobSeeker> seekers = matchingService.getAllSeekers();
//        if (seekers.isEmpty()) {
//            System.out.println("No job seekers found.");
//        } else {
//            for (JobSeeker seeker : seekers) {
//                System.out.println(seeker);
//            }
//        }
//    }
//
//    private void addSeeker() {
//        System.out.println("\n--- Add New Seeker ---");
//        System.out.print("Enter Full Name: ");
//        String name = scanner.nextLine();
//
//        System.out.println("Select Education Level:");
//        for (EducationLevel level : EducationLevel.values()) {
//            System.out.println(level.ordinal() + ". " + level);
//        }
//        System.out.print("Choice: ");
//        int eduChoice = readIntInput();
//        EducationLevel eduLevel = EducationLevel.NONE;
//        if (eduChoice >= 0 && eduChoice < EducationLevel.values().length) {
//            eduLevel = EducationLevel.values()[eduChoice];
//        }
//
//        System.out.print("Years of Experience: ");
//        int experience = readIntInput();
//
//        System.out.print("Enter Work Area / Skills: ");
//        String workArea = scanner.nextLine();
//
//        JobSeeker newSeeker = new JobSeeker(name, eduLevel, experience, workArea);
//        matchingService.addSeeker(newSeeker);
//        System.out.println("Job Seeker added successfully!");
//    }
//
//    private void findSeeker() {
//        System.out.println("\n--- Find Seeker by Name ---");
//        System.out.print("Enter Name: ");
//        String name = scanner.nextLine();
//        List<JobSeeker> results = matchingService.findSeekersByName(name);
//        if (results.isEmpty()) {
//            System.out.println("No seekers found.");
//        } else {
//            for (JobSeeker js : results) {
//                System.out.println(js);
//            }
//        }
//    }
//
//    private void updateSeeker() {
//        System.out.println("\n--- Update Job Seeker ---");
//        System.out.print("Enter Seeker ID to update: ");
//        String id = scanner.nextLine();
//
//        System.out.print("Enter new Full Name: ");
//        String name = scanner.nextLine();
//
//        System.out.println("Select Education Level:");
//        for (EducationLevel level : EducationLevel.values()) {
//            System.out.println(level.ordinal() + ". " + level);
//        }
//        System.out.print("Choice: ");
//        int eduIdx = readIntInput();
//        EducationLevel edu = EducationLevel.values()[eduIdx];
//
//        System.out.print("Enter years of experience: ");
//        int experience = readIntInput();
//
//        System.out.print("Enter work area (e.g. Software, Sales): ");
//        String workArea = scanner.nextLine();
//
//        JobSeeker updatedSeeker = new JobSeeker(id, name, edu, experience, workArea);
//        matchingService.updateSeeker(updatedSeeker);
//        System.out.println("Seeker updated (if ID existed).");
//    }
//
//    private void deleteSeeker() {
//        System.out.println("\n--- Delete Job Seeker ---");
//        System.out.print("Enter Seeker ID to delete: ");
//        String id = scanner.nextLine();
//        matchingService.deleteSeeker(id);
//        System.out.println("Seeker deleted (if ID existed).");
//    }
//
//    // --- Job Opening Management ---
//    private void handleJobs() {
//        boolean back = false;
//        while (!back) {
//            System.out.println("\n--- Manage Job Openings ---");
//            System.out.println("1. List All Jobs");
//            System.out.println("2. Add new Job");
//            System.out.println("3. Find Job by Title");
//            System.out.println("4. Update Job");
//            System.out.println("5. Delete Job");
//            System.out.println("6. Back to Main Menu");
//            System.out.print("Enter choice: ");
//
//
//        }
//    }
//
//    // --- Job Opening Helper Methods ---
//    private void listJobs() {
//        List<JobOpening> jobs = matchingService.getAllJobOpenings();
//        if (jobs.isEmpty()) {
//            System.out.println("No job openings found.");
//        } else {
//            System.out.println("\n--- Job Openings ---");
//            for (JobOpening job : jobs) {
//                System.out.println(job);
//            }
//        }
//    }
//
//    private void addJob() {
//        System.out.println("\n--- Add Job Opening ---");
//        System.out.print("Enter Job Title: ");
//        String title = scanner.nextLine();
//
//        System.out.println("Select Required Education Level:");
//        for (EducationLevel level : EducationLevel.values()) {
//            System.out.println(level.ordinal() + ". " + level);
//        }
//        System.out.print("Choice: ");
//        int eduIdx = readIntInput();
//        EducationLevel edu = EducationLevel.values()[eduIdx];
//
//        System.out.print("Enter minimum years of experience: ");
//        int experience = readIntInput();
//
//        System.out.print("Enter work area: ");
//        String workArea = scanner.nextLine();
//
//        JobOpening newJob = new JobOpening(title, edu, experience, workArea);
//        matchingService.addJobOpening(newJob);
//        System.out.println("Job Opening added with ID: " + newJob.getId());
//    }
//
////    private void findJob() {
////        System.out.println("\n--- Find Job by Title ---");
////        System.out.print("Enter title (partial matches allowed): ");
////        String title = scanner.nextLine();
////        List<JobOpening> jobs = matchingService.findJobOpeningsByTitle(title);
////        if (jobs.isEmpty()) {
////            System.out.println("No jobs found.");
////        } else {
////            for (JobOpening job : jobs) {
////                System.out.println(job);
////            }
////        }
////    }
//
//    private void updateJob() {
//        System.out.println("\n--- Update Job Opening ---");
//        System.out.print("Enter Job ID to update: ");
//        String id = scanner.nextLine();
//
//        System.out.print("Enter new Title: ");
//        String title = scanner.nextLine();
//
//        System.out.println("Select Required Education Level:");
//        for (EducationLevel level : EducationLevel.values()) {
//            System.out.println(level.ordinal() + ". " + level);
//        }
//        System.out.print("Choice: ");
//        int eduIdx = readIntInput();
//        EducationLevel edu = EducationLevel.values()[eduIdx];
//
//        System.out.print("Enter minimum years of experience: ");
//        int experience = readIntInput();
//
//        System.out.print("Enter work area: ");
//        String workArea = scanner.nextLine();
//
//        JobOpening updatedJob = new JobOpening(id, title, edu, experience, workArea);
//        matchingService.updateJobOpening(updatedJob);
//        System.out.println("Job updated (if ID existed).");
//    }
//
//    private void deleteJob() {
//        System.out.println("\n--- Delete Job Opening ---");
//        System.out.print("Enter Job ID to delete: ");
//        String id = scanner.nextLine();
//        matchingService.deleteJobOpening(id);
//        System.out.println("Job Opening deleted (if ID existed).");
//    }
//
//    // --- Matching Management ---
//    private void handleMatching() {
//        boolean back = false;
//        while (!back) {
//            System.out.println("\n--- Match Jobs ---");
//            System.out.println("1. Match Recommendations for a Seeker");
//            System.out.println("2. Find Candidates for a Job");
//            System.out.println("3. Back to Main Menu");
//            System.out.print("Enter choice: ");
//
//            int choice = readIntInput();
//            switch (choice) {
//                case 1:
//                    matchForSeeker();
//                    break;
//                case 2:
//                    matchForJob();
//                    break;
//                case 3:
//                    back = true;
//                    break;
//                default:
//                    System.out.println("Invalid option.");
//            }
//        }
//    }
//
//    private void matchForSeeker() {
//        System.out.println("\n--- Match Jobs for a Candidate ---");
//        System.out.print("Enter Seeker ID: ");
//        String seekerId = scanner.nextLine();
//
//        StrategyType strategy = selectStrategy();
//
//        List<MatchResult> results = matchingService.matchJobsToCandidate(seekerId, strategy);
//        displayResults(results);
//
//        saveReportPrompt(results);
//    }
//
//    private void matchForJob() {
//        System.out.println("\n--- Match Candidates for a Job ---");
//        System.out.print("Enter Job Opening ID: ");
//        String jobId = scanner.nextLine();
//
//        StrategyType strategy = selectStrategy();
//
//        List<MatchResult> results = matchingService.matchCandidatesToJob(jobId, strategy);
//        displayResults(results);
//
//        saveReportPrompt(results);
//    }
//
//    private StrategyType selectStrategy() {
//        System.out.println("Select Matching Strategy:");
//        System.out.println("1. Standard (Strict)");
//        System.out.println("2. Flexible (Fuzzy)");
//        System.out.println("3. Education Focused");
//        System.out.print("Choice: ");
//        int choice = readIntInput();
//        switch (choice) {
//            case 2:
//                return StrategyType.FLEXIBLE;
//            case 3:
//                return StrategyType.EDUCATION_FOCUSED;
//            default:
//                return StrategyType.STRICT;
//        }
//    }
//
//    private void displayResults(List<MatchResult> results) {
//        if (results.isEmpty()) {
//            System.out.println("No matches found.");
//        } else {
//            System.out.println("\n--- Match Results ---");
//            for (MatchResult result : results) {
//                System.out.println("Score: " + result.getScore() + " | " +
//                        result.getJobSeeker().getFullName() + " <-> " +
//                        result.getJobOpening().getTitle());
//            }
//        }
//    }
//
//    private void saveReportPrompt(List<MatchResult> results) {
//        if (results.isEmpty())
//            return;
//
//        System.out.print("Save report to file? (y/n): ");
//        String ans = scanner.nextLine();
//        if (ans.equalsIgnoreCase("y")) {
//            System.out.print("Enter filename (e.g., report.txt): ");
//            String filename = scanner.nextLine();
//            matchingService.saveMatchReport(results, filename);
//        }
//    }
//
//    // helper to read int safely
//    private int readIntInput() {
//        try {
//            String line = scanner.nextLine();
//            return Integer.parseInt(line);
//        } catch (NumberFormatException e) {
//            return -1;
//        }
//    }
//}
