package Controller;

import GUI.MainFrame;
import GUI.Subscriber;
import model.EducationLevel;
import model.JobOpening;
import model.JobSeeker;
import repository.FileJobOpeningRepository;
import repository.FileJobSeekerRepository;
import repository.JobOpeningRepository;
import repository.JobSeekerRepository;
import service.MatchingService;
import java.util.ArrayList;
import java.util.List;

public class ApplicationManager implements Subscriber {
    private List<Subscriber> subscribers = new ArrayList<>();
    private MainFrame mainFrame;
    private MatchingService matchingService;
    private JobOpeningRepository openingRepo;
    private JobSeekerRepository seekerRepo;
    private String openingFile = "jobOpenings.txt";
    private String seekerFile = "jobSeekers.txt";
    private String[] newInfo;

    public ApplicationManager() {
        mainFrame = new MainFrame(this);
        this.openingRepo = new FileJobOpeningRepository(openingFile);
        this.seekerRepo = new FileJobSeekerRepository(seekerFile);
        this.matchingService = new MatchingService(seekerRepo, openingRepo, this);
    }

    public void Update(Event event) {
        Event.Origin origin = event.getOrigin();
        switch (origin) {
            case GUI -> {
                if (event.getAction() == Event.Action.ADD && event.getContents() != null) {
                    event.setContents(createNewPost(event));
                }
                else if (event.getAction() == Event.Action.EDIT){
                    editPost(event);
                }
                else if (event.getAction() == Event.Action.REMOVE){
                    removePost(event);
                }
                matchingService.Update(event);
            }
            case LOGIC -> {
                mainFrame.Update(event);
            }
        }
    }
    private Object createNewPost(Controller.Event event) {
        Object newPost = null;
        if (event.getSubject() == Event.Subject.SEEKER){
           newPost = createNewSeeker(event.getContents());
        }
        else if (event.getSubject() == Event.Subject.OPENING){
            newPost = createNewOpening(event.getContents());
        }
        return newPost;
    }
    private JobSeeker createNewSeeker(Object data) {
        String[] userInput = (String[]) data;
        String name = userInput[0];
        String experience = userInput[1];
        String branch = userInput[2];
        String education = userInput[3];
        int thisExp = 0;
        try {
            thisExp = Integer.parseInt(experience);
        } catch (NumberFormatException e) {

        }
        EducationLevel thisEdu = null;
        for (EducationLevel level : EducationLevel.values()) {
            if (level.toString().equalsIgnoreCase(education)) {
                thisEdu = level;
            }
        }
        return new JobSeeker(name, thisEdu, thisExp, branch);
    }
    private JobOpening createNewOpening(Object data) {
        String[] userInput = (String[]) data;
        String name = userInput[0];
        String experience = userInput[1];
        String branch = userInput[2];
        String education = userInput[3];
        int thisExp = 0;
        try {
            thisExp = Integer.parseInt(experience);
        } catch (NumberFormatException e) {

        }
        EducationLevel thisEdu = null;
        for (EducationLevel level : EducationLevel.values()) {
            if (level.toString().equalsIgnoreCase(education)) {
                thisEdu = level;
            }
        }
        return new JobOpening(name, thisEdu, thisExp, branch);
    }
    private void editPost(Event event){
        if (event.getExtraContents() == null){
            matchingService.Update(event);
        }
        else {
            String[] newInfo = (String[]) event.getExtraContents();
            if (event.getSubject() == Event.Subject.SEEKER) {
                JobSeeker seeker = (JobSeeker) event.getContents();
                editSeeker(seeker, newInfo);
            } else if (event.getSubject() == Event.Subject.OPENING) {
                JobOpening opening = (JobOpening) event.getContents();
                editOpening(opening, newInfo);
            }
        }
    }
    private void removePost(Event event){
        if (event.getPhase() == Event.Phase.SUBMIT){
            matchingService.Update(event);
        }
        else {
            if (event.getSubject() == Event.Subject.SEEKER) {
                JobSeeker seeker = (JobSeeker) event.getContents();
                event.setExtraContents(seeker.getId());
                matchingService.Update(event);
            } else if (event.getSubject() == Event.Subject.OPENING) {
                JobOpening opening = (JobOpening) event.getContents();
                event.setExtraContents(opening.getId());
                matchingService.Update(event);
            }
        }
    }
    private void editSeeker(JobSeeker seeker, String[]newInfo){
        String name = newInfo[0];
        String experience = newInfo[1];
        String branch = newInfo[2];
        int thisExp = 0;
        try {
            thisExp = Integer.parseInt(experience);
        }
        catch (NumberFormatException e) {
        }
        String education = newInfo[3];
        EducationLevel thisEdu = null;
        for (EducationLevel level : EducationLevel.values()){
            if (level.toString().equalsIgnoreCase(education)){
                thisEdu = level;
            }
        }
        seeker.setName(name);
        seeker.setYearsExperience(thisExp);
        seeker.setEducationLevel(thisEdu);
        seeker.setWorkArea(branch);
        matchingService.updateSeeker(seeker);
    }

    private void editOpening(JobOpening opening, String[]newInfo){
        String title = newInfo[0];
        String experience = newInfo[1];
        String branch = newInfo[2];
        int thisExp = 0;
        try {
            thisExp = Integer.parseInt(experience);
        }
        catch (NumberFormatException e) {
        }
        String education = newInfo[3];
        EducationLevel thisEdu = null;
        for (EducationLevel level : EducationLevel.values()){
            if (level.toString().equalsIgnoreCase(education)){
                thisEdu = level;
            }
        }
        opening.setTitle(title);
        opening.setMinYearsExperience(thisExp);
        opening.setRequiredEducation(thisEdu);
        opening.setWorkArea(branch);
        matchingService.updateJobOpening(opening);
    }
}

