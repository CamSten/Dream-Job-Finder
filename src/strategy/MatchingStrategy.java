package strategy;

import model.JobOpening;
import model.JobSeeker;
import model.MatchResult;

public interface MatchingStrategy {
    MatchResult match(JobSeeker jobSeeker, JobOpening jobOpening);
}
