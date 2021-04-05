package edu.ucalgary.ensf409;

public class Chair 
{
	private String id;
	private String type;
	private char legs;
	private char arms;
	private char seat;
	private char cushion;
	private int price;
	private String manuID;
	
	public Chair (String id, String type, char legs, char arms, char seat, char cushion, int price, String manuID)
	{
		setID (id);
		setType (type);
		setLegs (legs);
		setArms (arms);
		setSeat (seat);
		setCushion (cushion);
		setPrice (price);
	}
	
	public void setID (String id)
	{
		this.id = id;
	}
	
	public void setType (String type)
	{
		this.type = type;
	}
	
	public void setLegs (char legs)
	{
		this.legs = legs;
	}
	
	public void setArms (char arms)
	{
		this.arms = arms;
	}
	
	public void setSeat (char seat)
	{
		this.seat = seat;
	}
	
	public void setCushion (char cushion)
	{
		this.cushion = cushion;
	}
	
	public void setPrice (int price)
	{
		this.price = price;
	}
	
	public String getID () 
	{
		return this.id;
	}
	
	public String getType () 
	{
		return this.type;
	}
	
	public char getLegs () 
	{
		return this.legs;
	}
	
	public char getArms () 
	{
		return this.arms;
	}
	
	public char getSeat () 
	{
		return this.seat;
	}
	
	public char getCushion () 
	{
		return this.cushion;
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
