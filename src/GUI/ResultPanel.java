package GUI;

import Controller.Event;
import model.JobOpening;
import model.JobSeeker;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResultPanel extends JPanel implements Subscriber {
    private MainFrame mainFrame;
    private PanelDecorator decorator;
    private JPanel centerPanel;
    private Controller.Event event;
    List<JobOpening> jobOpeningList = new ArrayList<>();
    List<JobSeeker> jobSeekerList = new ArrayList<>();
    private JobOpening jobOpening;
    private JobSeeker jobSeeker;
    private String[] allUserinput;
    int totalCount;

    public ResultPanel(MainFrame mainFrame, Controller.Event event, PanelDecorator decorator){
        this.mainFrame = mainFrame;
        this.event = event;
        this.decorator = decorator;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(550, 500));
        setBackground(Colors.getBackgroundColor());
        setLayout(new BorderLayout());
        if (centerPanel == null){
            this.centerPanel = new JPanel();
        }
        else if (centerPanel != null){
            centerPanel.removeAll();
        }
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Colors.getBackgroundColor());
        HeaderPanel headerPanel = new HeaderPanel(decorator, event);
        if (event.getOutcome() != Event.Outcome.FAILURE && event.getOutcome() != Event.Outcome.NOT_FOUND) {
            getData(event.getContents());
            checkInput(event);
        }
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
        repaint();
        revalidate();
    }
    private void displayFoundTerm() {
        System.out.println("displayfoundTerm was reached");
        centerPanel.removeAll();

        JTextArea resultArea = new JTextArea();
        String text = "";
        if (jobSeeker != null) {
            text = jobSeeker.printout();

        }
        else if (jobOpening != null){
            text = jobOpening.printout();
        }
        resultArea.setText(text);

        JPanel wrapperPanel = new JPanel();
        getTextArea(wrapperPanel, text);
        wrapperPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 40, 10, 40)
        );
        wrapperPanel.setBackground(Colors.getBackgroundColor());
        centerPanel.add(wrapperPanel, BorderLayout.CENTER);
        repaint();
        revalidate();
    }
    private void displayList(Event event){
        centerPanel.removeAll();
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Colors.getBackgroundColor());
        if (event.getSubject() == Event.Subject.SEEKER) {
            String result = getPrintout(true);
            getTextArea(wrapperPanel, result);
        }
        else if (event.getSubject() == Event.Subject.OPENING){
            String result = getPrintout(false);
            getTextArea(wrapperPanel, result);
        }
        wrapperPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 40, 10, 40)
        );
        centerPanel.add(wrapperPanel, BorderLayout.CENTER);
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
        JPanel countPanel = new JPanel(new GridLayout(1, 3));
        JLabel count = new JLabel("Total count:");
        JTextArea showNumber = new JTextArea(String.valueOf(totalCount));
        JPanel placeHolder = new JPanel();
        placeHolder.setBackground(Colors.getBackgroundColor());

        countPanel.add(count);
        countPanel.add(showNumber);
        countPanel.add(placeHolder);

        decorator.adjustLabel(count);
        decorator.adjustTextArea(showNumber);
        wrapperPanel.add(countPanel, BorderLayout.NORTH);
        wrapperPanel.add(scrollResults, BorderLayout.CENTER);
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
                totalCount+=1;
                printout.append(seeker.printout());
                printout.append("\n\n");
            }
        }
        else {
            for (JobOpening opening : jobOpeningList){
                totalCount+=1;
                printout.append(opening.printout());
                printout.append("\n\n");
                System.out.println("opening.toDataString is: " + opening);
            }
        }
        System.out.println("Printout is: " + printout);
        return printout.toString();
    }

    public void checkInput(Controller.Event event) {
        System.out.println("checkInput is reached");
        this.event = event;
        if (event.getAction() == Event.Action.VIEW){
            displayList(event);
        }
        else if(event.getAction() == Event.Action.SEARCH){
                if (jobOpening != null || jobSeeker != null) {
                    displayFoundTerm();
                }
                else {
                    displayList(event);
                }
            }
        else if (event.getAction() == Event.Action.EDIT && event.getOutcome() == Event.Outcome.OK) {
            displayFoundTerm();
        }
            else {
                displayList(event);
            }
        }

    @Override
    public void Update(Controller.Event event) {
        switch (event.getAction()){
            case SEARCH -> {
                displayFoundTerm();
            }
            default -> {
                displayList(event);
            }
        }
    }
}
