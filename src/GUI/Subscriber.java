package GUI;

public interface Subscriber {
    public enum EventType {
        REQUEST_HANDLE_OPENING(GUI.EventType.Action.VIEW, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        REQUEST_HANDLE_SEEKER(GUI.EventType.Action.VIEW, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        REQUEST_SEE_OPENING_LIST(GUI.EventType.Action.VIEW, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.MULTIPLE),
        REQUEST_SEE_SEEKER_LIST(GUI.EventType.Action.VIEW, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.MULTIPLE),
        REQUEST_ADD_SEEKER (GUI.EventType.Action.ADD, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.MULTIPLE, GUI.EventType.Quantity.SINGLE),
        REQUEST_ADD_OPENING(GUI.EventType.Action.ADD, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.MULTIPLE, GUI.EventType.Quantity.SINGLE),
        REQUEST_SEARCH_SEEKER(GUI.EventType.Action.SEARCH, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.SINGLE, GUI.EventType.Quantity.SINGLE),
        REQUEST_SEARCH_OPENING(GUI.EventType.Action.SEARCH, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.SINGLE, GUI.EventType.Quantity.SINGLE),
        REQUEST_MATCH(GUI.EventType.Action.MATCH, GUI.EventType.Subject.NONE, GUI.EventType.InputType.SINGLE, GUI.EventType.Quantity.SINGLE),
        REQUEST_EDIT_FIELDS_SEEKER(GUI.EventType.Action.EDIT, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        REQUEST_EDIT_FIELDS_OPENING(GUI.EventType.Action.EDIT, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        REQUEST_EDIT_OPENING(GUI.EventType.Action.EDIT, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.MULTIPLE, GUI.EventType.Quantity.SINGLE),
        REQUEST_EDIT_SEEKER(GUI.EventType.Action.EDIT, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.MULTIPLE, GUI.EventType.Quantity.SINGLE),
        REQUEST_REMOVE_OPENING(GUI.EventType.Action.REMOVE, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.SINGLE, GUI.EventType.Quantity.SINGLE),
        REQUEST_REMOVE_SEEKER(GUI.EventType.Action.REMOVE, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.SINGLE, GUI.EventType.Quantity.SINGLE),
        RETURN_ADD_SEEKER(GUI.EventType.Action.ADD, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.SINGLE),
        RETURN_ADD_OPENING(GUI.EventType.Action.ADD, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.SINGLE),
        RETURN_REMOVE_SEEKER(GUI.EventType.Action.REMOVE, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.SINGLE),
        RETURN_REMOVE_OPENING(GUI.EventType.Action.REMOVE, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.SINGLE),
        RETURN_SEARCH_SEEKER(GUI.EventType.Action.SEARCH, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.MULTIPLE),
        RETURN_SEARCH_OPENING(GUI.EventType.Action.SEARCH, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.MULTIPLE),
        RETURN_FOUND_THIS_OPENING(GUI.EventType.Action.CONFIRM_SUCCESS, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.SINGLE),
        RETURN_FOUND_THIS_SEEKER(GUI.EventType.Action.CONFIRM_SUCCESS, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.SINGLE),
        RETURN_FOUND_OPENINGS(GUI.EventType.Action.CONFIRM_SUCCESS, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.MULTIPLE),
        RETURN_FOUND_SEEKERS(GUI.EventType.Action.CONFIRM_SUCCESS, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.MULTIPLE),
        RETURN_ADD_SUCCESSFUL(GUI.EventType.Action.CONFIRM_SUCCESS, GUI.EventType.Subject.NONE, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        RETURN_SEEKER_NOT_FOUND(GUI.EventType.Action.CONFIRM_FAIL, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        RETURN_OPENING_NOT_FOUND(GUI.EventType.Action.CONFIRM_FAIL, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        RETURN_SEEKER_ALREADY_EXIST(GUI.EventType.Action.CONFIRM_FAIL, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        RETURN_OPENING_ALREADY_EXISTS(GUI.EventType.Action.CONFIRM_FAIL, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        RETURN_MATCH(GUI.EventType.Action.VIEW, GUI.EventType.Subject.NONE, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        RETURN_EDITING_OPENING(GUI.EventType.Action.EDIT, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.SINGLE),
        RETURN_EDITING_SEEKER(GUI.EventType.Action.EDIT, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.SINGLE),
        RETURN_EDIT_THIS_SEEKER(GUI.EventType.Action.EDIT, GUI.EventType.Subject.SEEKER, GUI.EventType.InputType.MULTIPLE, GUI.EventType.Quantity.SINGLE),
        RETURN_EDIT_THIS_OPENING(GUI.EventType.Action.EDIT, GUI.EventType.Subject.OPENING, GUI.EventType.InputType.MULTIPLE, GUI.EventType.Quantity.SINGLE),
        RETURN_EDIT_SUCCESSFUL(GUI.EventType.Action.CONFIRM_SUCCESS, GUI.EventType.Subject.NONE, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        RETURN_REMOVE_SUCCESSFUL(GUI.EventType.Action.CONFIRM_SUCCESS, GUI.EventType.Subject.NONE, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE),
        RETURN_REMOVE_NOT_SUCCESSFUL(GUI.EventType.Action.CONFIRM_FAIL, GUI.EventType.Subject.NONE, GUI.EventType.InputType.NONE, GUI.EventType.Quantity.NONE);

        private final GUI.EventType.Action action;
        private final GUI.EventType.Subject subject;
        private final GUI.EventType.InputType inputType;
        private final GUI.EventType.Quantity quantity;

        EventType(GUI.EventType.Action action, GUI.EventType.Subject subject, GUI.EventType.InputType inputType, GUI.EventType.Quantity quantity) {
            this.action = action;
            this.subject = subject;
            this.inputType = inputType;
            this.quantity = quantity;
        }
        enum Quantity{NONE, SINGLE, MULTIPLE}
        enum InputType{NONE, SINGLE, MULTIPLE}
        enum Action {CONFIRM_SUCCESS, CONFIRM_FAIL, VIEW, ADD, EDIT, SEARCH, REMOVE, MATCH}
        enum Subject{OPENING, SEEKER, NONE}
    }


    void update(EventType option, Object data);
}
