package repository;

import Controller.ApplicationManager;
import Controller.Event;
import GUI.Subscriber;
import model.JobOpening;
import model.JobSeeker;

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

    //Finds an opening by title
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

    // removes valid job opening by id
    @Override
    public void delete(String id) {
        List<JobOpening> all = findAll();
        boolean removed = all.removeIf(job -> job.getId().equals(id));
        if (removed) {
            writeAll(all);
            System.out.println("Deleted job with ID: " + id);
        } else {
            System.out.println("ID not found: " + id);
        }
    }

    // helper to save the whole list back to the file
    private void writeAll(List<JobOpening> jobs) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (JobOpening job : jobs) {
                writer.println(job.toDataString());
            }
        } catch (IOException e) {
            System.err.println("Could not write file: " + e.getMessage());
        }
    }
}
