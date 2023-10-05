package src.helper;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import src.entity.Person;
import src.entity.Student;
import src.entity.Subject;
import src.entity.Teacher;
import src.enums.StudentStatus;

public class ProcessHelper {

  private static final Logger LOGGER = Logger.getLogger(String.valueOf(ProcessHelper.class));
  private final Scanner sc = new Scanner(System.in);
  private static final String NO_STUDENT_WITH_SUCH_ID = "No student with such id : ";
  public static final String NO_TEACHER_WITH_SUCH_ID = "No teacher with such id : ";
  public static final String NO_PERSON_WITH_SUCH_ID = "No person with such id: ";
  private static final String TEXT_BLOCK = "\"1\" Add student."
      + "\n\"2\" Add teacher."
      + "\n\"3\" Remove person."
      + "\n\"4\" Add subject to student."
      + "\n\"5\" Student subject list."
      + "\n\"6\" Add teacher to a student."
      + "\n\"7\" Add student to teacher."
      + "\n\"8\" Student teachers list."
      + "\n\"9\" Teacher students list."
      + "\n\"10\" Remove teacher from student."
      + "\n\"11\" Remove student from teacher."
      + "\n\"12\" Student list."
      + "\n\"13\" Teacher list."
      + "\n\"14\" Person salary."
      + "\n\"15\" All salary payment."
      + "\n\"16\" Person info."
      + "\n\"17\" Student subject update."
      + "\n\"18\" All person list."
      + "\n\"19\" Command list."
      + "\n\"20\" End process.";
  public static final String DELIMITER = "----------------------------------------";

  private final LoggerHelper loggerHelper = new LoggerHelper();

  public ProcessHelper() {
    loggerHelper.loggerSetting(LOGGER);
  }

  public void addStudent(Map<String, Person> personMap) throws NoSuchAlgorithmException {

    if (personMap.isEmpty() || personMap.entrySet().stream().anyMatch(Teacher.class::isInstance)) {
      LOGGER.info("You should add at least one teacher before you will add student.");
      return;
    }

    LOGGER.info("Set name, surname and year for student");
    LOGGER.info("Name :");
    String name = sc.next();
    LOGGER.info("Surname :");
    String surname = sc.next();
    int birthYear = 0;
    while (birthYear > 2005 || birthYear < 1990) {
      LOGGER.info("Students birth year must be higher than 1990 and less than 2005. Print valid birthYear: ");
      while (!sc.hasNextInt()) {
        sc.next();
      }
      birthYear = sc.nextInt();
    }
    // Add student
    Student student = new Student(generateId(personMap), name, surname, birthYear);
    student.setStudentStatus(StudentStatus.FIRST_YEAR);

    LOGGER.info("Choose teacher by id.");

    teacherListPrint(personMap);

    int teacherId = -1;

    while (!(personMap.containsKey(String.valueOf(teacherId)) && personMap.get(String.valueOf(teacherId)) instanceof Teacher)) {
      while (!sc.hasNextInt()) {
        sc.next();
      }
      teacherId = sc.nextInt();
    }

    linkTeacherAndStudent(student, (Teacher) personMap.get(String.valueOf(teacherId)));

    // Add student to personMap
    personMap.put(student.getId(), student);

    LOGGER.info("Student has been added to personMap");
  }

  public void addTeacher(Map<String, Person> personMap) throws NoSuchAlgorithmException {
    LOGGER.info("Print Teachers Name and Surname");
    LOGGER.info("Name :");
    String name = sc.next();
    LOGGER.info("Surname :");
    String surname = sc.next();
    int birthYear = 0;
    while (birthYear > 1995 || birthYear < 1965) {
      LOGGER.info("Teachers birth year must be higher than 1965 and less than 1995. Print valid birthYear");
      while (!sc.hasNextInt()) {
        sc.next();
      }
      birthYear = sc.nextInt();
    }

    // Create teacher
    Teacher teacher = new Teacher(generateId(personMap), name, surname, birthYear);

    // Add teacher to personMap
    personMap.put(teacher.getId(), teacher);
    LOGGER.info("Successfully add new teacher:" + teacher);
  }

  public void showPersons(Map<String, Person> personMap) {
    studentListPrint(personMap);
    teacherListPrint(personMap);
  }

  public void deletePerson(Map<String, Person> personMap) {
    showPersons(personMap);
    LOGGER.info("Print person id to delete him.");

    while (!sc.hasNextInt()) {
      sc.next();
    }
    int removeID = sc.nextInt();

    if (personMap.containsKey(String.valueOf(removeID))) {
      Person personToDelete = personMap.get(String.valueOf(removeID));

      List<String> idList = personToDelete instanceof Teacher ?
          ((Teacher) personToDelete).getStudentList().stream().map(Student::getId).collect(Collectors.toList()) :
          ((Student) personToDelete).getTeacherList().stream().map(Teacher::getId).collect(Collectors.toList());

      if (personToDelete instanceof Teacher && !idList.isEmpty()) {
        LOGGER.info("Could not delete teacher with students! Delete teacher from student first.");
        return;
      }

      LOGGER.info("Delete info about this person from another people.");
      for (String id: idList) {
        deleteStudentFromTeacher((Teacher) personMap.get(id), (Student) personToDelete);
      }

      // Remove person from personMap
      personMap.remove(personToDelete.getId());
      LOGGER.info("Person has successfully deleted");
    } else {
      LOGGER.info("No person with such id : " + removeID);
    }


  }

  public void addSubject(Map<String, Person> personMap) {
    studentListPrint(personMap);
    LOGGER.info("Print student`s id for adding subject");

    while (!sc.hasNextInt()) {
      sc.next();
    }
    String studentId = String.valueOf(sc.nextInt());

    if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
      addSubjectForStudent((Student) personMap.get(studentId));
      LOGGER.info("Add new subject for student.");
    } else {
      LOGGER.info(NO_STUDENT_WITH_SUCH_ID + studentId);
    }
  }

  public void studentSubjectList(Map<String, Person> personMap) {
    studentListPrint(personMap);
    LOGGER.info("Print student's id");

    while (!sc.hasNextInt()) {
      sc.next();
    }
    String studentId = String.valueOf(sc.nextInt());

    if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
      LOGGER.info(((Student) personMap.get(studentId)).getSubjectList().stream().map(Subject::toString).collect(Collectors.joining("\n")));
    } else {
      LOGGER.info(NO_STUDENT_WITH_SUCH_ID + studentId);
    }
  }

  public void addTeacherToStudent(Map<String, Person> personMap) {
    studentListPrint(personMap);
    LOGGER.info("Print student's id to add teacher.");

    while (!sc.hasNextInt()) {
      sc.next();
    }
    String studentId = String.valueOf(sc.nextInt());

    if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
      LOGGER.info("Choose one of the teacher.");
      teacherListPrint(personMap);

      while (!sc.hasNextInt()) {
        sc.next();
      }
      String teacherId = String.valueOf(sc.nextInt());
      if (personMap.containsKey(teacherId) && personMap.get(teacherId) instanceof Teacher) {
        Teacher teacher = (Teacher) personMap.get(teacherId);
        if (((Student) personMap.get(studentId)).getTeacherList().contains(teacher)) {
          LOGGER.info("Student already study with this teacher.");
          return;
        }
        linkTeacherAndStudent((Student) personMap.get(studentId), teacher);
        LOGGER.info("Student now have new Teacher.");
      } else {
        LOGGER.info(NO_TEACHER_WITH_SUCH_ID + teacherId);
      }
    } else {
      LOGGER.info(NO_STUDENT_WITH_SUCH_ID + studentId);
    }
  }

  public void addStudentToTeacher(Map<String, Person> personMap) {
    teacherListPrint(personMap);
    LOGGER.info("Print teacher's id to add student");

    while (!sc.hasNextInt()) {
      sc.next();
    }
    String teacherId = String.valueOf(sc.nextInt());

    if (personMap.containsKey(teacherId) && personMap.get(teacherId) instanceof Teacher) {
      LOGGER.info("Choose one of the students");
      studentListPrint(personMap);

      while (!sc.hasNextInt()) {
        sc.next();
      }
      String studentId = String.valueOf(sc.nextInt());

      if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
        Teacher teacher = (Teacher) personMap.get(teacherId);
        if (((Student) personMap.get(studentId)).getTeacherList().contains(teacher)) {
          LOGGER.info("Student already study with this teacher.");
          return;
        }
        linkTeacherAndStudent((Student) personMap.get(studentId), teacher);
        LOGGER.info("Teacher now have new student.");
      } else {
        LOGGER.info(NO_STUDENT_WITH_SUCH_ID + studentId);
      }
    } else {
      LOGGER.info(NO_TEACHER_WITH_SUCH_ID + teacherId);
    }
  }
  
  public void studentTeacherList(Map<String, Person> personMap) {
    studentListPrint(personMap);


    while (!sc.hasNextInt()) {
      sc.next();
    }
    String studentId = String.valueOf(sc.nextInt());

    if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
        LOGGER.info(((Student) personMap.get(studentId)).getTeacherList().stream().map(Teacher::toString).collect(Collectors.joining("\n")));
    } else {
      LOGGER.info(NO_STUDENT_WITH_SUCH_ID + studentId);
    }
  }

  public void teacherStudentList(Map<String, Person> personMap) {
    teacherListPrint(personMap);
    LOGGER.info("\n" +"Print teacher id to view studentList");

    while (!sc.hasNextInt()) {
      sc.next();
    }
    String teacherId = String.valueOf(sc.nextInt());

    if (personMap.containsKey(teacherId) && personMap.get(teacherId) instanceof Teacher) {
      LOGGER.info(((Teacher) personMap.get(teacherId)).getStudentList().stream().map(Student::toString).collect(Collectors.joining("\n")));
    } else {
      LOGGER.info("\n" +NO_TEACHER_WITH_SUCH_ID + teacherId);
    }
  }

  public void removeTeacherFromStudent(Map<String, Person> personMap) {
    studentListPrint(personMap);
    LOGGER.info("\n" +"Print student id to remove teacher.");

    while (!sc.hasNextInt()) {
      sc.next();
    }
    String studentId = String.valueOf(sc.nextInt());

    if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
      Student student = (Student) personMap.get(studentId);
      if (student.getTeacherList().size() == 1) {
        LOGGER.info("Couldn't delete teacher because student has only one.");
        return;
      }
      LOGGER.info("\n" +"Choose one of teachers id:");
      LOGGER.info(student.getTeacherList().stream().map(Teacher::toString).collect(Collectors.joining("\n")));

      while (!sc.hasNextInt()) {
        sc.next();
      }
      String teacherId = String.valueOf(sc.nextInt());

      if (student.getTeacherList().stream().map(Teacher::getId).collect(Collectors.toList()).contains(teacherId)) {
        Teacher teacher = (Teacher) personMap.get(teacherId);
        student.getTeacherList().remove(teacher);
        deleteStudentFromTeacher(teacher, student);
        LOGGER.info("\n" +"Successful delete teacher from student.");
      } else {
        LOGGER.info("\n" +"Student dont have teacher with such id.");
      }
    } else {
      LOGGER.info("\n" +NO_STUDENT_WITH_SUCH_ID + studentId);
    }
  }

  public void removeStudentFromTeacher(Map<String, Person> personMap) {
    teacherListPrint(personMap);
    LOGGER.info("\n" +"Print teacher id to remove student.");

    while (!sc.hasNextInt()) {
      sc.next();
    }
    String teacherId = String.valueOf(sc.nextInt());

    if (personMap.containsKey(teacherId) && personMap.get(teacherId) instanceof Teacher) {
      Teacher teacher = (Teacher) personMap.get(teacherId);
      LOGGER.info("\n" +"Choose one of students id:");
      LOGGER.info(teacher.getStudentList().stream().map(Student::toString).collect(Collectors.joining("\n")));

      while (!sc.hasNextInt()) {
        sc.next();
      }
      String studentId = String.valueOf(sc.nextInt());

      if (teacher.getStudentList().stream().map(Student::getId).collect(Collectors.toList()).contains(studentId)) {
        Student student = (Student) personMap.get(studentId);

        if (student.getTeacherList().size() == 1) {
          LOGGER.info("This student have only one teacher so it's not possible to remove it now.");
          return;
        }

        student.getTeacherList().remove(teacher);
        deleteStudentFromTeacher(teacher, student);
        LOGGER.info("\n" +"Successful delete student from teacher.");
      } else {
        LOGGER.info("\n" +"Teacher dont have student with such id: " + studentId);
      }
    } else {
      LOGGER.info("\n" +NO_TEACHER_WITH_SUCH_ID + teacherId);
    }
  }

  public void studentListPrint(Map<String, Person> personMap) {
    LOGGER.info("\n" +"Teacher list:");
    List<Person> studentList = personMap.values().stream()
        .filter(Student.class::isInstance)
        .collect(Collectors.toList());

    for (Person student: studentList) {
      LOGGER.info(student.getName() + " " + student.getSurname() + " : " + student.getId());
    }
  }

  public void teacherListPrint(Map<String, Person> personMap) {
    LOGGER.info("\n" +"Teacher list:");
    List<Person> teacherList = personMap.values().stream()
        .filter(Teacher.class::isInstance)
        .collect(Collectors.toList());

    for (Person teacher: teacherList) {
      LOGGER.info(teacher.getName() + " " + teacher.getSurname() + " : " + teacher.getId());
    }
  }

  public void personSalary(Map<String, Person> personMap) {
    showPersons(personMap);
    LOGGER.info("\n" +"Print person id to view his salary.");

    while (!sc.hasNextInt()) {
      sc.next();
    }
    String personId = String.valueOf(sc.nextInt());

    if (personMap.containsKey(personId)) {
      if (personMap.get(personId) instanceof Student) {
        Student student = (Student) personMap.get(personId);
        if (student.isStipend()) {
          LOGGER.info("\n" +student + ". Actual stipend: " + student.getSalary());
        } else {
          LOGGER.info("\n" +student + ". Doesn't have stipend.");
        }
      } else {
        Teacher teacher = (Teacher) personMap.get(personId);
        int studentsWithStipend = (int) teacher.getStudentList().stream().map(Student::isStipend).count();
        LOGGER.info("\n" +teacher + ". Actual salary " + teacher.getSalary() + ".\nStudent with stipend: " + studentsWithStipend);
      }
    } else {
      LOGGER.info("\n" +NO_PERSON_WITH_SUCH_ID + personId);
    }
  }

  public void salaryPayment(Map<String, Person> personMap) {
    int stipendSum = personMap.entrySet().stream().filter(Student.class::isInstance).mapToInt(student -> ((Student) student).getSalary()).sum();
    int teacherSalarySum = personMap.entrySet().stream().filter(Teacher.class::isInstance).mapToInt(teacher -> ((Teacher) teacher).getSalary()).sum();
    LOGGER.info("\n" +"Total financial expenses:\nStipend: " + stipendSum + "\nSalary: " + teacherSalarySum + "\nTotal expenses: " + stipendSum + teacherSalarySum);
  }

  public void personInfo(Map<String, Person> personMap) {
    showPersons(personMap);
    LOGGER.info("\n" +"Print person id to view full information.");

    while (!sc.hasNextInt()) {
      sc.next();
    }
    String personId = String.valueOf(sc.nextInt());

    if (personMap.containsKey(personId)) {
      if (personMap.get(personId) instanceof Student) {
        Student student = (Student) personMap.get(personId);
        LOGGER.info("\n" +String.format("%s%nBirth year: %d. Salary: %d%nTeachers:%s%nSubjects :%s", student, student.getBirthYear(), student.getSalary(),
            student.getTeacherList().stream().map(Teacher::toString).collect(Collectors.joining("\n")),
            student.getSubjectList().stream().map(Subject::toString).collect(Collectors.joining("\n"))
        ));
      }
      else {
        Teacher teacher = (Teacher) personMap.get(personId);
        LOGGER.info("\n" +String.format("%s%nBirth year: %d. Salary: %d%nStudents:%s%n", teacher, teacher.getBirthYear(), teacher.getSalary(),
            teacher.getStudentList().stream().map(Student::toString).collect(Collectors.joining("\n"))
        ));
      }
    } else {
      LOGGER.info("\n" +NO_PERSON_WITH_SUCH_ID + personId);
    }
  }

  public void subjectUpdate(Map<String, Person> personMap) {
    studentListPrint(personMap);
    LOGGER.info("\n" +"Print student id to update subject.");
    while (!sc.hasNextInt()) {
      sc.next();
    }
    String studentId = String.valueOf(sc.nextInt());

    if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
      Student student = (Student) personMap.get(studentId);
      List<Subject> subjectList = student.getSubjectList();

      if (subjectList.isEmpty()) {
        LOGGER.info("\n" +"This student doesn't have any subject. Add some of them first.");
        return;
      }

      LOGGER.info("\n" +"Student subjects:\n" + subjectList.stream().map(Subject::toString).collect(Collectors.joining("\n")));
      LOGGER.info("\n" +"Print subject name to edit");
      String subjectName = sc.next();

      Optional<Subject> optionalSubject = subjectList.stream().filter(subject -> subjectName.equals(subject.getSubjectName())).findAny();

      if (optionalSubject.isEmpty()) {
        LOGGER.info("\n" +"No subject with such name.");
        return;
      }

      int mark = 0;
      while (mark > 4 || mark < 1) {
        LOGGER.info("\n" +"Print new subject mark between 1 and 4:");
        while (!sc.hasNextInt()) {
          sc.next();
        }
        mark = sc.nextInt();
      }

      optionalSubject.get().setMark(mark);

      checkStudentStatus(student);
      studentStipendCalculate(student);

      LOGGER.info("\n" +subjectName + " has successfully update.");
    } else {
      LOGGER.info("\n" +NO_STUDENT_WITH_SUCH_ID + studentId);
    }
  }

  public void printCommandList() {
    LOGGER.info("\n" +TEXT_BLOCK);
    LOGGER.info("\n" +DELIMITER);
  }

  private void linkTeacherAndStudent(Student student, Teacher teacher) {
    // Add teacher to student
    student.getTeacherList().add(teacher);

    // Add student to teacher
    teacher.getStudentList().add(student);
  }

  private String generateId(Map<String, Person> personMap) throws NoSuchAlgorithmException {
    Random random = SecureRandom.getInstanceStrong();

    String id = String.valueOf(random.nextInt(9) + 1 + random.nextDouble()).replace(".", "").replace("-", "").substring(0, 8);

    if (personMap.containsKey(id)) {
      id = generateId(personMap);
    }

    return id;
  }

  private void deleteStudentFromTeacher(Teacher teacher, Student student) {
    teacher.getStudentList().remove(student);
    calculateSalary(teacher);
  }

  private void addSubjectForStudent (Student student) {
    LOGGER.info("Print subject name:");
    String subjectName = sc.next();

    Optional<Subject> optionalSubject = student.getSubjectList().stream().filter(subject -> subjectName.equals(subject.getSubjectName())).findAny();

    if (optionalSubject.isPresent()) {
      LOGGER.info("Student already have subject with name " + subjectName);
    }

    // Subject credits
    int creditNumber = 0;
    while (creditNumber > 6 || creditNumber < 1) {
      LOGGER.info("Print credits between 1 and 6");
      while (!sc.hasNextInt()) {
        sc.next();
      }
      creditNumber = sc.nextInt();
    }

    Subject subject = new Subject(subjectName, 4, creditNumber);
    student.getSubjectList().add(subject);

    // set student new Stipend status
    studentStipendCalculate(student);
    //TODO check personMap that it really changed teachers parameters
  }

  private void studentStipendCalculate (Student student) {
    LOGGER.info("Calculating students new markAverage");
    float markAverage = (float) student.getSubjectList().stream().mapToInt(Subject::getMark).sum() / student.getSubjectList().size();

    student.setStipend(markAverage < 2);
    calculateSalary(student);

    for (Teacher teacher: student.getTeacherList()) {
      calculateSalary(teacher);
    }
  }

  private void calculateSalary(Person person) {
    if (person instanceof Student) {
      Student student = (Student) person;
      float markAverage = (float) student.getSubjectList().stream().mapToInt(Subject::getMark).sum() / student.getSubjectList().size();
      student.setSalary(student.isStipend() ? (int) ((5 - markAverage) * 1000) : 0);
    } else {
      Teacher teacher = (Teacher) person;
      teacher.setSalary((int) (teacher.getStudentList().stream().filter(Student::isStipend).count() * 1500 + 10000));
    }
  }

  private void checkStudentStatus(Student student) {
    int creditsSum = student.getSubjectList().stream().filter(subject -> subject.getMark() < 3).mapToInt(Subject::getSubjectCredits).sum();
    if (creditsSum < 60) {
      student.setStudentStatus(StudentStatus.FIRST_YEAR);
    } else if (creditsSum < 120) {
      student.setStudentStatus(StudentStatus.SECOND_YEAR);
    } else if (creditsSum < 180){
      student.setStudentStatus(StudentStatus.THIRD_YEAR);
    } else {
      student.setStudentStatus(StudentStatus.GRADUATED);
    }
  }
}
