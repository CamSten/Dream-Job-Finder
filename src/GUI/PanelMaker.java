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
    private Subscriber.EventType eventTypeReturn;
    private Subscriber.EventType eventType;
    private String[] allUserinput;
    private String[] allEditedUserInput;
    private JComboBox inputEdu;
    private MainFrame mainFrame;
    private Object data;
    private boolean edit = false;
    String pluralNoun = "";
    String pluralVerb = "s";
    private List<JobOpening> jobOpeningList = new ArrayList<>();
    private List<JobSeeker> jobSeekerList = new ArrayList<>();
    boolean multiple = false;
    boolean editSeekers = false;
    private JPanel centerPanel;
    private JPanel multipleInputPanel;
    private JPanel singleInputPanel;
    JTextField singleInputField;
    private JPanel buttonPanel;
    private EditPanel editPanel;
    private RemovePanel removePanel;

    public PanelMaker(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    public JPanel getPanels(Subscriber.EventType eventTypeRequest, Object data) {
        this.data = data;
        setTerms(eventTypeRequest);
        System.out.println("getPanels in PanelMaker is reached, eventType is: " + eventTypeRequest);
        this.eventTypeRequest = eventTypeRequest;
        JPanel headerPanel = getHeaderPanel(eventTypeRequest);
        JPanel inputPanel =  new JPanel();
        if (eventTypeRequest == Subscriber.EventType.REQUEST_ADD_OPENING || eventTypeRequest == Subscriber.EventType.REQUEST_ADD_SEEKER){
            inputPanel = getMultipleInputPanel(eventTypeRequest, data);
        }
        else if(eventTypeRequest == Subscriber.EventType.REQUEST_SEARCH_SEEKER || eventTypeRequest == Subscriber.EventType.REQUEST_SEARCH_OPENING || eventTypeRequest == Subscriber.EventType.REQUEST_EDIT_OPENING || eventTypeRequest == Subscriber.EventType.REQUEST_EDIT_SEEKER || eventTypeRequest == Subscriber.EventType.REQUEST_REMOVE_SEEKER || eventTypeRequest == Subscriber.EventType.REQUEST_REMOVE_OPENING){
            inputPanel = getSingleInputPanel(eventTypeRequest);
        }
        else if (eventTypeRequest == Subscriber.EventType.RETURN_FOUND_OPENINGS || eventTypeRequest == Subscriber.EventType.RETURN_FOUND_SEEKERS){
            inputPanel = getOptionsPanel(eventTypeRequest, data);
        }
        else if(eventTypeRequest == Subscriber.EventType.REQUEST_EDIT_FIELDS_OPENING || eventTypeRequest == Subscriber.EventType.REQUEST_EDIT_FIELDS_SEEKER) {
            inputPanel = getMultipleInputPanel(eventTypeRequest, data);
        }
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
        System.out.print("getSubmitButton in PanelMaker was reached");
        JButton submitButton = new JButton("Submit");
        adjustSubmitButton(submitButton);
        submitButton.addActionListener(_ -> {
            if (eventType == Subscriber.EventType.RETURN_EDITING_SEEKER || eventType == Subscriber.EventType.RETURN_EDITING_OPENING || eventType == Subscriber.EventType.REQUEST_EDIT_FIELDS_OPENING || eventType == Subscriber.EventType.REQUEST_EDIT_FIELDS_SEEKER) {
                String[] newInput = gatherEditedInput();
                if (newInput != null){
                    sendUpdatedInfo(newInput);
                }
            }
            else if (eventType == Subscriber.EventType.REQUEST_EDIT_SEEKER || eventType == Subscriber.EventType.REQUEST_EDIT_OPENING || eventType == Subscriber.EventType.REQUEST_SEARCH_SEEKER || eventType == Subscriber.EventType.REQUEST_SEARCH_OPENING || eventType == Subscriber.EventType.REQUEST_REMOVE_SEEKER || eventType == Subscriber.EventType.REQUEST_REMOVE_OPENING){
                gatherSingleInput(eventType);
            }
            else {
                gatherMultipleInput();
                checkIfComplete();
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
    private void checkIfComplete(){
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
            mainFrame.update(eventTypeReturn, allUserinput);
            clearAllInputFields();
        }
        else {
            JOptionPane.showMessageDialog(null, "All fields must be completed before submitting.");
        }
    }
    public void clearAllInputFields(){
        for (JTextField t : allInputFields){
            t.setText("");
        }
    }
    private JPanel getMultipleInputPanel(Subscriber.EventType eventType, Object data) {
        boolean isEditing = getIsEditing(eventType);
        System.out.println("in addInputFields, eventType is: " + eventType);
        List<JTextField> allInputFields = getInputFields(data);
        allLabels = new ArrayList<>();
        if (eventType == Subscriber.EventType.REQUEST_ADD_SEEKER || eventType == Subscriber.EventType.REQUEST_EDIT_SEEKER || eventType == Subscriber.EventType.RETURN_FOUND_SEEKERS || eventType == Subscriber.EventType.REQUEST_EDIT_FIELDS_SEEKER) {
            allLabels = getInputPromptsSeeker();
        } else if (eventType == Subscriber.EventType.REQUEST_ADD_OPENING || eventType == Subscriber.EventType.REQUEST_EDIT_OPENING || eventType == Subscriber.EventType.RETURN_FOUND_OPENINGS || eventType == Subscriber.EventType.REQUEST_EDIT_FIELDS_OPENING) {
            allLabels = getInputPromptOpening();
        }
        System.out.println("in addInputFields, allLabels.size is: " + allLabels.size());
        this.promptPanel = new JPanel();
        this.inputFieldsPanel = new JPanel();
        adjustPanel(allLabels.size(), promptPanel);
        adjustPanel(allLabels.size(), inputFieldsPanel);
        for (JLabel l : allLabels) {
            l.setForeground(Colors.getButtonTextColor());
            l.setPreferredSize(new Dimension(270, 45));
            l.setMinimumSize(new Dimension(270, 45));
            l.setMinimumSize(new Dimension(270, 45));
            l.setFont(Fonts.getInputPromptFont());
        }
        for (JTextField t : allInputFields) {
            editInputField(t);
        }
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
        inputPanel.add(getEduRow(isEditing, data));
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(Colors.getHeaderColor());
        wrapperPanel.setBorder(BorderFactory.createLineBorder(Colors.getHeaderColor(), 5, true));
        wrapperPanel.add(inputPanel);

        allUserinput = new String[allLabels.size() + 1];
        this.multipleInputPanel = wrapperPanel;
        return multipleInputPanel;
    }
    private void adjustPanel(int size, JPanel panel) {
        panel.setVisible(true);
        panel.setLayout(new BoxLayout(promptPanel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(250, size * 60));
        panel.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
    }
    private java.util.List<JTextField> getInputFields(Object data) {
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
        JLabel promptBranch = new JLabel("Professional field: ");
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
        JLabel promptBranch = new JLabel("Professional field: ");
        allLabels.add(promptBranch);
        System.out.println("in getInputPromptOpening, allLabels.size is: " + allLabels.size());
        return allLabels;
    }
    private boolean getIsEditing(Subscriber.EventType eventType){
        return eventType == Subscriber.EventType.REQUEST_EDIT_FIELDS_SEEKER || eventType == Subscriber.EventType.REQUEST_EDIT_FIELDS_OPENING;
    }
//    public void setTerm(Subscriber.EventType eventType){
//        if (eventType == Subscriber.EventType.REQUEST_ADD_SEEKER || eventType == Subscriber.EventType.REQUEST_EDIT_SEEKER || eventType == Subscriber.EventType.REQUEST_REMOVE_SEEKER) {
//            this.term = "job seeker";
//            this.eventTypeReturn = Subscriber.EventType.RETURN_ADD_SEEKER;
//        }
//        else if (eventType == Subscriber.EventType.REQUEST_ADD_OPENING || eventType == Subscriber.EventType.REQUEST_EDIT_OPENING || eventType == Subscriber.EventType.REQUEST_REMOVE_OPENING) {
//            this.term = "job opening";
//            this.eventTypeReturn = Subscriber.EventType.RETURN_ADD_OPENING;
//        }
//    }
    public void gatherMultipleInput(){
        for (int i = 0; i < allLabels.size(); i++) {
            if (allInputFields.get(i).getText() != null) {
                allUserinput[i] = allInputFields.get(i).getText();
                System.out.println("in gatherInput, input is: " + allUserinput[i]);
            }
        }
        allUserinput[allLabels.size()] = String.valueOf(inputEdu.getSelectedItem().toString());
        System.out.println("in gatherInput, input is: " + allUserinput[allLabels.size()]);
        System.out.println("allUserInput.length is: " + allUserinput.length);
    }
    private void gatherSingleInput(Subscriber.EventType eventType){
        String input = singleInputField.getText();
        if (eventType == Subscriber.EventType.REQUEST_EDIT_SEEKER || eventType == Subscriber.EventType.REQUEST_EDIT_OPENING || eventType == Subscriber.EventType.REQUEST_REMOVE_SEEKER || eventType == Subscriber.EventType.REQUEST_REMOVE_OPENING){
            handleSingleInput(input, eventType);
        }
    }
    public String[] gatherEditedInput(){
        String[] previousUserInput = allUserinput;
        boolean edited = false;
        gatherMultipleInput();
        for (String s : previousUserInput) {
            if (allUserinput[0] != null && !(s.equalsIgnoreCase(allUserinput[0]))) {
                edited = true;
            }
        }
        if (edited){
            return previousUserInput;
        }
        else{
            return null;
        }
    }
    private JPanel getEduRow(boolean edit, Object data){
        System.out.println("getEduRow is reached, edit is: " + edit);
        JPanel eduRow = new JPanel();
        eduRow.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
        eduRow.setBackground(Colors.getHeaderColor());
        JLabel promptEdu = new JLabel("Level of education: ");
        promptEdu.setForeground(Colors.getButtonTextColor());
        promptEdu.setPreferredSize(new Dimension(270, 45) );
        promptEdu.setMinimumSize(new Dimension(270, 45));
        promptEdu.setMinimumSize(new Dimension(270, 45));
        promptEdu.setFont(Fonts.getInputPromptFont());
        eduRow.add(promptEdu);
        this.inputEdu =
                new JComboBox<>(EducationLevel.values());
        if (edit){
            inputEdu.setSelectedItem(getEduValue(data));
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
        System.out.println("eduvalue is: " + eduValue);
        return eduValue;
    }
    private void sendUpdatedInfo(String [] newInfo) {
        if (data instanceof JobOpening opening) {
            mainFrame.update(Subscriber.EventType.RETURN_EDIT_THIS_OPENING, newInfo);
            mainFrame.update(Subscriber.EventType.RETURN_EDIT_THIS_OPENING, opening);
        } else if (data instanceof JobSeeker seeker) {
            mainFrame.update(Subscriber.EventType.RETURN_EDIT_THIS_SEEKER, newInfo);
            mainFrame.update(Subscriber.EventType.RETURN_EDIT_THIS_SEEKER, seeker);
        }
    }
    public JPanel getOptionsPanel(Subscriber.EventType eventType, Object data){
        System.out.println("getOptionsPanel in PanelMaker is reached. EventType is: " + eventType);
        this.eventType = eventType;
        this.eventTypeRequest = eventType;
        unpackData(data);
        JPanel displayingResult = new JPanel();
        displayingResult.setLayout(new BoxLayout(displayingResult, BoxLayout.Y_AXIS));
        displayingResult.setBackground(Colors.getBorderColor());
        for (JPanel results : getResult()){
            displayingResult.add(results);
        }
        JScrollPane scrollResults = new JScrollPane(displayingResult);
        scrollResults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollResults.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        JTextArea headerText = new JTextArea(getHeaderTerm(eventType));
//        headerText.setEditable(false);
//        headerText.setBackground(Colors.getBackgroundColor());
//        headerText.setFont(Fonts.getHeaderFont());
//        headerText.setForeground(Colors.getHeaderColor());
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(Colors.getBackgroundColor());
        wrapperPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 40, 10, 40)
        );
//        wrapperPanel.add(headerText, BorderLayout.NORTH);
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
    private void unpackData(Object data){
        if (data instanceof List list && list.getFirst() instanceof JobSeeker){
            jobSeekerList = (List<JobSeeker>) data;
            editSeekers = true;
            if (jobSeekerList.size() > 1){
                multiple = true;
                pluralNoun = "s";
                pluralVerb = "";
            }
        }
        else if (data instanceof List list && list.getFirst() instanceof JobOpening){
            jobOpeningList = (List<JobOpening>) data;
            editSeekers = false;
            if (jobOpeningList.size() > 1){
                multiple = true;
                pluralNoun ="s";
                pluralVerb = "";
            }
        }
    }
    JPanel getHeaderPanel(Subscriber.EventType eventType) {
        JPanel headerPanel = new JPanel(new GridLayout(2,1));
        headerPanel.setBackground(Colors.getBackgroundColor());
        JLabel header = new JLabel(actionTerm + " " + term );
        header.setForeground(Colors.getHeaderColor());
        header.setFont(Fonts.getHeaderFont());
        String promptText = getHeaderTerm(eventType);
        JTextArea prompt = new JTextArea(promptText);
        prompt.setEditable(false);
        prompt.setFont(Fonts.getButtonFont());
        prompt.setForeground(Colors.getHeaderColor());
        prompt.setBackground(Colors.getBackgroundColor());
        headerPanel.add(header);
        headerPanel.add(prompt);
        return headerPanel;
    }
    private void setActionTerm(Subscriber.EventType eventType){
        switch (eventType){
            case REQUEST_ADD_OPENING, REQUEST_ADD_SEEKER -> {
                this.actionTerm = "Adding ";
            }
            case REQUEST_EDIT_OPENING, REQUEST_EDIT_SEEKER -> {
                this.actionTerm = "Editing";
            }
            case REQUEST_REMOVE_OPENING, REQUEST_REMOVE_SEEKER -> {
                this.actionTerm = "Deleting";
            }
        }
    }

    JPanel getSingleInputPanel(Subscriber.EventType eventType){
        System.out.println("getSingleInputPanel in PanelMaker is reached");
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        JLabel prompt = new JLabel("Search term:");
        prompt.setFont(Fonts.getInputPromptFont());
        prompt.setForeground(Colors.getButtonTextColor());
        prompt.setPreferredSize(new Dimension(270, 45) );
        prompt.setMinimumSize(new Dimension(270, 45));
        prompt.setMinimumSize(new Dimension(270, 45));
        prompt.setFont(Fonts.getInputPromptFont());
        this.singleInputField = new JTextField();
        editInputField(singleInputField);
        inputPanel.add(prompt);
        inputPanel.add(singleInputField);
        inputPanel.setBackground(Colors.getHeaderColor());
        inputPanel.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
//        wrapperPanel.add(inputPanel);
//        wrapperPanel.add(Box.createVerticalStrut(5));
//        wrapperPanel.add(submitButton);
//        wrapperPanel.setBackground(Colors.getHeaderColor());
//        wrapperPanel.setBorder(
//                BorderFactory.createEmptyBorder(10, 40, 10, 40)
//        );
        this.singleInputPanel = inputPanel;
        return inputPanel;
    }
    //    private JButton getEditSubmitButton() {
//        System.out.println();
//        JButton submitButton = new JButton(" Submit ");
//        submitButton.setFont(Fonts.getButtonFont());
//        submitButton.setBackground(Colors.getButtonBackgroundColor());
//        submitButton.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
//        submitButton.setForeground(Colors.getButtonTextColor());
//        submitButton.setPreferredSize(new Dimension(200, 45) );
//        submitButton.setMinimumSize(new Dimension(200, 45));
//        submitButton.setMinimumSize(new Dimension(200, 45));
//        return submitButton;
//    }
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
        List <JPanel> allSingleResultPanels = new ArrayList<>();
        String printout;
        if (editSeekers) {
            for (JobSeeker seeker: jobSeekerList) {
                printout = seeker.printoutWithoutName() + "\n";
                String name = seeker.getFullName();
                JLabel nameLabel = new JLabel("Name: ");
                nameLabel.setFont(Fonts.getInputPromptFont());
                nameLabel.setForeground(Colors.getButtonTextColor());
                JPanel namePanel = new JPanel();
                JPanel singleResultPanel = new JPanel();
                singleResultPanel.setLayout(new BoxLayout(singleResultPanel, BoxLayout.Y_AXIS));
                JButton nameButton = getNameButton(name, seeker);
                namePanel.add(nameLabel);
                namePanel.add(nameButton);
                namePanel.setBackground(Colors.getHeaderColor());
                JTextArea infoText = new JTextArea(printout);
                infoText.setEditable(false);
                infoText.setFont(Fonts.getButtonFont());
                infoText.setForeground(Color.WHITE);
                infoText.setBackground(Colors.getHeaderColor());
                infoText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
                JButton result = getNameButton(i, printout);
                singleResultPanel.add(Box.createVerticalStrut(5));
                singleResultPanel.add(result);
                singleResultPanel.add(Box.createVerticalStrut(5));
                allSingleResultPanels.add(singleResultPanel);
            }
        }
        return allSingleResultPanels;
    }
    private JButton getNameButton(String name, JobSeeker seeker) {
        JButton nameButton = new JButton(name);
        nameButton.setFont(Fonts.getButtonFont());
        nameButton.setBackground(Colors.getButtonBackgroundColor());
        nameButton.setForeground(Colors.getButtonTextColor());
        nameButton.setBorder(
                BorderFactory.createLineBorder(Colors.getBorderColor(), 4, true)
        );
        nameButton.setPreferredSize(new Dimension(200, 40));
        nameButton.addActionListener(_ -> requestEditingPanel(eventType, seeker));
        return nameButton;
    }
    private JButton getNameButton(int i, String printout) {
        JobOpening opening = jobOpeningList.get(i);
        JButton result = new JButton(printout);
        result.addActionListener(_ -> requestEditingPanel(eventType, opening));
        result.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
        result.setBackground(Colors.getHeaderColor());
        return result;
    }
    void setTerms(Subscriber.EventType eventType){
        System.out.println("setTerms in PanelMaker is reached, eventType is: " + eventType);
        if (eventType == Subscriber.EventType.REQUEST_SEARCH_SEEKER || eventType == Subscriber.EventType.REQUEST_ADD_SEEKER || eventType == Subscriber.EventType.REQUEST_EDIT_SEEKER || eventType == Subscriber.EventType.RETURN_FOUND_SEEKERS || eventType == Subscriber.EventType.REQUEST_REMOVE_SEEKER || eventType == Subscriber.EventType.RETURN_REMOVE_SEEKER) {
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
        else if(eventType == Subscriber.EventType.REQUEST_SEARCH_OPENING || eventType == Subscriber.EventType.REQUEST_ADD_OPENING || eventType == Subscriber.EventType.REQUEST_EDIT_OPENING || eventType == Subscriber.EventType.RETURN_FOUND_OPENINGS || eventType == Subscriber.EventType.REQUEST_REMOVE_OPENING || eventType == Subscriber.EventType.RETURN_REMOVE_OPENING) {
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

    private void handleSingleInput(String searchTerm, Subscriber.EventType eventType){
        System.out.println("___ edit in EditPanel was reached, eventType is: " + eventType);
        if (Objects.equals(searchTerm, "")) {
            JOptionPane.showMessageDialog(null, "Type the term for what you would like to edit");
        }
        else if (eventType == Subscriber.EventType.REQUEST_EDIT_OPENING){
            mainFrame.update(Subscriber.EventType.RETURN_EDITING_OPENING, searchTerm);
        }
        else if (eventType == Subscriber.EventType.REQUEST_EDIT_SEEKER){
            mainFrame.update(Subscriber.EventType.RETURN_EDITING_SEEKER, searchTerm);
        }
        else if (eventType == Subscriber.EventType.REQUEST_REMOVE_SEEKER) {
            mainFrame.update(Subscriber.EventType.RETURN_REMOVE_SEEKER, searchTerm);
        }
        else if (eventType == Subscriber.EventType.REQUEST_REMOVE_OPENING) {
            mainFrame.update(Subscriber.EventType.RETURN_REMOVE_OPENING, searchTerm);
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
    void setEditPanel(EditPanel editPanel){
        this.editPanel = editPanel;
    }
    void setRemovePanel(RemovePanel removePanel){
        this.removePanel = removePanel;
    }
}