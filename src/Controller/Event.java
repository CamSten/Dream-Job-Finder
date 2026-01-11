package Controller;

public class Event {
    private Phase phase;
    private final Action action;
    private final Subject subject;
    private final Origin origin;
    private final Outcome outcome;
    private Object contents;
    private Object extraContents;

    public Event(Phase phase, Action action, Subject subject, Origin origin, Outcome outcome, Object contents, Object extraContents){
        this.phase = phase;
        this.action = action;
        this.subject = subject;
        this.origin = origin;
        this.outcome = outcome;
        this.contents = contents;
        this.extraContents = extraContents;
    }
    public enum Phase {
        AWAIT_INPUT,
        SUBMIT,
        SELECT,
        HANDLING,
        DISPLAY,
        COMPLETE,
        MATCH_ENTER_TERM,
        MATCH_TERM_SUBMITTED,
        MATCH_TARGET_SELECTED,
        MATCH_STRATEGY_SELECTED,
        MATCH_RESULT
        }

    public enum Action {
        CHOOSE_TYPE,
        ADD,
        EDIT,
        REMOVE,
        SEARCH,
        MATCH,
        VIEW;
    }
    public enum Subject {
        OPENING,
        SEEKER,
        NONE
    }
    public enum Origin {
        LOGIC,
        GUI
    }

    public enum Outcome {
        OK,
        NOT_FOUND,
        INVALID_INPUT,
        ALREADY_EXISTS,
        FAILURE
    }
    public static Event awaitInput(Action action, Subject subject, Origin origin) {
        return new Event(
                Phase.AWAIT_INPUT,
                action,
                subject,
                origin,
                null,
                null,
                null
        );
    }
    public static Event select(Action action, Subject subject, Object contents) {
        return new Event(
                Phase.SELECT,
                action,
                subject,
                Origin.GUI,
                Outcome.OK,
                contents,
                null
        );
    }
    public static Event submit(Action action, Subject subject, Object contents, Object extraContents) {
        return new Event(
                Phase.SUBMIT,
                action,
                subject,
                Origin.GUI,
                Outcome.OK,
                contents,
                extraContents

        );
    }
    public static Event confirmComplete(Action action, Outcome outcome) {
        return new Event(
                Phase.COMPLETE,
                action,
                Subject.NONE,
                Origin.LOGIC,
                outcome,
                null,
                null
        );
    }
    public static Event chooseType(Subject subject) {
        return new Event(
                Phase.COMPLETE,
                Action.CHOOSE_TYPE,
                subject,
                Origin.GUI,
                Outcome.OK,
                null,
                null
        );
    }
    public static Event error(Action action, Origin origin, Outcome outcome) {
        return new Event(
                Phase.COMPLETE,
                action,
                Subject.NONE,
                origin,
                outcome,
                null,
                null
        );
    }

    public Phase getPhase() {
        return phase;
    }

    public Action getAction() {
        return action;
    }

    public Subject getSubject() {
        return subject;
    }

    public Origin getOrigin() {
        return origin;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public Object getContents() {
        return contents;
    }
    public Object getExtraContents(){
        return extraContents;
    }
    public void setContents(Object input){
        this.contents = input;
    }
    public void setPhase(Phase phase){
        this.phase = phase;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
