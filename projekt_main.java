import java.util.*;

public class projekt_main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		System.out.println("Main projekt class change");
		int idStudenta = 1;
		int idUcitele = 101;
		boolean exit = false;
		int pocetUcitelu = 0;
		int mzda=0;
		
		Map<Integer,Osoba> databaze = new HashMap<Integer,Osoba>();
		Scanner sc = new Scanner(System.in);
		while(exit == false)
		{
			int choise;
			System.out.println("----------------------------------------");
			System.out.println("Vyberte si cislo, co byste chteli udelat: \n\"1\" Pridat studenta do databaze. \n\"2\" Pridat ucitele."
					+ "\n\"3\" Odstranit osobu podle ID." + "\n\"4\" Pridat studentovi predmet." + "\n\"5\" Vypis predmetu studentovi."
					+ "\n\"6\" Pridat ucitele studentovi." + "\n\"7\" Pridat studenta ucitelovi." + "\n\"8\" Vypis vsech ucitelu studentovi." 
					+ "\n\"9\" Vypis vsech studentu ucitelovi." + "\n\"10\" Odstraneni ucitele u studenta." + "\n\"11\" Odstranit studenta u ucitelovi."
					+ "\n\"12\" Vypis studentu." + "\n\"13\" Vypis ucitelu." + "\n\"14\" Vypis platu osoby." + "\n\"15\" Mzdove naklady."
					+ "\n\"16\" Vypis plne informace o osobe." + "\n\"17\" Ukonceni aplikace.");
			System.out.println("----------------------------------------");
			while(!sc.hasNextInt())
			{
				sc.next();
			}
			choise=sc.nextInt();
			switch(choise)
			{
				case 1:
				{
					if (pocetUcitelu == 0)
					{
						System.out.println("Neni mozne pridat studenta, pokud nebyl zadan zadny ucitel!");
						break;
					}
					Student student = new Student();
					System.out.println("Zadejte jmeno, prijmeni a rok narozeni");
					String newJmeno = sc.next();
					String newPrijmeni = sc.next();
					int rockNarozeni = 0;
					while(rockNarozeni > 2003 || rockNarozeni < 1980)
						{
							System.out.println("Rock narozeni musi byt mezi 1980 a 2003!");
							while(!sc.hasNextInt())
							{
								sc.next();
							}
							rockNarozeni = sc.nextInt();
						}				
					student.setJmeno(newJmeno);
					student.setPrijmeni(newPrijmeni);
					student.setID(idStudenta);
					student.setRokNarozeni(rockNarozeni);
					System.out.println("Vyberte si jednoho ucitele. \n Seznam ucitelu");
					for (int uciteli: databaze.keySet())
					{
						if (databaze.get(uciteli) instanceof Ucitel)
							System.out.println(databaze.get(uciteli).getJmeno()+" "+databaze.get(uciteli).getPrijmeni()+" ID:"+databaze.get(uciteli).getID());
					}
					System.out.println("Zadejte ID ucitele");
					int IDUcitele = 0;
					while( IDUcitele < 1101 || IDUcitele> 1000+ idUcitele)
					{
						while(!sc.hasNextInt())
						{
							sc.next();
						}
						IDUcitele = sc.nextInt();
					}
					student.addTeacher((Ucitel)databaze.get(IDUcitele));
					databaze.put(student.getID(),student);
					System.out.println("Student byl ulozen do databazi s ID: " + student.getID());
					((Ucitel)databaze.get(IDUcitele)).addStudent(student);
					idStudenta++;
					break;
				}
				case 2:
				{
					Ucitel ucitel = new Ucitel();
					System.out.println("Zadejte jmeno, prijmeni a rok narozeni ucitele");
					String newJmeno = sc.next();
					String newPrijmeni = sc.next();
					int rockNarozeni = 0;
					while(rockNarozeni > 1995 || rockNarozeni < 1970)
						{
							System.out.println("Rock narozeni musi byt mezi 1970 a 1995!");
							while(!sc.hasNextInt())
							{
								sc.next();
							}
							rockNarozeni = sc.nextInt();
						}
					ucitel.setJmeno(newJmeno);
					ucitel.setPrijmeni(newPrijmeni);
					ucitel.setID(idUcitele);
					ucitel.setRokNarozeni(rockNarozeni);
				
					databaze.put(ucitel.getID(),ucitel);
					System.out.println("Ucitel byl ulozen do databazi s ID: " + ucitel.getID());
					idUcitele ++;
					pocetUcitelu++;
					break;
				}
				case 3:
				{
					System.out.println("Zadejte ID ucitele nebo studenta, ktereho chcete odstranit:");
					while(!sc.hasNextInt())
					{
						sc.next();
					}
					int removeID = sc.nextInt();
					if (databaze.containsKey(removeID))
					{
						if(databaze.get(removeID) instanceof Ucitel)
							pocetUcitelu--;						
						System.out.println(databaze.get(removeID).getJmeno() + " " + databaze.get(removeID).getPrijmeni()+ " bude odstranen z databazi.");
						if(databaze.get(removeID) instanceof Ucitel)
						{
							for (Student students: ((Ucitel)databaze.get(removeID)).student)
							{
								students.deleteUcitel(removeID);
							}
						}
						else if(databaze.get(removeID) instanceof Student)
						{
							for (Ucitel teachers: ((Student)databaze.get(removeID)).ucitel)
							{
								teachers.deleteStudent(removeID);
							}
						}
						
						databaze.remove(removeID);
					}
					else
						System.out.println("Student s ID " + removeID + " jiz neni v databazi");
					break;
				}
				case 4:
				{
					System.out.println("Zadejte ID studenta, kteremu chcete pridat predmet:");
					while(!sc.hasNextInt())
					{
						sc.next();
					}
					int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student)
					{ 
						((Student)databaze.get(workID)).addPredmet();
					}
					else
						System.out.println("Student s ID " + workID + " jiz neni v databazi");
					break;
				}
				case 5:
				{
					System.out.println("Zadejte ID studenta:");
					while(!sc.hasNextInt())
					{
						sc.next();
					}
					int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student)
					{ 
						((Student)databaze.get(workID)).showPredmet();
					}
					else
						System.out.println("Student s ID " + workID + " jiz neni v databazi");
					break;
				}
				case 6:
				{
					System.out.println("Zadejte ID studenta, kteremu chcete pridat ucitele:");
					while(!sc.hasNextInt())
					{
						sc.next();
					}
					int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student)
					{ 
					
						System.out.println("Pocet ucitelu v databazi : " + pocetUcitelu);
						for(int seznamUcitelu: databaze.keySet())
						{
							if( databaze.get(seznamUcitelu) instanceof Ucitel)
								System.out.println(databaze.get(seznamUcitelu).getJmeno()+" "+databaze.get(seznamUcitelu).getPrijmeni()+" ID:"+databaze.get(seznamUcitelu).getID());
						}
						System.out.println("Zadejte ID ucitele, ktereho chcete pridat ke studentovi:");
						while(!sc.hasNextInt())
						{
							sc.next();
						}						
						int IDUcitele = sc.nextInt();
						if (databaze.containsKey(IDUcitele) && databaze.get(IDUcitele) instanceof Ucitel)
						{
							if(!((Student)databaze.get(workID)).chekUcitel(IDUcitele))
							{
								((Student)databaze.get(workID)).addTeacher(((Ucitel)databaze.get(IDUcitele)));
								((Ucitel)databaze.get(IDUcitele)).addStudent(((Student)databaze.get(workID)));
								System.out.println("Ucitel byl pridan ke studentovi");
							}
							else
								System.out.println("Tento ucitel uz pracuje s timto studentem.");
						}
						else
							System.out.println("Ucitel nebyl nalezen");
					}
					else
						System.out.println("Student s ID " + workID + " jiz neni v databazi");
					break;
				}
				case 7:
				{
					System.out.println("Zadejte ID ucitele, kteremu chcete pridat studenta:");
					while(!sc.hasNextInt())
					{
						sc.next();
					}
					int IDUcitele = sc.nextInt();
					if (databaze.containsKey(IDUcitele) && databaze.get(IDUcitele) instanceof Ucitel)
					{ 
					
						for(int seznamStudentu: databaze.keySet())
						{
							if( databaze.get(seznamStudentu) instanceof Student)
								System.out.println(databaze.get(seznamStudentu).getJmeno()+" "+databaze.get(seznamStudentu).getPrijmeni()+" ID:"+databaze.get(seznamStudentu).getID());
						}
						System.out.println("Zadejte ID studenta, ktereho chcete pridat ke ucitelovi:");
						while(!sc.hasNextInt())
						{
							sc.next();
						}						
						int workID = sc.nextInt();
						if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student)
						{
							if(!((Ucitel)databaze.get(IDUcitele)).chekStudent(workID))
							{
								((Student)databaze.get(workID)).addTeacher(((Ucitel)databaze.get(IDUcitele)));
								((Ucitel)databaze.get(IDUcitele)).addStudent(((Student)databaze.get(workID)));
								System.out.println("Student byl pridan ke ucitelovi");
							}
							else
								System.out.println("Tento ucitel uz pracuje s timto studentem.");
						}
						else
							System.out.println("Student nebyl nalezen");
					}
					else
						System.out.println("Ucitel s ID " + IDUcitele + " neni v databazi");
					break;
				}
				case 8:
				{
					System.out.println("Zadejte ID studentu, u ktereho chcete vypsat ucitele");
					while(!sc.hasNextInt())
					{
						sc.next();
					}
					int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student)
					{
						((Student)databaze.get(workID)).vypisUcitelu();
					}
					else
						System.out.println("Student s ID " + workID + " neni v databazi");
					break;
				}
				case 9:
				{
					System.out.println("Zadejte ID ucitele, u ktereho chcete vypsat studenti");
					while(!sc.hasNextInt())
					{
						sc.next();
					}
					int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Ucitel)
					{
						((Ucitel)databaze.get(workID)).vypisStudentu();
					}
					else
						System.out.println("Ucitel s ID " + workID + " neni v databazi");
					break;
				}
				case 10:
				{
					System.out.println("Zadejte ID studenta, u ktereho chcete odstranit ucitele");
					while(!sc.hasNextInt())
					{
						sc.next();
					}
					int workID = sc.nextInt();
					if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student)
					{
						System.out.println("Zadejte ID ucitele, ktereho chcete odstranit");
						while(!sc.hasNextInt())
						{
							sc.next();
						}
						int IDUcitele = sc.nextInt();
						if (databaze.containsKey(IDUcitele) && databaze.get(IDUcitele) instanceof Ucitel)
						{
							((Student)databaze.get(workID)).deleteUcitel(IDUcitele);
							((Ucitel)databaze.get(IDUcitele)).deleteStudent(workID);
						}
						else
							System.out.println("Ucitel nebyl nalezen");
					}
					else
						System.out.println("Student s ID " + workID + " neni v databazi");
					break;
				}
				case 11:
				{
					System.out.println("Zadejte ID ucetele, u ktereho chcete odstranit studenta");
					while(!sc.hasNextInt())
					{
						sc.next();
					}
					int IDUcitele = sc.nextInt();
					if (databaze.containsKey(IDUcitele) && databaze.get(IDUcitele) instanceof Ucitel)
					{
						System.out.println("Zadejte ID studenta, ktereho chcete odstranit");
						while(!sc.hasNextInt())
						{
							sc.next();
						}
						int workID = sc.nextInt();
						if (databaze.containsKey(workID) && databaze.get(workID) instanceof Student)
						{
							
							((Ucitel)databaze.get(IDUcitele)).deleteStudent(workID);
							((Student)databaze.get(workID)).deleteUcitel(IDUcitele);
						}
						else
							System.out.println("Ucitel nebyl nalezen");
					}
					else
						System.out.println("Student s ID " + IDUcitele + " neni v databazi");
					break;
				}
				case 12:
				{
					System.out.println("Seznam studentu:");
					List<Student> seznamStudentu = new ArrayList<Student>();
					for (int osoba: databaze.keySet())
					{
						if( databaze.get(osoba) instanceof Student)
						{
							seznamStudentu.add((Student)databaze.get(osoba));
						}
					}
					Collections.sort(seznamStudentu);
					for(Student vypisStudentu: seznamStudentu)
					{
						System.out.println(vypisStudentu.getPrijmeni() + " " + vypisStudentu.getJmeno() + ". ID : " + vypisStudentu.getID());
					}
					break;
				}
				case 13:
				{
					System.out.println("Seznam Ucitelu:");
					List<Ucitel> seznamUcitelu = new ArrayList<Ucitel>();
					for (int osoba: databaze.keySet())
					{
						if( databaze.get(osoba) instanceof Ucitel)
						{
							seznamUcitelu.add((Ucitel)databaze.get(osoba));
						}
					}
					Collections.sort(seznamUcitelu);
					for(Ucitel vypisUcitelu: seznamUcitelu)
					{
						System.out.println(vypisUcitelu.getPrijmeni() + " " + vypisUcitelu.getJmeno() + ". ID : " + vypisUcitelu.getID() + 
								". Pocet studentu :" + vypisUcitelu.student.size());
					}
					break;
				}
				case 14:
				{
					System.out.println("Zadejte ID osoby, u ktere chcete vypsat plat");
					while(!sc.hasNextInt())
					{
						sc.next();
					}
					int workID = sc.nextInt();
					if (databaze.containsKey(workID))
					{
						if (databaze.get(workID) instanceof Student)
						{
							if(((Student)databaze.get(workID)).stipendium)
							{
								System.out.println(databaze.get(workID).getJmeno()  + " " + databaze.get(workID).getPrijmeni() + ". ID: "
										+ databaze.get(workID).getID() + ". Ma stipendium " + databaze.get(workID).getPlat() + "kc." );
							}
							else
								System.out.println(databaze.get(workID).getJmeno()  + " " + databaze.get(workID).getPrijmeni() + ". ID: "
										+ databaze.get(workID).getID() + ". Student ma nizky prumer a nema stipendium.");
						}
						else if (databaze.get(workID) instanceof Ucitel)
						{
							databaze.get(workID).setPlat();
							System.out.println("Studenty se stipendiem: " + ((Ucitel)databaze.get(workID)).studentySeStipendiem);
							System.out.println(databaze.get(workID).getJmeno()  + " " + databaze.get(workID).getPrijmeni() + ". ID: "
									+ databaze.get(workID).getID() + ". Cista mzda: " + databaze.get(workID).getPlat());
						}
					}
					else
						System.out.println("Osoba s ID " + workID + " neni v databazi!");
					break;
				}
				case 15:
				{
					int stipendium = 0;
					int ucitelum = 0;
					for(int osoba: databaze.keySet())
					{
						mzda = mzda + databaze.get(osoba).getPlat();
						if(databaze.get(osoba) instanceof Student)
							stipendium = stipendium + databaze.get(osoba).getPlat();
						else if(databaze.get(osoba) instanceof Ucitel)
							ucitelum = ucitelum + databaze.get(osoba).getPlat();
					}
					System.out.println("Financni prostredky potrebne k pokryti jednoho mesice: " + mzda + "kc!");
					System.out.println("Z toho stipendium: " + stipendium + "kc!");
					System.out.println("Mzda ucitelum: " + ucitelum + "kc!");
					break;
				}
				case 16:
				{
					System.out.println("Zadejte ID osoby, u ktere chcete vypsat informace");
					while(!sc.hasNextInt())
					{
						sc.next();
					}
					int workID = sc.nextInt();
					if (databaze.containsKey(workID))
					{
						if(databaze.get(workID) instanceof Ucitel)
						{
							databaze.get(workID).setPlat();
							System.out.println(databaze.get(workID).getPrijmeni() + " " + databaze.get(workID).getJmeno() + 
									". ID : " + databaze.get(workID).getID() + ". Rock Narozeni: " + databaze.get(workID).getRokNarozeni() 
									+ ". Plat: " + databaze.get(workID).getPlat() + "kc. \nPocet studentu :" 
									+ ((Ucitel)databaze.get(workID)).student.size());
							for(Student students:  ((Ucitel)databaze.get(workID)).student)
							{
								System.out.println(students.getJmeno() + " " + students.getPrijmeni() + ". ID: " + students.getID());
							}
						}
						else if(databaze.get(workID) instanceof Student)
						{
							System.out.println(databaze.get(workID).getPrijmeni() + " " + databaze.get(workID).getJmeno() + 
									". ID : " + databaze.get(workID).getID() + ". Rock Narozeni: " + databaze.get(workID).getRokNarozeni());
							if (((Student)databaze.get(workID)).stipendium)
								System.out.println("Plat: " + databaze.get(workID).getPlat() + "kc. \nUciteli jsou:");
							else
								System.out.println("Uciteli jsou:");
							for(Ucitel teachers: ((Student)databaze.get(workID)).ucitel)
							{
								System.out.println(teachers.getJmeno() + " " + teachers.getPrijmeni() + ". ID: " + teachers.getID());
							}
						}
					}
					else
						System.out.println("Osoba s ID" + workID + " neni v databazi!");
					break;
				}
				case 17:
				{
					exit = true;
					break;
				}
			}
		}
		sc.close();
		System.out.println("----------------------------------------");
		for (int osoba: databaze.keySet())
		{
			System.out.println("Jmeno a Prijmeni: " + databaze.get(osoba).getJmeno() + " " + databaze.get(osoba).getPrijmeni() + ". Rock narozeni:"+ databaze.get(osoba).getRokNarozeni() +". ID: " + databaze.get(osoba).getID());
		}
	}
}