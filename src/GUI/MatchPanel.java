//package GUI;
//
//import com.sun.tools.javac.Main;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class MatchPanel extends JPanel implements Subscriber {
//    private MainFrame mainFrame;
//    private PanelMaker panelMaker;
//    private JPanel centerPanel;
//
//    public MatchPanel(MainFrame mainFrame, PanelMaker panelMaker, Subscriber.EventType eventType, Object data){
//        this.mainFrame = mainFrame;
//        this.panelMaker = panelMaker;
//    }
//    public void showMatchPanel(Subscriber.EventType eventType, Object data){
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
//        System.out.println("panels are returned from panelMaker, in matchPanel");
//        centerPanel.add(panels, BorderLayout.CENTER);
//        centerPanel.setBackground(Colors.getBackgroundColor());
//        repaint();
//        revalidate();
//    }
//
//    @Override
//    public void update(EventType option, Object data) {
//
//    }
//}
