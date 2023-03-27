package src.abl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;
import src.entity.Person;
import src.entity.Student;
import src.entity.Teacher;

public class ProcessAbl {

  private static final Logger LOGGER = Logger.getLogger("ProcessAbl logger.");
  public static final String DELIMITER = "----------------------------------------";
  private String textBlock = "\"1\" Add student."
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
      + "\n\"17\" End process.";

  public void processStart() {
    LOGGER.info("Start of application!");
    boolean endProcess = false;

    Map<String, Person> personMap = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    while (!endProcess) {
      LOGGER.info(DELIMITER);

      LOGGER.info(textBlock);
      LOGGER.info(DELIMITER);

      while(!sc.hasNextInt()) {
        sc.next();
      }

      int choice = sc.nextInt();
      switch (choice) {
        case 1 : {
          if (personMap.isEmpty() || personMap.containsValue(Teacher.class)) {
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

          LOGGER.info("Choose teacher by id.");
          // TODO add teacher list to view

          int teacherId = -1;

          while (!(personMap.containsKey(String.valueOf(teacherId)) && personMap.get(teacherId) instanceof Teacher)) {
            while (!sc.hasNextInt()) {
              sc.next();
            }
            teacherId = sc.nextInt();
          }

          // Add teacher to student
          student.addTeacher((Teacher) personMap.get(teacherId));

          // Add student to teacher
          ((Teacher) personMap.get(teacherId)).getStudentList().add(student);

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
        case 17 : {
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

  private String generateId(Map<String, Person> personMap) {
    Random random = new Random();

    String id = String.valueOf(random.nextDouble()).replace(".", "").replace("-", "").substring(0, 10);

    if (personMap.containsKey(id)) {
      id = generateId(personMap);
    }

    return id;
  }
}
