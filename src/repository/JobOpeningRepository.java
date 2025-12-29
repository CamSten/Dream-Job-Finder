package repository;

import model.JobOpening;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobOpeningRepository {
    void save(JobOpening jobOpening);

    Optional<JobOpening> findById(UUID id);

    List<JobOpening> findAll();

    void delete(UUID id);
}
