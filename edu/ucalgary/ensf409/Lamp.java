/**
  @author      nathaniel lipura <a href="mailto:nathaniel.lipura@ucalgary.ca">nathaniel.lipura@ucalgary.ca</a>
  @author      kruti bhatt <a href="mailto:kruti.bhatt1@ucalgary.ca">kruti.bhatt1@ucalgary.ca</a>
  @version     1.0
  @since       1.0
*/

package edu.ucalgary.ensf409;


public class Lamp 
{
	private String id;
	private String type;
	private String base;
	private String bulb;
	private int price;
	private String manuID;
	
	public Lamp (String id, String type, String base, String bulb, int price, String manuID)
	{
		setID (id);
		setType (type);
		setBase (base);
		setBulb (bulb);
		setPrice (price);
		setManuID (manuID);
	}
	//Lamp setter methods
	public void setID (String id)
	{
		this.id = id;
	}
	
	public void setType (String type)
	{
		this.type = type;
	}
	
	public void setBase (String base)
	{
		this.base = base;
	}
	
	public void setBulb (String bulb)
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
	//Lamp getter methods
	public String getID () 
	{
		return this.id;
	}
	
	public String getType () 
	{
		return this.type;
	}
	
	public String getBase () 
	{
		return this.base;
	}
	
	public String getBulb () 
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
