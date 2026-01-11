//package GUI;
//
//import Controller.Event;
//import com.sun.tools.javac.Main;
//import strategy.StrategyType;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.List;
//
//public class StrategyPanel extends JPanel implements Subscriber{
//    private MainFrame mainFrame;
//    private Event event;
//    private PanelDecorator decorator;
//
//    public StrategyPanel(MainFrame mainFrame, Event event, PanelDecorator decorator) {
//        this.mainFrame = mainFrame;
//        this.event = event;
//        this.decorator = decorator;
//        setBackground(Colors.getBackgroundColor());
//        JPanel headerPanel = new HeaderPanel(decorator, event);
//        JPanel inputPanel = getStrategyPanel();
//        JPanel wrapperPanel = new JPanel();
//        wrapperPanel.setLayout(new BorderLayout());
//        wrapperPanel.setBackground(Colors.getBackgroundColor());
//        wrapperPanel.setOpaque(false);
//        wrapperPanel.add(headerPanel, BorderLayout.NORTH);
//        wrapperPanel.add(inputPanel, BorderLayout.CENTER);
//        add(wrapperPanel);
//    }
//    private JPanel getStrategyPanel(){
//        List<JButton> allOptionButtons = new ArrayList<>();
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        setBackground(Colors.getBackgroundColor());
//        setMinimumSize(new Dimension(550, 500));
//        JLabel prompt = new JLabel("Choose which matching strategy to use:");
//        prompt.setHorizontalAlignment(SwingConstants.CENTER);
//        prompt.setFont(Fonts.getHeaderFont());
//        prompt.setForeground(Colors.getHeaderColor());
//        JPanel header = new JPanel(new GridLayout(2,1));
//        header.add(prompt);
//        header.setBackground(Colors.getBackgroundColor());
//        add(header);
//        setOpaque(true);
//
//        JButton optionStrict = new JButton("Strict");
//        allOptionButtons.add(optionStrict);
//        optionStrict.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Update(Event.chooseType(Event.Subject.SEEKER));
//            }
//        });
//        JButton optionFlex= new JButton("Flexible");
//        allOptionButtons.add(optionFlex);
//        optionFlex.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                Update(Event.chooseType(Event.Subject.OPENING));
//            }
//        });
//        JButton optionEdu= new JButton("Education focus");
//        allOptionButtons.add(optionEdu);
//        optionEdu.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                Update(Event.chooseType(Event.Subject.OPENING));
//            }
//        });
//        JPanel menuButtons = new JPanel();
//        menuButtons.setBackground(Colors.getButtonBackgroundColor());
//        menuButtons.setOpaque(true);
//        menuButtons.setLayout(new BoxLayout(menuButtons, BoxLayout.Y_AXIS));
//        menuButtons.setPreferredSize(new Dimension(250, allOptionButtons.size() * 60));
//        menuButtons.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
//        menuButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//    }
//
//    @Override
//    public void Update(Event event) {
//        event.setPhase(Event.Phase.MATCH_STRATEGY_SELECTED);
//        mainFrame.Update(event);
//    }
//}
