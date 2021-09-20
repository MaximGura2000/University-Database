import java.util.*;

public class Student extends Osoba implements Comparable {
	
	private int ID;
	private String jmeno;
	private String prijmeni;
	private int rokNarozeni;
	private float plat=0;
	public boolean stipendium;
	float prumer;
	int pocetPredmetu =0;
	int soucetZnamek = 0;
	Scanner sc = new Scanner(System.in);
	List<Predmet> predmet = new ArrayList<Predmet>();
	List<Ucitel> ucitel = new ArrayList<Ucitel>();
	
	public void setID(int cislo)
	{
		this.ID = cislo;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void setJmeno(String jmeno)
	{
		this.jmeno = jmeno;
	}
	
	public String getJmeno()
	{
		return this.jmeno;
	}
	
	public void setPrijmeni(String prijmeni)
	{
		this.prijmeni = prijmeni;
	}
	
	public String getPrijmeni()
	{
		return this.prijmeni;
	}
	
	public void setRokNarozeni(int rock)
	{
		this.rokNarozeni = rock;
	}
	
	public int getRokNarozeni()
	{
		return rokNarozeni;
	}
	
	public boolean chekUcitel( int chekID)
	{
		for(Ucitel chekUcitel: ucitel)
		{
			if( chekUcitel.getID() == chekID)
				return true;
		}
		
		return false;
	}
	
	public void addPredmet()
	{
		System.out.println("Zadejte nazev predmetu: ");
		String nazev = sc.next();
		boolean opakujiciPredmet = false;
		for (Predmet listPredmetu: predmet)
		{
			if (listPredmetu.getNazev().compareTo(nazev) == 0)
				opakujiciPredmet = true;
		}
		if (opakujiciPredmet)
			System.out.println("Student uz takovy predmet ma.");
		else
		{
			int znamka = 0;			
			while (znamka > 4 || znamka < 1)
			{	
				System.out.println("Zadejte znamku predmeta mezi 1 a 4");
				while(!sc.hasNextInt())
				{
					sc.next();
				}
				znamka = sc.nextInt();
			}
			Predmet newPredmet = new Predmet(nazev, znamka);
			predmet.add(newPredmet);		
			pocetPredmetu ++;
			soucetZnamek = soucetZnamek + znamka;
			this.prumer = (float)soucetZnamek / (float)pocetPredmetu;
			newStipendium();
		}
	}
	
	public void showPredmet()
	{
		for(Predmet listPredmetu: predmet)
		{
			System.out.println(listPredmetu.getNazev() + ": znamka - " + listPredmetu.getZnamka());
		}
	}
	
	private void newStipendium()
	{
		System.out.println("Studijni prumer se zmnenil. Aktualni prumer je " + prumer);
		if (this.prumer <= 2)
			stipendium = true;
		else
			stipendium = false;
		setPlat();
	}
	
	public void addTeacher(Ucitel newUcitel)
	{
		ucitel.add(newUcitel);
	}
	
	public void vypisUcitelu()
	{
		for (Ucitel allUcitel : ucitel)
		{
			System.out.println(allUcitel.getJmeno() + " " +  allUcitel.getPrijmeni() + " s ID:" +  allUcitel.getID());
		}
	}
	
	public void deleteUcitel(int IDUcitele)
	{
		for(Ucitel teacher: ucitel)
		{
			if( teacher.getID() == IDUcitele)
				{
					ucitel.remove(teacher);
					break;
				}
		}
		System.out.println("Ucitel byl odstranen od studenta!");
	}
	
 	public void setPlat()
	{
		if(stipendium)
		{
			System.out.println("Student ma dostatecny prumer, aby mel stipendium.");
			plat = (5- prumer) * 1000;
		}
		else
			System.out.println("Student ma prumer mene nez 2, a proto nema stipendium");
	}
	
	public int getPlat()
	{
		if(stipendium)
			return (int)this.plat;
		else
			return 0;
	}

	@Override
	public int compareTo(Object o) {
		String comparePrijmeni = ((Student)o).getPrijmeni();
		return prijmeni.compareTo(comparePrijmeni);
	}
}
