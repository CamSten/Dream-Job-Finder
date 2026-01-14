package GUI;

import Controller.Event;
import model.JobOpening;
import model.JobSeeker;
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
    private JTextArea resultArea;
    boolean scroll;

    public ResultPanel(MainFrame mainFrame, Controller.Event event, PanelDecorator decorator, boolean scroll){
        System.out.println("ResultPanel constructor is reached");
        this.mainFrame = mainFrame;
        this.event = event;
        this.decorator = decorator;
        this.scroll = true;
        getData(event.getContents());
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(550, 500));
        setBackground(Colors.getBackgroundColor());
        if (centerPanel == null){
            this.centerPanel = new JPanel();
        }
        else if (centerPanel != null){
            centerPanel.removeAll();
        }
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Colors.getBackgroundColor());
        HeaderPanel headerPanel = new HeaderPanel(decorator, event);
        add(headerPanel, BorderLayout.NORTH);
        checkInput(event);
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
        repaint();
        revalidate();
    }
    private void displayFoundTerm() {
        centerPanel.removeAll();
        this.resultArea = new JTextArea();
        String text = "";
        if (event.getAction() == Event.Action.REMOVE){
            text = (String) event.getContents();
        }
        if (event.getPhase() == Event.Phase.MATCH_RESULT){
            text = (String) event.getContents();
        }
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
        JTextArea resultArea = new JTextArea(result);
        decorator.adjustTextArea(resultArea);
        wrapperPanel.add(resultArea, BorderLayout.CENTER);
        if (scroll){
            addScrollBar(wrapperPanel, resultArea);
        };
    }

    private void getData(Object data){
        if (data != null && data instanceof ArrayList list){
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
        StringBuilder printout = new StringBuilder();
        if (event.getPhase() == Event.Phase.MATCH_RESULT){
            if (event.getContents() instanceof List list && list.getFirst() instanceof String){
                List<String> results = (List<String>) event.getContents();
                for (String line : results){

                    String [] parts = line.split(";");
                    for (int i = 0; i < parts.length; i++){
                        printout.append(parts[i]);
                    }
                    printout.append("\n\n");
                }
            }
        }
        else if (showSeekers){
            for (JobSeeker seeker : jobSeekerList){
                printout.append(seeker.printout());
                printout.append("\n\n");
            }
        }
        else {
            for (JobOpening opening : jobOpeningList){
                printout.append(opening.printout());
                printout.append("\n\n");
            }
        }
        return printout.toString();
    }

    public void checkInput(Controller.Event event) {
        this.event = event;

        if (event.getAction() == Event.Action.VIEW){
            displayList(event);
        }
        else if (event.getAction() == Event.Action.REMOVE || event.getAction() == Event.Action.ADD){
            displayFoundTerm();
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
    public void addScrollBar(JPanel wrapperPanel, JTextArea resultArea){
        JScrollPane scrollResults = new JScrollPane(resultArea);
        scrollResults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollResults.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollResults.setPreferredSize(new Dimension(550, 400));
        SwingUtilities.invokeLater(() ->
                scrollResults.getVerticalScrollBar().setValue(0)
        );
        wrapperPanel.add(scrollResults);
        repaint();
        revalidate();
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
