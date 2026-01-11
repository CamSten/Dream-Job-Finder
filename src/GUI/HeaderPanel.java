package GUI;

import Controller.Event;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HeaderPanel extends JPanel {
    private PanelDecorator decorator;
    private Controller.Event event;
    private String subject;
    public String term;
    public String promptTerm = "";
    public String actionTerm = "";
    public String imperativeActionTerm;
    public String completedVerb;
    private String pluralNoun = "";
    private String pluralVerb = "s";
    private String pluralHas = "s";

    public HeaderPanel(PanelDecorator decorator, Controller.Event event){
        this.decorator = decorator;
        this.event = event;
        setTerms(event);
        setBackground(Colors.getBackgroundColor());
        add(getHeaderPanel());
    }
    private JPanel getHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(2,1));
        headerPanel.setBackground(Colors.getBackgroundColor());
        JTextArea header = new JTextArea();
        String text = "";
        headerPanel.add(header);
        if (event.getAction() == Event.Action.CHOOSE_TYPE && event.getSubject() == Event.Subject.OPENING){
            text = "Job opening";
        }
        else if (event.getAction() == Event.Action.CHOOSE_TYPE && event.getSubject() == Event.Subject.SEEKER) {
            text = "Job seeker";
        }
        else if (event.getAction() == Event.Action.VIEW) {
            text = "The following " + term + pluralNoun + " ha" + pluralHas + " been found:";
        }
        else if(event.getPhase() == Event.Phase.COMPLETE){
            text = "The following " + term + pluralNoun + " ha" + pluralHas + " been " + completedVerb;
        }
        else {
            System.out.println("else-clause is reached in getHeaderPanel");
            text = actionTerm + " " + term;
            String promptText = getHeaderTerm();
            JTextArea prompt = new JTextArea(promptText);
            decorator.adjustHeader(prompt);
            headerPanel.add(prompt);
        }
        header.setText(text);
        header.setBackground(Colors.getBackgroundColor());
        header.setForeground(Colors.getHeaderColor());
        header.setFont(Fonts.getHeaderFont());
        return headerPanel;
    }

    public String getHeaderTerm(){
        System.out.println("in getHeaderTerm, terms are: " + term + " " + imperativeActionTerm + " " + actionTerm) ;
        String headerTerm = "";
        Event.Origin origin = event.getOrigin();
        switch (origin) {
            case LOGIC -> {
                if (event.getOutcome() != Event.Outcome.NOT_FOUND && event.getOutcome() != Event.Outcome.FAILURE) {
                    if (event.getAction() == Event.Action.VIEW) {
                        headerTerm = "The following" + term + "ha"+ pluralHas+ " been found:";
                    }
                    else {
                        headerTerm = "The following " + term + pluralNoun + " contain" + pluralVerb + " your search term:";
                    }
                }
                else {
                    headerTerm = "No " + subject + " was found for " + actionTerm ;
            }

            }
            case GUI -> {
                if (event.getAction() == Event.Action.CHOOSE_TYPE && event.getSubject() == Event.Subject.OPENING) {
                    headerTerm = "Job seeker";
                }
                else if (event.getAction() == Event.Action.EDIT && event.getContents() != null && (!(event.getContents() instanceof ArrayList))) {
                    headerTerm = "Submit new input:";
                }
                else {
                    headerTerm = "Input the " + promptTerm + " that you would like to " + imperativeActionTerm + ":";
                }
            }

        }
        return headerTerm;
    }
    public void setTerms(Event event) {
        Terms terms = new Terms(event);
        this.term = terms.getTerm();
        this.promptTerm = terms.getPromptTerm();
        this.imperativeActionTerm = terms.getImperativeActionTerm();
        this.actionTerm = terms.getActionTerm();
        this.completedVerb = terms.getCompletedVerb();
        Object contents = event.getContents();
        if (event.getSubject() == Event.Subject.SEEKER){
            if (event.getAction() == Event.Action.MATCH) {
                subject = "job opening";
            }
            else {
                subject = "job seeker";
            }
        }
        else if (event.getSubject() == Event.Subject.OPENING){
            if (event.getAction() == Event.Action.MATCH) {
                subject = "job seeker";
            }
            else {
                subject = "job opening";
            }
        }
        if (contents != null && contents instanceof ArrayList list && list.size() > 1) {
            this.pluralVerb = "";
            this.pluralNoun = "s";
            this.pluralHas = "ve";
        }
    }
}
