package GUI ;

public interface Subscriber {
    public enum EventType {
        REQUEST_HANDLE_OPENING(Action.VIEW, Subject.OPENING, InputType.NONE, Quantity.NONE, Status.NONE),
        REQUEST_HANDLE_SEEKER(Action.VIEW, Subject.SEEKER, InputType.NONE, Quantity.NONE, Status.NONE),
        REQUEST_SEE_OPENING_LIST(Action.VIEW, Subject.OPENING, InputType.NONE, Quantity.MULTIPLE, Status.NONE),
        REQUEST_SEE_SEEKER_LIST(Action.VIEW, Subject.SEEKER, InputType.NONE, Quantity.MULTIPLE, Status.NONE),
        REQUEST_ADD_SEEKER (Action.ADD, Subject.SEEKER, InputType.MULTIPLE, Quantity.SINGLE, Status.AWAIT_INPUT),
        REQUEST_ADD_OPENING(Action.ADD, Subject.OPENING, InputType.MULTIPLE, Quantity.SINGLE, Status.AWAIT_INPUT),
        REQUEST_SEARCH_SEEKER(Action.SEARCH, Subject.SEEKER, InputType.SINGLE, Quantity.SINGLE, Status.AWAIT_INPUT),
        REQUEST_SEARCH_OPENING(Action.SEARCH, Subject.OPENING, InputType.SINGLE, Quantity.SINGLE, Status.AWAIT_INPUT),
        REQUEST_MATCH_SEEKER(Action.MATCH, Subject.SEEKER, InputType.SINGLE, Quantity.SINGLE, Status.AWAIT_INPUT),
        REQUEST_MATCH_OPENING(Action.MATCH, Subject.OPENING, InputType.SINGLE, Quantity.SINGLE, Status.AWAIT_INPUT),
        REQUEST_EDIT_FIELDS_SEEKER(Action.EDIT, Subject.SEEKER, InputType.MULTIPLE, Quantity.NONE, Status.AWAIT_INPUT),
        REQUEST_EDIT_FIELDS_OPENING(Action.EDIT, Subject.OPENING, InputType.MULTIPLE, Quantity.NONE, Status.AWAIT_INPUT),
        REQUEST_EDIT_OPENING(Action.EDIT, Subject.OPENING, InputType.SINGLE, Quantity.SINGLE, Status.AWAIT_INPUT),
        REQUEST_EDIT_SEEKER(Action.EDIT, Subject.SEEKER, InputType.SINGLE, Quantity.SINGLE,Status.AWAIT_INPUT    ),
        REQUEST_REMOVE_OPENING(Action.REMOVE, Subject.OPENING, InputType.SINGLE, Quantity.SINGLE, Status.AWAIT_INPUT),
        REQUEST_REMOVE_SEEKER(Action.REMOVE, Subject.SEEKER, InputType.SINGLE, Quantity.SINGLE, Status.AWAIT_INPUT),
        RETURN_ADD_SEEKER(Action.ADD, Subject.SEEKER, InputType.NONE, Quantity.SINGLE, Status.HAS_INPUT),
        RETURN_ADD_OPENING(Action.ADD, Subject.OPENING, InputType.NONE, Quantity.SINGLE, Status.HAS_INPUT),
        RETURN_OPTIONS(Action.VIEW, Subject.NONE, InputType.NONE, Quantity.MULTIPLE, Status.ASSESSED_INPUT),
        RETURN_REMOVE_SEEKER(Action.REMOVE, Subject.SEEKER, InputType.NONE, Quantity.SINGLE, Status.HAS_INPUT),
        RETURN_REMOVE_OPENING(Action.REMOVE, Subject.OPENING, InputType.NONE, Quantity.SINGLE, Status.HAS_INPUT),
        RETURN_SEARCH_SEEKER(Action.SEARCH, Subject.SEEKER, InputType.NONE, Quantity.MULTIPLE, Status.HAS_INPUT),
        RETURN_SEARCH_OPENING(Action.SEARCH, Subject.OPENING, InputType.NONE, Quantity.MULTIPLE, Status.HAS_INPUT),
        RETURN_FOUND_THIS_OPENING(Action.CONFIRM_SUCCESS, Subject.OPENING, InputType.NONE, Quantity.SINGLE, Status.ASSESSED_INPUT),
        RETURN_FOUND_THIS_SEEKER(Action.CONFIRM_SUCCESS, Subject.SEEKER, InputType.NONE, Quantity.SINGLE, Status.ASSESSED_INPUT),
        RETURN_FOUND_OPENINGS(Action.CONFIRM_SUCCESS, Subject.OPENING, InputType.NONE, Quantity.MULTIPLE, Status.ASSESSED_INPUT),
        RETURN_FOUND_SEEKERS(Action.CONFIRM_SUCCESS, Subject.SEEKER, InputType.NONE, Quantity.MULTIPLE, Status.ASSESSED_INPUT),
        RETURN_ADD_SUCCESSFUL(Action.CONFIRM_SUCCESS, Subject.NONE, InputType.NONE, Quantity.NONE, Status.ASSESSED_INPUT),
        RETURN_SEEKER_NOT_FOUND(Action.CONFIRM_FAIL, Subject.SEEKER, InputType.NONE, Quantity.NONE, Status.ASSESSED_INPUT),
        RETURN_OPENING_NOT_FOUND(Action.CONFIRM_FAIL, Subject.OPENING, InputType.NONE, Quantity.NONE, Status.ASSESSED_INPUT),
        RETURN_SEEKER_ALREADY_EXIST(Action.CONFIRM_FAIL, Subject.SEEKER, InputType.NONE, Quantity.NONE, Status.ASSESSED_INPUT),
        RETURN_OPENING_ALREADY_EXISTS(Action.CONFIRM_FAIL, Subject.OPENING, InputType.NONE, Quantity.NONE, Status.ASSESSED_INPUT),
        RETURN_MATCH(Action.VIEW, Subject.NONE, InputType.NONE, Quantity.NONE, Status.HAS_INPUT),
        RETURN_EDITING_OPENING(Action.EDIT, Subject.OPENING, InputType.NONE, Quantity.SINGLE, Status.HAS_INPUT),
        RETURN_EDITING_SEEKER(Action.EDIT, Subject.SEEKER, InputType.NONE, Quantity.SINGLE, Status.HAS_INPUT),
        RETURN_EDIT_THIS_SEEKER(Action.EDIT, Subject.SEEKER, InputType.MULTIPLE, Quantity.SINGLE, Status.HAS_INPUT),
        RETURN_EDIT_THIS_OPENING(Action.EDIT, Subject.OPENING, InputType.MULTIPLE, Quantity.SINGLE, Status.HAS_INPUT),
        RETURN_EDIT_SUCCESSFUL(Action.CONFIRM_SUCCESS, Subject.NONE, InputType.NONE, Quantity.NONE, Status.ASSESSED_INPUT),
        RETURN_REMOVE_SUCCESSFUL(Action.CONFIRM_SUCCESS, Subject.NONE, InputType.NONE, Quantity.NONE, Status.ASSESSED_INPUT),
        RETURN_REMOVE_NOT_SUCCESSFUL(Action.CONFIRM_FAIL, Subject.NONE, InputType.NONE, Quantity.NONE, Status.ASSESSED_INPUT);

        private final Action action;
        private Subject subject;
        private final InputType inputType;
        private final Quantity quantity;
        private Status status;

        EventType(Action action, Subject subject, InputType inputType, Quantity quantity, Status status) {
            this.action = action;
            this.subject = subject;
            this.inputType = inputType;
            this.quantity = quantity;
            this.status = status;
        }

        public Subject getSubject() {
            return subject;
        }

        public Status getStatus() {
            return status;
        }

        public Action getAction(){
            return action;
        }
        public InputType getInputType(){
            return inputType;
        }
        public Quantity getQuantity() {
            return quantity;
        }
        public void setStatus(Status newStatus){
            this.status = newStatus;
        }
        public void setSubject(Subject newSubject){
            this.subject = newSubject;
        }
        public enum Status{NONE, AWAIT_INPUT, HAS_INPUT, ASSESSED_INPUT}
        enum Quantity{NONE, SINGLE, MULTIPLE}
        enum InputType{NONE, SINGLE, MULTIPLE}
        enum Action {CONFIRM_SUCCESS, CONFIRM_FAIL, VIEW, ADD, EDIT, SEARCH, REMOVE, MATCH}
        enum Subject{OPENING, SEEKER, NONE}
    }


    void update(EventType option, Object data);
}
