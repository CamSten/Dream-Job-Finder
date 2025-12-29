package strategy;

import model.JobOpening;
import model.JobSeeker;
import model.MatchResult;

public class StrictMatchingStrategy implements MatchingStrategy {

    // this strategy is strict - everything must match to get a score
    @Override
    public MatchResult match(JobSeeker jobSeeker, JobOpening jobOpening) {
        MatchResult result = new MatchResult(jobSeeker, jobOpening);
        int score = 0;

        // 1. check education (must be equal or higher)
        // enums have natural order: 0=NONE, 1=HIGH_SCHOOL...
        if (jobSeeker.getEducation().compareTo(jobOpening.getRequiredEducation()) >= 0) {
            score += 33;
            result.addMatchedSkill("Education: " + jobSeeker.getEducation());
        } else {
            result.addReason("Education too low: " + jobSeeker.getEducation());
        }

        // 2. check work area (must match exactly)
        if (jobSeeker.getWorkArea().equalsIgnoreCase(jobOpening.getWorkArea())) {
            score += 33;
            result.addMatchedSkill("Work Area: " + jobSeeker.getWorkArea());
        } else {
            result.addReason("Wrong work area: " + jobSeeker.getWorkArea());
        }

        // 3. check experience (years >= required)
        if (jobSeeker.getYearsOfExperience() >= jobOpening.getMinYearsExperience()) {
            score += 34; // makes 100 total
            result.addMatchedSkill("Experience: " + jobSeeker.getYearsOfExperience() + " years");
        } else {
            result.addReason("Not enough experience");
        }

        // Strict strategy: if score is not 100, it is 0!
        if (score < 100) {
            score = 0;
            result.addReason("STRICT: Did not match all criteria.");
        }

        result.setScore(score);
        return result;
    }
}
