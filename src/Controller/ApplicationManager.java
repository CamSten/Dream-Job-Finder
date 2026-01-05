package Controller;

import GUI.MainFrame;
import GUI.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManager implements Subscriber {
    private List<Subscriber> subscribers = new ArrayList<>();
    private MainFrame mainFrame;

    public ApplicationManager () {
        mainFrame = new MainFrame(this);
    }
    @Override
    public void update(Subscriber.EventType option, Object data) {
        System.out.println("update in MainFrame was reached. eventtype is: " + option);
        switch (option){
            case REQUEST_ADD_SEEKER -> {
                mainFrame.showAdderPanel(MainFrame.AdderObject.SEEKER);
            }
            case REQUEST_ADD_OPENING -> {
                mainFrame.showAdderPanel(MainFrame.AdderObject.OPENING);
            }
            case REQUEST_SEARCH -> {
                mainFrame.showSearchPanel();
            }
            case REQUEST_MATCH -> {
                mainFrame.showMatchPanel();
            }
            case RETURN_ADD_OPENING, RETURN_ADD_SEEKER -> {
                mainFrame.showResultPanel(option);
            }
            case REQUEST_EDIT -> {
                mainFrame.showEditPanel();
            }
            case REQUEST_REMOVE -> {
                mainFrame.showRemovePanel();
            }
        }
    }
    private void notifySubscribers(Subscriber.EventType e, Object data){
        for (Subscriber subscriber : subscribers){
            subscriber.update(e, data);
        }
    }
    public  void addSubscriber(Subscriber s) {
        subscribers.add(s);
    }
}

