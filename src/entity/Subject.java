package src.entity;

public class Subject {

  private String subjectName;
  private Integer mark;

  public Subject(String subjectName, int znamka) {
    this.subjectName = subjectName;
    this.mark = znamka;
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
}