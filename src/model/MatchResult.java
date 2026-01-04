package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatchResult {

    private final JobSeeker jobSeeker;
    private final JobOpening jobOpening;
    private int score;
    private final List<String> reasons;
    private final Set<String> matchedSkills;

    public MatchResult(JobSeeker jobSeeker, JobOpening jobOpening) {
        this.jobSeeker = jobSeeker;
        this.jobOpening = jobOpening;
        this.score = 0;
        this.reasons = new ArrayList<>();
        this.matchedSkills = new HashSet<>();
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public JobOpening getJobOpening() {
        return jobOpening;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // helpful to know why we got this score
    public void addReason(String reason) {
        if (reason != null && !reason.isBlank()) {
            reasons.add(reason);
        }
    }

    // keeps track of skills that matched
    public void addMatchedSkill(String skill) {
        if (skill != null && !skill.isBlank()) {
            matchedSkills.add(skill);
        }
    }
}
