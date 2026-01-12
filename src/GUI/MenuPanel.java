package GUI;

import Controller.Event;
import strategy.StrategyType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MenuPanel extends JPanel implements Subscriber{
    private MainFrame mainFrame;
    private Event event;
    private List<JButton> allOptionButtons = new ArrayList<>();
    private String term;

    public MenuPanel(MainFrame mainFrame, Event event){
        this.mainFrame = mainFrame;
        this.event = event;
        setTerms();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.getBackgroundColor());
        setMinimumSize(new Dimension(550, 500));
        JLabel prompt = new JLabel(term);
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        prompt.setFont(Fonts.getHeaderFont());
        prompt.setForeground(Colors.getHeaderColor());
        JPanel header = new JPanel(new GridLayout(2,1));
        header.add(prompt);
        header.setBackground(Colors.getBackgroundColor());
        add(header);
        setOpaque(true);

        getButtons();

        JPanel menuButtons = new JPanel();
        menuButtons.setBackground(Colors.getButtonBackgroundColor());
        menuButtons.setOpaque(true);
        menuButtons.setLayout(new BoxLayout(menuButtons, BoxLayout.Y_AXIS));
        menuButtons.setPreferredSize(new Dimension(250, allOptionButtons.size() * 60));
        menuButtons.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        menuButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (JButton button : allOptionButtons){
            button.setFont(Fonts.getButtonFont());
            button.setBackground(Colors.getButtonBackgroundColor());
            button.setForeground(Colors.getButtonTextColor());
            button.setPreferredSize(new Dimension(200, 45) );
            button.setMinimumSize(new Dimension(200, 45));
            button.setMinimumSize(new Dimension(200, 45));
            button.add(Box.createHorizontalStrut(200));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBorder(BorderFactory.createLineBorder(Colors.getBorderColor(), 5, true));
//
            menuButtons.add(Box.createVerticalStrut(5));
            menuButtons.add(button);
            menuButtons.add(Box.createVerticalStrut(5));
            menuButtons.setBorder(BorderFactory.createLineBorder(Colors.getButtonBackgroundColor(), 10, true));
        }
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Colors.getBackgroundColor());
        buttonPanel.setOpaque(true);
        buttonPanel.setPreferredSize(menuButtons.getPreferredSize());
        buttonPanel.setMaximumSize(menuButtons.getMaximumSize());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.add(menuButtons);
        add(Box.createHorizontalGlue());
        add(buttonPanel);
        add(Box.createHorizontalGlue());
        repaint();
        revalidate();
    }

    private void setTerms(){
        if (event.getPhase() == Event.Phase.AWAIT_INPUT){
            term = "Choose from the actions below:";
        }
        else if (event.getAction() == Event.Action.MATCH){
            term = "Choose which matching strategy to use:";
        }
    }

    private void getButtons(){
        if (event.getPhase() == Event.Phase.AWAIT_INPUT) {
            JButton optionHandleSeeker = new JButton("Handle job seekers");
            allOptionButtons.add(optionHandleSeeker);
            optionHandleSeeker.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Update(Event.chooseType(Event.Subject.SEEKER));
                }
            });
            JButton optionHandleOpening = new JButton("Handle job openings");
            allOptionButtons.add(optionHandleOpening);
            optionHandleOpening.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    Update(Event.chooseType(Event.Subject.OPENING));
                }
            });
        }
         else if (event.getAction() == Event.Action.MATCH) {
            Event.Phase newPhase = Event.Phase.MATCH_STRATEGY_SELECTED;

            JButton optionStrict = new JButton("Strict");
            allOptionButtons.add(optionStrict);
            optionStrict.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Update(Event.submit(Event.Action.MATCH, event.getSubject(), event.getContents(), StrategyType.STRICT));
                }
            });
            JButton optionFlex= new JButton("Flexible");
            allOptionButtons.add(optionFlex);
            optionFlex.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Update(Event.submit(Event.Action.MATCH, event.getSubject(), event.getContents(), StrategyType.FLEXIBLE));
                }
            });
            JButton optionEdu= new JButton("Education focus");
            allOptionButtons.add(optionEdu);
            optionEdu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Update(Event.submit(Event.Action.MATCH, event.getSubject(), event.getContents(), StrategyType.EDUCATION_FOCUSED));
                }
            });
            }
        }
    @Override
    public void Update(Controller.Event event ) {
        if (event.getAction() == Event.Action.MATCH){
            event.setPhase(Event.Phase.MATCH_STRATEGY_SELECTED);
        }
        mainFrame.Update(event);
    }
}
