package repository;

import model.JobOpening;

import java.util.ArrayList;
import java.util.List;

public class FileJobOpeningRepository extends FileRepository<JobOpening> implements JobOpeningRepository {

    public FileJobOpeningRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String getId(JobOpening job) {
        return job.getId();
    }

    @Override
    protected JobOpening fromDataString(String line) {
        return JobOpening.fromDataString(line);
    }

    @Override
    protected String toDataString(JobOpening job) {
        return job.toDataString();
    }

    @Override
    public List<JobOpening> findByTitle(String title) {
        List<JobOpening> found = new ArrayList<>();

        for (JobOpening j : findAll()) {
            if (j.getTitle().toLowerCase().contains(title.toLowerCase())) {
                found.add(j);
            }
        }
        return found;
    }
}
