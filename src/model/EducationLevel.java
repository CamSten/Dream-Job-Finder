package model;

public enum EducationLevel {

    NONE(0),
    HIGH_SCHOOL(1),
    BACHELOR(2),
    MASTER(3),
    PHD(4);

    private final int level;
    EducationLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
}
