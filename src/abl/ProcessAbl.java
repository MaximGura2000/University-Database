package src.abl;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import src.entity.Person;
import src.helper.LoggerHelper;
import src.helper.ProcessHelper;

public class ProcessAbl {

  private static final Logger LOGGER = Logger.getLogger("ProcessAbl logger.");

  private final ProcessHelper processHelper = new ProcessHelper();
  private final LoggerHelper loggerHelper = new LoggerHelper();

  public void processStart() throws NoSuchAlgorithmException {
    
    loggerHelper.loggerSetting(LOGGER);

    LOGGER.info("Start of application!");
    boolean endProcess = false;

    Map<String, Person> personMap = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    processHelper.printCommandList();

    while (!endProcess) {
      while(!sc.hasNextInt()) {
        sc.next();
      }

      int choice = sc.nextInt();
      switch (choice) {
        case 1 : {
          processHelper.addStudent(personMap);
          break;
        }
        case 2 : {
          processHelper.addTeacher(personMap);
          break;
        }
        case 3 : {
          processHelper.deletePerson(personMap);
          break;
        }
        case 4 : {
          processHelper.addSubject(personMap);
          break;
        }
        case 5 : {
          processHelper.studentSubjectList(personMap);
          break;
        }
        case 6 : {
          processHelper.addTeacherToStudent(personMap);
          break;
        }
        case 7 : {
          processHelper.addStudentToTeacher(personMap);
          break;
        }
        case 8 : {
          processHelper.studentTeacherList(personMap);
          break;
        }
        case 9 : {
          processHelper.teacherStudentList(personMap);
          break;
        }
        case 10 : {
          processHelper.removeTeacherFromStudent(personMap);
          break;
        }
        case 11 : {
          processHelper.removeStudentFromTeacher(personMap);
          break;
        }
        case 12 : {
          processHelper.studentListPrint(personMap);
          break;
        }
        case 13 : {
          processHelper.teacherListPrint(personMap);
          break;
        }
        case 14 : {
          processHelper.personSalary(personMap);
          break;
        }
        case 15 : {
          processHelper.salaryPayment(personMap);
          break;
        }
        case 16 : {
          processHelper.personInfo(personMap);
          break;
        }
        case 17: {
          processHelper.subjectUpdate(personMap);
          break;
        }
        case 18: {
          processHelper.showPersons(personMap);
          break;
        }
        case 19: {
          processHelper.printCommandList();
          break;
        }
        case 20 : {
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
  }
}
