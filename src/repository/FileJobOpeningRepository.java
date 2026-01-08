package repository;

import Controller.ApplicationManager;
import GUI.Subscriber;
import model.JobOpening;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileJobOpeningRepository implements JobOpeningRepository {
    private final String filePath = "jobOpenings.txt";
    private ApplicationManager applicationManager;

    public FileJobOpeningRepository(ApplicationManager applicationManager) {
        System.out.println("FileJobOpeningRepository constructor is reached");
        this.applicationManager = applicationManager;
        ensureFileExists();
    }

    // checks if file exists so we dont get error
    private void ensureFileExists() {
        System.out.println("ensureFileExists in FJOR is reached");

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("file did not exist");
                file.createNewFile();
            }
            else {
                System.out.println("file exits");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // saves the job opening to the list and then to file
    @Override
    public void save(JobOpening jobOpening) {
        List<JobOpening> all = findAll();
        boolean exists = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(jobOpening.getId())) {
                all.set(i, jobOpening);
                exists = true;
                applicationManager.update(Subscriber.EventType.RETURN_OPENING_ALREADY_EXISTS, null);
                break;
            }
        }
        if (!exists) {
            all.add(jobOpening);
            applicationManager.update(Subscriber.EventType.RETURN_ADD_SUCCESSFUL, null);

        }
        writeAll(all);
    }

    @Override
    public Optional<JobOpening> findById(String id) {
        return findAll().stream()
                .filter(job -> job.getId().equals(id))
                .findFirst();
    }

    // reads everything from the file and returns a list
    @Override
    public List<JobOpening> findAll() {
        System.out.println("findAll in FJOR is reached. FilePath is: " + filePath);
        List<JobOpening> jobs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    jobs.add(JobOpening.fromDataString(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read file: " + e.getMessage());
        }
        System.out.println("in FJOR findAll, jobs.size is: " + jobs.size());
        return jobs;
    }

    // updates the job opening
    @Override
    public void update(JobOpening jobOpening) {
        save(jobOpening);
    }

    // filters list by title
    @Override
    public List<JobOpening> findByTitle(String title) {
        List<JobOpening> all = findAll();
        List<JobOpening> found = new ArrayList<>();

        for (JobOpening j : all) {
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
