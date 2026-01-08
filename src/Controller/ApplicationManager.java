package Controller;

import GUI.MainFrame;
import GUI.Subscriber;
import model.EducationLevel;
import model.JobOpening;
import model.JobSeeker;
import repository.FileJobOpeningRepository;
import repository.FileJobSeekerRepository;
import repository.JobOpeningRepository;
import repository.JobSeekerRepository;
import service.MatchingService;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManager implements Subscriber {
    private List<Subscriber> subscribers = new ArrayList<>();
    private MainFrame mainFrame;
    private MatchingService matchingService;
    private JobOpeningRepository openingRepo;
    private JobSeekerRepository seekerRepo;
    private boolean waitingForEdit = false;
    private boolean waitingForRemove = false;
    private String[] newInfo;

    public ApplicationManager() {
        mainFrame = new MainFrame(this);
        this.openingRepo = new FileJobOpeningRepository(this);
        this.seekerRepo = new FileJobSeekerRepository(this);
        this.matchingService = new MatchingService(seekerRepo, openingRepo, this);
    }

    @Override
    public void update(Subscriber.EventType option, Object data) {
        System.out.println("update in ApplicationManager was reached. eventtype is: " + option);
        System.out.println("waitingForEdit is: " + waitingForEdit);
        System.out.println("waitingForRemove is: " + waitingForRemove);
        switch (option) {
            case REQUEST_HANDLE_SEEKER -> {
                mainFrame.showSeekerPanel();
            }
            case REQUEST_HANDLE_OPENING -> {
                mainFrame.showJobPanel();
            }
            case REQUEST_SEE_SEEKER_LIST -> {
                List<JobSeeker> seekers = matchingService.getAllSeekers();
                if (seekers.isEmpty()) {
                    mainFrame.showResultPanel(EventType.RETURN_SEEKER_NOT_FOUND, null);
                } else {
                    mainFrame.showResultPanel(EventType.RETURN_FOUND_SEEKERS, seekers);
                }
            }
            case REQUEST_SEE_OPENING_LIST -> {
                System.out.println("case in AppManager: see job list. ");
                List<JobOpening> jobOpenings = matchingService.getAllJobOpenings();
                System.out.println("list length :" + jobOpenings.size());
                if (jobOpenings.isEmpty()) {
                    mainFrame.showResultPanel(EventType.RETURN_OPENING_NOT_FOUND, null);
                } else {
                   mainFrame.showResultPanel(EventType.RETURN_FOUND_OPENINGS, jobOpenings);
                }
            }
            case REQUEST_ADD_SEEKER, REQUEST_ADD_OPENING -> {
                mainFrame.showAdderPanel(option);
            }
            case REQUEST_SEARCH_SEEKER, REQUEST_SEARCH_OPENING -> {
                mainFrame.showSearchPanel(option);
            }
            case RETURN_SEARCH_SEEKER-> {
                String name = (String) data;
                matchingService.findSeekersByName(name);
            }
            case RETURN_SEARCH_OPENING -> {
                String title =  (String) data;
                matchingService.findJobOpeningsByTitle(title);
            }
            case RETURN_SEEKER_NOT_FOUND, RETURN_OPENING_NOT_FOUND, RETURN_FOUND_OPENINGS, RETURN_FOUND_SEEKERS -> {
                if (waitingForEdit) {
                    mainFrame.showEditPanel(option, data);
                    waitingForEdit = false;
                } else if (waitingForRemove) {
                    mainFrame.showRemovePanel(option);
                    waitingForRemove = false;
                } else {
                    mainFrame.showResultPanel(option, data);
                }
            }
            case REQUEST_MATCH -> {
                mainFrame.showMatchPanel();
            }
            case RETURN_ADD_SEEKER -> {
                JobSeeker newSeeker = createNewSeeker(data);
                matchingService.addSeeker(newSeeker);

            }
            case RETURN_ADD_OPENING -> {
               JobOpening newOpening = createNewOpening(data);
                matchingService.addJobOpening(newOpening);
            }
                case REQUEST_EDIT_OPENING, REQUEST_EDIT_SEEKER -> {
                mainFrame.showEditPanel(option, data);
            }
            case RETURN_EDITING_OPENING -> {
                waitingForEdit = true;
                String title = (String) data;
                matchingService.findJobOpeningsByTitle(title);
            }
            case RETURN_EDITING_SEEKER -> {
                waitingForEdit = true;
                String name = (String) data;
                matchingService.findSeekersByName(name);
            }
            case RETURN_EDIT_THIS_OPENING ->{
                if (data instanceof String[]) {
                    newInfo = (String[]) data;
                }
                if (data instanceof JobOpening jobOpening){
                    editOpening(jobOpening, newInfo);
                }
            }
            case RETURN_EDIT_THIS_SEEKER -> {
                System.out.println("EDIT_THIS_SEEKER in applicationManager is reached. Data is: " + data.getClass());
               if (data instanceof String[]) {
                   newInfo = (String[]) data;
                }
                if (data instanceof JobSeeker seeker){
                    editSeeker(seeker, newInfo);
                }
            }
            case REQUEST_DELETE_OPENING, REQUEST_DELETE_SEEKER -> {
                mainFrame.showRemovePanel(option);
            }
            case RETURN_REMOVE_SEEKER -> {
                this.waitingForRemove = true;
                matchingService.deleteSeeker((String) data);
            }
            case RETURN_REMOVE_OPENING -> {
                this.waitingForRemove = true;
                matchingService.deleteJobOpening((String) data);
            }
            case RETURN_ADD_SUCCESSFUL, RETURN_EDIT_SUCCESSFUL, RETURN_REMOVE_SUCCESSFUL -> {
                mainFrame.showResultPanel(option, data);
            }
        }
    }

    private JobSeeker createNewSeeker(Object data){
        String[]userInput = (String[]) data;
        String name = userInput[0];
        String experience = userInput[1];
        String branch = userInput[2];
        String education = userInput[3];
        int thisExp = 0;
        try {
            thisExp = Integer.parseInt(experience);
        }
        catch (NumberFormatException e) {

        }
        EducationLevel thisEdu = null;
        for (EducationLevel level : EducationLevel.values()){
            if (level.toString().equalsIgnoreCase(education)){
                thisEdu = level;
            }
        }
        return new JobSeeker(name, thisEdu, thisExp, branch);
    }
    private JobOpening createNewOpening(Object data){
        String[]userInput = (String[]) data;
        String title = userInput[0];
        String experience = userInput[1];
        String branch = userInput[2];
        String education = userInput[3];
        int thisExp = 0;
        try {
            thisExp = Integer.parseInt(experience);
        }
        catch (NumberFormatException e) {

        }
        EducationLevel thisEdu = null;
        for (EducationLevel level : EducationLevel.values()){
            if (level.toString().equalsIgnoreCase(education)){
                thisEdu = level;
            }
        }
        return new JobOpening(title, thisEdu, thisExp, branch);
    }
    private void editSeeker(JobSeeker seeker, String[]newInfo){
        String name = newInfo[0];
        String experience = newInfo[1];
        String branch = newInfo[2];
        int thisExp = 0;
        try {
            thisExp = Integer.parseInt(experience);
        }
        catch (NumberFormatException e) {
        }
        String education = newInfo[3];
        EducationLevel thisEdu = null;
        for (EducationLevel level : EducationLevel.values()){
            if (level.toString().equalsIgnoreCase(education)){
                thisEdu = level;
            }
        }
        seeker.setName(name);
        seeker.setYearsExperience(thisExp);
        seeker.setEducationLevel(thisEdu);
        seeker.setWorkArea(branch);
        matchingService.updateSeeker(seeker);
    }

    private void editOpening(JobOpening opening, String[]newInfo){
        String title = newInfo[0];
        String experience = newInfo[1];
        String branch = newInfo[2];
        int thisExp = 0;
        try {
            thisExp = Integer.parseInt(experience);
        }
        catch (NumberFormatException e) {
        }
        String education = newInfo[3];
        EducationLevel thisEdu = null;
        for (EducationLevel level : EducationLevel.values()){
            if (level.toString().equalsIgnoreCase(education)){
                thisEdu = level;
            }
        }
        opening.setTitle(title);
        opening.setMinYearsExperience(thisExp);
        opening.setRequiredEducation(thisEdu);
        opening.setWorkArea(branch);
        matchingService.updateJobOpening(opening);
    }

    private void notifySubscribers(Subscriber.EventType e, Object data) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(e, data);
        }
    }

    public void addSubscriber(Subscriber s) {
        subscribers.add(s);
    }
}




//    public void start() {
//        boolean running = true;
//        while (running) {
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

//
//
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
//            int choice = readIntInput();
//            switch (choice) {
//                case 1:
//                    listJobs();
//                    break;
//                case 2:
//                    addJob();
//                    break;
//                case 3:
//                    findJob();
//                    break;
//                case 4:
//                    updateJob();
//                    break;
//                case 5:
//                    deleteJob();
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
//    private void findJob() {
//        System.out.println("\n--- Find Job by Title ---");
//        System.out.print("Enter title (partial matches allowed): ");
//        String title = scanner.nextLine();
//        List<JobOpening> jobs = matchingService.findJobOpeningsByTitle(title);
//        if (jobs.isEmpty()) {
//            System.out.println("No jobs found.");
//        } else {
//            for (JobOpening job : jobs) {
//                System.out.println(job);
//            }
//        }
//    }
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
//    }


