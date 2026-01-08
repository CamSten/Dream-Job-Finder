package GUI;

import javax.swing.*;
import java.awt.*;

public class RemovePanel extends JPanel implements Subscriber {
    private MainFrame mainFrame;
    private PanelMaker panelMaker;
    private JPanel centerPanel;

    public RemovePanel(MainFrame mainFrame, PanelMaker panelMaker, EventType eventType, Object data){
        this.mainFrame = mainFrame;
        this.panelMaker = panelMaker;
        showRemovePanel(eventType, data);

    }
    public void showRemovePanel(EventType eventType, Object data){
//        panelMaker.setTerms(eventType);
        setBackground(Colors.getBackgroundColor());
        if (centerPanel != null){
            centerPanel.removeAll();
        }
        else {
            this.centerPanel = new JPanel(new BorderLayout());
            panelMaker.setRemovePanel(this);
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

    @Override
    public void update(EventType option, Object data) {
        mainFrame.update(option, data);
    }
}
