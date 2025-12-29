package model;

import java.io.Serializable;
import java.util.UUID;

public class JobOpening implements Serializable {
    private final UUID id;
    private String title;
    private EducationLevel requiredEducation;
    private int minYearsExperience;
    private String workArea;

    // empty constructor for compatibility
    public JobOpening() {
        this.id = UUID.randomUUID();
    }

    public JobOpening(String title, EducationLevel requiredEducation, int minYearsExperience, String workArea) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.requiredEducation = requiredEducation;
        this.minYearsExperience = minYearsExperience;
        this.workArea = workArea;

    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public EducationLevel getRequiredEducation() {
        return requiredEducation;
    }

    public int getMinYearsExperience() {
        return minYearsExperience;
    }

    public String getWorkArea() {
        return workArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        JobOpening that = (JobOpening) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "JobOpening{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", requiredEducation=" + requiredEducation +
                ", minYearsExperience=" + minYearsExperience +
                ", workArea='" + workArea + '\'' +
                '}';
    }

    // converts to string so we can save to text file
    public String toDataString() {
        return id + ";" + title + ";" + requiredEducation + ";" + minYearsExperience + ";" + workArea;
    }

    // reads from text file line and creates object
    public static JobOpening fromDataString(String line) {
        String[] parts = line.split(";");
        UUID id = UUID.fromString(parts[0]);
        String title = parts[1];
        EducationLevel edu = EducationLevel.valueOf(parts[2]);
        int years = Integer.parseInt(parts[3]);
        String area = parts[4];

        return new JobOpening(id, title, edu, years, area);
    }

    private JobOpening(UUID id, String title, EducationLevel requiredEducation, int minYearsExperience,
            String workArea) {
        this.id = id;
        this.title = title;
        this.requiredEducation = requiredEducation;
        this.minYearsExperience = minYearsExperience;
        this.workArea = workArea;
    }
}
