import model.EducationLevel;
import model.JobSeeker;

public class Main {
    public static void main(String[] args) {

        //just to test the logic in JobSeeker so far
        JobSeeker seeker = new JobSeeker("Super Man", EducationLevel.BACHELOR, 3, "IT");
        System.out.println(seeker);
    }
}
