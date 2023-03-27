import java.util.*;
import src.entity.Person;
import src.entity.Student;
import src.entity.Teacher;

public class SubAppRunner {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    System.out.println("Main projekt class change");
    int idStudenta = 1;
    int idUcitele = 101;
    boolean exit = false;
    int pocetUcitelu = 0;
    float mzda = 0;

    Map<Integer, Person> databaze = new HashMap<Integer, Person>();
    Scanner sc = new Scanner(System.in);
    while (exit == false) {
      int choise;
      System.out.println("----------------------------------------");
      System.out.println("Vyberte si cislo, co byste chteli udelat: \n\"1\" Pridat studenta do databaze. \n\"2\" Pridat ucitele."
          + "\n\"3\" Odstranit osobu podle ID." + "\n\"4\" Pridat studentovi predmet." + "\n\"5\" Vypis predmetu studentovi."
          + "\n\"6\" Pridat ucitele studentovi." + "\n\"7\" Pridat studenta ucitelovi." + "\n\"8\" Vypis vsech ucitelu studentovi."
          + "\n\"9\" Vypis vsech studentu ucitelovi." + "\n\"10\" Odstraneni ucitele u studenta." + "\n\"11\" Odstranit studenta u ucitelovi."
          + "\n\"12\" Vypis studentu." + "\n\"13\" Vypis ucitelu." + "\n\"14\" Vypis platu osoby." + "\n\"15\" Mzdove naklady."
          + "\n\"16\" Vypis plne informace o osobe." + "\n\"17\" Ukonceni aplikace.");
      System.out.println("----------------------------------------");
      while (!sc.hasNextInt()) {
        sc.next();
      }
      choise = sc.nextInt();
      switch (choise) {
        case 1: {
          if (pocetUcitelu == 0) {
            System.out.println("Neni mozne pridat studenta, pokud nebyl zadan zadny ucitel!");
            break;
          }
          System.out.println("Zadejte jmeno, prijmeni a rok narozeni");
          String newJmeno = sc.next();
          String newPrijmeni = sc.next();
          int rockNarozeni = 0;
          while (rockNarozeni > 2003 || rockNarozeni < 1980) {
            System.out.println("Rock narozeni musi byt mezi 1980 a 2003!");
            while (!sc.hasNextInt()) {
              sc.next();
            }
            rockNarozeni = sc.nextInt();
          }
          Student student = new Student(String.valueOf(idStudenta), newJmeno, newPrijmeni, rockNarozeni);
          System.out.println("Vyberte si jednoho ucitele. \n Seznam ucitelu");
          for (int uciteli : databaze.keySet()) {
						if (databaze.get(uciteli) instanceof Teacher) {
							System.out.println(databaze.get(uciteli).getName() + " " + databaze.get(uciteli).getSurname() + " ID:" + databaze.get(uciteli).getId());
						}
          }
          System.out.println("Zadejte ID ucitele");
          int IDUcitele = 0;
          while (IDUcitele < 1101 || IDUcitele > 1000 + idUcitele) {
            while (!sc.hasNextInt()) {
              sc.next();
            }
            IDUcitele = sc.nextInt();
          }
          student.addTeacher((Teacher) databaze.get(IDUcitele));
          databaze.put(Integer.decode(student.getId()), student);
          System.out.println("src.entity.Student byl ulozen do databazi s ID: " + student.getId());
          ((Teacher) databaze.get(IDUcitele)).addStudent(student);
          idStudenta++;
          break;
        }
        case 2: {
          System.out.println("Zadejte jmeno, prijmeni a rok narozeni ucitele");
          String newJmeno = sc.next();
          String newPrijmeni = sc.next();
          int rockNarozeni = 0;
          while (rockNarozeni > 1995 || rockNarozeni < 1970) {
            System.out.println("Rock narozeni musi byt mezi 1970 a 1995!");
            while (!sc.hasNextInt()) {
              sc.next();
            }
            rockNarozeni = sc.nextInt();
          }
          Teacher teacher = new Teacher(String.valueOf(idUcitele), newJmeno, newPrijmeni, rockNarozeni);

          databaze.put(Integer.decode(teacher.getId()), teacher);
          System.out.println("src.entity.Ucitel byl ulozen do databazi s ID: " + teacher.getId());
          idUcitele++;
          pocetUcitelu++;
          break;
        }
        case 3: {
          System.out.println("Zadejte ID ucitele nebo studenta, ktereho chcete odstranit:");
          while (!sc.hasNextInt()) {
            sc.next();
          }
          int removeID = sc.nextInt();
					if (databaze.containsKey(removeID)) {
						if (databaze.get(removeID) instanceof Teacher) {
							pocetUcitelu--;
						}
						System.out.println(databaze.get(removeID).getName() + " " + databaze.get(removeID).getSurname() + " bude odstranen z databazi.");
						if (databaze.get(removeID) instanceof Teacher) {
							for (Student students : ((Teacher) databaze.get(removeID)).getStudentList()) {
								students.deleteTeacher(String.valueOf(removeID));
							}
						} else if (databaze.get(removeID) instanceof Student) {
							for (Teacher teachers : ((Student) databaze.get(removeID)).getTeacherList()) {
								teachers.deleteStudent(String.valueOf(removeID));
							}
						}

						databaze.remove(removeID);
					} else {
						System.out.println("src.entity.Student s ID " + removeID + " jiz neni v databazi");
					}
          break;
        }
        case 4: {
          System.out.println("Zadejte ID studenta, kteremu chcete pridat predmet:");
          while (!sc.hasNextInt()) {
            sc.next();
          }
          int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student) {
						((Student) databaze.get(workID)).addSubject();
					} else {
						System.out.println("src.entity.Student s ID " + workID + " jiz neni v databazi");
					}
          break;
        }
        case 5: {
          System.out.println("Zadejte ID studenta:");
          while (!sc.hasNextInt()) {
            sc.next();
          }
          int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student) {
						((Student) databaze.get(workID)).showSubject();
					} else {
						System.out.println("src.entity.Student s ID " + workID + " jiz neni v databazi");
					}
          break;
        }
        case 6: {
          System.out.println("Zadejte ID studenta, kteremu chcete pridat ucitele:");
          while (!sc.hasNextInt()) {
            sc.next();
          }
          int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student) {

						System.out.println("Pocet ucitelu v databazi : " + pocetUcitelu);
						for (int seznamUcitelu : databaze.keySet()) {
							if (databaze.get(seznamUcitelu) instanceof Teacher) {
								System.out.println(databaze.get(seznamUcitelu).getName() + " " + databaze.get(seznamUcitelu).getSurname() + " ID:" + databaze.get(seznamUcitelu).getId());
							}
						}
						System.out.println("Zadejte ID ucitele, ktereho chcete pridat ke studentovi:");
						while (!sc.hasNextInt()) {
							sc.next();
						}
						int IDUcitele = sc.nextInt();
						if (databaze.containsKey(IDUcitele) && databaze.get(IDUcitele) instanceof Teacher) {
							if (!((Student) databaze.get(workID)).checkTeacher(String.valueOf(IDUcitele))) {
								((Student) databaze.get(workID)).addTeacher(((Teacher) databaze.get(IDUcitele)));
								((Teacher) databaze.get(IDUcitele)).addStudent(((Student) databaze.get(workID)));
								System.out.println("src.entity.Ucitel byl pridan ke studentovi");
							} else {
								System.out.println("Tento ucitel uz pracuje s timto studentem.");
							}
						} else {
							System.out.println("src.entity.Ucitel nebyl nalezen");
						}
					} else {
						System.out.println("src.entity.Student s ID " + workID + " jiz neni v databazi");
					}
          break;
        }
        case 7: {
          System.out.println("Zadejte ID ucitele, kteremu chcete pridat studenta:");
          while (!sc.hasNextInt()) {
            sc.next();
          }
          int IDUcitele = sc.nextInt();
					if (databaze.containsKey(IDUcitele) && databaze.get(IDUcitele) instanceof Teacher) {

						for (int seznamStudentu : databaze.keySet()) {
							if (databaze.get(seznamStudentu) instanceof Student) {
								System.out.println(databaze.get(seznamStudentu).getName() + " " + databaze.get(seznamStudentu).getSurname() + " ID:" + databaze.get(seznamStudentu).getId());
							}
						}
						System.out.println("Zadejte ID studenta, ktereho chcete pridat ke ucitelovi:");
						while (!sc.hasNextInt()) {
							sc.next();
						}
						int workID = sc.nextInt();
						if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student) {
							if (!((Teacher) databaze.get(IDUcitele)).checkStudent(String.valueOf(workID))) {
								((Student) databaze.get(workID)).addTeacher(((Teacher) databaze.get(IDUcitele)));
								((Teacher) databaze.get(IDUcitele)).addStudent(((Student) databaze.get(workID)));
								System.out.println("src.entity.Student byl pridan ke ucitelovi");
							} else {
								System.out.println("Tento ucitel uz pracuje s timto studentem.");
							}
						} else {
							System.out.println("src.entity.Student nebyl nalezen");
						}
					} else {
						System.out.println("src.entity.Ucitel s ID " + IDUcitele + " neni v databazi");
					}
          break;
        }
        case 8: {
          System.out.println("Zadejte ID studentu, u ktereho chcete vypsat ucitele");
          while (!sc.hasNextInt()) {
            sc.next();
          }
          int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student) {
						((Student) databaze.get(workID)).teacherList();
					} else {
						System.out.println("src.entity.Student s ID " + workID + " neni v databazi");
					}
          break;
        }
        case 9: {
          System.out.println("Zadejte ID ucitele, u ktereho chcete vypsat studenti");
          while (!sc.hasNextInt()) {
            sc.next();
          }
          int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Teacher) {
						((Teacher) databaze.get(workID)).studentList();
					} else {
						System.out.println("src.entity.Ucitel s ID " + workID + " neni v databazi");
					}
          break;
        }
        case 10: {
          System.out.println("Zadejte ID studenta, u ktereho chcete odstranit ucitele");
          while (!sc.hasNextInt()) {
            sc.next();
          }
          int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student) {
						System.out.println("Zadejte ID ucitele, ktereho chcete odstranit");
						while (!sc.hasNextInt()) {
							sc.next();
						}
						int IDUcitele = sc.nextInt();
						if (databaze.containsKey(IDUcitele) && databaze.get(IDUcitele) instanceof Teacher) {
							((Student) databaze.get(workID)).deleteTeacher(String.valueOf(IDUcitele));
							((Teacher) databaze.get(IDUcitele)).deleteStudent(String.valueOf(workID));
						} else {
							System.out.println("src.entity.Ucitel nebyl nalezen");
						}
					} else {
						System.out.println("src.entity.Student s ID " + workID + " neni v databazi");
					}
          break;
        }
        case 11: {
          System.out.println("Zadejte ID ucetele, u ktereho chcete odstranit studenta");
          while (!sc.hasNextInt()) {
            sc.next();
          }
          int IDUcitele = sc.nextInt();
					if (databaze.containsKey(IDUcitele) && databaze.get(IDUcitele) instanceof Teacher) {
						System.out.println("Zadejte ID studenta, ktereho chcete odstranit");
						while (!sc.hasNextInt()) {
							sc.next();
						}
						int workID = sc.nextInt();
						if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student) {

							((Teacher) databaze.get(IDUcitele)).deleteStudent(String.valueOf(workID));
							((Student) databaze.get(workID)).deleteTeacher(String.valueOf(IDUcitele));
						} else {
							System.out.println("src.entity.Ucitel nebyl nalezen");
						}
					} else {
						System.out.println("src.entity.Student s ID " + IDUcitele + " neni v databazi");
					}
          break;
        }
        case 12: {
          System.out.println("Seznam studentu:");
          List<Student> seznamStudentu = new ArrayList<Student>();
          for (int osoba : databaze.keySet()) {
            if (databaze.get(osoba) instanceof Student) {
              seznamStudentu.add((Student) databaze.get(osoba));
            }
          }
          //Collections.sort(seznamStudentu); TODO normal sorting
          for (Student vypisStudentu : seznamStudentu) {
            System.out.println(vypisStudentu.getSurname() + " " + vypisStudentu.getName() + ". ID : " + vypisStudentu.getId());
          }
          break;
        }
        case 13: {
          System.out.println("Seznam Ucitelu:");
          List<Teacher> seznamUcitelu = new ArrayList<Teacher>();
          for (int osoba : databaze.keySet()) {
            if (databaze.get(osoba) instanceof Teacher) {
              seznamUcitelu.add((Teacher) databaze.get(osoba));
            }
          }
          //Collections.sort(seznamUcitelu); TODO normal sorting
          for (Teacher vypisUcitelu : seznamUcitelu) {
            System.out.println(vypisUcitelu.getSurname() + " " + vypisUcitelu.getName() + ". ID : " + vypisUcitelu.getId() +
                ". Pocet studentu :" + vypisUcitelu.getStudentList().size());
          }
          break;
        }
        case 14: {
          System.out.println("Zadejte ID osoby, u ktere chcete vypsat plat");
          while (!sc.hasNextInt()) {
            sc.next();
          }
          int workID = sc.nextInt();
					if (databaze.containsKey(workID)) {
						if (databaze.get(workID) instanceof Student) {
							if (((Student) databaze.get(workID)).isStipend()) {
								System.out.println(databaze.get(workID).getName() + " " + databaze.get(workID).getSurname() + ". ID: "
										+ databaze.get(workID).getId() + ". Ma stipendium " + databaze.get(workID).getSalary() + "kc.");
							} else {
								System.out.println(databaze.get(workID).getName() + " " + databaze.get(workID).getSurname() + ". ID: "
										+ databaze.get(workID).getId() + ". src.entity.Student ma nizky prumer a nema stipendium.");
							}
						} else if (databaze.get(workID) instanceof Teacher) {
							((Teacher) databaze.get(workID)).calculateSalary();
							System.out.println("Studenty se stipendiem: " + ((Teacher) databaze.get(workID)).getStudentsWithStipend());
							System.out.println(databaze.get(workID).getName() + " " + databaze.get(workID).getSurname() + ". ID: "
									+ databaze.get(workID).getId() + ". Cista mzda: " + databaze.get(workID).getSalary());
						}
					} else {
						System.out.println("src.entity.Osoba s ID " + workID + " neni v databazi!");
					}
          break;
        }
        case 15: {
          int stipendium = 0;
          int ucitelum = 0;
          for (int osoba : databaze.keySet()) {
            mzda = mzda + databaze.get(osoba).getSalary();
						if (databaze.get(osoba) instanceof Student) {
							stipendium = (int) (stipendium + databaze.get(osoba).getSalary());
						} else if (databaze.get(osoba) instanceof Teacher) {
							ucitelum = (int) (ucitelum + databaze.get(osoba).getSalary());
						}
          }
          System.out.println("Financni prostredky potrebne k pokryti jednoho mesice: " + mzda + "kc!");
          System.out.println("Z toho stipendium: " + stipendium + "kc!");
          System.out.println("Mzda ucitelum: " + ucitelum + "kc!");
          break;
        }
        case 16: {
          System.out.println("Zadejte ID osoby, u ktere chcete vypsat informace");
          while (!sc.hasNextInt()) {
            sc.next();
          }
          int workID = sc.nextInt();
					if (databaze.containsKey(workID)) {
						if (databaze.get(workID) instanceof Teacher) {
							((Teacher) databaze.get(workID)).calculateSalary();
							System.out.println(databaze.get(workID).getSurname() + " " + databaze.get(workID).getName() +
									". ID : " + databaze.get(workID).getId() + ". Rock Narozeni: " + databaze.get(workID).getBirthYear()
									+ ". Plat: " + databaze.get(workID).getSalary() + "kc. \nPocet studentu :"
									+ ((Teacher) databaze.get(workID)).getStudentList().size());
							for (Student students : ((Teacher) databaze.get(workID)).getStudentList()) {
								System.out.println(students.getName() + " " + students.getSurname() + ". ID: " + students.getId());
							}
						} else if (databaze.get(workID) instanceof Student) {
							System.out.println(databaze.get(workID).getSurname() + " " + databaze.get(workID).getName() +
									". ID : " + databaze.get(workID).getId() + ". Rock Narozeni: " + databaze.get(workID).getBirthYear());
							if (((Student) databaze.get(workID)).isStipend()) {
								System.out.println("Plat: " + databaze.get(workID).getSalary() + "kc. \nUciteli jsou:");
							} else {
								System.out.println("Uciteli jsou:");
							}
							for (Teacher teachers : ((Student) databaze.get(workID)).getTeacherList()) {
								System.out.println(teachers.getName() + " " + teachers.getSurname() + ". ID: " + teachers.getId());
							}
						}
					} else {
						System.out.println("src.entity.Osoba s ID" + workID + " neni v databazi!");
					}
          break;
        }
        case 17: {
          exit = true;
          break;
        }
      }
    }
    sc.close();
    System.out.println("----------------------------------------");
    for (int osoba : databaze.keySet()) {
      System.out.println(
          "Jmeno a Prijmeni: " + databaze.get(osoba).getName() + " " + databaze.get(osoba).getSurname() + ". Rock narozeni:" + databaze.get(osoba).getBirthYear() + ". ID: " + databaze.get(osoba)
              .getId());
    }
  }
}