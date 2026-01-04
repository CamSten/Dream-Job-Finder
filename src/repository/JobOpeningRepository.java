package repository;

import model.JobOpening;
import java.util.List;
import java.util.Optional;

public interface JobOpeningRepository {
    void save(JobOpening jobOpening);

    Optional<JobOpening> findById(String id);

    List<JobOpening> findAll();

    void update(JobOpening jobOpening);

    List<JobOpening> findByTitle(String title);

    void delete(String id);
}
