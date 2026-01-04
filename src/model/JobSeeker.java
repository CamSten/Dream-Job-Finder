package model;

import java.io.Serializable;

public class JobSeeker implements Serializable {
    private final String id;
    private String name;
    private EducationLevel educationLevel;
    private int yearsExperience;
    private String workArea;

    // empty constructor for compatibility
    public JobSeeker() {
        this.id = generateId();
    }

    public JobSeeker(String name, EducationLevel educationLevel, int yearsExperience, String workArea) {
        this.id = generateId();
        this.name = name;
        this.educationLevel = educationLevel;
        this.yearsExperience = yearsExperience;
        this.workArea = workArea;
    }

    private String generateId() {
        // Generates a random 4-digit ID between 1000 and 9999
        int num = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(num);
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return name;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public int getYearsExperience() {
        return yearsExperience;
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

        JobSeeker jobSeeker = (JobSeeker) o;

        return id.equals(jobSeeker.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "JobSeeker{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", educationLevel=" + educationLevel +
                ", yearsExperience=" + yearsExperience +
                ", workArea='" + workArea + '\'' +
                '}';
    }

    // converts to string so we can save to text file
    public String toDataString() {
        return id + ";" + name + ";" + educationLevel + ";" + yearsExperience + ";" + workArea;
    }

    // reads from text file line and creates object
    public static JobSeeker fromDataString(String line) {
        String[] parts = line.split(";");
        String id = parts[0];
        String name = parts[1];
        EducationLevel edu = EducationLevel.valueOf(parts[2]);
        int years = Integer.parseInt(parts[3]);
        String area = parts[4];

        return new JobSeeker(id, name, edu, years, area);
    }

    public JobSeeker(String id, String name, EducationLevel educationLevel, int yearsExperience, String workArea) {
        this.id = id;
        this.name = name;
        this.educationLevel = educationLevel;
        this.yearsExperience = yearsExperience;
        this.workArea = workArea;
    }
}
