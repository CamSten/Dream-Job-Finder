package GUI;

import java.util.Queue;

public enum EventType {
        REQUEST_HANDLE_OPENING(Action.VIEW, Subject.OPENING, InputType.NONE, Quantity.NONE),
        REQUEST_HANDLE_SEEKER(Action.VIEW, Subject.SEEKER, InputType.NONE, Quantity.NONE),
        REQUEST_SEE_OPENING_LIST(Action.VIEW, Subject.OPENING, InputType.NONE, Quantity.MULTIPLE),
        REQUEST_SEE_SEEKER_LIST(Action.VIEW, Subject.SEEKER, InputType.NONE, Quantity.MULTIPLE),
        REQUEST_ADD_SEEKER (Action.ADD, Subject.SEEKER, InputType.MULTIPLE, Quantity.SINGLE),
        REQUEST_ADD_OPENING(Action.ADD, Subject.OPENING, InputType.MULTIPLE, Quantity.SINGLE),
        REQUEST_SEARCH_SEEKER(Action.SEARCH, Subject.SEEKER, InputType.SINGLE, Quantity.SINGLE),
        REQUEST_SEARCH_OPENING(Action.SEARCH, Subject.OPENING, InputType.SINGLE, Quantity.SINGLE),
        REQUEST_MATCH(Action.MATCH, Subject.NONE, InputType.SINGLE, Quantity.SINGLE),
        REQUEST_EDIT_FIELDS_SEEKER(Action.EDIT, Subject.SEEKER, InputType.NONE, Quantity.NONE),
        REQUEST_EDIT_FIELDS_OPENING(Action.EDIT, Subject.OPENING, InputType.NONE, Quantity.NONE),
        REQUEST_EDIT_OPENING(Action.EDIT, Subject.OPENING, InputType.MULTIPLE, Quantity.SINGLE),
        REQUEST_EDIT_SEEKER(Action.EDIT, Subject.SEEKER, InputType.MULTIPLE, Quantity.SINGLE),
        REQUEST_REMOVE_OPENING(Action.REMOVE, Subject.OPENING, InputType.SINGLE, Quantity.SINGLE),
        REQUEST_REMOVE_SEEKER(Action.REMOVE, Subject.SEEKER, InputType.SINGLE, Quantity.SINGLE),
        RETURN_ADD_SEEKER(Action.ADD, Subject.SEEKER, InputType.NONE, Quantity.SINGLE),
        RETURN_ADD_OPENING(Action.ADD, Subject.OPENING, InputType.NONE, Quantity.SINGLE),
        RETURN_REMOVE_SEEKER(Action.REMOVE, Subject.SEEKER, InputType.NONE, Quantity.SINGLE),
        RETURN_REMOVE_OPENING(Action.REMOVE, Subject.OPENING, InputType.NONE, Quantity.SINGLE),
        RETURN_SEARCH_SEEKER(Action.SEARCH, Subject.SEEKER, InputType.NONE, Quantity.MULTIPLE),
        RETURN_SEARCH_OPENING(Action.SEARCH, Subject.OPENING, InputType.NONE, Quantity.MULTIPLE),
        RETURN_FOUND_THIS_OPENING(Action.CONFIRM_SUCCESS, Subject.OPENING, InputType.NONE, Quantity.SINGLE),
        RETURN_FOUND_THIS_SEEKER(Action.CONFIRM_SUCCESS, Subject.SEEKER, InputType.NONE, Quantity.SINGLE),
        RETURN_FOUND_OPENINGS(Action.CONFIRM_SUCCESS, Subject.OPENING, InputType.NONE, Quantity.MULTIPLE),
        RETURN_FOUND_SEEKERS(Action.CONFIRM_SUCCESS, Subject.SEEKER, InputType.NONE, Quantity.MULTIPLE),
        RETURN_ADD_SUCCESSFUL(Action.CONFIRM_SUCCESS, Subject.NONE, InputType.NONE, Quantity.NONE),
        RETURN_SEEKER_NOT_FOUND(Action.CONFIRM_FAIL, Subject.SEEKER, InputType.NONE, Quantity.NONE),
        RETURN_OPENING_NOT_FOUND(Action.CONFIRM_FAIL, Subject.OPENING, InputType.NONE, Quantity.NONE),
        RETURN_SEEKER_ALREADY_EXIST(Action.CONFIRM_FAIL, Subject.SEEKER, InputType.NONE, Quantity.NONE),
        RETURN_OPENING_ALREADY_EXISTS(Action.CONFIRM_FAIL, Subject.OPENING, InputType.NONE, Quantity.NONE),
        RETURN_MATCH(Action.VIEW, Subject.NONE, InputType.NONE, Quantity.NONE),
        RETURN_EDITING_OPENING(Action.EDIT, Subject.OPENING, InputType.NONE, Quantity.SINGLE),
        RETURN_EDITING_SEEKER(Action.EDIT, Subject.SEEKER, InputType.NONE, Quantity.SINGLE),
        RETURN_EDIT_THIS_SEEKER(Action.EDIT, Subject.SEEKER, InputType.MULTIPLE, Quantity.SINGLE),
        RETURN_EDIT_THIS_OPENING(Action.EDIT, Subject.OPENING, InputType.MULTIPLE, Quantity.SINGLE),
        RETURN_EDIT_SUCCESSFUL(Action.CONFIRM_SUCCESS, Subject.NONE, InputType.NONE, Quantity.NONE),
        RETURN_REMOVE_SUCCESSFUL(Action.CONFIRM_SUCCESS, Subject.NONE, InputType.NONE, Quantity.NONE),
        RETURN_REMOVE_NOT_SUCCESSFUL(Action.CONFIRM_FAIL, Subject.NONE, InputType.NONE, Quantity.NONE);

private final Action action;
private final Subject subject;
private final InputType inputType;
private final Quantity quantity;

 EventType(Action action, Subject subject, InputType inputType, Quantity quantity) {
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

