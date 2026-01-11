//package GUI;
//
//import javax.swing.*;
//
//import java.awt.*;
//
//public class EditPanel extends JPanel implements Subscriber{
//    private MainFrame mainFrame;
//    private Subscriber.EventType eventType;
//    private JPanel centerPanel;
//    private PanelMaker panelMaker;
//
//    public EditPanel (MainFrame mainFrame, Subscriber.EventType eventType, Object data, PanelMaker panelMaker){
//        this.mainFrame = mainFrame;
//        this.eventType = eventType;
//        this.panelMaker = panelMaker;
//    }
//    public void showEditPanel(EventType eventType, Object data){
//        System.out.println("showEditPanel is reached");
//        setBackground(Colors.getBackgroundColor());
//        if (centerPanel != null){
//            centerPanel.removeAll();
//        }
//        else {
//            this.centerPanel = new JPanel(new BorderLayout());
//            add(centerPanel);
//        }
//        JPanel panels = new JPanel();
//        panels = panelMaker.getPanels(eventType, data);
//        System.out.println("panels are returned from panelMaker, in editPanel");
//        centerPanel.add(panels, BorderLayout.CENTER);
//        centerPanel.setBackground(Colors.getBackgroundColor());
//        repaint();
//        revalidate();
//    }
//
//    @Override
//    public void update(EventType option, Object data) {
//        mainFrame.update(option, data);
//    }
//}
