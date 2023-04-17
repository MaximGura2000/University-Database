package src.entity;

import java.util.*;
import src.enums.StudentStatus;

public class Student extends Person {

  private boolean stipend;
  Scanner sc = new Scanner(System.in);
  List<Subject> subjectList = new ArrayList<>();
  List<Teacher> teacherList = new ArrayList<>();
  private StudentStatus studentStatus;

  public Student(String id, String name, String surname, Integer birthYear) {
    super(id, name, surname, birthYear, 0);
  }

  public boolean isStipend() {
    return stipend;
  }

  public void setStipend(boolean stipend) {
    this.stipend = stipend;
  }

  public List<Subject> getSubjectList() {
    return subjectList;
  }

  public void setSubjectList(List<Subject> subjectList) {
    this.subjectList = subjectList;
  }

  public List<Teacher> getTeacherList() {
    return teacherList;
  }

  public void setTeacherList(List<Teacher> teacherList) {
    this.teacherList = teacherList;
  }

  public StudentStatus getStudentStatus() {
    return studentStatus;
  }

  public void setStudentStatus(StudentStatus studentStatus) {
    this.studentStatus = studentStatus;
  }

  @Override
  public String toString() {
    return this.getName() + " " + this.getSurname() + ". Id:" + this.getId();
  }
}
