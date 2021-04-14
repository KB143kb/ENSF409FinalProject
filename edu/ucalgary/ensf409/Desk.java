package edu.ucalgary.ensf409;


public class Desk 
{
	private String id;
	private String type;
	private String legs;
	private String top; 
	private String drawer;
	private int price;
	private String manuID;
	
	public Desk (String id, String type, String legs, String top, String drawer, int price, String manuID)
	{
		setID (id);
		setType (type);
		setLegs (legs);
		setTop (top);
		setDrawer (drawer);
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
	
	public void setLegs (String legs)
	{
		this.legs = legs;
	}
	
	public void setTop (String top)
	{
		this.top = top;
	}
	
	public void setDrawer (String drawer)
	{
		this.drawer = drawer;
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
	
	public String getLegs () 
	{
		return this.legs;
	}
	
	public String getTop () 
	{
		return this.top;
	}
	
	public String getDrawer () 
	{
		return this.drawer;
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
