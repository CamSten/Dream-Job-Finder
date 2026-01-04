package strategy;

import model.JobOpening;
import model.JobSeeker;
import model.MatchResult;

public class FlexibleMatchingStrategy implements MatchingStrategy {

    // this strategy gives points even if not everything matches perfectly
    @Override
    public MatchResult match(JobSeeker jobSeeker, JobOpening jobOpening) {
        MatchResult result = new MatchResult(jobSeeker, jobOpening);
        int score = 0;

        // 1. Education (25 points)
        // If equal or higher -> full points, if one level lower -> partial points
        int eduGap = jobSeeker.getEducationLevel().compareTo(jobOpening.getRequiredEducation());
        if (eduGap >= 0) {
            score += 25;
            result.addMatchedSkill("Education: " + jobSeeker.getEducationLevel());
        } else if (eduGap == -1) {
            score += 10; // Partial credit
            result.addMatchedSkill("Education (Close): " + jobSeeker.getEducationLevel());
        }

        // 2. Work Area (50 points) - Fuzzy Match
        // "Software Engineering" should match "Software"
        String seekerArea = jobSeeker.getWorkArea().toLowerCase();
        String jobArea = jobOpening.getWorkArea().toLowerCase();

        if (seekerArea.contains(jobArea) || jobArea.contains(seekerArea)) {
            score += 50;
            result.addMatchedSkill("Work Area: " + jobSeeker.getWorkArea());
        }

        // 3. Experience (25 points)
        // Full points if met, partial if close (within 1 year)
        int diff = jobSeeker.getYearsExperience() - jobOpening.getMinYearsExperience();
        if (diff >= 0) {
            score += 25;
            result.addMatchedSkill("Experience: " + jobSeeker.getYearsExperience() + " years");
        } else if (diff == -1) {
            score += 10; // Partial
            result.addReason("Experience slightly low: " + jobSeeker.getYearsExperience());
        } else {
            result.addReason("Experience too low");
        }

        // cap score at 100
        if (score > 100)
            score = 100;

        result.setScore(score);
        return result;
    }
}
