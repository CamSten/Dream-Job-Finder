package repository;

import java.io.*;
import java.util.*;

public abstract class FileRepository<T> {

    protected final String filePath;

    protected FileRepository(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    // checks if file exists so we don't get an error
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

    //some helper methods for use cases that requires a specific object type
    protected abstract String getId(T object);

    protected abstract T fromDataString(String line);

    protected abstract String toDataString(T object);

    // reads everything from the file and returns a list
    public List<T> findAll() {
        List<T> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    list.add(fromDataString(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read file: " + e.getMessage());
        }

        return list;
    }

    // saves the object to the list and then to file
    public void save(T object) {
        List<T> all = findAll();
        boolean exists = false;

        for (int i = 0; i < all.size(); i++) {
            if (getId(all.get(i)).equals(getId(object))) {
                all.set(i, object);
                exists = true;
                break;
            }
        }

        if (!exists) {
            all.add(object);
        }

        writeAll(all);
    }

    //Finds an object by id
    public Optional<T> findById(String id) {
        return findAll().stream()
                .filter(object -> getId(object).equals(id))
                .findFirst();
    }

    // updates the object
    public void update(T object) {
        save(object);
    }

    // removes valid object by id
    public void delete(String id) {
        List<T> all = findAll();
        boolean removed = all.removeIf(object -> getId(object).equals(id));

        if (removed) {
            writeAll(all);
            System.out.println("Deleted object with ID: " + id);
        } else {
            System.out.println("ID not found: " + id);
        }
    }

    // helper to save the whole list back to the file
    private void writeAll(List<T> objects) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (T object : objects) {
                writer.println(toDataString(object));
            }
        } catch (IOException e) {
            System.err.println("Could not write file: " + e.getMessage());
        }
    }
}
