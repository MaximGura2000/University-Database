package src.entity;

import java.util.*;

public class Student extends Person {

  private boolean stipend;
  float markAverage;
  int subjectNumber = 0;
  int markNumber = 0;
  Scanner sc = new Scanner(System.in);
  List<Subject> subjectList = new ArrayList<>();
  List<Teacher> teacherList = new ArrayList<>();

  public Student(String id, String name, String surname, Integer birthYear) {
    super(id, name, surname, birthYear, 0);
  }


  public boolean checkTeacher(String checkID) {
    for (Teacher teacher : teacherList) {
			if (teacher.getId().equals(checkID)) {
				return true;
			}
    }

    return false;
  }

  public void addSubject() {
    System.out.println("Zadejte subjectName predmetu: ");
    String subjectName = sc.next();
    boolean repeatSubject = false;
    for (Subject subject : subjectList) {
			if (subject.getSubjectName().compareTo(subjectName) == 0) {
				repeatSubject = true;
			}
    }
		if (repeatSubject) {
			System.out.println("src.entity.Student uz takovy predmet ma.");
		} else {
			int mark = 0;
			while (mark > 4 || mark < 1) {
				System.out.println("Zadejte znamku predmeta mezi 1 a 4");
				while (!sc.hasNextInt()) {
					sc.next();
				}
				mark = sc.nextInt();
			}
			Subject subject = new Subject(subjectName, mark);
			subjectList.add(subject);
			subjectNumber++;
			markNumber = markNumber + mark;
			this.markAverage = (float) markNumber / (float) subjectNumber;
			newStipendium();
		}
  }

  public void showSubject() {
    for (Subject subject : subjectList) {
      System.out.println(subject.getSubjectName() + ": znamka - " + subject.getMark());
    }
  }

  private void newStipendium() {
    System.out.println("Studijni prumer se zmnenil. Aktualni prumer je " + markAverage);
		if (this.markAverage <= 2) {
			stipend = true;
		} else {
			stipend = false;
		}
    calculateSalary();
  }

  public void addTeacher(Teacher newTeacher) {
    teacherList.add(newTeacher);
  }

  public void teacherList() {
    for (Teacher teacher : teacherList) {
      System.out.println(teacher.getName() + " " + teacher.getSurname() + " s ID:" + teacher.getId());
    }
  }

  public void deleteTeacher(String IDUcitele) {
    for (Teacher teacher : teacherList) {
      if (teacher.getId().equals(IDUcitele)) {
        this.teacherList.remove(teacher);
        break;
      }
    }
    System.out.println("src.entity.Ucitel byl odstranen od studenta!");
  }

  public boolean isStipend() {
    return stipend;
  }

  public void setStipend(boolean stipend) {
    this.stipend = stipend;
  }

  public float getMarkAverage() {
    return markAverage;
  }

  public void setMarkAverage(float markAverage) {
    this.markAverage = markAverage;
  }

  public int getSubjectNumber() {
    return subjectNumber;
  }

  public void setSubjectNumber(int subjectNumber) {
    this.subjectNumber = subjectNumber;
  }

  public int getMarkNumber() {
    return markNumber;
  }

  public void setMarkNumber(int markNumber) {
    this.markNumber = markNumber;
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

  @Override
  public boolean equals(Student o) {
    return this.getName().equals(o.getName())
        && this.getSurname().equals(o.getSurname())
        && this.getId().equals(o.getId())
        && this.getBirthYear().equals(o.getBirthYear());
  }

  public void calculateSalary() {
		if (this.isStipend()) {
			System.out.println("Student ma dostatecny prumer, aby mel stipendium.");
			this.setSalary((5 - this.getSalary()) * 1000);
		} else {
			System.out.println("Student ma prumer mene nez 2, a proto nema stipendium");
		}
  }
}
