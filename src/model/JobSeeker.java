package model;

import java.util.UUID;

public class JobSeeker {
    private final UUID id;
    private String fullName;
    private EducationLevel education;
    private int yearsOfExperience;
    private String workArea;

    public JobSeeker( String fullName, EducationLevel education, int yearsOfExperience, String workArea) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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

    // --- Helper methods for Text File Storage ---

    /**
     * Converts this object to a semicolon-separated string (e.g., "id;Name;DEGREE;years;Area")
     */
    public String toDataString() {
        return id + ";" + fullName + ";" + education + ";" + yearsOfExperience + ";" + workArea;
    }

    /**
     * Creates a JobSeeker from a semicolon-separated string line.
     */
    public static JobSeeker fromDataString(String line) {
        String[] parts = line.split(";");
        // Expected format: id;fullName;education;years;workArea
        UUID id = UUID.fromString(parts[0]);
        String name = parts[1];
        EducationLevel edu = EducationLevel.valueOf(parts[2]);
        int years = Integer.parseInt(parts[3]);
        String area = parts[4];

        // Reconstruct using the existing constructor
        JobSeeker seeker = new JobSeeker(name, edu, years, area);
        // Reflection or a protected setter would be needed to restore the exact UUID,
        // but for now, since the constructor generates a random UUID, we might need a constructor that accepts UUID
        // to fully restore state. Let's add a constructor for restoring state.
        return new JobSeeker(id, name, edu, years, area);
    }

    // Private constructor for restoring from file with existing ID
    private JobSeeker(UUID id, String fullName, EducationLevel education, int yearsOfExperience, String workArea) {
        this.id = id;
        this.fullName = fullName;
        this.education = education;
        this.yearsOfExperience = yearsOfExperience;
        this.workArea = workArea;
    }
}
