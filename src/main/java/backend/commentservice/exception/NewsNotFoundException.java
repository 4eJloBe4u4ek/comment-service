package backend.commentservice.exception;

public class NewsNotFoundException extends RuntimeException {
  public NewsNotFoundException(String message) {
    super(message);
  }
}
