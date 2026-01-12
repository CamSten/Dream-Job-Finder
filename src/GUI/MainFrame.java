package GUI;

import Controller.ApplicationManager;
import Controller.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame implements Subscriber {
    public static Controller.ApplicationManager applicationManager;
    private PanelDecorator decorator;
    private List<Subscriber> subscribers = new ArrayList<>();
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private MenuPanel menuPanel;

    public MainFrame(ApplicationManager applicationManager) {
        this.decorator = new PanelDecorator();
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
        showStartMenuPanel(Event.awaitInput(Event.Action.VIEW, Event.Subject.NONE, Event.Origin.GUI));
    }

    public void showStartMenuPanel(Event event) {
        removeCenterPanelContent();
        if (bottomPanel != null) {
            bottomPanel.removeAll();
        }
        MenuPanel menuPanel = new MenuPanel(this, event);
        centerPanel.add(menuPanel);
        adjustCenterPanel(menuPanel);
    }
    private void removeCenterPanelContent() {
        if (centerPanel != null) {
            centerPanel.removeAll();
        }
    }

    private void adjustCenterPanel(JPanel panel) {
        panel.setVisible(true);
        panel.setEnabled(true);
        panel.setFocusable(true);
        repaint();
        revalidate();
    }

    private void adjustBottomPanel() {
        if (bottomPanel == null) {
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
                showStartMenuPanel(Event.awaitInput(Event.Action.VIEW, Event.Subject.NONE, Event.Origin.GUI));
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

    public void Update(Controller.Event event) {
        Event.Action action = event.getAction();
        switch (action) {
            case CHOOSE_TYPE -> {
                showTypeMenu(event);
            }
            case ADD -> {
                add(event);
            }
            case EDIT -> {
                edit(event);
            }
            case VIEW -> {
                view(event);
            }
            case MATCH -> {
                match(event);
            }
            case REMOVE -> {
                remove(event);
            }
            case SEARCH -> {
                search(event);
            }
        }
    }
    private void setPanel(JPanel panel){
        centerPanel.removeAll();
        centerPanel.add(panel, BorderLayout.CENTER);
        adjustCenterPanel(panel);
        adjustBottomPanel();
    }
    private void showTypeMenu(Event event){
        if (event.getSubject() == Event.Subject.SEEKER){
            setPanel(new SeekerPanel(this, event, decorator));
        }
        else if (event.getSubject() == Event.Subject.OPENING){
            setPanel(new JobPanel(this, event, decorator));
        }
    }

    private void add(Event event) {
        Event.Phase phase = event.getPhase();
        switch (event.getOrigin()){
            case GUI -> {
                if (phase == Event.Phase.SUBMIT) {
                    setPanel(new MultipleInputPanel(this, event, decorator));
                }
                else if (phase == Event.Phase.HANDLING) {
                    applicationManager.Update(event);
                }
            }
            case LOGIC -> {
                setPanel(new ResultPanel(this, event, decorator, false));
            }
        }
    }

    private void remove(Event event) {
        Event.Origin origin = event.getOrigin();
        Event.Phase phase = event.getPhase();
        switch (origin) {
            case GUI -> {
                if (phase == Event.Phase.SUBMIT) {
                    if (event.getContents() == null) {
                        setPanel(new SingleInputPanel(this, event, decorator));
                    } else if (event.getContents() != null) {
                        applicationManager.Update(event);
                    }
                }
                else if (phase == Event.Phase.SELECT){
                    applicationManager.Update(event);
                }
            }
            case LOGIC -> {
                if (phase == Event.Phase.DISPLAY && event.getContents() instanceof ArrayList list) {
                    setPanel(new OptionPanel(this, decorator, event));
                } else {
                    setPanel(new ResultPanel(this, event, decorator, true));
                }
            }
        }
    }

    private void view(Event event) {
        Event.Origin origin = event.getOrigin();
        switch (origin) {
            case GUI -> {
                applicationManager.Update(event);
            }
            case LOGIC -> {
                setPanel(new ResultPanel(this, event, decorator, true));
            }
        }
    }

    private void edit(Event event) {
        Event.Origin origin = event.getOrigin();
        if (event.getContents() != null) {
        }
        switch (origin) {
            case GUI -> {
                if (event.getPhase() == Event.Phase.SUBMIT && event.getContents() == null) {
                    setPanel(new SingleInputPanel(this, event, decorator));
                }
                else if (event.getPhase() == Event.Phase.SELECT) {
                    setPanel(new MultipleInputPanel(this, event, decorator));
                }
                else {
                    applicationManager.Update(event);
                }
            }
            case LOGIC -> {
                if(event.getContents() instanceof List){
                    setPanel(new OptionPanel(this, decorator, event));
                }
                else if (event.getPhase() == Event.Phase.COMPLETE){
                    setPanel(new ResultPanel(this, event, decorator, false));
                }
            }
        }
    }
    private void search(Event event){
        switch (event.getOrigin()){
            case GUI -> {
                if (event.getContents() == null){
                    setPanel(new SingleInputPanel(this, event, decorator));
                }
                else {
                    applicationManager.Update(event);                }
            }
            case LOGIC -> {
                setPanel(new ResultPanel(this, event, decorator, true));
            }
        }
    }

    private void match(Event event) {
        Event.Phase phase = event.getPhase();
        Event.Origin origin = event.getOrigin();
        switch (origin) {
            case GUI -> {
                if (event.getPhase() == Event.Phase.SUBMIT) {
                    setPanel(new SingleInputPanel(this, event, decorator));
                }
                else if (event.getPhase() == Event.Phase.SELECT){
                    setPanel(new MenuPanel(this, event));
                }
                else {
                    applicationManager.Update(event);
                }
            }
            case LOGIC -> {
                if (event.getPhase() == Event.Phase.MATCH_TERM_SUBMITTED) {
                    setPanel(new OptionPanel(this, decorator, event));
                } else {
                    setPanel(new ResultPanel(this, event, decorator, true));
                }
            }
        }
    }
}
