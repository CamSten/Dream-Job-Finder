//package GUI;
//
//import model.EducationLevel;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AdderPanel extends JPanel implements Subscriber {
//    private MainFrame mainFrame;
//    private JPanel centerpanel;
//    private String term;
//    List<JLabel> allLabels;
//    List<JTextField> allInputFields;
//    private JPanel promptPanel;
//    private JPanel inputFieldsPanel;
//    private String[] allUserinput;
//    private JComboBox inputEdu;
//    PanelMaker panelMaker;
//
//    public AdderPanel(MainFrame mainFrame, EventType eventTypeRequest, PanelMaker panelMaker) {
//        this.mainFrame = mainFrame;
//        this.eventTypeRequest = eventTypeRequest;
//        this.panelMaker = panelMaker;
//        showAdderPanel(eventTypeRequest);
//    }
//
//    public void showAdderPanel(EventType eventTypeRequest) {
//        System.out.println("in showAdderPanel, eventType is: " + eventTypeRequest);
////        panelMaker.setTerm(eventTypeRequest);
//        setLayout(new BorderLayout());
//        setBackground(Colors.getBackgroundColor());
//        setMinimumSize(new Dimension(550, 500));
//        this.centerpanel = new JPanel();
//        add(centerpanel, BorderLayout.CENTER);
//        centerpanel.setLayout(new BorderLayout());
//        centerpanel.setBackground(Colors.getBackgroundColor());
//        JPanel panels = panelMaker.getPanels(eventTypeRequest, null);
//        centerpanel.add(panels, BorderLayout.CENTER);
//        repaint();
//        JPanel headerPanel = getHeaderPanel();
//        add(headerPanel, BorderLayout.NORTH);
//        repaint();
//        revalidate();
//    }
//
//    private JPanel getHeaderPanel() {
//        JPanel headerPanel = new JPanel(new GridLayout(2,1));
//        headerPanel.setBackground(Colors.getBackgroundColor());
//        JLabel header = new JLabel("Adding new " + term);
//        header.setForeground(Colors.getHeaderColor());
//        header.setFont(Fonts.getHeaderFont());
//        JLabel prompt = new JLabel("Please complete the form: ");
//        prompt.setFont(Fonts.getButtonFont());
//        prompt.setForeground(Colors.getHeaderColor());
//        headerPanel.add(header);
//        headerPanel.add(prompt);
//        return headerPanel;
//    }
//
//    @Override
//    public void Update(Controller.Event event) {
//        mainFrame.Update(event);
//    }
//}
////private void addSeeker() {
////        System.out.println("\n--- Add New Seeker ---");
////        System.out.print("Enter Full Name: ");
////        String name = scanner.nextLine();
////
////
////
////        System.out.print("Years of Experience: ");
////        int experience = readIntInput();
////
////        System.out.print("Enter Work Area / Skills: ");
////        String workArea = scanner.nextLine();
////
////        JobSeeker newSeeker = new JobSeeker(name, eduLevel, experience, workArea);
////        matchingService.addSeeker(newSeeker);
////        System.out.println("Job Seeker added successfully!");
////    }
