package src.entity;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

  private int studentsWithStipend = 0;
  List<Student> studentList = new ArrayList<>();

  public Teacher(Integer id, String name, String surname, Integer birthYear) {
    super(id, name, surname, birthYear, 10000);
  }

  public boolean checkStudent(int id) {
    for (Student student : studentList) {
			if (student.getId() == id) {
				return true;
			}
    }

    return false;
  }

  public void addStudent(Student newStudent) {
    studentList.add(newStudent);
  }

  public void deleteStudent(int studentID) {
    for (Student student : studentList) {
      if (student.getId() == studentID) {
        studentList.remove(student);
        break;
      }
    }
    System.out.println("src.entity.Student byl odstranen od ucitele!");
  }

  public void studentList() {
    for (Student student : studentList) {
      System.out.println(student.getName() + " " + student.getSurname() + " s ID:" + student.getId());
    }
  }

  public void calculateSalary() {
    for (Student student : studentList) {
      if (student.isStipend()) {
        studentsWithStipend++;
      }
    }
    this.setSalary(10000 + studentsWithStipend * 1500);
  }

  @Override
  public boolean equals(Student o) {
    return false;
  }

  public int getStudentsWithStipend() {
    return studentsWithStipend;
  }

  public void setStudentsWithStipend(int studentsWithStipend) {
    this.studentsWithStipend = studentsWithStipend;
  }

  public List<Student> getStudentList() {
    return studentList;
  }

  public void setStudentList(List<Student> studentList) {
    this.studentList = studentList;
  }
}
