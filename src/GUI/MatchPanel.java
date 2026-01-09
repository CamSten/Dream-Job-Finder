package GUI;

import com.sun.tools.javac.Main;

import javax.swing.*;

public class MatchPanel extends JPanel implements Subscriber {
    private MainFrame mainFrame;
    private PanelMaker panelMaker;

    public MatchPanel(MainFrame mainFrame, PanelMaker panelMaker){
        this.mainFrame = mainFrame;
        this.panelMaker = panelMaker;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.getBackgroundColor());
        showMatchPanel();
    }
    public void showMatchPanel(){

    }

    @Override
    public void update(EventType option, Object data) {

    }
}
