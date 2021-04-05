package edu.ucalgary.ensf409;


public class Lamp 
{
	private String id;
	private String type;
	private char base;
	private char bulb;
	private int price;
	private String manuID;
	
	public Lamp (String id, String type, char base, char bulb, int price, String manuID)
	{
		setID (id);
		setType (type);
		setBase (base);
		setBulb (bulb);
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
	
	public void setBase (char base)
	{
		this.base = base;
	}
	
	public void setBulb (char bulb)
	{
		this.bulb = bulb;
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
	
	public char getBase () 
	{
		return this.base;
	}
	
	public char getBulb () 
	{
		return this.bulb;
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
