package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdderPanel extends JPanel implements Subscriber {
    private MainFrame mainFrame;
    private JPanel centerpanel;
    private String term;
    List<JLabel> allLabels;
    List<JTextField> allInputFields;
    private JPanel promptPanel;
    private JPanel inputFieldsPanel;
    private MainFrame.AdderObject objectType;
    private EventType eventType;
    private String[] allUserinput;

    public AdderPanel (MainFrame mainFrame, MainFrame.AdderObject objectType){
        this.mainFrame = mainFrame;
        setTerm(objectType);
        setLayout(new BorderLayout());
        setBackground(Colors.getBackgroundColor());
        setMinimumSize(new Dimension(550, 500));
        if (centerpanel == null){
            this.centerpanel = new JPanel();
            add(centerpanel, BorderLayout.CENTER);
            centerpanel.setLayout(new BorderLayout());
            centerpanel.setBackground(Colors.getBackgroundColor());
        }
        else {
            centerpanel.removeAll();
        }
        addInputFields(objectType);
        repaint();
        revalidate();
    }
    private void addInputFields(MainFrame.AdderObject objectType){

        List<JTextField> allInputFields = getInputFields();
        JPanel headerPanel = getHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        if (objectType == MainFrame.AdderObject.SEEKER){
            allLabels = getInputPromptsSeeker();
        }
        else {
            allLabels = getInputPromptOpening();
        }
        this.promptPanel = new JPanel();
        this.inputFieldsPanel = new JPanel();
        adjustPanel(allLabels.size(), promptPanel);
        adjustPanel(allLabels.size(), inputFieldsPanel);

        for(JLabel l : allLabels){
            l.setForeground(Colors.getButtonTextColor());
            l.setPreferredSize(new Dimension(270, 45) );
            l.setMinimumSize(new Dimension(270, 45));
            l.setMinimumSize(new Dimension(270, 45));
            l.setFont(Fonts.getInputPromptFont());
        }
        for (JTextField t : allInputFields) {
            t.setForeground(Colors.getButtonTextColor());
            t.setPreferredSize(new Dimension(180, 45));
            t.setMinimumSize(new Dimension(180, 45));
            t.setMinimumSize(new Dimension(180, 45));
            t.setFont(Fonts.getInputPromptFont());
        }

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(true);
        inputPanel.setVisible(true);
        inputPanel.setEnabled(true);
        inputPanel.setBackground(Colors.getHeaderColor());
        inputPanel.setBorder(BorderFactory.createLineBorder(Colors.getHeaderColor(), 10, true));

        for (int i = 0; i < allLabels.size(); i++){
            JPanel queryPanel = new JPanel();
            queryPanel.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
            queryPanel.setBackground(Colors.getHeaderColor());
            queryPanel.add(allLabels.get(i));
            queryPanel.add(allInputFields.get(i));
            inputPanel.add(Box.createVerticalStrut(5));
            inputPanel.add(queryPanel);
            inputPanel.add(Box.createVerticalStrut(5));
        }
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(Colors.getHeaderColor());
        wrapperPanel.setBorder(BorderFactory.createLineBorder(Colors.getHeaderColor(), 5, true));
        wrapperPanel.add(inputPanel);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(Fonts.getButtonFont());
        submitButton.setBackground(Colors.getButtonBackgroundColor());
        submitButton.setBorder(BorderFactory.createLineBorder(Colors.getButtonBackgroundColor(), 3, true));
        submitButton.setForeground(Colors.getButtonTextColor());
        submitButton.setPreferredSize(new Dimension(200, 45) );
        submitButton.setMinimumSize(new Dimension(200, 45));
        submitButton.setMinimumSize(new Dimension(200, 45));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkIfComplete();
            }
        });
        JPanel submitPanel = new JPanel();
        submitPanel.setBackground(Colors.getBorderColor());
        submitPanel.add(submitButton);
        submitPanel.setBorder(BorderFactory.createLineBorder(Colors.getButtonBackgroundColor(), 10, true));
        JPanel submitWrapper = new JPanel();
        submitWrapper.setBackground(Colors.getBackgroundColor());
        submitWrapper.add(submitPanel);

        allUserinput = new String[allLabels.size()];
        centerpanel.add(wrapperPanel, BorderLayout.CENTER);
        centerpanel.add(submitWrapper, BorderLayout.SOUTH);
        repaint();
        revalidate();
    }

    private JPanel getHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(2,1));
        headerPanel.setBackground(Colors.getBackgroundColor());
        JLabel header = new JLabel("Adding new " + term);
        header.setForeground(Colors.getHeaderColor());
        header.setFont(Fonts.getHeaderFont());
        JLabel prompt = new JLabel("Please complete the form: ");
        prompt.setFont(Fonts.getButtonFont());
        prompt.setForeground(Colors.getHeaderColor());
        headerPanel.add(header);
        headerPanel.add(prompt);
        return headerPanel;
    }

    private void adjustPanel(int size, JPanel panel) {
        panel.setVisible(true);
        panel.setLayout(new BoxLayout(promptPanel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(250, size * 60));
        panel.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
    }

    private List<JTextField> getInputFields() {
        allInputFields = new ArrayList<>();
        JTextField inputIP = new JTextField();
        allInputFields.add(inputIP);

        JTextField inputName = new JTextField();
        allInputFields.add(inputName);
        JTextField inputYearsExp = new JTextField();
        allInputFields.add(inputYearsExp);
        JTextField inputEdu = new JTextField();
        allInputFields.add(inputEdu);
        JTextField inputBranch = new JTextField();
        allInputFields.add(inputBranch);
        return allInputFields;
    }

    private List<JLabel> getInputPromptsSeeker(){
         this.allLabels = new ArrayList<>();
            JLabel promptID = new JLabel("Job seeker ID: ");
            allLabels.add(promptID);
            JLabel promptName = new JLabel("Name: ");
            allLabels.add(promptName);
            JLabel promptExp = new JLabel("Years of experience: ");
            allLabels.add(promptExp);
            JLabel promptEdu = new JLabel("Level of education: ");
            allLabels.add(promptEdu);
            JLabel promptBranch = new JLabel("Professional field: ");
            allLabels.add(promptBranch);
        System.out.println("in getInputPromptsSeeker, list.size is: " + allLabels.size());
        return allLabels;
    }
    private List<JLabel> getInputPromptOpening(){
        this.allLabels = new ArrayList<>();
            JLabel promptID = new JLabel("Job opening ID: ");
            allLabels.add(promptID);
            JLabel promptName = new JLabel("Title of job opening: ");
            allLabels.add(promptName);
            JLabel promptExp = new JLabel("Years of experience required: ");
            allLabels.add(promptExp);
            JLabel promptEdu = new JLabel("Level of education required: ");
            allLabels.add(promptEdu);
            JLabel promptBranch = new JLabel("Professional field: ");
            allLabels.add(promptBranch);
        return allLabels;
    }
    private void setTerm(MainFrame.AdderObject object){
        if (object == MainFrame.AdderObject.SEEKER){
            this.term = "job seeker";
            this.eventType = EventType.RETURN_ADD_SEEKER;
        }
        else {
            this.term = "job opening";
            this.eventType = EventType.RETURN_ADD_OPENING;
        }
    }
    private void gatherInput(){
        for (int i = 0; i < allLabels.size(); i++) {
            if (allInputFields.get(i).getText() != null) {
                allUserinput[i] = allInputFields.get(i).getText();
            }
        }
    }
    private void checkIfComplete(){
        gatherInput();
        System.out.println("checkIfCompleted in AdderPanel is reached");
        boolean allIsCollected = true;
        for (int i = 0; i < allLabels.size(); i++){
            System.out.println("allUserInput " + i + " is: " + allUserinput[i]);
            if (allUserinput[i] == null  || allUserinput[i].isEmpty()){
                allIsCollected = false;
            }
        }
        if (allIsCollected){
            System.out.println("allIsCollected is true");
            update(eventType, allUserinput);
            clearAll();
        }
        else {
            JOptionPane.showMessageDialog(null, "All fields must be completed before submitting.");
        }
    }
    private void clearAll(){
        for (JTextField t : allInputFields){
            t.setText("");
        }
    }

    @Override
    public void update(EventType option, Object data) {
        mainFrame.update(option, data);
    }
}
