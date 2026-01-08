package GUI;

import javax.swing.*;

import java.awt.*;

public class EditPanel extends JPanel implements Subscriber{
    private MainFrame mainFrame;
    private Subscriber.EventType eventType;
    private JPanel centerPanel;
    private String term;
    private String promptTerm;
    private PanelMaker panelMaker;


    public EditPanel (MainFrame mainFrame, Subscriber.EventType eventType, Object data, PanelMaker panelMaker){
        this.mainFrame = mainFrame;
        this.eventType = eventType;
        this.panelMaker = panelMaker;
        showEditPanel(eventType, data);
    }
    public void showEditPanel(EventType eventType, Object data){
//        panelMaker.setTerms(eventType);
        setBackground(Colors.getBackgroundColor());
        if (centerPanel != null){
            centerPanel.removeAll();
        }
        else {
            this.centerPanel = new JPanel(new BorderLayout());
            panelMaker.setEditPanel(this);
            add(centerPanel);
        }
        JPanel panels = new JPanel();
        panels = panelMaker.getPanels(eventType, data);
        System.out.println("panels are returned from panelMaker, in editPanel");
        centerPanel.add(panels, BorderLayout.CENTER);
        centerPanel.setBackground(Colors.getBackgroundColor());
        repaint();
        revalidate();
    }
    public void addMultipleInputFields(EventType eventType, Object data){
        showEditPanel(eventType, data);
    }

    // if (eventType == Subscriber.EventType.RETURN_FOUND_THIS_SEEKER || eventType == Subscriber.EventType.RETURN_FOUND_THIS_OPENING){
    //            submitWrapper.setVisible(false);
    //        }
    //        else {
    //            submitWrapper.setVisible(true);
    //        }




    @Override
    public void update(EventType option, Object data) {
        mainFrame.update(option, data);
    }
}
