package GUI;

import com.sun.tools.javac.Main;

import javax.swing.*;

public class MatchPanel extends JPanel implements Subscriber {
    private MainFrame mainFrame;

    public MatchPanel(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.getBackgroundColor());
    }

    @Override
    public void update(EventType option, Object data) {

    }
}
