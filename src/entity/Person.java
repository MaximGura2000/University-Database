package src.entity;

public abstract class Person {

  private String id;
  private String name;
  private String surname;
  private Integer birthYear;
  private float salary;

  public Person(String id, String name, String surname, Integer birthYear, float salary) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.birthYear = birthYear;
    this.salary = salary;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public Integer getBirthYear() {
    return birthYear;
  }

  public void setBirthYear(Integer birthYear) {
    this.birthYear = birthYear;
  }

  public float getSalary() {
    return salary;
  }

  public void setSalary(float salary) {
    this.salary = salary;
  }

  public abstract boolean equals(Student o);
}
