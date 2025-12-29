package repository;

import model.JobOpening;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileJobOpeningRepository implements JobOpeningRepository {
    private final String filePath;

    public FileJobOpeningRepository(String filePath) {
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

    // saves the job opening to the list and then to file
    @Override
    public void save(JobOpening jobOpening) {
        List<JobOpening> allOpenings = findAll();
        // Remove existing if updating (based on ID)
        allOpenings.removeIf(jo -> jo.getId().equals(jobOpening.getId()));
        allOpenings.add(jobOpening);
        writeToFile(allOpenings);
    }

    @Override
    public Optional<JobOpening> findById(UUID id) {
        return findAll().stream()
                .filter(jo -> jo.getId().equals(id))
                .findFirst();
    }

    // reads everything from the file and returns a list
    @Override
    public List<JobOpening> findAll() {
        List<JobOpening> openings = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (!line.isBlank()) {
                    openings.add(JobOpening.fromDataString(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return openings;
    }

    // helper to save the whole list back to the file
    private void writeToFile(List<JobOpening> openings) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (JobOpening opening : openings) {
                writer.println(opening.toDataString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
