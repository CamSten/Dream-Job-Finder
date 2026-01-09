package GUI;

import model.EducationLevel;
import model.JobOpening;
import model.JobSeeker;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PanelMaker {
    private String term;
    private String promptTerm = "";
    private String actionTerm = "";
    private String imperativeActionTerm;
    List<JLabel> allLabels = new ArrayList<>();
    List<JTextField> allInputFields = new ArrayList<>();
    private JPanel promptPanel;
    private JPanel inputFieldsPanel;
    private JTextField inputField;
    private Subscriber.EventType eventTypeRequest;
    private Subscriber.EventType eventType;
    private String[] allUserinput;
    private String[] allInitialUserInput;
    private JComboBox inputEdu;
    private MainFrame mainFrame;
    private Object data;
    private boolean edit = false;
    String pluralNoun = "";
    String pluralVerb = "s";
    private List<JobOpening> jobOpeningList = new ArrayList<>();
    private List<JobSeeker> jobSeekerList = new ArrayList<>();
    private JPanel multipleInputPanel;
    private JPanel singleInputPanel;
    JTextField singleInputField;
    private JPanel buttonPanel;
    private EditPanel editPanel;
    private RemovePanel removePanel;
    private Subscriber.EventType.Subject subject;
    private Object editObject;

    public PanelMaker(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    public JPanel getPanels(Subscriber.EventType eventTypeRequest, Object data) {
        this.data = data;
        this.subject = eventTypeRequest.getSubject();
        setList(data);
        setTerms(eventTypeRequest);
        System.out.println("getPanels in PanelMaker is reached, eventType is: " + eventTypeRequest + " and eventTypeRequest.getInputType() is: " + eventTypeRequest.getInputType() );
        this.eventTypeRequest = eventTypeRequest;
        JPanel headerPanel = getHeaderPanel(eventTypeRequest);
        JPanel inputPanel = getInputPanel(eventTypeRequest, data);
        JPanel panels = new JPanel();
        panels.setLayout(new BoxLayout(panels, BoxLayout.Y_AXIS));
        panels.add(headerPanel);
        panels.add(inputPanel);
        if (shouldShowSubmitButton(eventTypeRequest)) {
            this.buttonPanel = getSubmitButtonPanel(eventTypeRequest);
            panels.add(buttonPanel);
        }
        System.out.println("----panels is returned from getPanels");
        return panels;
    }
    private JPanel getInputPanel(Subscriber.EventType eventTypeRequest, Object data){
        System.out.println("getInputPanel in PanelMaker is reached. EventType is: " + eventTypeRequest);
        JPanel inputPanel = new JPanel();
        if (eventTypeRequest.getAction() == Subscriber.EventType.Action.ADD){
            inputPanel = getMultipleInputPanel(eventTypeRequest, data);
        }
        else if(eventTypeRequest.getInputType() == Subscriber.EventType.InputType.SINGLE){
            inputPanel = getSingleInputPanel(eventTypeRequest);
        }
        //Action.EDIT, Subject.NONE, InputType.NONE, Quantity.MULTIPLE, Status.ASSESSED_INPUT),
        // (Action.EDIT, Subject.SEEKER, InputType.MULTIPLE, Quantity.NONE, Status.AWAIT_INPUT),
        else if (eventTypeRequest == Subscriber.EventType.RETURN_OPTIONS){
            inputPanel = getOptionsPanel(data);
        }
        else if(eventTypeRequest.getInputType() == Subscriber.EventType.InputType.MULTIPLE) {
            inputPanel = getMultipleInputPanel(eventTypeRequest, data);
        }
        return inputPanel;
    }

    private boolean shouldShowSubmitButton(Subscriber.EventType eventType) {
        return switch (eventType) {
            case REQUEST_ADD_OPENING,
                 REQUEST_ADD_SEEKER,
                 REQUEST_EDIT_OPENING,
                 REQUEST_EDIT_SEEKER,
                 REQUEST_EDIT_FIELDS_OPENING,
                 REQUEST_EDIT_FIELDS_SEEKER,
                 REQUEST_REMOVE_SEEKER,
                 REQUEST_REMOVE_OPENING,
                 REQUEST_SEARCH_OPENING,
                 REQUEST_SEARCH_SEEKER -> true;

            case RETURN_FOUND_OPENINGS,
                 RETURN_FOUND_SEEKERS -> false;

            default -> false;
        };
    }
    private JPanel getSubmitButtonPanel(Subscriber.EventType eventType) {
        JButton submitButton = getSubmitButton(eventType);
        JPanel submitPanel = new JPanel();
        submitPanel.setBackground(Colors.getBorderColor());
        submitPanel.add(submitButton);
        submitPanel.setBorder(BorderFactory.createLineBorder(Colors.getButtonBackgroundColor(),10,true));
        JPanel submitWrapper = new JPanel();
        submitWrapper.setBackground(Colors.getBackgroundColor());
        submitWrapper.add(submitPanel);

        return submitWrapper;
    }
    private JButton getSubmitButton(Subscriber.EventType eventType) {
        System.out.print("getSubmitButton in PanelMaker was reached, eventType.InputType is: " + eventType.getInputType() + " & eventType is " + eventType );
        JButton submitButton = new JButton("Submit");
        adjustSubmitButton(submitButton);
        submitButton.addActionListener(_ -> {
            if(eventType.getInputType() == Subscriber.EventType.InputType.MULTIPLE){
                gatherMultipleInput(eventType);
            }
            else if (eventType.getInputType() == Subscriber.EventType.InputType.SINGLE){
                gatherSingleInput(eventType);
            }
        });
        return submitButton;
    }
    private void adjustSubmitButton(JButton submitButton) {
        submitButton.setFont(Fonts.getButtonFont());
        submitButton.setBackground(Colors.getButtonBackgroundColor());
        submitButton.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
        submitButton.setForeground(Colors.getButtonTextColor());
        submitButton.setPreferredSize(new Dimension(200, 45) );
        submitButton.setMinimumSize(new Dimension(200, 45));
        submitButton.setMinimumSize(new Dimension(200, 45));
    }
    private boolean checkIfComplete() {
        System.out.println("checkIfCompleted in AdderPanel is reached, allUserInput.Length is: " + allUserinput.length);
        boolean allIsCollected = true;
        for (int i = 0; i < allLabels.size(); i++) {
            System.out.println("allUserInput " + i + " is: " + allUserinput[i]);
            if (allUserinput[i] == null || allUserinput[i].isEmpty()) {
                allIsCollected = false;

            }
        }
        if (allIsCollected) {
            if (eventTypeRequest.getAction() == Subscriber.EventType.Action.EDIT) {
                if (checkIfEdited()) {
                    return true;
                } else {
                    System.out.println("checkIfEdited is false");
                    JOptionPane.showMessageDialog(null, "No edit has been done");
                }
            }
            else {
                return true;
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "All fields must be completed before submitting.");
        }
       return false;
    }
    private boolean checkIfEdited (){
        boolean edited = false;
        for (int i = 0; i < allUserinput.length; i++){
            System.out.println("allUserInput is: " + allUserinput[i] + " and allInitialUserInput is: " + allInitialUserInput[i]);
            if (!allUserinput[i].equalsIgnoreCase(allInitialUserInput[i])){
                edited = true;
            }
        }
        return edited;
    }
    public void clearAllInputFields(){
        for (JTextField t : allInputFields){
            t.setText("");
        }
        inputEdu.setSelectedItem(EducationLevel.NONE);
    }
    private void sendGatheredInput() {
        Subscriber.EventType eventTypeReturn = null;
        if (eventTypeRequest.getAction() == Subscriber.EventType.Action.EDIT && subject == Subscriber.EventType.Subject.OPENING) {
            eventTypeReturn = Subscriber.EventType.RETURN_EDIT_THIS_OPENING;
        }
        else if (eventTypeRequest.getAction() == Subscriber.EventType.Action.EDIT && subject == Subscriber.EventType.Subject.SEEKER){
            eventTypeReturn = Subscriber.EventType.RETURN_EDIT_THIS_SEEKER;
        }
        else if (eventTypeRequest.getAction() == Subscriber.EventType.Action.ADD && subject == Subscriber.EventType.Subject.OPENING){
            eventTypeReturn = Subscriber.EventType.RETURN_ADD_OPENING;
        }
        else if (eventTypeRequest.getAction() == Subscriber.EventType.Action.ADD && subject == Subscriber.EventType.Subject.SEEKER){
            eventTypeReturn = Subscriber.EventType.RETURN_ADD_SEEKER;
        }
            mainFrame.update(eventTypeReturn, allUserinput);
        mainFrame.update(eventTypeReturn,editObject);
        clearAllInputFields();
    }
    private JPanel getMultipleInputPanel(Subscriber.EventType eventType, Object data) {
        System.out.println("_ _ _ _ getMultipleInputPanel is reached");
        List<JTextField> allInputFields = getFieldList(data);
        allLabels = new ArrayList<>();
        if(eventType.getSubject() == Subscriber.EventType.Subject.SEEKER){
            allLabels = getInputPromptsSeeker();
        }
        else if(eventType.getSubject() == Subscriber.EventType.Subject.OPENING){
            allLabels = getInputPromptOpening();
        }
        this.promptPanel = new JPanel();
        this.inputFieldsPanel = new JPanel();
        adjustPanel(allLabels.size(), promptPanel);
        adjustPanel(allLabels.size(), inputFieldsPanel);
        for (JLabel l : allLabels) {
            adjustLabel(l);
        }
        for (JTextField t : allInputFields) {
            editInputField(t);
        }
        JPanel inputPanel = getAdjustedInputPanel();
        inputPanel.add(getEduRow(eventType, data));
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(Colors.getHeaderColor());
        wrapperPanel.setBorder(BorderFactory.createLineBorder(Colors.getHeaderColor(), 5, true));
        wrapperPanel.add(inputPanel);
        allUserinput = new String[allLabels.size() + 1];
        this.multipleInputPanel = wrapperPanel;
        return multipleInputPanel;
    }
    private JPanel getAdjustedInputPanel (){
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(true);
        inputPanel.setVisible(true);
        inputPanel.setEnabled(true);
        inputPanel.setBackground(Colors.getHeaderColor());
        inputPanel.setBorder(BorderFactory.createLineBorder(Colors.getHeaderColor(), 10, true));

        for (int i = 0; i < allInputFields.size(); i++) {
            JPanel queryPanel = new JPanel();
            queryPanel.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
            queryPanel.setBackground(Colors.getHeaderColor());
            queryPanel.add(allLabels.get(i));
            queryPanel.add(allInputFields.get(i));
            inputPanel.add(Box.createVerticalStrut(5));
            inputPanel.add(queryPanel);
            inputPanel.add(Box.createVerticalStrut(5));
        }
        return inputPanel;
    }
    private void adjustPanel(int size, JPanel panel) {
        panel.setVisible(true);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(250, size * 60));
        panel.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
    }
    private void adjustLabel(JLabel label){
        label.setForeground(Colors.getButtonTextColor());
        label.setPreferredSize(new Dimension(270, 45));
        label.setMinimumSize(new Dimension(270, 45));
        label.setMinimumSize(new Dimension(270, 45));
        label.setFont(Fonts.getInputPromptFont());
    }
    private java.util.List<JTextField> getFieldList(Object data) {
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
    private void setInputText(Object data){
        this.allInitialUserInput = new String[allInputFields.size()+1];
        System.out.println("setInputText in PanelMaker is reached");
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
    private List<JLabel> getInputPromptsSeeker(){
        System.out.println("getInputPromptsSeeker is reached");
        allLabels = new ArrayList<>();
        JLabel promptName = new JLabel("Name: ");
        allLabels.add(promptName);
        JLabel promptExp = new JLabel("Years of experience: ");
        allLabels.add(promptExp);
        JLabel promptBranch = new JLabel("Work area: ");
        allLabels.add(promptBranch);
        System.out.println("in getInputPromptsSeeker, list.size is: " + allLabels.size());
        return allLabels;
    }
    private List<JLabel> getInputPromptOpening(){
        allLabels = new ArrayList<>();
        JLabel promptName = new JLabel("Title of job opening: ");
        allLabels.add(promptName);
        JLabel promptExp = new JLabel("Years of experience required: ");
        allLabels.add(promptExp);
        JLabel promptBranch = new JLabel("Work area: ");
        allLabels.add(promptBranch);
        System.out.println("in getInputPromptOpening, allLabels.size is: " + allLabels.size());
        return allLabels;
    }
    public void gatherMultipleInput(Subscriber.EventType eventType) {
        System.out.println("gatherMultipleInput is reached, action is: " + eventType.getAction());
        for (int i = 0; i < allLabels.size(); i++) {
            if (allInputFields.get(i).getText() != null) {
                allUserinput[i] = allInputFields.get(i).getText();
            }
        }
        allUserinput[allLabels.size()] = String.valueOf(inputEdu.getSelectedItem().toString());
            if(checkIfComplete()){
                sendGatheredInput();
            }
        }

    private void gatherSingleInput(Subscriber.EventType eventType){
        String input = singleInputField.getText();
            System.out.println("gatherSingle in panelMaker is reached. EventType is: " + eventType + " & input is: " + input );
            if (Objects.equals(input, "")) {
                JOptionPane.showMessageDialog(null, "Type the term for what you would like to edit");
            }
            else {
                eventType.setStatus(Subscriber.EventType.Status.HAS_INPUT);
                mainFrame.update(eventType, input);
            }
    }
    private JPanel getEduRow(Subscriber.EventType eventType, Object data){
        System.out.println("getEduRow is reached, edit is: " + edit);
        JPanel eduRow = new JPanel();
        eduRow.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
        eduRow.setBackground(Colors.getHeaderColor());
        JLabel promptEdu = new JLabel("Level of education: ");
        adjustLabel(promptEdu);
        eduRow.add(promptEdu);
        this.inputEdu =
                new JComboBox<>(EducationLevel.values());
        if (eventType.getAction() == Subscriber.EventType.Action.EDIT){
            inputEdu.setSelectedItem(getEduValue(data));
            allInitialUserInput[allInputFields.size()] = getEduValue(data).toString();
        }
        inputEdu.setFont(Fonts.getButtonFont());
        inputEdu.setBackground(Color.WHITE);
        inputEdu.setBorder(BorderFactory.createLineBorder(Colors.getButtonBackgroundColor(), 3, true));
        inputEdu.setForeground(Colors.getButtonTextColor());
        inputEdu.setPreferredSize(new Dimension(200, 45) );
        for (int i = 0; i < 2; i++) {
            inputEdu.setMinimumSize(new Dimension(200, 45));
        }
        eduRow.add(inputEdu);
        return eduRow;
    }
    private EducationLevel getEduValue(Object data){
        EducationLevel eduValue = null;
        if(data instanceof JobSeeker jobSeeker){
            eduValue = jobSeeker.getEducationLevel();
        }
        else if (data instanceof JobOpening jobOpening){
            eduValue = jobOpening.getRequiredEducation();
        }
        return eduValue;
    }
//    private void sendUpdatedInfo(String [] newInfo) {
//        if (data instanceof JobOpening opening) {
//            mainFrame.update(Subscriber.EventType.RETURN_EDIT_THIS_OPENING, newInfo);
//            mainFrame.update(Subscriber.EventType.RETURN_EDIT_THIS_OPENING, opening);
//        } else if (data instanceof JobSeeker seeker) {
//            mainFrame.update(Subscriber.EventType.RETURN_EDIT_THIS_SEEKER, newInfo);
//            mainFrame.update(Subscriber.EventType.RETURN_EDIT_THIS_SEEKER, seeker);
//        }
//    }
    public JPanel getOptionsPanel(Object data){
        JPanel displayingResult = new JPanel();
        displayingResult.setLayout(new BoxLayout(displayingResult, BoxLayout.Y_AXIS));
        displayingResult.setBackground(Colors.getBorderColor());
        for (JPanel results : getResult()){
            displayingResult.add(results);
        }
        JScrollPane scrollResults = new JScrollPane(displayingResult);
        scrollResults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollResults.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(Colors.getBackgroundColor());
        wrapperPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 40, 10, 40)
        );
        wrapperPanel.add(scrollResults, BorderLayout.CENTER);
        System.out.println("wrapperPanel is returned from getOptionsPanel");
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.setOpaque(true);
        wrapperPanel.setVisible(true);
        wrapperPanel.setEnabled(true);
        return wrapperPanel;
    }
    private String getHeaderTerm(Subscriber.EventType eventType){
        System.out.println("in getHeaderTerm, terms are: " + term + " " + imperativeActionTerm + " " + actionTerm) ;
        String headerTerm = "";

         if (eventType == Subscriber.EventType.RETURN_FOUND_OPENINGS || eventType == Subscriber.EventType.RETURN_FOUND_SEEKERS){
            headerTerm = "The following " + term + pluralNoun + " contain" + pluralVerb + " your search term:";
        }
        else if (eventType == Subscriber.EventType.REQUEST_EDIT_SEEKER || eventType == Subscriber.EventType.REQUEST_EDIT_OPENING || eventType == Subscriber.EventType.RETURN_REMOVE_OPENING || eventType == Subscriber.EventType.RETURN_REMOVE_SEEKER) {
            headerTerm = "Input the " + promptTerm + " that you would like to " + imperativeActionTerm + ":";
        }
        return headerTerm;
    }
    JPanel getHeaderPanel(Subscriber.EventType eventType) {
        JPanel headerPanel = new JPanel(new GridLayout(2,1));
        headerPanel.setBackground(Colors.getBackgroundColor());
        JLabel header = new JLabel(actionTerm + " " + term );
        header.setForeground(Colors.getHeaderColor());
        header.setFont(Fonts.getHeaderFont());
        String promptText = getHeaderTerm(eventType);
        JTextArea prompt = new JTextArea(promptText);
        adjustTextArea(prompt);
        headerPanel.add(header);
        headerPanel.add(prompt);
        return headerPanel;
    }

    JPanel getSingleInputPanel(Subscriber.EventType eventType){
        System.out.println("getSingleInputPanel in PanelMaker is reached");
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        JLabel prompt = new JLabel("Search term:");
        adjustLabel(prompt);
        prompt.setFont(Fonts.getInputPromptFont());
        this.singleInputField = new JTextField();
        editInputField(singleInputField);
        inputPanel.add(prompt);
        inputPanel.add(singleInputField);
        inputPanel.setBackground(Colors.getHeaderColor());
        inputPanel.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
        this.singleInputPanel = inputPanel;
        return inputPanel;
    }
    private void editInputField(JTextField inputField) {
        inputField.setForeground(Colors.getButtonTextColor());
        inputField.setBackground(Color.WHITE);
        inputField.setPreferredSize(new Dimension(180, 45));
        inputField.setMinimumSize(new Dimension(180, 45));
        inputField.setMinimumSize(new Dimension(180, 45));
        inputField.setFont(Fonts.getInputPromptFont());
    }
    private List <JPanel> getResult(){
        System.out.println("getPrintout in EditPanel is reached");
        System.out.println("subject is:" + subject);
        List <JPanel> allSingleResultPanels = new ArrayList<>();
        String printout;
        if (subject == Subscriber.EventType.Subject.SEEKER) {
            System.out.println("jobSeekerList.size is: " + jobSeekerList.size());
            for (JobSeeker seeker: jobSeekerList) {
                printout = seeker.printoutWithoutName() + "\n";
                String name = seeker.getFullName();
                JLabel nameLabel = new JLabel("Name: ");
                nameLabel.setFont(Fonts.getInputPromptFont());
                nameLabel.setForeground(Colors.getButtonTextColor());
                JPanel namePanel = new JPanel();
                JPanel singleResultPanel = new JPanel();
                singleResultPanel.setLayout(new BoxLayout(singleResultPanel, BoxLayout.Y_AXIS));
                JButton nameButton = getNameButtonSeeker(name, seeker);
                namePanel.add(nameLabel);
                namePanel.add(nameButton);
                namePanel.setBackground(Colors.getHeaderColor());
                JTextArea infoText = new JTextArea(printout);
                adjustTextArea(infoText);
                singleResultPanel.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
                singleResultPanel.setBackground(Colors.getHeaderColor());
                singleResultPanel.setMaximumSize(new Dimension(420, Integer.MAX_VALUE));
                singleResultPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                singleResultPanel.add(Box.createVerticalStrut(5));
                singleResultPanel.add(namePanel);
                singleResultPanel.add(infoText);
                singleResultPanel.add(Box.createVerticalStrut(5));
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
    private JButton getNameButtonSeeker(String name, JobSeeker seeker) {
        System.out.println("----getNameButtonSeeker in PanelMaker is reached");
        JButton nameButton = new JButton(name);
        adjustButton(nameButton);
        nameButton.addActionListener(_ -> requestEditingPanel(eventType, seeker));
        this.editObject = seeker;
        return nameButton;
    }
    private JButton getNameButtonOpening(int i, String printout) {
        JobOpening opening = jobOpeningList.get(i);
        JButton result = new JButton(printout);
        adjustButton(result);
        result.addActionListener(_ -> requestEditingPanel(eventType, opening));
        this.editObject = opening;
        return result;
    }
    private void adjustButton(JButton button){
        button.setFont(Fonts.getButtonFont());
        button.setBackground(Colors.getButtonBackgroundColor());
        button.setForeground(Colors.getButtonTextColor());
        button.setBorder(
                BorderFactory.createLineBorder(Colors.getBorderColor(), 4, true)
        );
        button.setPreferredSize(new Dimension(200, 40));
    }
    private void adjustTextArea(JTextArea textArea){
        textArea.setEditable(false);
        textArea.setFont(Fonts.getButtonFont());
        textArea.setForeground(Colors.getHeaderColor());
        textArea.setBackground(Colors.getBackgroundColor());
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    void setTerms(Subscriber.EventType eventType){
        System.out.println("setTerms in PanelMaker is reached, eventType is: " + eventType + " & subject is: " + eventType.getSubject() );
        if (eventType.getSubject() == Subscriber.EventType.Subject.NONE){
            if (data instanceof List list && list.getFirst() instanceof JobSeeker){
                eventType.setSubject(Subscriber.EventType.Subject.SEEKER);
            }
            else if (data instanceof List list && list.getFirst() instanceof JobOpening){
                eventType.setSubject(Subscriber.EventType.Subject.OPENING);
            }
        }
        if(eventType.getSubject() == Subscriber.EventType.Subject.SEEKER) {
            this.term = "job seeker";
            this.promptTerm = "name of the job seeker";
            if (eventType == Subscriber.EventType.REQUEST_REMOVE_SEEKER) {
                this.imperativeActionTerm = "delete";
                this.actionTerm = "Deleting";
            } else if (eventType == Subscriber.EventType.REQUEST_EDIT_SEEKER) {
                this.imperativeActionTerm = "edit";
                this.actionTerm = "Editing";
            }
            else if (eventType == Subscriber.EventType.REQUEST_ADD_SEEKER){
                this.imperativeActionTerm = "add";
                this.actionTerm = "Adding";
            }
            else if (eventType == Subscriber.EventType.REQUEST_SEARCH_SEEKER){
                this.actionTerm = "Searching for";
            }
        }
        else if (eventType.getSubject() == Subscriber.EventType.Subject.OPENING){
            this.term = "job opening";
            this.promptTerm = "title of the job opening";
            if (eventType == Subscriber.EventType.REQUEST_REMOVE_OPENING) {
                this.imperativeActionTerm = "delete";
                this.actionTerm = "deleting";
            } else if (eventType == Subscriber.EventType.REQUEST_EDIT_OPENING) {
                this.imperativeActionTerm = "edit";
                this.actionTerm = "editing";
            }
            else if (eventType == Subscriber.EventType.REQUEST_ADD_OPENING) {
                this.imperativeActionTerm = "add";
                this.actionTerm = "adding";
            }
            else if (eventType == Subscriber.EventType.REQUEST_SEARCH_OPENING){
                this.actionTerm = "Searching for";
            }
        }
    }

    private void requestEditingPanel(Subscriber.EventType eventType, Object data){
        System.out.println("getEditingPanel in PanelMaker is reached");
        if (data instanceof JobSeeker){
            mainFrame.showEditPanel(Subscriber.EventType.REQUEST_EDIT_FIELDS_SEEKER, data);
        }
        else if (data instanceof JobOpening) {
            mainFrame.showEditPanel(Subscriber.EventType.REQUEST_EDIT_FIELDS_OPENING, data);
        }
    }
    private void setList(Object data){
        if (data instanceof List list){
            if (subject == Subscriber.EventType.Subject.SEEKER){
                jobSeekerList = list;
            }
            else if (subject == Subscriber.EventType.Subject.OPENING){
                jobOpeningList = list;
            }
        }
    }
    void setEditPanel(EditPanel editPanel){
        this.editPanel = editPanel;
    }
    void setRemovePanel(RemovePanel removePanel){
        this.removePanel = removePanel;
    }
}