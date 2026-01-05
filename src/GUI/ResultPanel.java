package GUI;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel implements Subscriber {
    private MainFrame mainFrame;
    private JPanel centerPanel;

    public ResultPanel(MainFrame mainFrame, EventType eventType){
        this.mainFrame = mainFrame;
        System.out.println("resultPanel constructor was reached");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(550, 500));
        setBackground(Colors.getBackgroundColor());
        if (centerPanel == null){
            this.centerPanel = new JPanel();
        }
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Colors.getBackgroundColor());
        add(centerPanel);
        setVisible(true);
        repaint();
        revalidate();
        displayAddConfirmation(eventType);
    }

    private void displayAddConfirmation(EventType eventType){
        JLabel confirmation = new JLabel("New submission has been successfully added.");

        confirmation.setBackground(Colors.getBackgroundColor());
        confirmation.setForeground(Colors.getHeaderColor());
        confirmation.setFont(Fonts.getHeaderFont());
        if (centerPanel != null){
            centerPanel.removeAll();
            centerPanel.add(confirmation, BorderLayout.CENTER);
        }
        revalidate();
        repaint();
    }

    @Override
    public void update(EventType eventType, Object data) {
        switch (eventType){
            case RETURN_ADD_OPENING, RETURN_ADD_SEEKER ->{
                displayAddConfirmation(eventType);
            }
        }
    }
}
