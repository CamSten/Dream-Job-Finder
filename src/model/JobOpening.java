package model;

import java.io.Serializable;

public class JobOpening implements Serializable {
    private final String id;
    private String title;
    private EducationLevel requiredEducation;
    private int minYearsExperience;
    private String workArea;

    // empty constructor for compatibility
    public JobOpening() {
        this.id = generateId();
    }

    public JobOpening(String title, EducationLevel requiredEducation, int minYearsExperience, String workArea) {
        this.id = generateId();
        this.title = title;
        this.requiredEducation = requiredEducation;
        this.minYearsExperience = minYearsExperience;
        this.workArea = workArea;
    }

    private String generateId() {
        // Generates a random 4-digit ID between 1000 and 9999
        int num = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(num);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRequiredEducation(EducationLevel requiredEducation) {
        this.requiredEducation = requiredEducation;
    }

    public void setMinYearsExperience(int minYearsExperience) {
        this.minYearsExperience = minYearsExperience;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }

    public String getId() {
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
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", requiredEducation=" + requiredEducation.name() +
                ", minYearsExperience=" + minYearsExperience +
                ", workArea='" + workArea + '\'' +
                '}';
    }

    public String printout(){
        return "ID: " + id + "\nTitle: " + title + "\nRequired education level: " + requiredEducation + "\nRequired years of experience: " + minYearsExperience + "\nWork area: " + workArea;
    }

    // converts to string so we can save to text file
    public String toDataString() {
        return id + ";" + title + ";" + requiredEducation + ";" + minYearsExperience + ";" + workArea;
    }

    // reads from text file line and creates object
    public static JobOpening fromDataString(String line) {
        String[] parts = line.split(";");
        String id = parts[0];
        String title = parts[1];
        EducationLevel edu = EducationLevel.valueOf(parts[2]);
        int years = Integer.parseInt(parts[3]);
        String area = parts[4];

        return new JobOpening(id, title, edu, years, area);
    }

    public JobOpening(String id, String title, EducationLevel requiredEducation, int minYearsExperience,
            String workArea) {
        this.id = id;
        this.title = title;
        this.requiredEducation = requiredEducation;
        this.minYearsExperience = minYearsExperience;
        this.workArea = workArea;
    }
}
