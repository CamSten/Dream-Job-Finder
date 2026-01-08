package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SeekerPanel extends JPanel implements Subscriber{
    private MainFrame mainFrame;
    private JPanel centerPanel;
    private List<JButton> allOptionButtons = new ArrayList<>();

    public SeekerPanel(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(550, 500));
        setBackground(Colors.getBackgroundColor());
        if (centerPanel == null){
            this.centerPanel = new JPanel();
        }
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Colors.getBackgroundColor());
        add(centerPanel);

        JButton optionAddSeeker = new JButton("List all job seekers");
        allOptionButtons.add(optionAddSeeker);
        optionAddSeeker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(EventType.REQUEST_SEE_SEEKER_LIST, null);
            }
        });
        JButton optionAddOpening = new JButton("Add new job seeker");
        allOptionButtons.add(optionAddOpening);
        optionAddOpening.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(EventType.REQUEST_ADD_SEEKER, null);
            }
        });
        JButton optionSearch = new JButton("Find job seeker by name");
        allOptionButtons.add(optionSearch);
        optionSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(EventType.REQUEST_SEARCH_SEEKER, null);
            }
        });
        JButton optionMatch = new JButton("Edit job seeker");
        allOptionButtons.add(optionMatch);
        optionMatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(EventType.REQUEST_EDIT_SEEKER, null);
            }
        });
        JButton optionEdit = new JButton("Delete job seeker");
        allOptionButtons.add(optionEdit);
        optionEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(EventType.REQUEST_REMOVE_SEEKER, null);
            }
        });

        JPanel menuButtons = new JPanel();
        menuButtons.setBackground(Colors.getButtonBackgroundColor());
        menuButtons.setOpaque(true);
        menuButtons.setLayout(new BoxLayout(menuButtons, BoxLayout.Y_AXIS));
        menuButtons.setPreferredSize(new Dimension(300, allOptionButtons.size() * 60));
        menuButtons.setMinimumSize(new Dimension(300, allOptionButtons.size() * 60));
        menuButtons.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        menuButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (JButton button : allOptionButtons){
            button.setFont(Fonts.getButtonFont());
            button.setBackground(Colors.getButtonBackgroundColor());
            button.setForeground(Colors.getButtonTextColor());
            button.setPreferredSize(new Dimension(250, 45) );
            button.setMinimumSize(new Dimension(250, 45));
            button.setMinimumSize(new Dimension(250, 45));
            button.add(Box.createHorizontalStrut(250));
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
        setVisible(true);
        repaint();
        revalidate();

    }

    @Override
    public void update(EventType option, Object data) {
        mainFrame.update(option, data);
    }
}
