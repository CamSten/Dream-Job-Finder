package repository;

import Controller.ApplicationManager;
import Controller.Event;
import GUI.Subscriber;
import model.JobSeeker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileJobSeekerRepository implements JobSeekerRepository {
    private final String filePath = "jobSeekers.txt";
    private ApplicationManager applicationManager;

    public FileJobSeekerRepository(ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
        System.out.println("FileJobSeekerRepository constructor is reached");
        ensureFileExists();
    }

    // checks if file exists so we dont get error
    private void ensureFileExists() {
        System.out.println("ensureFileExists in FJSR is reached");
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(JobSeeker jobSeeker) {
        // Remove existing if updating (based on ID)
        // Since we are appending, we need to check if exists logic or just append
        // For simplicity in this dummy version:
        // 1. Read all
        // 2. If ID exists, replace (update)
        // 3. Else append
        // BUT here we just append to file for now or overwrite all.
        // Let's implement full rewrite for simplicity to handle updates correctly.

        List<JobSeeker> all = findAll();
        Event.Phase newPhase = Event.Phase.COMPLETE;
        boolean exists = false;
        Event.Outcome outcome = Event.Outcome.ALREADY_EXISTS;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(jobSeeker.getId())) {
                all.set(i, jobSeeker);
                exists = true;
                break;
            }
        }
        if (!exists) {
            outcome = Event.Outcome.OK;
            all.add(jobSeeker);
        }
        applicationManager.Update(new Event(newPhase, Event.Action.ADD, Event.Subject.SEEKER, Event.Origin.LOGIC, outcome, jobSeeker, null));
        writeAll(all);
    }

    @Override
    public Optional<JobSeeker> findById(String id) {
        return findAll().stream()
                .filter(seeker -> seeker.getId().equals(id))
                .findFirst();
    }

    // reads everything from the file and returns a list
    @Override
    public List<JobSeeker> findAll() {
        System.out.println("findAll in FJSR is reached");
        List<JobSeeker> seekers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    seekers.add(JobSeeker.fromDataString(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read file: " + e.getMessage());
        }
        return seekers;
    }

    // updates the job seeker
    @Override
    public void update(JobSeeker jobSeeker) {
        save(jobSeeker);
    }

    // filters list by name
    @Override
    public List<JobSeeker> findByName(String name) {
        List<JobSeeker> all = findAll();
        List<JobSeeker> found = new ArrayList<>();

        for (JobSeeker js : all) {
            if (js.getFullName().toLowerCase().contains(name.toLowerCase())) {
                found.add(js);
            }
        }
        return found;
    }

    // removes valid job seeker by id
    @Override
    public void delete(String id) {
        Event.Phase phase = Event.Phase.COMPLETE;
        List<JobSeeker> all = findAll();
        boolean removed = all.removeIf(seeker -> seeker.getId().equals(id));
        Event.Outcome outcome = Event.Outcome.OK;
        if (!removed) {
            System.out.println("ID not found: " + id);
            outcome = Event.Outcome.FAILURE;
        }
        writeAll(all);
        System.out.println("Deleted seeker with ID: " + id);
        applicationManager.Update(new Event(phase, Event.Action.REMOVE, Event.Subject.SEEKER, Event.Origin.LOGIC, Event.Outcome.OK, id, null));
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
