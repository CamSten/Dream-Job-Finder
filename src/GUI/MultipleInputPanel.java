package GUI;

import Controller.Event;
import model.EducationLevel;
import model.JobOpening;
import model.JobSeeker;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MultipleInputPanel extends JPanel implements Subscriber {
    private MainFrame mainFrame;
    private PanelDecorator decorator;
    private Event event;
    private Event.Subject subject;
    private Event.Action action;
    private List<JLabel> allLabels = new ArrayList<>();
    private List<JTextField> allInputFields = new ArrayList<>();
    private String[] allUserinput;
    private String[] allInitialUserInput;
    private JComboBox inputEdu;

    public MultipleInputPanel(MainFrame mainFrame, Event event, PanelDecorator decorator){
        this.mainFrame = mainFrame;
        this.decorator = decorator;
        this.event = event;
        this.subject = event.getSubject();
        this.action = event.getAction();
        setLayout(new BorderLayout());
        setBackground(Colors.getBackgroundColor());
        JPanel headerPanel = new HeaderPanel(decorator, event);
        add(headerPanel, BorderLayout.NORTH);
        add(getMultipleInputPanel(event), BorderLayout.CENTER);
    }
    public JPanel getMultipleInputPanel(Controller.Event event) {
        this.allInputFields = getFieldList(event.getContents());
        allLabels = new ArrayList<>();
        if(subject == Event.Subject.SEEKER){
            allLabels = getInputPromptsSeeker();
        }
        else if(subject == Event.Subject.OPENING){
            allLabels = getInputPromptOpening();
        }
        allUserinput = new String[allLabels.size() + 1];
        for (JLabel l : allLabels) {
            decorator.adjustLabel(l);
        }
        for (JTextField t : allInputFields) {
            decorator.editInputField(t);
        }
        JPanel inputPanel = new JPanel();
        decorator.adjustInputPanel(inputPanel);
        for (int i = 0; i < allInputFields.size(); i++) {
            inputPanel.add(getAdjustedInputPanel(allLabels.get(i), allInputFields.get(i)));
        }
        inputPanel.add(getEduRow());
        inputPanel.setBackground(Colors.getHeaderColor());
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.setBackground(Colors.getHeaderColor());
        wrapperPanel.setBorder(BorderFactory.createLineBorder(Colors.getHeaderColor(), 5, true));
        wrapperPanel.add(inputPanel);
        wrapperPanel.add(getSubmitButton());
        return wrapperPanel;
    }
    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Submit");
        decorator.adjustSubmitButton(submitButton);
        submitButton.addActionListener(_ -> {
            gatherInput();

        });
        return submitButton;
    }
    public List<JLabel> getInputPromptsSeeker(){
        allLabels = new ArrayList<>();
        JLabel promptName = new JLabel("Name: ");
        allLabels.add(promptName);
        JLabel promptExp = new JLabel("Years of experience: ");
        allLabels.add(promptExp);
        JLabel promptBranch = new JLabel("Work area: ");
        allLabels.add(promptBranch);
        return allLabels;
    }
    public List<JLabel> getInputPromptOpening(){
        allLabels = new ArrayList<>();
        JLabel promptName = new JLabel("Title of job opening: ");
        allLabels.add(promptName);
        JLabel promptExp = new JLabel("Years of experience required: ");
        allLabels.add(promptExp);
        JLabel promptBranch = new JLabel("Work area: ");
        allLabels.add(promptBranch);
        return allLabels;
    }

    public java.util.List<JTextField> getFieldList(Object data) {
        this.allInputFields = new ArrayList<>();
        JTextField inputName = new JTextField();
        allInputFields.add(inputName);
        JTextField inputYearsExp = new JTextField();
        allInputFields.add(inputYearsExp);
        JTextField inputBranch = new JTextField();
        allInputFields.add(inputBranch);
        if (data instanceof JobSeeker || data instanceof JobOpening){
            setInputText(data);
        }
        return allInputFields;
    }

    public void setInputText(Object data){
        this.allInitialUserInput = new String[allInputFields.size()+1];
        if (data instanceof JobOpening opening){
            List<String> openingInfo = new ArrayList<>();
            openingInfo.add(opening.getTitle());
            openingInfo.add(String.valueOf(opening.getMinYearsExperience()));
            openingInfo.add(opening.getWorkArea());
            for (int i = 0; i < allInputFields.size(); i++){
                JTextField inputField = allInputFields.get(i);
                inputField.setText(openingInfo.get(i));
            }
        }
        else if(data instanceof JobSeeker seeker){
            List<String> seekerInfo = new ArrayList<>();
            seekerInfo.add(seeker.getFullName());
            seekerInfo.add(String.valueOf(seeker.getYearsExperience()));
            seekerInfo.add(seeker.getWorkArea());
            for (int i = 0; i < allInputFields.size(); i++){
                JTextField inputField = allInputFields.get(i);
                inputField.setText(seekerInfo.get(i));
                allInitialUserInput[i] = seekerInfo.get(i);
            }
        }
    }
    public JPanel getAdjustedInputPanel (JLabel label, Object inputArea){
        JPanel inputPanel = new JPanel();
        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new GridLayout(1, 2));
        queryPanel.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
        queryPanel.setBackground(Colors.getHeaderColor());
        queryPanel.add(label);
        if (inputArea instanceof JComboBox input) {
            queryPanel.add(input);
        }
        else if (inputArea instanceof JTextField input) {
            queryPanel.add(input);
        }
        inputPanel.add(Box.createVerticalStrut(5));
        inputPanel.add(queryPanel);
        inputPanel.add(Box.createVerticalStrut(5));
        return inputPanel;
    }
    public JPanel getEduRow(){
        JPanel eduRow = new JPanel();
        eduRow.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
        eduRow.setBackground(Colors.getHeaderColor());
        JLabel promptEdu = new JLabel("Level of education: ");
        decorator.adjustLabel(promptEdu);
        eduRow.add(promptEdu);
        this.inputEdu =
                new JComboBox<>(EducationLevel.values());
        if (action == Event.Action.EDIT){
            model.EducationLevel value = getEduValue();
            inputEdu.setSelectedItem(value);
            allInitialUserInput[allInputFields.size()] = value.toString();
        }
        inputEdu.setFont(Fonts.getButtonFont());
        inputEdu.setBackground(Color.WHITE);
        inputEdu.setBorder(BorderFactory.createLineBorder(Colors.getButtonBackgroundColor(), 3, true));
        inputEdu.setForeground(Colors.getButtonTextColor());
        inputEdu.setPreferredSize(new Dimension(200, 45) );
        for (int i = 0; i < 2; i++) {
            inputEdu.setMinimumSize(new Dimension(200, 45));
        }
        return getAdjustedInputPanel(promptEdu, inputEdu);
    }
    public EducationLevel getEduValue(){
        EducationLevel eduValue = null;
        if(event.getContents() instanceof JobSeeker jobSeeker){
            eduValue = jobSeeker.getEducationLevel();
        }
        else if (event.getContents() instanceof JobOpening jobOpening){
            eduValue = jobOpening.getRequiredEducation();
        }
        return eduValue;
    }
    private void gatherInput(){
        for (int i = 0; i < allInputFields.size(); i++) {
            allUserinput[i] = allInputFields.get(i).getText();
        }
        allUserinput[allInputFields.size()] = inputEdu.getSelectedItem().toString();
        boolean completed = true;
        for (String s : allUserinput) {
            if (s == null || s.isBlank()) {
                completed = false;
                JOptionPane.showMessageDialog(null, "The form has not been completed.");
            }
        }

        try {
         Integer.parseInt(allUserinput[1]);
        }
        catch (NumberFormatException e) {
                completed = false;
            JOptionPane.showMessageDialog(null, "You may only submit a number value for Experience");
            }
        if (completed) {
            Object contents = allUserinput;
            Object extraContentents = null;
             if (event.getAction() == Event.Action.EDIT){
                 contents = event.getContents();
                extraContentents = allUserinput;
            }
            Update(Event.submit(action, subject, contents, extraContentents));
        }
    }

    @Override
    public void Update(Event event) {
        if (event.getAction() == Event.Action.ADD){
            event.setPhase(Event.Phase.HANDLING);
        }
        mainFrame.Update(event);
    }
}
