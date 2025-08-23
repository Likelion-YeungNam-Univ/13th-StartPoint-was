package startpointwas.domain.chat.exception;

public class OpenAiApiException extends RuntimeException {
  public OpenAiApiException(String message, Throwable cause) {
    super(message, cause);
  }
}
