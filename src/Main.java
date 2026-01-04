import model.EducationLevel;
import model.JobOpening;
import model.JobSeeker;
import model.MatchResult;
import repository.FileJobOpeningRepository;
import repository.FileJobSeekerRepository;
import repository.JobOpeningRepository;
import repository.JobSeekerRepository;
import service.MatchingService;
import strategy.StrategyType;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Dream Job Finder Work ===");

        // create the repositories to save to our files
        JobSeekerRepository seekerRepo = new FileJobSeekerRepository("jobSeekers.txt");
        JobOpeningRepository openingRepo = new FileJobOpeningRepository("jobOpenings.txt");

        // create the service that handles the logic
        MatchingService matchingService = new MatchingService(seekerRepo, openingRepo);

        // make some testing data
        JobSeeker alice = new JobSeeker("Alice Engineer", EducationLevel.MASTER, 5, "Software");
        JobSeeker bob = new JobSeeker("Bob Junior", EducationLevel.BACHELOR, 1, "Software");

        // save to text file
        seekerRepo.save(alice);
        seekerRepo.save(bob);
        System.out.println("Saved Users: " + alice.getFullName() + ", " + bob.getFullName());

        // make a job opening
        JobOpening seniorDev = new JobOpening("Senior Java Dev", EducationLevel.BACHELOR, 5, "Software");
        openingRepo.save(seniorDev);
        System.out.println("Saved Job: " + seniorDev.getTitle());

        // test strict matching (everything must be perfect)
        System.out.println("\n--- Testing Strict Strategy ---");
        List<MatchResult> strictResults = matchingService.matchCandidatesToJob(seniorDev.getId(), StrategyType.STRICT);

        for (MatchResult result : strictResults) {
            System.out.println("Score: " + result.getScore() + " for " + result.getJobSeeker().getFullName());
        }

        // test flexible matching (gives points for being close)
        System.out.println("\n--- Testing Flexible Strategy ---");
        List<MatchResult> flexibleResults = matchingService.matchCandidatesToJob(seniorDev.getId(),
                StrategyType.FLEXIBLE);

        for (MatchResult result : flexibleResults) {
            System.out.println("Score: " + result.getScore() + " for " + result.getJobSeeker().getFullName());
        }

        // test education focused matching
        System.out.println("\n--- Testing Education Focused Strategy ---");
        List<MatchResult> eduResults = matchingService.matchCandidatesToJob(seniorDev.getId(),
                StrategyType.EDUCATION_FOCUSED);

        for (MatchResult result : eduResults) {
            System.out.println("Score: " + result.getScore() + " for " + result.getJobSeeker().getFullName());
        }

        System.out.println("\n=== Done ===");
    }
}
