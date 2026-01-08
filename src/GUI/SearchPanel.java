package GUI;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel implements Subscriber{
    private MainFrame mainFrame;
    private Subscriber.EventType eventType;
    private JPanel centerPanel;
    private String term;
    private String promptTerm;
    private PanelMaker panelMaker;

    public SearchPanel (MainFrame mainFrame, Subscriber.EventType eventType, PanelMaker panelMaker){
        this.mainFrame = mainFrame;
        this.eventType = eventType;
        this.panelMaker = panelMaker;
        showSearchPanel(eventType);
    }
    public void showSearchPanel(EventType eventType){
        setTerms(eventType);
        setBackground(Colors.getBackgroundColor());
        if (centerPanel != null){
            remove(centerPanel);
        }
        this.centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Colors.getBackgroundColor());
        add(centerPanel);
        JPanel headerPanel = getHeaderPanel();
        centerPanel.add(headerPanel, BorderLayout.NORTH);
        JPanel inputPanel = panelMaker.getSingleInputPanel(eventType);
        centerPanel.add(inputPanel, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    private JPanel getHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(2,1));
        headerPanel.setBackground(Colors.getBackgroundColor());
        JLabel header = new JLabel("Searching for " + term );
        header.setForeground(Colors.getHeaderColor());
        header.setFont(Fonts.getHeaderFont());
        JTextArea prompt = new JTextArea("Input the " + promptTerm + " that you would like to search for:");
        prompt.setEditable(false);
//        prompt.setPreferredSize(new Dimension(270, 45) );
//        prompt.setMinimumSize(new Dimension(270, 45));
//        prompt.setMinimumSize(new Dimension(270, 45));
        prompt.setFont(Fonts.getButtonFont());
        prompt.setForeground(Colors.getHeaderColor());
        prompt.setBackground(Colors.getBackgroundColor());
        headerPanel.add(header);
        headerPanel.add(prompt);
        return headerPanel;
    }
    private void search(String seachTerm){
        if (seachTerm == ""){
            JOptionPane.showMessageDialog(null, "Type the term that you would like to search for.");
        }
        else if (eventType == EventType.REQUEST_SEARCH_OPENING){
            update(EventType.RETURN_SEARCH_OPENING, seachTerm);
        }
        else if (eventType == EventType.REQUEST_SEARCH_SEEKER){
            update(EventType.RETURN_SEARCH_SEEKER, seachTerm);
        }
    }

    private void setTerms(EventType eventType){
        if (eventType == EventType.REQUEST_SEARCH_SEEKER){
            this.term = "job seeker";
            this.promptTerm = "name of the job seeker";
        }
        else if(eventType == EventType.REQUEST_SEARCH_OPENING) {
            this.term = "job opening";
            this.promptTerm = "title of the job opening";
        }
    }
    @Override
    public void update(EventType option, Object data) {
        mainFrame.update(option, data);
    }
}
