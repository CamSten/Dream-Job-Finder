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

        // 1. Education is heavily weighted (50% of score)
        if (jobSeeker.getEducation().compareTo(jobOpening.getRequiredEducation()) >= 0) {
            score += 50;
            result.addMatchedSkill("Education (High Importance): " + jobSeeker.getEducation());
        } else {
            result.addReason("Education too low for this focused strategy");
        }

        // 2. Work Area (25%)
        if (jobSeeker.getWorkArea().equalsIgnoreCase(jobOpening.getWorkArea())) {
            score += 25;
            result.addMatchedSkill("Work Area: " + jobSeeker.getWorkArea());
        }

        // 3. Experience (25%)
        if (jobSeeker.getYearsOfExperience() >= jobOpening.getMinYearsExperience()) {
            score += 25;
            result.addMatchedSkill("Experience: " + jobSeeker.getYearsOfExperience());
        }

        result.setScore(score);
        return result;
    }
}
