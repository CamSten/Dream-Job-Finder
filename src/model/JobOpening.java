package model;

import java.util.UUID;

public class JobOpening {
    private final UUID id;
    private String title;
    private EducationLevel requiredEducation;
    private int minYearsExperience;
    private String workArea;

    public JobOpening( String title, EducationLevel requiredEducation, int minYearsExperience, String workArea) {
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
    public String toString() {
        return "JobOpening{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", requiredEducation=" + requiredEducation +
                ", minYearsExperience=" + minYearsExperience +
                ", workArea='" + workArea + '\'' +
                '}';
    }

}
