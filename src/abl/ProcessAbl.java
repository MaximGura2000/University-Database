package src.abl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import src.entity.Person;
import src.entity.Student;
import src.entity.Teacher;
import src.enums.StudentStatus;

public class ProcessAbl {

  private static final Logger LOGGER = Logger.getLogger("ProcessAbl logger.");
  public static final String DELIMITER = "----------------------------------------";
  public static final String NO_STUDENT_WITH_SUCH_ID = "No student with such id : ";
  public static final String NO_TEACHER_WITH_SUCH_ID = "No teacher with such id : ";
  // TODO add subject update for students
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
      + "\n\"18\" End process.";

  public void processStart() {
    LOGGER.info("Start of application!");
    boolean endProcess = false;

    Map<String, Person> personMap = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    while (!endProcess) {
      LOGGER.info(DELIMITER);

      LOGGER.info(TEXT_BLOCK);
      LOGGER.info(DELIMITER);

      while(!sc.hasNextInt()) {
        sc.next();
      }

      int choice = sc.nextInt();
      switch (choice) {
        case 1 : {
          if (personMap.isEmpty() || personMap.entrySet().stream().anyMatch(Teacher.class::isInstance)) {
            LOGGER.info("You should add at least one teacher before you will add student.");
            break;
          }

          LOGGER.info("Set name, surname and year for student");
          LOGGER.info("Name :");
          String name = sc.next();
          LOGGER.info("Surname :");
          String surname = sc.next();
          int birthYear = 0;
          while (birthYear > 2005 || birthYear < 1990) {
            LOGGER.info("Students birth year must be higher than 1990 and less than 2005. Print valid birthYear");
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

          // Add teacher to student
          student.addTeacher((Teacher) personMap.get(String.valueOf(teacherId)));

          // Add student to teacher
          ((Teacher) personMap.get(String.valueOf(teacherId))).getStudentList().add(student);

          // Add student to personMap
          personMap.put(student.getId(), student);

          LOGGER.info("Student has been added to personMap");

          break;
        }
        case 2 : {
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

          break;
        }
        case 3 : {
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
              break;
            }

            LOGGER.info("Delete info about person from another people.");
            for (String id: idList) {
              deleteStudentFromTeacher((Teacher) personMap.get(id), personToDelete.getId(), ((Student) personToDelete).isStipend());
            }

            // Remove person from personMap
            personMap.remove(personToDelete.getId());
            LOGGER.info("Person has successfully deleted");
          } else {
            LOGGER.info("No person with such id : " + removeID);
          }

          break;
        }
        case 4 : {
          LOGGER.info("Print student`s id for adding subject");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String studentId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
            ((Student) personMap.get(studentId)).addSubject();
            LOGGER.info("Add new subject for student.");
          } else {
            LOGGER.info(NO_STUDENT_WITH_SUCH_ID + studentId);
          }

          break;
        }
        case 5 : {
          LOGGER.info("Print student's id");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String studentId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
            ((Student) personMap.get(studentId)).showSubject();
          } else {
            LOGGER.info(NO_STUDENT_WITH_SUCH_ID + studentId);
          }
          break;
        }
        case 6 : {
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
              if (((Student) personMap.get(studentId)).checkTeacher(teacherId)) {
                LOGGER.info("Student already study with this teacher.");
                break;
              }
              addTeacherAndStudentLink(personMap, studentId, teacherId);
              LOGGER.info("Student now have new Teacher.");
            } else {
              LOGGER.info(NO_TEACHER_WITH_SUCH_ID + teacherId);
            }
          } else {
            LOGGER.info(NO_STUDENT_WITH_SUCH_ID + studentId);
          }
          break;
        }
        case 7 : {
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
              if (((Student) personMap.get(studentId)).checkTeacher(teacherId)) {
                LOGGER.info("Student already study with this teacher.");
                break;
              }
              addTeacherAndStudentLink(personMap, studentId, teacherId);
              LOGGER.info("Teacher now have new student.");
            } else {
              LOGGER.info(NO_STUDENT_WITH_SUCH_ID + studentId);
            }
          } else {
            LOGGER.info(NO_TEACHER_WITH_SUCH_ID + teacherId);
          }
          break;
        }
        case 8 : {
          LOGGER.info("Print student id to view teacherList");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String studentId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
            for (Teacher teacher: ((Student) personMap.get(studentId)).getTeacherList()) {
              LOGGER.info(teacher.getName() + " " + teacher.getSurname() + " : " + teacher.getId() + ". Birth year: " + teacher.getBirthYear());
            }
          } else {
            LOGGER.info(NO_STUDENT_WITH_SUCH_ID + studentId);
          }
          break;
        }
        case 9 : {
          LOGGER.info("Print teacher id to view studentList");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String teacherId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(teacherId) && personMap.get(teacherId) instanceof Teacher) {
            for (Student student: ((Teacher) personMap.get(teacherId)).getStudentList()) {
              LOGGER.info(student.getName() + " " + student.getSurname() + " : " + student.getId() + ". Birth year: " + student.getBirthYear()
              + ". Status: " + student.getStudentStatus());
            }
          }
          break;
        }
        case 10 : {
          LOGGER.info("Print student id to remove teacher.");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String studentId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
            LOGGER.info("Choose one of teachers id:");
            List<String> teacherInfoList = ((Student) personMap.get(studentId)).getTeacherList().stream().map(Teacher::toString).collect(Collectors.toList());
            for (String teacher: teacherInfoList) {
              LOGGER.info(teacher);
            }

            while (!sc.hasNextInt()) {
              sc.next();
            }
            String teacherId = String.valueOf(sc.nextInt());

            if (((Student) personMap.get(studentId)).getTeacherList().stream().map(Teacher::getId).collect(Collectors.toList()).contains(teacherId)) {
              ((Student) personMap.get(studentId)).deleteTeacher(teacherId);
              deleteStudentFromTeacher((Teacher) personMap.get(teacherId), studentId, ((Student) personMap.get(studentId)).isStipend());
              LOGGER.info("Successful delete teacher from student.");
            } else {
             LOGGER.info("Student dont have teacher with such id.");
            }
          } else {
            LOGGER.info("No student with such id");
          }
          break;
        }
        case 11 : {
          LOGGER.info("Print teacher id to remove student.");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String teacherId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(teacherId) && personMap.get(teacherId) instanceof Teacher) {
            LOGGER.info("Choose one of students id:");
            List<String> studentInfoList = ((Teacher) personMap.get(teacherId)).getStudentList().stream().map(Student::toString).collect(Collectors.toList());
            for (String student: studentInfoList) {
              LOGGER.info(student);
            }

            while (!sc.hasNextInt()) {
              sc.next();
            }
            String studentId = String.valueOf(sc.nextInt());

            if (((Teacher) personMap.get(teacherId)).getStudentList().stream().map(Student::getId).collect(Collectors.toList()).contains(studentId)) {
              ((Student) personMap.get(studentId)).deleteTeacher(teacherId);
              deleteStudentFromTeacher((Teacher) personMap.get(teacherId), studentId, ((Student) personMap.get(studentId)).isStipend());
              LOGGER.info("Successful delete student from teacher.");
            } else {
             LOGGER.info("Teacher dont have student with such id.");
            }
          } else {
            LOGGER.info("No teacher with such id.");
          }
          break;
        }
        case 12 : {
          LOGGER.info("Student list:");
          studentListPrint(personMap);
          break;
        }
        case 13 : {
          LOGGER.info("Teacher list:");
          teacherListPrint(personMap);
          break;
        }
        case 18 : {
          LOGGER.info("Ending university process.");
          endProcess = true;
          break;
        }
        default : {
          LOGGER.info("No such option with number: " + choice);
        }
      }
    }
  }

  private void addTeacherAndStudentLink(Map<String, Person> personMap, String studentId, String teacherId) {
    ((Teacher) personMap.get(teacherId)).addStudent((Student) personMap.get(studentId));
    ((Student) personMap.get(studentId)).addTeacher((Teacher) personMap.get(teacherId));
  }

  private void teacherListPrint(Map<String, Person> personMap) {
    List<Person> teacherList = personMap.entrySet().stream()
        .filter(Teacher.class::isInstance)
        .map(personMap::get)
        .collect(Collectors.toList());

    for (Person teacher: teacherList) {
      LOGGER.info(teacher.getName() + " " + teacher.getSurname() + " : " + teacher.getId());
    }
  }

  private void studentListPrint(Map<String, Person> personMap) {
    List<Person> studentList = personMap.entrySet().stream()
        .filter(Student.class::isInstance)
        .map(personMap::get)
        .collect(Collectors.toList());

    for (Person student: studentList) {
      LOGGER.info(student.getName() + " " + student.getSurname() + " : " + student.getId());
    }
  }

  private String generateId(Map<String, Person> personMap) {
    Random random = new Random();

    String id = String.valueOf(random.nextDouble()).replace(".", "").replace("-", "").substring(0, 10);

    if (personMap.containsKey(id)) {
      id = generateId(personMap);
    }

    return id;
  }

  private void deleteStudentFromTeacher(Teacher teacher, String removeId, boolean stipend) {
    teacher.deleteStudent(removeId);
    if (stipend) {
      teacher.setStudentsWithStipend(teacher.getStudentsWithStipend() - 1);
    }
  }
}
