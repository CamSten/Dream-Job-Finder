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

        // 1. check education (if higher +10 bonus, if slightly lower still get some
        // points)
        int eduDiff = jobSeeker.getEducation().compareTo(jobOpening.getRequiredEducation());
        if (eduDiff >= 0) {
            score += 30;
            if (eduDiff > 0) {
                score += 10; // bonus for higher education
                result.addReason("Bonus: Higher education than required!");
            }
            result.addMatchedSkill("Education: " + jobSeeker.getEducation());
        } else if (eduDiff == -1) {
            // 1 step lower is okay in flexible mode, get partial points
            score += 15;
            result.addMatchedSkill("Education close enough: " + jobSeeker.getEducation());
        } else {
            result.addReason("Education too low");
        }

        // 2. check work area (must match exactly - usually can't be flexible here)
        if (jobSeeker.getWorkArea().equalsIgnoreCase(jobOpening.getWorkArea())) {
            score += 30;
            result.addMatchedSkill("Work Area: " + jobSeeker.getWorkArea());
        } else {
            result.addReason("Wrong work area");
        }

        // 3. check experience
        int expDiff = jobSeeker.getYearsOfExperience() - jobOpening.getMinYearsExperience();
        if (expDiff >= 0) {
            score += 30;
            result.addMatchedSkill("Experience: " + jobSeeker.getYearsOfExperience() + " years");
        } else if (expDiff >= -1) {
            // missing 1 year is okay
            score += 15;
            result.addReason("Experience almost matches (-1 year)");
        } else {
            result.addReason("Not enough experience");
        }

        // cap score at 100
        if (score > 100)
            score = 100;

        result.setScore(score);
        return result;
    }
}
