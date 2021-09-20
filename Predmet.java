
public class Predmet {

	private String nazev;
	private int znamka;
	
	Predmet(String nazev, int znamka)
	{
		this.nazev = nazev;
		this.znamka= znamka; 
	}
	
	public void setNazev( String predmet)
	{
		this.nazev = predmet;
	}
	
	public String getNazev()
	{
		return nazev;
	}
	
	public void setZnamka( int hodnoceni)
	{
		this.znamka = hodnoceni;
	}
	
	public int getZnamka()
	{
		return znamka;
	}
}