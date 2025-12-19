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
    public String toString() {
        return "JobSeeker{"+
                "id="+ id +
                ",fullName=" + fullName + '\'' +
                ", education=" + education +
                ", yearsExperience=" + yearsOfExperience  +
                ", workArea='" + workArea + '\'' +
                '}';

    }
}
