package GUI;

import Controller.Event;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SingleInputPanel extends JPanel implements Subscriber {
    private MainFrame mainFrame;
    private PanelDecorator decorator;
    private Event event;
    private Event.Action action;
    private Event.Subject subject;
    private JTextField singleInputField;

    public SingleInputPanel(MainFrame mainFrame, Event event, PanelDecorator decorator) {
        this.mainFrame = mainFrame;
        this.event = event;
        this.action = event.getAction();
        this.subject = event.getSubject();
        this.decorator = decorator;
        setBackground(Colors.getBackgroundColor());
        JPanel headerPanel = new HeaderPanel(decorator, event);
        JPanel inputPanel = getSingleInputPanel(event);
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout());
        wrapperPanel.setBackground(Colors.getBackgroundColor());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);
        wrapperPanel.add(inputPanel, BorderLayout.CENTER);
        add(wrapperPanel);
    }

    public JPanel getSingleInputPanel (Event event) {
        JPanel singleInputPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        singleInputPanel.setLayout(new BoxLayout(singleInputPanel, BoxLayout.Y_AXIS));
        JLabel prompt = new JLabel("Search term:");
        decorator.adjustLabel(prompt);
        prompt.setFont(Fonts.getInputPromptFont());
        this.singleInputField = new JTextField();
        decorator.editInputField(singleInputField);
        inputPanel.add(prompt);
        inputPanel.add(singleInputField);
        singleInputPanel.add(inputPanel);
        singleInputPanel.add(getSubmitButton());
        inputPanel.setBackground(Colors.getHeaderColor());
        inputPanel.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
        singleInputPanel.setBackground(Colors.getHeaderColor());
        singleInputPanel.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
        return singleInputPanel;
    }

    public JButton getSubmitButton() {
        JButton submitButton = new JButton("Submit");
        decorator.adjustSubmitButton(submitButton);
        submitButton.addActionListener(_ -> {
                gatherInput();

        });
        return submitButton;
    }
    private void gatherInput(){
        String input = singleInputField.getText();
        if (Objects.equals(input, "")) {
            JOptionPane.showMessageDialog(null, "Type the term for what you would like to edit");
        }
        else {
            Update(Event.submit(action, subject, input, null));
        }
    }

    @Override
    public void Update(Event event) {
        if (action == Event.Action.MATCH){
            event.setPhase(Event.Phase.MATCH_ENTER_TERM);
        }
        mainFrame.Update(event);
    }
}
