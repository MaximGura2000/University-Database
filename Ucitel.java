import java.util.ArrayList;
import java.util.List;

public class Ucitel extends Osoba implements Comparable {

	private int ID;
	private String jmeno;
	private String prijmeni;
	private int rokNarozeni;
	private float plat=10000;
	public int studentySeStipendiem = 0;
	List<Student> student = new ArrayList<Student>();
	
	public void setID(int cislo)
	{
		this.ID = cislo+1000;
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
	
	public boolean chekStudent( int chekID)
	{
		for(Student chekStudent: student)
		{
			if( chekStudent.getID() == chekID)
				return true;
		}
		
		return false;
	}
	
	public void addStudent(Student newStudent)
	{
		student.add(newStudent);
	}
	
	public void deleteStudent(int studentID)
	{
		for(Student studOsoba: student)
		{
			if( studOsoba.getID() == studentID)
				{
					student.remove(studOsoba);
					break;
				}
		}
		System.out.println("Student byl odstranen od ucitele!");
	}
	
	public void vypisStudentu()
	{
		for (Student allStudents : student)
		{
			System.out.println(allStudents.getJmeno() + " " +  allStudents.getPrijmeni() + " s ID:" +  allStudents.getID());
		}
	}
	
	@Override
	public int compareTo(Object o) {
		int comparePocetStudentu = ((Ucitel)o).student.size();
		return   (comparePocetStudentu) - this.student.size();
	}
	
	public void setPlat()
	{
		for( Student studentChek: student)
		{
			if(studentChek.stipendium)
			{
				studentySeStipendiem++;
			}
		}
		this.plat = (int)(10000 + studentySeStipendiem * 1500);
	}
	
	public int getPlat()
	{
		return (int)this.plat;
	}
}
