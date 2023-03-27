package src.enums;

public enum StudentStatus {

  FIRST_YEAR("First year student"),
  SECOND_YEAR("Second year student"),
  THIRD_YEAR("Third year student"),
  GRADUATED("Graduated student");

  StudentStatus(String value) {
    this.value = value;
  }

  private String value;

  public String getValue() {
    return value;
  }
}
