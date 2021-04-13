package edu.ucalgary.ensf409;

public class Filing 
{
	private String id;
	private String type;
	private char rails;
	private char drawers;
	private char cabinet;
	private int price;
	private String manuID;
	
	public Filing (String id, String type, char rails, char drawers, char cabinet, int price, String manuID)
	{
		setID (id);
		setType (type);
		setRails (rails);
		setDrawers (drawers);
		setCabinet (cabinet);
		setPrice (price);
		setManuID (manuID);
	}
	
	public void setID (String id)
	{
		this.id = id;
	}
	
	public void setType (String type)
	{
		this.type = type;
	}
	
	public void setRails (char rails)
	{
		this.rails = rails;
	}
	
	public void setDrawers (char drawers)
	{
		this.drawers = drawers;
	}
	
	public void setCabinet (char cabinet)
	{
		this.cabinet = cabinet;
	}
	
	public void setPrice (int price)
	{
		this.price = price;
	}
	
	public void setManuID (String manuID)
	{
		this.manuID = manuID;
	}
	
	public String getID () 
	{
		return this.id;
	}
	
	public String getType () 
	{
		return this.type;
	}
	
	public char getRails () 
	{
		return this.rails;
	}
	
	public char getDrawers () 
	{
		return this.drawers;
	}
	
	public char getCabinet () 
	{
		return this.cabinet;
	}
	
	public int getPrice () 
	{
		return this.price;
	}
	
	public String getManuID () 
	{
		return this.manuID;
	}
}