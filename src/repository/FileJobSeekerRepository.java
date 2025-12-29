package repository;

import model.JobSeeker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileJobSeekerRepository implements JobSeekerRepository {
    private final String filePath;

    public FileJobSeekerRepository(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    // checks if file exists so we dont get error
    private void ensureFileExists() {
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
        List<JobSeeker> allSeekers = findAll();
        // Remove existing if updating (based on ID)
        allSeekers.removeIf(js -> js.getId().equals(jobSeeker.getId()));
        allSeekers.add(jobSeeker);
        writeToFile(allSeekers);
    }

    @Override
    public Optional<JobSeeker> findById(UUID id) {
        return findAll().stream()
                .filter(js -> js.getId().equals(id))
                .findFirst();
    }

    // reads everything from the file and returns a list
    @Override
    public List<JobSeeker> findAll() {
        List<JobSeeker> seekers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (!line.isBlank()) {
                    seekers.add(JobSeeker.fromDataString(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return seekers;
    }

    // helper to save the whole list back to the file
    private void writeToFile(List<JobSeeker> seekers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (JobSeeker seeker : seekers) {
                writer.println(seeker.toDataString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
