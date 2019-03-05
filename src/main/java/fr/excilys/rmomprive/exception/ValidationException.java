package fr.excilys.rmomprive.exception;

public class ValidationException extends RuntimeException {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;
  
  public enum ValidationType {EMPTY_DATE, INVALID_NAME, INVALID_DATE_PRECEDENCE};
  
  private ValidationType type;
 
  public ValidationException(ValidationType type) {
    this.type = type;
  }
  
  public ValidationType getType() {
    return type;
  }
}
