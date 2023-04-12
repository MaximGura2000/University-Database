package src.abl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import src.entity.Person;
import src.entity.Student;
import src.entity.Subject;
import src.entity.Teacher;
import src.enums.StudentStatus;

public class ProcessAbl {

  private static final Logger LOGGER = Logger.getLogger("ProcessAbl logger.");
  public static final String DELIMITER = "----------------------------------------";
  public static final String NO_STUDENT_WITH_SUCH_ID = "No student with such id : ";
  public static final String NO_TEACHER_WITH_SUCH_ID = "No teacher with such id : ";
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
      + "\n\"19\" End process.";
  public static final String NO_PERSON_WITH_SUCH_ID = "No person with such id: ";

  public void processStart() {
    
    loggerSetting();

    LOGGER.info("Start of application!");
    boolean endProcess = false;

    Map<String, Person> personMap = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    while (!endProcess) {
      LOGGER.info("\n" +DELIMITER);

      LOGGER.info("\n" +TEXT_BLOCK);
      LOGGER.info("\n" +DELIMITER);

      while(!sc.hasNextInt()) {
        sc.next();
      }

      int choice = sc.nextInt();
      switch (choice) {
        case 1 : {
          if (personMap.isEmpty() || personMap.entrySet().stream().anyMatch(Teacher.class::isInstance)) {
            LOGGER.info("\n" +"You should add at least one teacher before you will add student.");
            break;
          }

          LOGGER.info("\n" +"Set name, surname and year for student");
          LOGGER.info("\n" +"Name :");
          String name = sc.next();
          LOGGER.info("\n" +"Surname :");
          String surname = sc.next();
          int birthYear = 0;
          while (birthYear > 2005 || birthYear < 1990) {
            LOGGER.info("\n" +"Students birth year must be higher than 1990 and less than 2005. Print valid birthYear");
            while (!sc.hasNextInt()) {
              sc.next();
            }
            birthYear = sc.nextInt();
          }
          // Add student
          Student student = new Student(generateId(personMap), name, surname, birthYear);
          student.setStudentStatus(StudentStatus.FIRST_YEAR);

          LOGGER.info("\n" +"Choose teacher by id.");

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

          LOGGER.info("\n" +"Student has been added to personMap");

          break;
        }
        case 2 : {
          LOGGER.info("\n" +"Print Teachers Name and Surname");
          LOGGER.info("\n" +"Name :");
          String name = sc.next();
          LOGGER.info("\n" +"Surname :");
          String surname = sc.next();
          int birthYear = 0;
          while (birthYear > 1995 || birthYear < 1965) {
            LOGGER.info("\n" +"Teachers birth year must be higher than 1965 and less than 1995. Print valid birthYear");
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
          //TODO add person list here
          LOGGER.info("\n" +"Print person id to delete him.");

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
              LOGGER.info("\n" +"Could not delete teacher with students! Delete teacher from student first.");
              break;
            }

            LOGGER.info("\n" +"Delete info about person from another people.");
            for (String id: idList) {
              deleteStudentFromTeacher((Teacher) personMap.get(id), personToDelete.getId(), ((Student) personToDelete).isStipend());
            }

            // Remove person from personMap
            personMap.remove(personToDelete.getId());
            LOGGER.info("\n" +"Person has successfully deleted");
          } else {
            LOGGER.info("\n" +"No person with such id : " + removeID);
          }

          break;
        }
        case 4 : {
          //TODO add students id here
          LOGGER.info("\n" +"Print student`s id for adding subject");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String studentId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
            ((Student) personMap.get(studentId)).addSubject();
            ((Student) personMap.get(studentId)).getTeacherList().forEach(Teacher::calculateSalary);
            LOGGER.info("\n" +"Add new subject for student.");
          } else {
            LOGGER.info("\n" +NO_STUDENT_WITH_SUCH_ID + studentId);
          }

          break;
        }
        case 5 : {
          //TODO add student list here
          LOGGER.info("\n" +"Print student's id");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String studentId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
            ((Student) personMap.get(studentId)).showSubject();
          } else {
            LOGGER.info("\n" +NO_STUDENT_WITH_SUCH_ID + studentId);
          }
          break;
        }
        case 6 : {
          //TODO student list
          LOGGER.info("\n" +"Print student's id to add teacher.");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String studentId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
            LOGGER.info("\n" +"Choose one of the teacher.");
            teacherListPrint(personMap);

            while (!sc.hasNextInt()) {
              sc.next();
            }
            String teacherId = String.valueOf(sc.nextInt());
            if (personMap.containsKey(teacherId) && personMap.get(teacherId) instanceof Teacher) {
              if (((Student) personMap.get(studentId)).checkTeacher(teacherId)) {
                LOGGER.info("\n" +"Student already study with this teacher.");
                break;
              }
              addTeacherAndStudentLink(personMap, studentId, teacherId);
              LOGGER.info("\n" +"Student now have new Teacher.");
            } else {
              LOGGER.info("\n" +NO_TEACHER_WITH_SUCH_ID + teacherId);
            }
          } else {
            LOGGER.info("\n" +NO_STUDENT_WITH_SUCH_ID + studentId);
          }
          break;
        }
        case 7 : {
          //TODO teachers list
          LOGGER.info("\n" +"Print teacher's id to add student");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String teacherId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(teacherId) && personMap.get(teacherId) instanceof Teacher) {
            LOGGER.info("\n" +"Choose one of the students");
            studentListPrint(personMap);

            while (!sc.hasNextInt()) {
              sc.next();
            }
            String studentId = String.valueOf(sc.nextInt());

            if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
              if (((Student) personMap.get(studentId)).checkTeacher(teacherId)) {
                LOGGER.info("\n" +"Student already study with this teacher.");
                break;
              }
              addTeacherAndStudentLink(personMap, studentId, teacherId);
              LOGGER.info("\n" +"Teacher now have new student.");
            } else {
              LOGGER.info("\n" +NO_STUDENT_WITH_SUCH_ID + studentId);
            }
          } else {
            LOGGER.info("\n" +NO_TEACHER_WITH_SUCH_ID + teacherId);
          }
          break;
        }
        case 8 : {
          //TODO student list
          LOGGER.info("\n" +"Print student id to view teacherList");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String studentId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
            for (Teacher teacher: ((Student) personMap.get(studentId)).getTeacherList()) {
              LOGGER.info("\n" +teacher.getName() + " " + teacher.getSurname() + " : " + teacher.getId() + ". Birth year: " + teacher.getBirthYear());
            }
          } else {
            LOGGER.info("\n" +NO_STUDENT_WITH_SUCH_ID + studentId);
          }
          break;
        }
        case 9 : {
          //TODO teacher list
          LOGGER.info("\n" +"Print teacher id to view studentList");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String teacherId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(teacherId) && personMap.get(teacherId) instanceof Teacher) {
            for (Student student: ((Teacher) personMap.get(teacherId)).getStudentList()) {
              LOGGER.info("\n" +student.getName() + " " + student.getSurname() + " : " + student.getId() + ". Birth year: " + student.getBirthYear()
              + ". Status: " + student.getStudentStatus());
            }
          } else {
            LOGGER.info("\n" +NO_TEACHER_WITH_SUCH_ID + teacherId);
          }
          break;
        }
        case 10 : {
          //TODO Student list
          LOGGER.info("\n" +"Print student id to remove teacher.");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String studentId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(studentId) && personMap.get(studentId) instanceof Student) {
            //TODO check if more than 1 teacher
            LOGGER.info("\n" +"Choose one of teachers id:");
            List<String> teacherInfoList = ((Student) personMap.get(studentId)).getTeacherList().stream().map(Teacher::toString).collect(Collectors.toList());
            for (String teacher: teacherInfoList) {
              LOGGER.info("\n" +teacher);
            }

            while (!sc.hasNextInt()) {
              sc.next();
            }
            String teacherId = String.valueOf(sc.nextInt());

            if (((Student) personMap.get(studentId)).getTeacherList().stream().map(Teacher::getId).collect(Collectors.toList()).contains(teacherId)) {
              ((Student) personMap.get(studentId)).deleteTeacher(teacherId);
              deleteStudentFromTeacher((Teacher) personMap.get(teacherId), studentId, ((Student) personMap.get(studentId)).isStipend());
              LOGGER.info("\n" +"Successful delete teacher from student.");
            } else {
             LOGGER.info("\n" +"Student dont have teacher with such id.");
            }
          } else {
            LOGGER.info("\n" +NO_STUDENT_WITH_SUCH_ID + studentId);
          }
          break;
        }
        case 11 : {
          LOGGER.info("\n" +"Print teacher id to remove student.");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String teacherId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(teacherId) && personMap.get(teacherId) instanceof Teacher) {
            LOGGER.info("\n" +"Choose one of students id:");
            List<String> studentInfoList = ((Teacher) personMap.get(teacherId)).getStudentList().stream().map(Student::toString).collect(Collectors.toList());
            for (String student: studentInfoList) {
              LOGGER.info("\n" +student);
            }

            while (!sc.hasNextInt()) {
              sc.next();
            }
            String studentId = String.valueOf(sc.nextInt());

            if (((Teacher) personMap.get(teacherId)).getStudentList().stream().map(Student::getId).collect(Collectors.toList()).contains(studentId)) {
              //TODO check if teachers more than 1
              ((Student) personMap.get(studentId)).deleteTeacher(teacherId);
              deleteStudentFromTeacher((Teacher) personMap.get(teacherId), studentId, ((Student) personMap.get(studentId)).isStipend());
              LOGGER.info("\n" +"Successful delete student from teacher.");
            } else {
             LOGGER.info("\n" +"Teacher dont have student with such id: " + studentId);
            }
          } else {
            LOGGER.info("\n" +NO_TEACHER_WITH_SUCH_ID + teacherId);
          }
          break;
        }
        case 12 : {
          LOGGER.info("\n" +"Student list:");
          studentListPrint(personMap);
          break;
        }
        case 13 : {
          LOGGER.info("\n" +"Teacher list:");
          teacherListPrint(personMap);
          break;
        }
        case 14 : {
          //TODO person list
          LOGGER.info("\n" +"Print person id to view his salary.");

          while (!sc.hasNextInt()) {
            sc.next();
          }
          String personId = String.valueOf(sc.nextInt());

          if (personMap.containsKey(personId)) {
            if (personMap.get(personId) instanceof Student) {
              Student student = (Student) personMap.get(personId);
              if (student.isStipend()) {
                student.calculateSalary();
                LOGGER.info("\n" +student + ". Actual stipend: " + student.getSalary());
              } else {
                LOGGER.info("\n" +student + ". Doesn't have stipend.");
              }
            } else {
              Teacher teacher = (Teacher) personMap.get(personId);
              teacher.calculateSalary();
              LOGGER.info("\n" +teacher + ". Actual salary " + teacher.getSalary() + ".\nStudent with stipend: " + teacher.getStudentsWithStipend());
            }
          } else {
            LOGGER.info("\n" +NO_PERSON_WITH_SUCH_ID + personId);
          }

          break;
        }
        case 15 : {
          int stipendSum = personMap.entrySet().stream().filter(Student.class::isInstance).mapToInt(student -> ((Student) student).getSalary()).sum();
          int teacherSalarySum = personMap.entrySet().stream().filter(Teacher.class::isInstance).mapToInt(teacher -> ((Teacher) teacher).getSalary()).sum();
          LOGGER.info("\n" +"Total financial expenses:\nStipend: " + stipendSum + "\nSalary: " + teacherSalarySum + "\nTotal expenses: " + stipendSum + teacherSalarySum);
          break;
        }
        case 16 : {
          //TODO Teacher list
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
          break;
        }
        case 17: {
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
              break;
            }

            LOGGER.info("\n" +"Student subjects:\n" + subjectList.stream().map(Subject::toString).collect(Collectors.joining("\n")));
            LOGGER.info("\n" +"Print subject name to edit");
            String subjectName = sc.next();

            Optional<Subject> optionalSubject = subjectList.stream().filter(subject -> subjectName.equals(subject.getSubjectName())).findAny();

            if (optionalSubject.isEmpty()) {
              LOGGER.info("\n" +"No subject with such name.");
              break;
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

            float averageMark = (float) student.getSubjectList().stream().mapToInt(Subject::getMark).sum()/(float) student.getSubjectList().size();
            LOGGER.info("\n" +"Actual average mark is : " + averageMark);

            student.setStipend(averageMark <= 2);

            LOGGER.info("\n" +subjectName + " has successfully update.");
          } else {
            LOGGER.info("\n" +NO_STUDENT_WITH_SUCH_ID + studentId);
          }
          break;
        }
        case 18: {
          studentListPrint(personMap);
          teacherListPrint(personMap);
          break;
        }
        case 19 : {
          LOGGER.info("\n" +"Ending university process.");
          endProcess = true;
          break;
        }
        default : {
          LOGGER.info("\n" +"No such option with number: " + choice);
        }
      }
    }
    sc.close();
    LOGGER.info("\n" +DELIMITER);
  }

  private void loggerSetting() {
    // Create custom handler
    LOGGER.setUseParentHandlers(false);
    Handler handler = new Handler() {
      @Override
      public void publish(LogRecord record) {
        if (isLoggable(record)) {
          String message = getFormatter().format(record);
          System.out.print(message);
        }
      }

      @Override
      public void flush() {
      }

      @Override
      public void close() throws SecurityException {
      }
    };

    // Set custom log formatter on the handler
    handler.setFormatter(new Formatter() {
      @Override
      public String format(LogRecord record) {
        return record.getMessage() + System.lineSeparator();
      }
    });

    // Add custom handler to the LOGGER instance
    LOGGER.addHandler(handler);
  }

  private void addTeacherAndStudentLink(Map<String, Person> personMap, String studentId, String teacherId) {
    ((Teacher) personMap.get(teacherId)).addStudent((Student) personMap.get(studentId));
    ((Student) personMap.get(studentId)).addTeacher((Teacher) personMap.get(teacherId));
  }

  private void teacherListPrint(Map<String, Person> personMap) {
    List<Person> teacherList = personMap.values().stream()
        .filter(Teacher.class::isInstance)
        .collect(Collectors.toList());

    for (Person teacher: teacherList) {
      LOGGER.info("\n" +teacher.getName() + " " + teacher.getSurname() + " : " + teacher.getId());
    }
  }

  private void studentListPrint(Map<String, Person> personMap) {
    List<Person> studentList = personMap.values().stream()
        .filter(Student.class::isInstance)
        .collect(Collectors.toList());

    for (Person student: studentList) {
      LOGGER.info("\n" +student.getName() + " " + student.getSurname() + " : " + student.getId());
    }
  }

  private String generateId(Map<String, Person> personMap) {
    Random random = new Random();

    String id = String.valueOf(random.nextInt(9) + 1 + random.nextDouble()).replace(".", "").replace("-", "").substring(0, 8);

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
