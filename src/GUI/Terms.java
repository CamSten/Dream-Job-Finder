package GUI;

import Controller.Event;
import model.JobOpening;
import model.JobSeeker;

import java.util.List;

public class Terms {
    private Event.Subject subject;
    private Event.Action action;
    private String term;
    private String promptTerm;
    private String imperativeActionTerm;
    private String actionTerm;
    private String completedVerb;

    public Terms (Controller.Event event){
        this.subject = event.getSubject();
        this.action = event.getAction();
        setTerms();
    }
    private void setTerms() {

        if (subject == Event.Subject.SEEKER) {
            this.term = "job seeker";
            this.promptTerm = "name of the job seeker";
        } else if (subject == Event.Subject.OPENING) {
            this.term = "job opening";
            this.promptTerm = "title of the job opening";
        }
        switch (action) {
            case REMOVE -> {
                this.imperativeActionTerm = "delete";
                this.actionTerm = "Deleting";
                this.completedVerb = "deleted";
            }
            case EDIT -> {
                this.imperativeActionTerm = "edit";
                this.actionTerm = "Editing";
                this.completedVerb = "edited";
            }
            case ADD -> {
                this.imperativeActionTerm = "add";
                this.actionTerm = "Adding";
                this.completedVerb = "added";
            }
            case SEARCH -> {
                this.actionTerm = "Searching for";
                this.completedVerb = "found";
            }
            case MATCH -> {
                this.imperativeActionTerm = "match";
                this.actionTerm = "Matching";
                this.completedVerb = "matched";
            }

        }
    }
    public String getTerm() {
        return term;
    }
    public String getPromptTerm() {
        return promptTerm;
    }

    public String getImperativeActionTerm() {
        return imperativeActionTerm;
    }
    public String getActionTerm() {
        return actionTerm;
    }
    public String getCompletedVerb(){
        return completedVerb;
    }
}
