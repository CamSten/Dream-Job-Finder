package strategy;

import model.JobOpening;
import model.JobSeeker;
import model.MatchResult;

public class EducationFocusedMatchingStrategy implements MatchingStrategy {

    // this strategy thinks Education is the most important thing
    @Override
    public MatchResult match(JobSeeker jobSeeker, JobOpening jobOpening) {
        MatchResult result = new MatchResult(jobSeeker, jobOpening);
        int score = 0;

        // 1. Education (50 points) - Heavily weighted
        // Must match or exceed required education
        if (jobSeeker.getEducationLevel().compareTo(jobOpening.getRequiredEducation()) >= 0) {
            score += 50;
            result.addMatchedSkill("Education: " + jobSeeker.getEducationLevel());
        } else {
            result.addReason("Education too low: " + jobSeeker.getEducationLevel());
            // In this strategy, if education fails, we penalize heavily, effectively
            // disqualifying them
            score = 0;
            result.setScore(0);
            return result;
        }

        // 2. Work Area (30 points)
        if (jobSeeker.getWorkArea().equalsIgnoreCase(jobOpening.getWorkArea())) {
            score += 30;
            result.addMatchedSkill("Work Area: " + jobSeeker.getWorkArea());
        }

        // 3. Experience (20 points)
        if (jobSeeker.getYearsExperience() >= jobOpening.getMinYearsExperience()) {
            score += 20;
            result.addMatchedSkill("Experience: " + jobSeeker.getYearsExperience() + " years");
        } else {
            result.addReason("Experience too low");
        }
        result.setScore(score);
        return result;
    }
}
