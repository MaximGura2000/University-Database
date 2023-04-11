package src.entity;

public class Subject {

  private String subjectName;
  private Integer mark;
  private int subjectCredits;

  public Subject(String subjectName, int znamka, int subjectCredits) {
    this.subjectName = subjectName;
    this.mark = znamka;
    this.subjectCredits = subjectCredits;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setMark(Integer mark) {
    this.mark = mark;
  }

  public int getMark() {
    return mark;
  }

  public int getSubjectCredits() {
    return subjectCredits;
  }

  public void setSubjectCredits(int subjectCredits) {
    this.subjectCredits = subjectCredits;
  }

  @Override
  public String toString() {
    return "Subject: " + this.getSubjectName() + ", mark: " + this.getMark() + ". Credits: " + this.getSubjectCredits();
  }
}