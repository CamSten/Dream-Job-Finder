package repository;

import model.JobSeeker;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobSeekerRepository {
    void save(JobSeeker jobSeeker);

    Optional<JobSeeker> findById(UUID id);

    List<JobSeeker> findAll();
}
