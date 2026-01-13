package service;

import Controller.ApplicationManager;
import Controller.Event;
import model.JobOpening;
import model.JobSeeker;
import model.MatchResult;
import repository.JobOpeningRepository;
import repository.JobSeekerRepository;
import strategy.MatchingStrategy;
import strategy.MatchingStrategyFactory;
import strategy.StrategyType;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MatchingService {
    private ApplicationManager applicationManager;
    private final JobSeekerRepository seekerRepo;
    private final JobOpeningRepository openingRepo;
    private Event event;
    private Event.Action action;
    private Event.Subject subject;
    private Event.Phase phase;

    // Wrapper methods for CLI
    public void getAllSeekers() {
        List<JobSeeker> allJobSeekers = seekerRepo.findAll();
        returnResult(Event.Outcome.OK, allJobSeekers, phase);
    }

    public void  getAllJobOpenings() {
        List<JobOpening> allJobOpenings = openingRepo.findAll();
        returnResult(Event.Outcome.OK, allJobOpenings, phase);
    }

    public void addSeeker(JobSeeker seeker) {
        seekerRepo.save(seeker);
    }

    public void addJobOpening(JobOpening opening) {
        openingRepo.save(opening);
    }

    public void findSeekersByName(String name) {
        List<JobSeeker> results = seekerRepo.findByName(name);
        Event.Outcome outcome = Event.Outcome.OK;
        if (results.isEmpty()){
            outcome = Event.Outcome.NOT_FOUND;
        }
        returnResult( outcome, results, Event.Phase.DISPLAY);
    }

    public void updateSeeker(JobSeeker seeker) {
        seekerRepo.update(seeker);
        returnResult(Event.Outcome.OK, seeker, Event.Phase.COMPLETE);
    }

    public void deleteSeeker(String id) {
        seekerRepo.delete(id);
        returnResult(Event.Outcome.OK, id, Event.Phase.COMPLETE);
    }

    public void findJobOpeningsByTitle(String title) {
        List<JobOpening> results = openingRepo.findByTitle(title);
        Event.Outcome outcome = Event.Outcome.OK;
        if (results.isEmpty()){
            outcome = Event.Outcome.NOT_FOUND;
        }
        returnResult(outcome, results, Event.Phase.DISPLAY);
    }

    public void updateJobOpening(JobOpening opening) {
        openingRepo.update(opening);
        returnResult(Event.Outcome.OK, opening.getTitle(), Event.Phase.COMPLETE);
    }

    public void deleteJobOpening(String id) {
        openingRepo.delete(id);
    returnResult(Event.Outcome.OK, id, Event.Phase.COMPLETE);
    }

    public MatchingService(JobSeekerRepository seekerRepo, JobOpeningRepository openingRepo, ApplicationManager applicationManager) {
        this.seekerRepo = seekerRepo;
        this.openingRepo = openingRepo;
        this.applicationManager = applicationManager;
    }

    // Matches all candidates to a specific job opening
    public void matchCandidatesToJob(String jobOpeningId, StrategyType strategyType) {
        // 1. Get the job opening
        JobOpening job = openingRepo.findById(jobOpeningId).orElse(null);
        if (job == null) {
            System.err.println("Job Opening not found: " + jobOpeningId);
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

        if (!results.isEmpty()) {
            Event.Outcome outcome = Event.Outcome.OK;
            saveMatchReport(results, jobOpeningId+".txt");
            List<String> report = getMatchReport(jobOpeningId+".txt");
            returnResult(outcome, report, Event.Phase.MATCH_RESULT);
        }
        else {
            Event.Outcome outcome = Event.Outcome.NOT_FOUND;
            returnResult(outcome, results, Event.Phase.MATCH_RESULT);
        }

    }

    // Matches a candidate to all job openings
    public void matchJobsToCandidate(String jobSeekerId, StrategyType strategyType) {
        // 1. Get the job seeker
        JobSeeker seeker = seekerRepo.findById(jobSeekerId).orElse(null);
        if (seeker == null) {
            System.err.println("Job Seeker not found: " + jobSeekerId);
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
            if (result != null) {
                results.add(result);
            }
        }
        // 5. Sort by score (highest first)
        if (results.isEmpty()) {
            Event.Outcome outcome = Event.Outcome.NOT_FOUND;
            returnResult(outcome, results, Event.Phase.MATCH_RESULT);
        }
        else {
            if(results.size() > 1) {
                results.sort(Comparator.comparingInt(MatchResult::getScore).reversed());
            }
            Event.Outcome outcome = Event.Outcome.OK;
            saveMatchReport(results, jobSeekerId+".txt");
            getMatchReport(jobSeekerId+".txt");
            List<String> report = getMatchReport(jobSeekerId+".txt");
            returnResult(outcome, report, Event.Phase.MATCH_RESULT);
        }

    }

    // saves the match results to a file for reporting
    public void saveMatchReport(List<MatchResult> results, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            String name = ";";
            writer.println("=== MATCH REPORT ===");
            for (MatchResult result : results) {
               if (event.getSubject() == Event.Subject.OPENING){
                        name = "Candidate: " + result.getJobSeeker().getFullName();
                        writer.println(name);
                    }
                writer.println("Job: " + result.getJobOpening().getTitle());
                writer.println("Score: " + result.getScore());
                writer.println("-------------------------");
            }
            System.out.println("Report saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving report: " + e.getMessage());
        }
    }
    public List<String> getMatchReport(String filename){
        List<String> report = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    report.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read file: " + e.getMessage());
        }
        return report;
    }

    public void Update(Event event){
        this.event = event;
        this.action = event.getAction();
        this.subject = event.getSubject();
        this.phase = event.getPhase();
        switch (event.getAction()){
            case VIEW -> {
                if (subject == Event.Subject.SEEKER){
                    getAllSeekers();
                }
                else if (subject == Event.Subject.OPENING) {
                    getAllJobOpenings();
                }
            }
            case SEARCH -> {
                String name = (String) event.getContents();
                if (subject == Event.Subject.SEEKER){
                    findSeekersByName(name);
                }
                else if (subject == Event.Subject.OPENING) {
                    findJobOpeningsByTitle(name);
                }
            }
            case EDIT -> {
                if (event.getExtraContents() == null) {
                    String name = (String) event.getContents();
                            if (event.getSubject() == Event.Subject.SEEKER) {
                                findSeekersByName(name);
                            }
                            else if (event.getSubject() == Event.Subject.OPENING){
                                findJobOpeningsByTitle(name);
                            }
                } else {
                    if (subject == Event.Subject.SEEKER) {
                        JobSeeker jobSeeker = (JobSeeker) event.getContents();
                        updateSeeker(jobSeeker);
                    } else if (subject == Event.Subject.OPENING) {
                        JobOpening jobOpening = (JobOpening) event.getContents();
                        updateJobOpening(jobOpening);
                    }
                }
            }
            case ADD -> {
                if (subject == Event.Subject.SEEKER){
                    JobSeeker jobSeeker = (JobSeeker) event.getContents();
                    addSeeker(jobSeeker);
                }
                else if (subject == Event.Subject.OPENING) {
                    JobOpening jobOpening = (JobOpening) event.getContents();
                    addJobOpening(jobOpening);
                }
            }
            case REMOVE -> {
                if (event.getPhase() == Event.Phase.SUBMIT) {
                    String name = (String) event.getContents();
                    if (event.getSubject() == Event.Subject.SEEKER) {
                        findSeekersByName(name);
                    }
                    else if (event.getSubject() == Event.Subject.OPENING){
                        findJobOpeningsByTitle(name);
                    }
                } else if (event.getPhase() == Event.Phase.SELECT) {
                    String ID = (String) event.getExtraContents();
                    if (subject == Event.Subject.SEEKER) {
                        deleteSeeker(ID);
                    } else if (subject == Event.Subject.OPENING) {
                        deleteJobOpening(ID);
                    }
                }
            }
            case MATCH -> {
                switch (event.getPhase()){
                    case MATCH_ENTER_TERM -> {
                        String name = (String) event.getContents();
                        if (event.getSubject() == Event.Subject.SEEKER){
                            findSeekersByName(name);
                        }
                        else if (event.getSubject() == Event.Subject.OPENING){
                            findJobOpeningsByTitle(name);
                        }
                    }
                    case MATCH_STRATEGY_SELECTED -> {
                        StrategyType strategy = (StrategyType) event.getExtraContents();
                        if (event.getSubject() == Event.Subject.SEEKER){
                            JobSeeker seeker = (JobSeeker) event.getContents();
                            matchJobsToCandidate(seeker.getId(), strategy);
                        }
                        else if (event.getSubject() == Event.Subject.OPENING){
                            JobOpening opening = (JobOpening) event.getContents();
                          matchCandidatesToJob(opening.getId(), strategy);
                        }
                    }
                }
            }
        }
    }
    private void returnResult(Event.Outcome newoutcome, Object content, Event.Phase newphase){
        Event.Origin origin = Event.Origin.LOGIC;
        if (event.getPhase() == Event.Phase.MATCH_ENTER_TERM){
            newphase = Event.Phase.MATCH_TERM_SUBMITTED;
        }
        applicationManager.Update(new Event(newphase, action, subject, origin, newoutcome, content, null));
    }

}
