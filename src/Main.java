import model.EducationLevel;
import model.JobOpening;
import model.JobSeeker;

public class Main {
    public static void main(String[] args) {
        JobSeeker seeker = new JobSeeker("Super Man", EducationLevel.BACHELOR, 3, "IT");
        JobOpening opening = new JobOpening("Super Woman", EducationLevel.HIGH_SCHOOL, 0, "Hospitality");

        System.out.println(seeker);
        System.out.println(opening);
    }
}
