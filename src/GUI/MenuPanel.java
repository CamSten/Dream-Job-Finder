package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MenuPanel extends JPanel implements Subscriber{
    private MainFrame mainFrame;
    private List<JButton> allOptionButtons = new ArrayList<>();

    public MenuPanel(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.getBackgroundColor());
//        JLabel greeting = new JLabel("Welcome!");
//        greeting.setFont(Fonts.getHeaderFont());
//        greeting.setForeground(Colors.getHeaderColor());
//        greeting.setHorizontalAlignment(SwingConstants.CENTER);
        setMinimumSize(new Dimension(550, 500));
        JLabel prompt = new JLabel("Choose from the actions below:");
        prompt.setHorizontalAlignment(SwingConstants.CENTER);
        prompt.setFont(Fonts.getHeaderFont());
        prompt.setForeground(Colors.getHeaderColor());
        JPanel header = new JPanel(new GridLayout(2,1));
//        header.add(greeting);
        header.add(prompt);
        header.setBackground(Colors.getBackgroundColor());
        add(header);
        setOpaque(true);

        JButton optionHandleSeeker = new JButton("Handle job seekers");
        allOptionButtons.add(optionHandleSeeker);
        optionHandleSeeker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(EventType.REQUEST_HANDLE_SEEKER, null);
            }
        });
        JButton optionHandleOpening = new JButton("Handle job openings");
        allOptionButtons.add(optionHandleOpening);
        optionHandleOpening.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(EventType.REQUEST_HANDLE_OPENING, null);
            }
        });
//        JButton optionSearch = new JButton("Search database");
//        allOptionButtons.add(optionSearch);
//        optionSearch.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                update(EventType.REQUEST_SEARCH, null);
//            }
//        });
        JButton optionMatch = new JButton("Match");
        allOptionButtons.add(optionMatch);
        optionMatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(EventType.REQUEST_MATCH, null);
            }
        });
//        JButton optionEdit = new JButton("Edit");
//        allOptionButtons.add(optionEdit);
//        optionEdit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                update(EventType.REQUEST_EDIT, null);
//            }
//        });
//        JButton optionRemove = new JButton("Remove");
//        allOptionButtons.add(optionRemove);
//        optionRemove.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                update(EventType.REQUEST_REMOVE, null);
//            }
//        });
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
    @Override
    public void update(EventType option, Object data )  {
        mainFrame.update(option, data);
    }
}
