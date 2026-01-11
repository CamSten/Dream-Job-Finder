//package GUI;
//import javax.swing.*;
//import java.awt.*;
//
//
//public class PanelMaker extends JPanel {
//    private MainFrame mainFrame;
//    public PanelDecorator decorator;
//
//    public PanelMaker(MainFrame mainFrame, PanelDecorator decorator) {
//        this.mainFrame = mainFrame;
//        this.decorator = decorator;
//    }
//
//    public JPanel getPanel(JPanel inputPanel, JPanel header){
//        JPanel panels = new JPanel();
//        panels.setLayout(new BoxLayout(panels, BoxLayout.Y_AXIS));
//        panels.add(header);
//        panels.add(inputPanel);
//        return panels;
//    }
//}
//    public JPanel getPanels(JPanel inputPanel, JPanel headerPanel,) {
//        this.subject = setSubject(eventTypeRequest, data);
//        System.out.println("in getPanels, subject is: " + subject);
//        this.action = eventTypeRequest.getAction();
//        setTerms(eventTypeRequest, data);
//        System.out.println("getPanels in PanelMaker is reached, eventType is: " + eventTypeRequest + " and eventTypeRequest.getInputType() is: " + eventTypeRequest.getInputType() );
//        this.eventTypeRequest = eventTypeRequest;
//        JPanel headerPanel = getHeaderPanel(eventTypeRequest);
//        JPanel inputPanel = getInputPanel(eventTypeRequest, data);
//        JPanel panels = new JPanel();
//        panels.setLayout(new BoxLayout(panels, BoxLayout.Y_AXIS));
//        panels.add(headerPanel);
//        panels.add(inputPanel);
//        if (shouldShowSubmitButton(eventTypeRequest)) {
//            this.buttonPanel = getSubmitButtonPanel(eventTypeRequest);
//            panels.add(buttonPanel);
//        }
//        System.out.println("----panels is returned from getPanels");
//        return panels;
//    }
//    public JPanel getInputPanel(Subscriber.EventType eventTypeRequest, Object data){
//        System.out.println("getInputPanel in PanelMaker is reached. EventType is: " + eventTypeRequest);
//        JPanel inputPanel = new JPanel();
//
//        if (eventTypeRequest == Subscriber.EventType.RETURN_OPTIONS){
//            setList(data);
//            inputPanel = getOptionsPanel();
//        }
//        else if (eventTypeRequest.getInputType() == Subscriber.EventType.InputType.MULTIPLE){
//            inputPanel = getMultipleInputPanel(eventTypeRequest, data);
//        }
//      else if (eventTypeRequest == Subscriber.EventType.RETURN_FOUND_OPENINGS) {
//            inputPanel = getMatchInputPanel();
//        }
//        else if(eventTypeRequest.getInputType() == Subscriber.EventType.InputType.SINGLE){
//            inputPanel = getSingleInputPanel();
//        }
//        return inputPanel;
//    }
//
//    public boolean checkIfComplete() {
//        System.out.println("checkIfCompleted in AdderPanel is reached, allUserInput.Length is: " + allUserinput.length);
//        boolean allIsCollected = true;
//        for (int i = 0; i < allLabels.size(); i++) {
//            System.out.println("allUserInput " + i + " is: " + allUserinput[i]);
//            if (allUserinput[i] == null || allUserinput[i].isEmpty()) {
//                allIsCollected = false;
//
//            }
//        }
//        if (allIsCollected) {
//            if (eventTypeRequest.getAction() == Subscriber.EventType.Action.EDIT) {
//                if (checkIfEdited()) {
//                    return true;
//                } else {
//                    System.out.println("checkIfEdited is false");
//                    JOptionPane.showMessageDialog(null, "No edit has been done");
//                }
//            }
//            else {
//                return true;
//            }
//        }
//        else {
//            JOptionPane.showMessageDialog(null, "All fields must be completed before submitting.");
//        }
//       return false;
//    }
//    public boolean checkIfEdited (){
//        boolean edited = false;
//        for (int i = 0; i < allUserinput.length; i++){
//            System.out.println("allUserInput is: " + allUserinput[i] + " and allInitialUserInput is: " + allInitialUserInput[i]);
//            if (!allUserinput[i].equalsIgnoreCase(allInitialUserInput[i])){
//                edited = true;
//            }
//        }
//        return edited;
//    }
//    public void clearAllInputFields(){
//        for (JTextField t : allInputFields){
//            t.setText("");
//        }
//        inputEdu.setSelectedItem(EducationLevel.NONE);
//    }
//    public void sendGatheredInput() {
//        Subscriber.EventType eventTypeReturn = null;
//        if (eventTypeRequest.getAction() == Subscriber.EventType.Action.EDIT && subject == Subscriber.EventType.Subject.OPENING) {
//            eventTypeReturn = Subscriber.EventType.RETURN_EDIT_THIS_OPENING;
//        }
//        else if (eventTypeRequest.getAction() == Subscriber.EventType.Action.EDIT && subject == Subscriber.EventType.Subject.SEEKER){
//            eventTypeReturn = Subscriber.EventType.RETURN_EDIT_THIS_SEEKER;
//        }
//        else if (eventTypeRequest.getAction() == Subscriber.EventType.Action.ADD && subject == Subscriber.EventType.Subject.OPENING){
//            eventTypeReturn = Subscriber.EventType.RETURN_ADD_OPENING;
//        }
//        else if (eventTypeRequest.getAction() == Subscriber.EventType.Action.ADD && subject == Subscriber.EventType.Subject.SEEKER){
//            eventTypeReturn = Subscriber.EventType.RETURN_ADD_SEEKER;
//        }
//            mainFrame.update(eventTypeReturn, allUserinput);
//        mainFrame.update(eventTypeReturn,editObject);
//        clearAllInputFields();
//    }
//    public JPanel getMultipleInputPanel(Subscriber.EventType eventType, Object data) {
//        System.out.println("_ _ _ _ getMultipleInputPanel is reached");
//        List<JTextField> allInputFields = getFieldList(data);
//        allLabels = new ArrayList<>();
//        if(eventType.getSubject() == Subscriber.EventType.Subject.SEEKER){
//            allLabels = getInputPromptsSeeker();
//        }
//        else if(eventType.getSubject() == Subscriber.EventType.Subject.OPENING){
//            allLabels = getInputPromptOpening();
//        }
//        this.promptPanel = new JPanel();
//        this.inputFieldsPanel = new JPanel();
//        decorator.adjustPanel(allLabels.size(), promptPanel);
//        decorator.adjustPanel(allLabels.size(), inputFieldsPanel);
//        for (JLabel l : allLabels) {
//            decorator.adjustLabel(l);
//        }
//        for (JTextField t : allInputFields) {
//           decorator.editInputField(t);
//        }
//        JPanel inputPanel = new JPanel();
//        decorator.adjustInputPanel(inputPanel);
//        for (int i = 0; i < allInputFields.size(); i++) {
//            inputPanel.add(getAdjustedInputPanel(allLabels.get(i), allInputFields.get(i)));
//        }
//        inputPanel.add(getEduRow(eventType, data));
//        JPanel wrapperPanel = new JPanel();
//        wrapperPanel.setBackground(Colors.getHeaderColor());
//        wrapperPanel.setBorder(BorderFactory.createLineBorder(Colors.getHeaderColor(), 5, true));
//        wrapperPanel.add(inputPanel);
//        allUserinput = new String[allLabels.size() + 1];
//        this.multipleInputPanel = wrapperPanel;
//        return multipleInputPanel;
//    }
//    public JPanel getAdjustedInputPanel (JLabel label, Object inputArea){
//        JPanel inputPanel = new JPanel();
//            JPanel queryPanel = new JPanel();
//            queryPanel.setLayout(new GridLayout(1, 2));
//            queryPanel.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
//            queryPanel.setBackground(Colors.getHeaderColor());
//            queryPanel.add(label);
//            if(inputArea instanceof JTextField) {
//                JTextField textField = (JTextField) inputArea;
//                queryPanel.add(textField);
//            }
//            else if (inputArea instanceof JButton){
//                JButton button = (JButton) inputArea;
//                queryPanel.add(button);
//            }
//            else if (inputArea instanceof JComboBox){
//                JComboBox comboBox = (JComboBox) inputArea;
//                queryPanel.add(comboBox);
//            }
//            inputPanel.add(Box.createVerticalStrut(5));
//            inputPanel.add(queryPanel);
//            inputPanel.add(Box.createVerticalStrut(5));
//        return inputPanel;
//    }
//public JPanel getMatchInputPanel(){
//        System.out.println("getMatchInputPanel is reached");
//      JPanel matchInputPanel = new JPanel();
//      List<JButton> buttons = new ArrayList<>();
//      decorator.adjustInputPanel(matchInputPanel);
//      JTextArea header = new JTextArea("Choose type of matching:");
//      decorator.adjustHeader(header);
//      JButton strict = new JButton("Strict match");
//      buttons.add(strict);
//      strict.addActionListener(_ -> returnChoice(Subscriber.EventType.InputType.STRICT));
//      JButton flexible = new JButton("Flexible match");
//      buttons.add(flexible);
//      flexible.addActionListener(_ -> returnChoice(Subscriber.EventType.InputType.FLEX));
//      JButton eduFocus = new JButton("Prioritize education");
//      eduFocus.addActionListener(_ -> returnChoice(Subscriber.EventType.InputType.EDU));
//      buttons.add(eduFocus);
//      for (JButton button : buttons){
//          decorator.adjustButton(button);
//          matchInputPanel.add(button);
//      }
//      return matchInputPanel;
//}
//    public java.util.List<JTextField> getFieldList(Object data) {
//        this.allInputFields = new ArrayList<>();
//        JTextField inputName = new JTextField();
//        allInputFields.add(inputName);
//        JTextField inputYearsExp = new JTextField();
//        allInputFields.add(inputYearsExp);
//        JTextField inputBranch = new JTextField();
//        allInputFields.add(inputBranch);
//        if (data instanceof JobSeeker || data instanceof JobOpening){
//            setInputText(data);
//        }
//        return allInputFields;
//    }
//    public void setInputText(Object data){
//        this.allInitialUserInput = new String[allInputFields.size()+1];
//        System.out.println("setInputText in PanelMaker is reached");
//        if (data instanceof JobOpening opening){
//            List<String> openingInfo = new ArrayList<>();
//            openingInfo.add(opening.getTitle());
//            openingInfo.add(String.valueOf(opening.getMinYearsExperience()));
//            openingInfo.add(opening.getWorkArea());
//            for (int i = 0; i < allInputFields.size(); i++){
//                JTextField inputField = allInputFields.get(i);
//                inputField.setText(openingInfo.get(i));
//            }
//        }
//        else if(data instanceof JobSeeker seeker){
//            List<String> seekerInfo = new ArrayList<>();
//            seekerInfo.add(seeker.getFullName());
//            seekerInfo.add(String.valueOf(seeker.getYearsExperience()));
//            seekerInfo.add(seeker.getWorkArea());
//            for (int i = 0; i < allInputFields.size(); i++){
//                JTextField inputField = allInputFields.get(i);
//                inputField.setText(seekerInfo.get(i));
//                allInitialUserInput[i] = seekerInfo.get(i);
//            }
//        }
//    }
//    public List<JLabel> getInputPromptsSeeker(){
//        System.out.println("getInputPromptsSeeker is reached");
//        allLabels = new ArrayList<>();
//        JLabel promptName = new JLabel("Name: ");
//        allLabels.add(promptName);
//        JLabel promptExp = new JLabel("Years of experience: ");
//        allLabels.add(promptExp);
//        JLabel promptBranch = new JLabel("Work area: ");
//        allLabels.add(promptBranch);
//        System.out.println("in getInputPromptsSeeker, list.size is: " + allLabels.size());
//        return allLabels;
//    }
//    public List<JLabel> getInputPromptOpening(){
//        allLabels = new ArrayList<>();
//        JLabel promptName = new JLabel("Title of job opening: ");
//        allLabels.add(promptName);
//        JLabel promptExp = new JLabel("Years of experience required: ");
//        allLabels.add(promptExp);
//        JLabel promptBranch = new JLabel("Work area: ");
//        allLabels.add(promptBranch);
//        System.out.println("in getInputPromptOpening, allLabels.size is: " + allLabels.size());
//        return allLabels;
//    }
//    public void gatherMultipleInput(Subscriber.EventType eventType) {
//        System.out.println("gatherMultipleInput is reached, action is: " + eventType.getAction());
//        for (int i = 0; i < allLabels.size(); i++) {
//            if (allInputFields.get(i).getText() != null) {
//                allUserinput[i] = allInputFields.get(i).getText();
//            }
//        }
//        allUserinput[allLabels.size()] = String.valueOf(inputEdu.getSelectedItem().toString());
//            if(checkIfComplete()){
//                sendGatheredInput();
//            }
//        }
//
//    public void gatherSingleInput(Subscriber.EventType eventType){
//        String input = singleInputField.getText();
//            System.out.println("gatherSingle in panelMaker is reached. EventType is: " + eventType + " & input is: " + input );
//            if (Objects.equals(input, "")) {
//                JOptionPane.showMessageDialog(null, "Type the term for what you would like to edit");
//            }
//            else {
//                eventType.setStatus(Subscriber.EventType.Status.HAS_INPUT);
//                eventType.setSubject(subject);
//                mainFrame.update(eventType, input);
//            }
//    }
//    public JPanel getEduRow(Subscriber.EventType eventType, Object data){
//        System.out.println("getEduRow is reached, edit is: " + edit);
//        JPanel eduRow = new JPanel();
//        eduRow.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
//        eduRow.setBackground(Colors.getHeaderColor());
//        JLabel promptEdu = new JLabel("Level of education: ");
//        decorator.adjustLabel(promptEdu);
//        eduRow.add(promptEdu);
//        this.inputEdu =
//                new JComboBox<>(EducationLevel.values());
//        if (eventType.getAction() == Subscriber.EventType.Action.EDIT){
//            inputEdu.setSelectedItem(getEduValue(data));
//            allInitialUserInput[allInputFields.size()] = getEduValue(data).toString();
//        }
//        inputEdu.setFont(Fonts.getButtonFont());
//        inputEdu.setBackground(Color.WHITE);
//        inputEdu.setBorder(BorderFactory.createLineBorder(Colors.getButtonBackgroundColor(), 3, true));
//        inputEdu.setForeground(Colors.getButtonTextColor());
//        inputEdu.setPreferredSize(new Dimension(200, 45) );
//        for (int i = 0; i < 2; i++) {
//            inputEdu.setMinimumSize(new Dimension(200, 45));
//        }
//        return getAdjustedInputPanel(promptEdu, inputEdu);
//    }
//    public EducationLevel getEduValue(Object data){
//        EducationLevel eduValue = null;
//        if(data instanceof JobSeeker jobSeeker){
//            eduValue = jobSeeker.getEducationLevel();
//        }
//        else if (data instanceof JobOpening jobOpening){
//            eduValue = jobOpening.getRequiredEducation();
//        }
//        return eduValue;
//    }
//    public JPanel getOptionsPanel(){
//        System.out.println("_____getOptionsPanel in PanelMaker is reached");
//        JPanel localHeader = new JPanel();
//        decorator.adjustWrapperPanel(localHeader);
//        JTextArea headerPrompt = new JTextArea("Choose from the options below:");
//        decorator.adjustHeader(headerPrompt);
//        headerPrompt.setEditable(false);
//        localHeader.add(headerPrompt);
//        JPanel displayingResult = new JPanel();
//        displayingResult.setLayout(new BoxLayout(displayingResult, BoxLayout.Y_AXIS));
//        displayingResult.setBackground(Colors.getBorderColor());
//        displayingResult.add(localHeader);
//        for (JPanel results : getResult()){
//            displayingResult.add(results);
//        }
//        JScrollPane scrollResults = new JScrollPane(displayingResult);
//        scrollResults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scrollResults.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        JPanel wrapperPanel = new JPanel();
//        wrapperPanel.setBackground(Colors.getBackgroundColor());
//        wrapperPanel.setBorder(
//                BorderFactory.createEmptyBorder(10, 40, 10, 40)
//        );
//        wrapperPanel.add(scrollResults);
//        decorator.adjustWrapperPanel(wrapperPanel);
//        return wrapperPanel;
//    }
//    public String getHeaderTerm(Subscriber.EventType eventType){
//        System.out.println("in getHeaderTerm, terms are: " + term + " " + imperativeActionTerm + " " + actionTerm) ;
//        String headerTerm = "";
//         if (eventType == Subscriber.EventType.RETURN_FOUND_OPENINGS || eventType == Subscriber.EventType.RETURN_FOUND_SEEKERS){
//            headerTerm = "The following " + term + pluralNoun + " contain" + pluralVerb + " your search term:";
//        }
//        else if (eventType.getInputType() == Subscriber.EventType.InputType.SINGLE) {
//            headerTerm = "Input the " + promptTerm + " that you would like to " + imperativeActionTerm + ":";
//        }
//        return headerTerm;
//    }
//    JPanel getHeaderPanel(Subscriber.EventType eventType) {
//        JPanel headerPanel = new JPanel(new GridLayout(2,1));
//        headerPanel.setBackground(Colors.getBackgroundColor());
//        JLabel header = new JLabel(actionTerm + " " + term );
//        header.setForeground(Colors.getHeaderColor());
//        header.setFont(Fonts.getHeaderFont());
//        String promptText = getHeaderTerm(eventType);
//        JTextArea prompt = new JTextArea(promptText);
//        decorator.adjustHeader(prompt);
//        headerPanel.add(header);
//        headerPanel.add(prompt);
//        return headerPanel;
//    }
//
//    public JPanel getSingleInputPanel(){
//        System.out.println("getSingleInputPanel in PanelMaker is reached");
//        JPanel inputPanel = new JPanel();
//        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
//        JPanel wrapperPanel = new JPanel();
//        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
//        JLabel prompt = new JLabel("Search term:");
//        decorator.adjustLabel(prompt);
//        prompt.setFont(Fonts.getInputPromptFont());
//        this.singleInputField = new JTextField();
//        decorator.editInputField(singleInputField);
//        inputPanel.add(prompt);
//        inputPanel.add(singleInputField);
//        inputPanel.setBackground(Colors.getHeaderColor());
//        inputPanel.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
//        return inputPanel;
//    }
//
//    public List <JPanel> getResult(){
//        System.out.println("getPrintout in EditPanel is reached");
//        System.out.println("subject is:" + subject);
//        List <JPanel> allSingleResultPanels = new ArrayList<>();
//        String printout;
//        if (subject == Subscriber.EventType.Subject.SEEKER) {
//            System.out.println("jobSeekerList.size is: " + jobSeekerList.size());
//            for (JobSeeker seeker: jobSeekerList) {
//                JPanel singleResultPanel = new JPanel();
//                decorator.adjustSingleResultPanel(singleResultPanel);
//
//                printout = seeker.printoutWithoutName() + "\n";
//                String name = seeker.getFullName();
//                JLabel nameLabel = new JLabel("Name: ");
//                decorator.adjustLabel(nameLabel);
//                JButton nameButton = getNameButtonSeeker(name, seeker);
//                JPanel namePanel = getAdjustedInputPanel(nameLabel, nameButton);
//                decorator.adjustSingleResultLine(namePanel);
//                String[] part = printout.split("-");
//                System.out.println("part.length is: "+ part.length);
//
//                singleResultPanel.add(namePanel);
//                singleResultPanel.add(Box.createVerticalStrut(5));
//
//                for (int i = 0; i < part.length; i++) {
//                    JPanel singleResultLine = new JPanel();
//                    decorator.adjustSingleResultLine(singleResultLine);
//                    String[] text = part[i].split(";");
//                    System.out.println("text.length is:" + text.length);
//                    JLabel infoTextType = new JLabel (text[0]);
//                    JTextArea infoTextInput = new JTextArea(text[1]);
//                    decorator.adjustLabel(infoTextType);
//                    decorator.adjustTextArea(infoTextInput);
//                    singleResultLine.add(infoTextType);
//                    singleResultLine.add(infoTextInput);
//                    singleResultPanel.add(singleResultLine);
//                    singleResultPanel.add(Box.createVerticalStrut(5));
//                }
//
//                allSingleResultPanels.add(singleResultPanel);
//
//            }
//        }
//        else {
//            for (int i = 0; i < jobOpeningList.size(); i++){
//                printout = jobOpeningList.get(i).printout() + "\n\n";
//                JPanel singleResultPanel = new JPanel();
//                JButton result = getNameButtonOpening(i, printout);
//                singleResultPanel.add(Box.createVerticalStrut(5));
//                singleResultPanel.add(result);
//                singleResultPanel.add(Box.createVerticalStrut(5));
//                allSingleResultPanels.add(singleResultPanel);
//            }
//        }
//        return allSingleResultPanels;
//    }
//    public JButton getNameButtonSeeker(String name, JobSeeker seeker) {
//        System.out.println("----getNameButtonSeeker in PanelMaker is reached, name is: " + name);
//        JButton nameButton = new JButton(name);
//        decorator.adjustNameButton(nameButton);
//        nameButton.addActionListener(_ -> returnChoice(seeker));
//        this.editObject = seeker;
//        return nameButton;
//    }
//    public JButton getNameButtonOpening(int i, String printout) {
//        JobOpening opening = jobOpeningList.get(i);
//        JButton result = new JButton(printout);
//        decorator.adjustButton(result);
//        result.addActionListener(_ -> returnChoice(opening));
//        this.editObject = opening;
//        return result;
//    }
//    public void returnChoice(Object data){
//        Subscriber.EventType returnEventType = Subscriber.EventType.RETURN_CHOICE;
//        System.out.println("returnChoice in PanelMaker is reached");
//        if (data instanceof Subscriber.EventType.InputType inputType){
//            returnEventType.setInputType(inputType);
//        }
//        returnEventType.setSubject(subject);
//        returnEventType.setAction(action);
//        mainFrame.update(returnEventType, data);
//    }
//    public void setList(Object data){
//        if (data instanceof List list){
//            if (subject == Subscriber.EventType.Subject.SEEKER){
//                jobSeekerList = list;
//            }
//            else if (subject == Subscriber.EventType.Subject.OPENING){
//                jobOpeningList = list;
//            }
//        }
//    }
//
//    public Subscriber.EventType.Subject setSubject(Subscriber.EventType eventType, Object data) {
//        Subscriber.EventType.Subject thisSubject = null;
//        if (eventType.getSubject() != null) {
//            thisSubject = eventType.getSubject();
//        } else if (data instanceof List list && list.getFirst() instanceof JobSeeker jobSeeker) {
//            thisSubject = Subscriber.EventType.Subject.SEEKER;
//        } else if (data instanceof List list && list.getFirst() instanceof JobOpening jobOpening) {
//            thisSubject = Subscriber.EventType.Subject.OPENING;
//        }
//        return thisSubject;
//    }
//}