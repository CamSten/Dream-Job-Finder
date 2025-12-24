package GUI;

import javax.swing.*;

public class SearchPanel extends JPanel {
    private MainFrame mainFrame;

    public SearchPanel (MainFrame mainFrame){
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Colors.getBackgroundColor());
    }
}
