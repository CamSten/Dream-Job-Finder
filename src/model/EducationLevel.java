package model;

public enum EducationLevel {
    NONE(0, "None"),
    HIGH_SCHOOL(1, "High school"),
    BACHELOR(2, "Bachelor"),
    MASTER(3, "Master"),
    PHD(4, "PhD");

    private final int level;
    private final String displayLevel;
    EducationLevel(int level, String displayLevel) {
        this.level = level;
        this.displayLevel = displayLevel;
    }
    public int getLevel() {
        return level;
    }

    @Override
    public String toString(){
        return displayLevel;
    }
}
