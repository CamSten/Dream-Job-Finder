package model;

import java.io.Serializable;
import java.util.UUID;

public class JobSeeker implements Serializable {
    private final UUID id;
    private String fullName;
    private EducationLevel education;
    private int yearsOfExperience;
    private String workArea;

    // empty constructor for compatibility
    public JobSeeker() {
        this.id = UUID.randomUUID();
    }

    public JobSeeker(String fullName, EducationLevel education, int yearsOfExperience, String workArea) {
        this.id = UUID.randomUUID();
        this.fullName = fullName;
        this.education = education;
        this.yearsOfExperience = yearsOfExperience;
        this.workArea = workArea;

    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public EducationLevel getEducation() {
        return education;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
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
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", education=" + education +
                ", yearsOfExperience=" + yearsOfExperience +
                ", workArea='" + workArea + '\'' +
                '}';
    }

    // converts to string so we can save to text file
    public String toDataString() {
        return id + ";" + fullName + ";" + education + ";" + yearsOfExperience + ";" + workArea;
    }

    // reads from text file line and creates object
    public static JobSeeker fromDataString(String line) {
        String[] parts = line.split(";");
        UUID id = UUID.fromString(parts[0]);
        String name = parts[1];
        EducationLevel edu = EducationLevel.valueOf(parts[2]);
        int years = Integer.parseInt(parts[3]);
        String area = parts[4];

        return new JobSeeker(id, name, edu, years, area);
    }

    private JobSeeker(UUID id, String fullName, EducationLevel education, int yearsOfExperience, String workArea) {
        this.id = id;
        this.fullName = fullName;
        this.education = education;
        this.yearsOfExperience = yearsOfExperience;
        this.workArea = workArea;
    }
}
