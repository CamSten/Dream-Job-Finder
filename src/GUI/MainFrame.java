package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements Subscriber {
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private MenuPanel menuPanel;
    private SearchPanel searchPanel;
    private AdderPanel adderPanel;
    private MatchPanel matchPanel;
    private ResultPanel resultPanel;
    public enum AdderObject {SEEKER, OPENING}

    public MainFrame(){
        setTitle("DreamJobFinder");
        setVisible(true);
        setLayout(new BorderLayout());
        setEnabled(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        setBackground(Colors.getBackgroundColor());

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Colors.getBackgroundColor());
        centerPanel.setOpaque(false);
        add(centerPanel, BorderLayout.CENTER);
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Colors.getBackgroundColor());
        bottomPanel.setOpaque(false);
        add(bottomPanel, BorderLayout.SOUTH);
        showMenuPanel();
        menuPanel.addSubscriber(this);
    }

    public void showMenuPanel(){
        removeCenterPanelContent();
        if (menuPanel == null){
            this.menuPanel = new MenuPanel();
        }
        if (bottomPanel != null){
            bottomPanel.removeAll();
        }
        centerPanel.add(menuPanel, BorderLayout.CENTER);
        adjustCenterPanel(menuPanel);
    }
    public void showSearchPanel(){
        removeCenterPanelContent();
        if (searchPanel == null){
            this.searchPanel = new SearchPanel();
        }
        centerPanel.add(searchPanel, BorderLayout.CENTER);
        adjustCenterPanel(searchPanel);
        adjustBottomPanel();
    }
    public void showAdderPanel(AdderObject adderObject){
        removeCenterPanelContent();
        if (adderPanel == null){
            this.adderPanel = new AdderPanel(adderObject);
        }
        centerPanel.add(adderPanel, BorderLayout.CENTER);
        adjustCenterPanel(adderPanel);
        adjustBottomPanel();
    }
    public void showMatchPanel(){
        removeCenterPanelContent();
        if (matchPanel == null){
            this.matchPanel = new MatchPanel();
        }
        centerPanel.add(matchPanel, BorderLayout.CENTER);
        adjustCenterPanel(menuPanel);
        adjustBottomPanel();
    }
    public void showResultPanel(){
        removeCenterPanelContent();
        if (resultPanel == null){
            this.resultPanel = new ResultPanel();
        }
        centerPanel.add(resultPanel, BorderLayout.CENTER);
        adjustCenterPanel(resultPanel);
        adjustBottomPanel();
    }
    private void removeCenterPanelContent(){
        if (centerPanel != null) {
            centerPanel.removeAll();
        }
    }
    private void adjustCenterPanel(JPanel panel){
        panel.setVisible(true);
        panel.setEnabled(true);
        panel.setFocusable(true);
        repaint();
        revalidate();
        pack();
    }
    private void adjustBottomPanel(){
        if (bottomPanel == null){
            this.bottomPanel = new ResultPanel();
        }
        JButton backToMenu = new JButton("Return to main menu");
        backToMenu.setBackground(Colors.getButtonBackgroundColor());
        backToMenu.setForeground(Colors.getButtonTextColor());
        backToMenu.setFont(Fonts.getButtonFont());
        backToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMenuPanel();
            }
        });
        repaint();
        revalidate();
        pack();
    }

    @Override
    public void update(MenuOption option) {
        switch (option){
            case ADD_SEEKER -> {
                showAdderPanel(AdderObject.SEEKER);
            }
            case ADD_OPENING -> {
                showAdderPanel(AdderObject.OPENING);
            }
            case SEARCH -> {
                showSearchPanel();
            }
            case MATCH -> {
                showMatchPanel();
            }
            case EDIT -> {

            }
            case REMOVE -> {

            }
        }
    }
}
