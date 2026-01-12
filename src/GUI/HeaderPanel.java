package GUI;

import Controller.Event;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    private int counter;

    public HeaderPanel(PanelDecorator decorator, Controller.Event event){
        this.decorator = decorator;
        this.event = event;
        setTerms(event);
        setBackground(Colors.getBackgroundColor());
        setLayout(new BorderLayout());
        setVisible(true);
        add(getHeaderPanel(), BorderLayout.CENTER);
        if (event.getAction() == Event.Action.VIEW || event.getPhase() == Event.Phase.MATCH_RESULT)
        {
            add(getCounter(), BorderLayout.SOUTH);
        }
    }
    private JPanel getHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(2,1));
        headerPanel.setBackground(Colors.getBackgroundColor());
        JTextArea header = new JTextArea();
        String text = "";
        headerPanel.add(header);
        if (event.getPhase() == Event.Phase.MATCH_RESULT){
            if (event.getContents() instanceof List list && (!list.isEmpty())){
            text = "The following match result has been found:";
            }
            else {
            text = "No result was found";
            }
        }

        else if (event.getAction() == Event.Action.CHOOSE_TYPE && event.getSubject() == Event.Subject.OPENING){
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
    private JPanel getCounter(){
        JPanel wrapperPanel = new JPanel();
        JPanel countPanel = new JPanel(new GridLayout(1, 3));
        JLabel count = new JLabel("Total count:");
        JTextArea showNumber = new JTextArea(String.valueOf(counter));
        decorator.adjustLabel(count);
        decorator.adjustTextArea(showNumber);
        countPanel.add(count);
        countPanel.add(showNumber);
        wrapperPanel.add(countPanel);
        wrapperPanel.setBackground(Colors.getBackgroundColor());
        return wrapperPanel;
    }
    public String getHeaderTerm(){
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
            if (event.getPhase() == Event.Phase.MATCH_RESULT) {
                String totalResult = "";
                List<String> result = (List<String>) list;
                for (String s : result){
                    totalResult += s;
                }
                String[] parts = totalResult.split("-------------------------");
                counter = parts.length;
            } else {
                counter = list.size();
                this.pluralVerb = "";
                this.pluralNoun = "s";
                this.pluralHas = "ve";
            }
        }
    }
}
