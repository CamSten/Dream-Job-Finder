package service;

import model.JobOpening;
import model.JobSeeker;
import model.MatchResult;
import repository.JobOpeningRepository;
import repository.JobSeekerRepository;
import strategy.MatchingStrategy;
import strategy.MatchingStrategyFactory;
import strategy.StrategyType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class MatchingService {

    private final JobSeekerRepository seekerRepo;
    private final JobOpeningRepository openingRepo;

    public MatchingService(JobSeekerRepository seekerRepo, JobOpeningRepository openingRepo) {
        this.seekerRepo = seekerRepo;
        this.openingRepo = openingRepo;
    }

    // Matches all candidates to a specific job opening
    public List<MatchResult> matchCandidatesToJob(UUID jobOpeningId, StrategyType strategyType) {
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
}
