package GUI;

import Controller.Event;
import model.JobOpening;
import model.JobSeeker;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OptionPanel extends JPanel implements Subscriber{
    private MainFrame mainFrame;
    private PanelDecorator decorator;
    private Event event;
    private Event.Subject subject;
    private Event.Action action;
    private List<JobSeeker> jobSeekerList;
    private List<JobOpening> jobOpeningList;
    private JPanel displayingResult;
    private JPanel inputPanel;

    public OptionPanel (MainFrame mainFrame, PanelDecorator decorator, Event event){
        this.mainFrame = mainFrame;
        this.event = event;
        this.subject = event.getSubject();
        this.action = event.getAction();
        this.decorator = decorator;
        setLayout(new BorderLayout());
        setBackground(Colors.getBackgroundColor());
        JPanel headerPanel = new HeaderPanel(decorator, event);
        add(headerPanel, BorderLayout.NORTH);
        add(getOptionsPanel(event), BorderLayout.CENTER);
    }

    public JPanel getOptionsPanel(Event event){
        if (subject == Event.Subject.OPENING){
            jobOpeningList = (List<JobOpening>) event.getContents();
        }
        else if ( subject == Event.Subject.SEEKER){
            jobSeekerList = (List<JobSeeker>) event.getContents();
        }

        this.displayingResult = new JPanel();
        displayingResult.setLayout(new BoxLayout(displayingResult, BoxLayout.Y_AXIS));
        displayingResult.setBackground(Colors.getBackgroundColor());
        for (JPanel results : getResult()){
            displayingResult.add(results);
            displayingResult.add(Box.createVerticalStrut(5));
        }
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(Colors.getBackgroundColor());
        wrapperPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 40, 10, 40)
        );
        decorator.adjustWrapperPanel(wrapperPanel);
        this.inputPanel = wrapperPanel;
        addScrollBar(inputPanel, displayingResult);
        return wrapperPanel;
    }

    private List<JPanel> getResult(){
        List <JPanel> allSingleResultPanels = new ArrayList<>();
        String printout;
        if (subject == Event.Subject.SEEKER) {
            for (JobSeeker seeker: jobSeekerList) {
                JPanel singleResultPanel = new JPanel();
                decorator.adjustSingleResultPanel(singleResultPanel);

                printout = seeker.printoutWithoutName() + "\n";
                String name = seeker.getFullName();
                JLabel nameLabel = new JLabel("Name: ");
                decorator.adjustLabel(nameLabel);
                JButton nameButton = getNameButtonSeeker(name, seeker);
                JPanel namePanel = getAdjustedInputPanel(nameLabel, nameButton);
                decorator.adjustSingleResultLine(namePanel);
                String[] part = printout.split("-");
                singleResultPanel.add(namePanel);
                singleResultPanel.add(Box.createVerticalStrut(5));

                for (String s : part) {
                    JPanel singleResultLine = new JPanel();
                    decorator.adjustSingleResultLine(singleResultLine);
                    String[] text = s.split(";");
                    JLabel infoTextType = new JLabel(text[0]);
                    JTextArea infoTextInput = new JTextArea(text[1]);
                    decorator.adjustLabel(infoTextType);
                    decorator.adjustTextArea(infoTextInput);
                    singleResultLine.add(infoTextType);
                    singleResultLine.add(infoTextInput);
                    singleResultPanel.add(singleResultLine);
                    singleResultPanel.add(Box.createVerticalStrut(5));
                }
                allSingleResultPanels.add(singleResultPanel);

            }
        }
        else {
            for (int i = 0; i < jobOpeningList.size(); i++){
                printout = jobOpeningList.get(i).printout() + "\n\n";
                JPanel singleResultPanel = new JPanel();
                JButton result = getNameButtonOpening(i, printout);
                singleResultPanel.add(Box.createVerticalStrut(5));
                singleResultPanel.add(result);
                singleResultPanel.add(Box.createVerticalStrut(5));
                allSingleResultPanels.add(singleResultPanel);
            }
        }
        return allSingleResultPanels;
    }
    public JPanel getAdjustedInputPanel (JLabel label, Object inputArea){
        JPanel inputPanel = new JPanel();
        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new GridLayout(1, 2));
//        queryPanel.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
        queryPanel.setBackground(Colors.getHeaderColor());
        queryPanel.add(label);
        if(inputArea instanceof JTextField) {
            JTextField textField = (JTextField) inputArea;
            queryPanel.add(textField);
        }
        else if (inputArea instanceof JButton){
            JButton button = (JButton) inputArea;
            queryPanel.add(button);
        }
        inputPanel.add(Box.createVerticalStrut(5));
        inputPanel.add(queryPanel);
        inputPanel.add(Box.createVerticalStrut(5));
        return inputPanel;
    }
    public JButton getNameButtonSeeker(String name, JobSeeker thisSeeker) {
        JButton nameButton = new JButton(name);
        nameButton.setHorizontalAlignment(SwingConstants.LEFT);
        decorator.adjustButton(nameButton);
        Event newEvent = Event.select(event.getAction(), subject, thisSeeker);
        nameButton.addActionListener(_ -> Update(newEvent));
        return nameButton;
    }
    public JButton getNameButtonOpening(int i, String printout) {
        JButton result = new JButton(printout);
        result.setHorizontalAlignment(SwingConstants.LEFT);
        result.setContentAreaFilled(true);
        decorator.adjustButton(result);
        result.addActionListener(_ -> Update(Event.select(event.getAction(), subject, jobOpeningList.get(i))));
        return result;
    }
    public void addScrollBar(JPanel inputPanel, JPanel panel){
        inputPanel.removeAll();
        JScrollPane scrollResults = new JScrollPane(panel);
        scrollResults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollResults.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollResults.setPreferredSize(new Dimension(550, 400));
        SwingUtilities.invokeLater(() ->
                scrollResults.getVerticalScrollBar().setValue(0)
        );
        inputPanel.add(scrollResults);

    }
    @Override
    public void Update(Event newEvent) {
        mainFrame.Update(newEvent);
    }
}
