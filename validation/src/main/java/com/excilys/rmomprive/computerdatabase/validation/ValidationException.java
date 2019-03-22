package com.excilys.rmomprive.computerdatabase.validation;

public class ValidationException extends RuntimeException {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  public enum ValidationType {
    EMPTY_DATE, INVALID_NAME, INVALID_DATE_PRECEDENCE
  };

  private ValidationType type;

  public ValidationException(ValidationType type) {
    this.type = type;
  }

  public ValidationType getType() {
    return type;
  }

  @Override
  public String getMessage() {
    switch (type) {
    case EMPTY_DATE:
      return "If a discontinution date is set, the introduction date should not be null";
    case INVALID_NAME:
      return "The computer name should not be null";
    case INVALID_DATE_PRECEDENCE:
      return "The intruduction date should be before the discontinution date";
    default:
      return "";
    }
  }
}
