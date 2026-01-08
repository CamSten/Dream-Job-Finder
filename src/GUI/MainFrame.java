package GUI;

import Controller.ApplicationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame implements Subscriber {
    private List<Subscriber> subscribers = new ArrayList<>();
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private MenuPanel menuPanel;
    private JobPanel jobPanel;
    private SeekerPanel seekerPanel;
    private SearchPanel searchPanel;
    private AdderPanel adderPanel;
    private MatchPanel matchPanel;
    private ResultPanel resultPanel;
    private EditPanel editPanel;
    private RemovePanel removePanel;
    public static Controller.ApplicationManager applicationManager;
    private PanelMaker panelMaker;

    public MainFrame(ApplicationManager applicationManager){
        this.panelMaker = new PanelMaker(this);
        System.out.println("MainFrame constructor was reached");
        this.applicationManager = applicationManager;
        setTitle("DreamJobFinder");
        setVisible(true);
        setLayout(new BorderLayout());
        setEnabled(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(575, 525));
        setBackground(Colors.getBackgroundColor());

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Colors.getBackgroundColor());
        centerPanel.setMinimumSize(new Dimension(550, 500));
        centerPanel.setOpaque(false);
        add(centerPanel, BorderLayout.CENTER);
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Colors.getBackgroundColor());
        bottomPanel.setOpaque(false);
        add(bottomPanel, BorderLayout.SOUTH);
        showMenuPanel();
    }

    public void showMenuPanel(){
        removeCenterPanelContent();
        if (menuPanel == null){
            this.menuPanel = new MenuPanel(this);
        }
        if (bottomPanel != null){
            bottomPanel.removeAll();
        }
        centerPanel.add(menuPanel, BorderLayout.CENTER);
        adjustCenterPanel(menuPanel);
    }
    public void showSearchPanel(EventType eventType){
        removeCenterPanelContent();
        if (searchPanel == null){
            this.searchPanel = new SearchPanel(this, eventType, panelMaker);
        }
        else {
            searchPanel.showSearchPanel(eventType);
        }
        centerPanel.add(searchPanel, BorderLayout.CENTER);
        adjustCenterPanel(searchPanel);
        adjustBottomPanel();
    }
    public void showAdderPanel(EventType eventType){
        removeCenterPanelContent();
        if (adderPanel == null){
            this.adderPanel = new AdderPanel(this, eventType, panelMaker);
        }
        else {
            adderPanel.showAdderPanel(eventType);
        }
        applicationManager.addSubscriber(adderPanel);
        centerPanel.add(adderPanel, BorderLayout.CENTER);
        adjustCenterPanel(adderPanel);
        adjustBottomPanel();
    }
    public void showMatchPanel(){
        removeCenterPanelContent();
        if (matchPanel == null){
            this.matchPanel = new MatchPanel(this);
        }
        applicationManager.addSubscriber(matchPanel);
        centerPanel.add(matchPanel, BorderLayout.CENTER);
        adjustCenterPanel(menuPanel);
        adjustBottomPanel();
    }
    public void showResultPanel(EventType eventType, Object data){
        System.out.println("SHOW RESULT PANEL IS REACHED, eventType is: " + eventType);
        removeCenterPanelContent();
            resultPanel = new ResultPanel(this, eventType, data);
            centerPanel.add(resultPanel, BorderLayout.CENTER);
            applicationManager.addSubscriber(resultPanel);
        adjustCenterPanel(resultPanel);
        adjustBottomPanel();
    }
    public void showJobPanel(){
        removeCenterPanelContent();
        if (jobPanel == null){
            this.jobPanel = new JobPanel(this);
        }
        centerPanel.add(jobPanel, BorderLayout.CENTER);
        applicationManager.addSubscriber(jobPanel);
        adjustCenterPanel(jobPanel);
        adjustBottomPanel();
    }
    public void showSeekerPanel(){
        removeCenterPanelContent();
        if (seekerPanel == null){
            this.seekerPanel = new SeekerPanel(this);
        }
        centerPanel.add(seekerPanel, BorderLayout.CENTER);
        applicationManager.addSubscriber(seekerPanel);
        adjustCenterPanel(seekerPanel);
        adjustBottomPanel();
    }

    public void showEditPanel(EventType eventType, Object data){
        System.out.println("showEditPanel in MainFrame is reached");
        removeCenterPanelContent();
        if (editPanel == null){
            this.editPanel = new EditPanel(this, eventType, data, panelMaker);
            applicationManager.addSubscriber(editPanel);
        }
        else {
            editPanel.showEditPanel(eventType, data);
        }
        centerPanel.add(editPanel, BorderLayout.CENTER);
        adjustCenterPanel(editPanel);
        adjustBottomPanel();
    }
    public void showRemovePanel(EventType eventType, Object data){
        System.out.println("showRemovePanel in MainFrame is reached");
        removeCenterPanelContent();
        if (removePanel == null){
            this.editPanel = new EditPanel(this, eventType, data, panelMaker);
            applicationManager.addSubscriber(editPanel);
        }
        else {
            editPanel.showEditPanel(eventType, data);
        }
        centerPanel.add(editPanel, BorderLayout.CENTER);
        adjustCenterPanel(editPanel);
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
            this.bottomPanel = new JPanel();
        }
        bottomPanel.removeAll();
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
        bottomPanel.add(backToMenu);
        bottomPanel.setBackground(Colors.getBackgroundColor());
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setOpaque(true);
        repaint();
        revalidate();
        pack();
    }
    public void update(EventType option, Object data) {
        System.out.println("--------- update in MainFrame is: " + option);
        applicationManager.update(option, data);
    }
}
