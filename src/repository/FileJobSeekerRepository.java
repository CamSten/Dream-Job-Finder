package repository;

import model.JobSeeker;

import java.util.ArrayList;
import java.util.List;

public class FileJobSeekerRepository extends FileRepository<JobSeeker> implements JobSeekerRepository {

    public FileJobSeekerRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String getId(JobSeeker seeker) {
        return seeker.getId();
    }

    @Override
    protected JobSeeker fromDataString(String line) {
        return JobSeeker.fromDataString(line);
    }

    @Override
    protected String toDataString(JobSeeker seeker) {
        return seeker.toDataString();
    }

    //Finds a seeker by name
    @Override
    public List<JobSeeker> findByName(String name) {
        List<JobSeeker> found = new ArrayList<>();

        for (JobSeeker js : findAll()) {
            if (js.getFullName().toLowerCase().contains(name.toLowerCase())) {
                found.add(js);
            }
        }
        return found;
    }
}
