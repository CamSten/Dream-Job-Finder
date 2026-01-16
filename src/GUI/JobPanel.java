package GUI;

import Controller.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class JobPanel extends JPanel implements Subscriber {
    private MainFrame mainFrame;
    private Event event;
    private PanelDecorator decorator;
    private JPanel centerPanel;
    private List<JButton> allOptionButtons = new ArrayList<>();

    public JobPanel(MainFrame mainFrame, Event event, PanelDecorator decorator) {
        this.mainFrame = mainFrame;
        this.event = event;
        this.decorator = decorator;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(550, 500));
        setBackground(Colors.getBackgroundColor());
        HeaderPanel headerPanel = new HeaderPanel(decorator, event);
        if (centerPanel == null){
            this.centerPanel = new JPanel();
        }
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Colors.getBackgroundColor());
        add(centerPanel);

        JButton optionAddSeeker = new JButton("List all job openings");
        allOptionButtons.add(optionAddSeeker);
        optionAddSeeker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update(Event.submit(Event.Action.VIEW, Event.Subject.OPENING, null, null));
            }
        });
        JButton optionAddOpening = new JButton("Add new job opening");
        allOptionButtons.add(optionAddOpening);
        optionAddOpening.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update(Event.submit(Event.Action.ADD, Event.Subject.OPENING, null, null));
            }
        });
        JButton optionSearch = new JButton("Find job opening by title");
        allOptionButtons.add(optionSearch);
        optionSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update(Event.submit(Event.Action.SEARCH, Event.Subject.OPENING, null, null));
            }
        });
        JButton optionEdit = new JButton("Edit job opening");
        allOptionButtons.add(optionEdit);
        optionEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update(Event.submit(Event.Action.EDIT, Event.Subject.OPENING, null, null));
            }
        });
        JButton optionDelete = new JButton("Delete job opening");
        allOptionButtons.add(optionDelete);
        optionDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update(Event.submit(Event.Action.REMOVE, Event.Subject.OPENING, null, null));
            }
        });

        JButton optionMatch = new JButton("Match job opening to seekers");
        allOptionButtons.add(optionMatch);
        optionMatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update(Event.submit(Event.Action.MATCH, Event.Subject.OPENING, null, null));
            }
        });

        JPanel menuButtons = new JPanel();
        menuButtons.setBackground(Colors.getButtonBackgroundColor());
        menuButtons.setOpaque(true);
        menuButtons.setLayout(new BoxLayout(menuButtons, BoxLayout.Y_AXIS));
        menuButtons.setPreferredSize(new Dimension(350, allOptionButtons.size() * 60));
        menuButtons.setMinimumSize(new Dimension(350, allOptionButtons.size() * 60));
        menuButtons.setMaximumSize(new Dimension(350, Integer.MAX_VALUE));
        menuButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (JButton button : allOptionButtons){
            button.setFont(Fonts.getButtonFont());
            button.setBackground(Colors.getButtonBackgroundColor());
            button.setForeground(Colors.getButtonTextColor());
            button.setPreferredSize(new Dimension(300, 45) );
            button.setMinimumSize(new Dimension(300, 45));
            button.setMinimumSize(new Dimension(300, 45));
            button.add(Box.createHorizontalStrut(300));
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
        add(headerPanel);
        add(Box.createHorizontalGlue());
        add(buttonPanel);
        add(Box.createHorizontalGlue());
        setVisible(true);
        repaint();
        revalidate();
    }

    @Override
    public void Update(Controller.Event event) {
        mainFrame.Update(event);
    }
}
