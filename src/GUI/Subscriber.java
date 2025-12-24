package GUI;

public interface Subscriber {
  enum EventType {
      REQUEST_ADD_SEEKER,
      REQUEST_ADD_OPENING,
      REQUEST_SEARCH,
      REQUEST_MATCH,
      REQUEST_EDIT,
      REQUEST_REMOVE,
      RETURN_ADD_SEEKER,
      RETURN_ADD_OPENING,
      RETURN_SEARCH,
      RETURN_MATCH,
      RETURN_EDIT,
      RETURN_REMOVE;
  }
    void update(EventType option, Object data);
}
