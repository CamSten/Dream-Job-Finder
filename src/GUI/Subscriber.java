package GUI;

public interface Subscriber {
  enum MenuOption{ADD_SEEKER, ADD_OPENING, SEARCH, MATCH, EDIT, REMOVE}
    void update(MenuOption option);
}
