
public abstract class Osoba {

	private int ID;
	private String jmeno;
	private String prijmeni;
	private int rokNarozeni;
	private float plat;
	
	public abstract void setID(int cislo);
	
	public abstract int getID();
	
	public abstract void setJmeno(String jmeno);
	
	public abstract String getJmeno();
	
	public abstract void setPrijmeni(String prijmeni);
	
	public abstract String getPrijmeni();
	
	public abstract void setRokNarozeni(int rock);
	
	public abstract int getRokNarozeni();
	
	public abstract void setPlat();
	
	public abstract int getPlat();	
}
