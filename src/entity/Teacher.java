package src.entity;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

  List<Student> studentList = new ArrayList<>();

  public Teacher(String id, String name, String surname, Integer birthYear) {
    super(id, name, surname, birthYear, 10000);
  }

  @Override
  public String toString() {
    return this.getName() + " " + this.getSurname() + ". Id: " + this.getId();
  }

  public List<Student> getStudentList() {
    return studentList;
  }

  public void setStudentList(List<Student> studentList) {
    this.studentList = studentList;
  }
}
