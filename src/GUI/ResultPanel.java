package GUI;

import model.JobOpening;
import model.JobSeeker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResultPanel extends JPanel implements Subscriber {
    private MainFrame mainFrame;
    private JPanel centerPanel;
    private EventType eventType;
    List<JobOpening> jobOpeningList = new ArrayList<>();
    List<JobSeeker> jobSeekerList = new ArrayList<>();
    private JobOpening jobOpening;
    private JobSeeker jobSeeker;
    private String[] allUserinput;

    public ResultPanel(MainFrame mainFrame, EventType eventType, Object data){
        this.mainFrame = mainFrame;
        this.eventType = eventType;
        getData(data);
        System.out.println("resultPanel constructor was reached, eventType is: " + eventType);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(550, 500));
        setBackground(Colors.getBackgroundColor());
        if (centerPanel == null){
            this.centerPanel = new JPanel();
        }
        else if (centerPanel != null){
            centerPanel.removeAll();
        }
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Colors.getBackgroundColor());
        add(centerPanel);
        checkInput(eventType, data);
        setVisible(true);
        repaint();
        revalidate();
    }

    private void displayFoundTerm(Object data) {
        centerPanel.removeAll();
        String term = findTerm(data);
        JLabel resultlabel = new JLabel("Search was successful, found " + term + " :");
        resultlabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultlabel.setFont(Fonts.getHeaderFont());
        resultlabel.setForeground(Colors.getHeaderColor());

        JTextArea resultField = null;
        if (jobSeeker != null) {
            resultField = new JTextArea(jobSeeker.toString());
            resultField.setForeground(Colors.getBackgroundColor());
        }
        else if (jobOpening != null){
            resultField = new JTextArea(jobOpening.toString());
            resultField.setForeground(Colors.getBackgroundColor());
        }
        resultlabel.setForeground(Colors.getButtonTextColor());
        centerPanel.add(resultlabel, BorderLayout.NORTH);
        centerPanel.add(resultField, BorderLayout.CENTER);
        repaint();
        revalidate();
    }
    private void displayList(Object data){
        getData(data);
        System.out.println("in ResultPanel, displayList is reached, eventType is:" + eventType);
        centerPanel.removeAll();
        String term = findTerm(data);
        JLabel resultLabel = new JLabel("The following" + term + "have been found:");
        resultLabel.setFont(Fonts.getHeaderFont());
        resultLabel.setForeground(Colors.getHeaderColor());
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Colors.getBackgroundColor());
        if (eventType == EventType.RETURN_FOUND_SEEKERS) {
            System.out.println("--- eventType is return found seekers");
            String result = getPrintout(true);
            getTextArea(wrapperPanel, result);
        }
        else if (eventType == EventType.RETURN_FOUND_OPENINGS){
            System.out.println("--- eventType is return found openings");
            String result = getPrintout(false);
            getTextArea(wrapperPanel, result);
        }
        wrapperPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 40, 10, 40)
        );
        resultLabel.setBackground(Colors.getButtonTextColor());
        centerPanel.add(wrapperPanel, BorderLayout.CENTER);
        centerPanel.add(resultLabel, BorderLayout.NORTH);
        repaint();
        revalidate();
    }

    private void getTextArea(JPanel wrapperPanel, String result) {
        JTextArea resultArea = new JTextArea(10, 40);
        resultArea.setBackground(Colors.getBorderColor());
        resultArea.setEditable(false);
        JScrollPane scrollResults = new JScrollPane(resultArea);
        scrollResults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollResults.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        resultArea.setText(result);
        resultArea.setCaretPosition(0);
        resultArea.setFont(Fonts.getButtonFont());
        resultArea.setForeground(Colors.getHeaderColor());
        wrapperPanel.add(scrollResults, BorderLayout.CENTER);
    }

    private void displayConfirmation(EventType eventType, Object data){
        JPanel confirmationPanel = new JPanel();
        JPanel confirmation = getConfirmationPanel(eventType);
        confirmationPanel.add(confirmation);
        if (eventType == EventType.RETURN_ADD_SUCCESSFUL || eventType == EventType.RETURN_EDIT_SUCCESSFUL){
            JTextArea info = getConfirmationInfo(data);
            confirmation.add(info);
        }
        confirmationPanel.setBackground(Colors.getBackgroundColor());
        confirmationPanel.setForeground(Colors.getHeaderColor());
        confirmationPanel.setFont(Fonts.getHeaderFont());
        if (centerPanel != null){
            centerPanel.removeAll();
            centerPanel.add(confirmationPanel, BorderLayout.CENTER);
        }
        revalidate();
        repaint();
    }
    private JPanel getConfirmationPanel (EventType eventType){
        JPanel confirmationPanel = new JPanel();
        confirmationPanel.setLayout(new BoxLayout(confirmationPanel, BoxLayout.Y_AXIS));
        confirmationPanel.setBackground(Colors.getBackgroundColor());
        if (eventType == EventType.RETURN_ADD_SUCCESSFUL){
            JLabel confirmation = new JLabel("New submission has been added successfully.");
            confirmation.setFont(Fonts.getHeaderFont());
            confirmation.setBackground(Colors.getBackgroundColor());
            confirmation.setForeground(Colors.getHeaderColor());
            confirmationPanel.add(confirmation);
        }
        else if (eventType == EventType.RETURN_EDIT_SUCCESSFUL){
            JLabel confirmation = new JLabel("The post has been edited: ");
            confirmation.setFont(Fonts.getHeaderFont());
            confirmation.setBackground(Colors.getBackgroundColor());
            confirmation.setForeground(Colors.getHeaderColor());
            confirmationPanel.add(confirmation);
        }
        else if (eventType == EventType.RETURN_REMOVE_SUCCESSFUL){
            JLabel confirmation = new JLabel("The post has been deleted.");
            confirmation.setFont(Fonts.getHeaderFont());
            confirmation.setBackground(Colors.getBackgroundColor());
            confirmation.setForeground(Colors.getHeaderColor());
            confirmationPanel.add(confirmation);
        }
        return confirmationPanel;
    }
    private JTextArea getConfirmationInfo (Object data){
        JTextArea infoText = new JTextArea();
        if (data instanceof JobSeeker seeker){
            infoText.setText(seeker.printout());
        }
        else if (data instanceof JobOpening opening){
            infoText.setText(opening.printout());
        }
        infoText.setEditable(false);
        infoText.setFont(Fonts.getButtonFont());
        infoText.setForeground(Colors.getHeaderColor());
        infoText.setBackground(Colors.getButtonBackgroundColor());
        infoText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return infoText;
    }
    private String findTerm(Object data) {
        System.out.println("findTerm in ResultPanel is reached. Data is: " + data.getClass());
        String term = "";
        if (eventType == EventType.RETURN_FOUND_THIS_OPENING){
            term = " job opening ";
        }
        else if (eventType == EventType.RETURN_FOUND_OPENINGS){
            term = " job openings ";
        }
        else if (eventType == EventType.RETURN_FOUND_THIS_SEEKER){
            term = " job seeker ";
        }
        else if (eventType == EventType.RETURN_FOUND_SEEKERS){
            term = " job seekers ";
        }
        else if (data instanceof List list && list.getFirst() instanceof JobSeeker){
            if (list.size() > 1){
                term = "job seekers";
            }
            else {
                term = "job seeker";
            }
        }
        else if (data instanceof List list && list.getFirst() instanceof JobOpening){
            if (list.size() > 1){
                term = "job openings";
            }
            else {
                term = "job opening";
            }
        }
        return term;
    }
    private void getData(Object data){
        if (data != null && data instanceof List list){
            if (list.getFirst() instanceof JobSeeker){
                this.jobSeekerList = list;
            }
            else if (list.getFirst() instanceof JobOpening){
                this.jobOpeningList = list;
            }
        }
        else if (data != null && data instanceof JobSeeker){
            this.jobSeeker = (JobSeeker) data;
        }
        else if (data != null && data instanceof JobOpening){
            this.jobOpening = (JobOpening) data;
        }
    }
    private String getPrintout(boolean showSeekers){
        System.out.println("getPrintout is reached");
        StringBuilder printout = new StringBuilder();
        if (showSeekers){
            for (JobSeeker seeker : jobSeekerList){
                printout.append(seeker.printout());
                printout.append("\n\n");
            }
        }
        else {
            for (JobOpening opening : jobOpeningList){
                printout.append(opening.printout());
                printout.append("\n\n");
                System.out.println("opening.toDataString is: " + opening);
            }
        }
        System.out.println("Printout is: " + printout);
        return printout.toString();
    }

    public void checkInput(EventType eventType, Object data) {
        switch (eventType){
            case RETURN_ADD_OPENING, RETURN_ADD_SEEKER, RETURN_ADD_SUCCESSFUL, RETURN_EDIT_SUCCESSFUL, RETURN_REMOVE_SUCCESSFUL ->{
                displayConfirmation(eventType, data);
            }
            case RETURN_FOUND_THIS_OPENING, RETURN_FOUND_THIS_SEEKER -> {
                displayFoundTerm(data);
            }
            case RETURN_FOUND_OPENINGS, RETURN_FOUND_SEEKERS -> {
                displayList(data);
            }
        }
    }
    @Override
    public void update(EventType eventType, Object data) {
        System.out.println("update in ResultPanel was reached, case is: " + eventType);
        switch (eventType){
            case RETURN_ADD_OPENING, RETURN_ADD_SEEKER ->{
                displayConfirmation(eventType, data);
            }
            case RETURN_FOUND_THIS_OPENING, RETURN_FOUND_THIS_SEEKER -> {
                displayFoundTerm(data);
            }
            case RETURN_FOUND_OPENINGS, RETURN_FOUND_SEEKERS -> {
                displayList(data);
            }
        }
    }
}
