package repository;

import model.JobSeeker;
import java.util.List;
import java.util.Optional;

public interface JobSeekerRepository {
    void save(JobSeeker jobSeeker);

    Optional<JobSeeker> findById(String id);

    List<JobSeeker> findAll();

    void update(JobSeeker jobSeeker);

    List<JobSeeker> findByName(String name);

    void delete(String id);
}
