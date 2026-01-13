package repository;

import Controller.ApplicationManager;
import Controller.Event;
import GUI.Subscriber;
import model.JobSeeker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    // removes valid job seeker by id
    @Override
    public void delete(String id) {
        List<JobSeeker> all = findAll();
        boolean removed = all.removeIf(seeker -> seeker.getId().equals(id));
        if (removed) {
            writeAll(all);
            System.out.println("Deleted seeker with ID: " + id);
        } else {
            System.out.println("ID not found: " + id);
        }
    }

    // helper to save the whole list back to the file
    private void writeAll(List<JobSeeker> seekers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (JobSeeker seeker : seekers) {
                writer.println(seeker.toDataString());
            }
        } catch (IOException e) {
            System.err.println("Could not write file: " + e.getMessage());
        }
    }
}
