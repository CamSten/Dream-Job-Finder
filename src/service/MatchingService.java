package service;

import Controller.ApplicationManager;
import GUI.Subscriber;
import model.JobOpening;
import model.JobSeeker;
import model.MatchResult;
import repository.FileJobOpeningRepository;
import repository.FileJobSeekerRepository;
import repository.JobOpeningRepository;
import repository.JobSeekerRepository;
import strategy.MatchingStrategy;
import strategy.MatchingStrategyFactory;
import strategy.StrategyType;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MatchingService {
    private ApplicationManager applicationManager;

    private final JobSeekerRepository seekerRepo;
    private final JobOpeningRepository openingRepo;


    // Wrapper methods for CLI
    public List<JobSeeker> getAllSeekers() {
        System.out.println("getAllSeekers in MatchingService was reached");
        return seekerRepo.findAll();
    }

    public List<JobOpening> getAllJobOpenings() {
        return openingRepo.findAll();
    }

    public void addSeeker(JobSeeker seeker) {
        seekerRepo.save(seeker);
    }

    public void addJobOpening(JobOpening opening) {
        openingRepo.save(opening);
    }

    public void findSeekersByName(String name) {
        List<JobSeeker> results = seekerRepo.findByName(name);
        if (results.isEmpty()){
            applicationManager.update(Subscriber.EventType.RETURN_SEEKER_NOT_FOUND, null);
        }
        else {
            applicationManager.update(Subscriber.EventType.RETURN_FOUND_SEEKERS, results);
        }
    }

    public void updateSeeker(JobSeeker seeker) {
        seekerRepo.update(seeker);
        applicationManager.update(Subscriber.EventType.RETURN_EDIT_SUCCESSFUL, seeker);
    }

    public void deleteSeeker(String id) {
        seekerRepo.delete(id);
        applicationManager.update(Subscriber.EventType.RETURN_REMOVE_SUCCESSFUL, id);
    }

    public void findJobOpeningsByTitle(String title) {
        List<JobOpening> results = openingRepo.findByTitle(title);
        if (results.isEmpty()){
            applicationManager.update(Subscriber.EventType.RETURN_OPENING_NOT_FOUND, null);
        }
        else {
            applicationManager.update(Subscriber.EventType.RETURN_FOUND_OPENINGS, results);
        }
    }

    public void updateJobOpening(JobOpening opening) {
        openingRepo.update(opening);
        applicationManager.update(Subscriber.EventType.RETURN_EDIT_SUCCESSFUL, opening);
    }

    public void deleteJobOpening(String id) {
        openingRepo.delete(id);
        applicationManager.update(Subscriber.EventType.RETURN_REMOVE_SUCCESSFUL, id);
    }

    public MatchingService(JobSeekerRepository seekerRepo, JobOpeningRepository openingRepo, ApplicationManager applicationManager) {
        this.seekerRepo = seekerRepo;
        this.openingRepo = openingRepo;
        this.applicationManager = applicationManager;
    }

    // Matches all candidates to a specific job opening
    public List<MatchResult> matchCandidatesToJob(String jobOpeningId, StrategyType strategyType) {
        // 1. Get the job opening
        JobOpening job = openingRepo.findById(jobOpeningId).orElse(null);
        if (job == null) {
            System.err.println("Job Opening not found: " + jobOpeningId);
            return new ArrayList<>();
        }

        // 2. Get all candidates
        List<JobSeeker> seekers = seekerRepo.findAll();
        List<MatchResult> results = new ArrayList<>();

        // 3. Get the strategy
        MatchingStrategy strategy = MatchingStrategyFactory.getStrategy(strategyType);

        // 4. Run matching loop
        for (JobSeeker seeker : seekers) {
            MatchResult result = strategy.match(seeker, job);
            results.add(result);
        }

        // 5. Sort by score (highest first)
        results.sort(Comparator.comparingInt(MatchResult::getScore).reversed());

        return results;
    }

    // Matches a candidate to all job openings
    public List<MatchResult> matchJobsToCandidate(String jobSeekerId, StrategyType strategyType) {
        // 1. Get the job seeker
        JobSeeker seeker = seekerRepo.findById(jobSeekerId).orElse(null);
        if (seeker == null) {
            System.err.println("Job Seeker not found: " + jobSeekerId);
            return new ArrayList<>();
        }

        // 2. Get all job openings
        List<JobOpening> openings = openingRepo.findAll();
        List<MatchResult> results = new ArrayList<>();

        // 3. Get the strategy
        MatchingStrategy strategy = MatchingStrategyFactory.getStrategy(strategyType);

        // 4. Run matching loop
        for (JobOpening opening : openings) {
            // Note: Strategy match expects (seeker, opening) order
            MatchResult result = strategy.match(seeker, opening);
            results.add(result);
        }

        // 5. Sort by score (highest first)
        results.sort(Comparator.comparingInt(MatchResult::getScore).reversed());

        return results;
    }

    // saves the match results to a file for reporting
    public void saveMatchReport(List<MatchResult> results, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("=== MATCH REPORT ===");
            for (MatchResult result : results) {
                writer.println("Candidate: " + result.getJobSeeker().getFullName());
                writer.println("Job: " + result.getJobOpening().getTitle());
                writer.println("Score: " + result.getScore());
                writer.println("-------------------------");
            }
            System.out.println("Report saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving report: " + e.getMessage());
        }
    }

}
