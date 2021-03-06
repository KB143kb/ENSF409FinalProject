/**
  @author      nathaniel lipura <a href="mailto:nathaniel.lipura@ucalgary.ca">nathaniel.lipura@ucalgary.ca</a>
  @author      kruti bhatt <a href="mailto:kruti.bhatt1@ucalgary.ca">kruti.bhatt1@ucalgary.ca</a>
  @version     1.0
  @since       1.0
*/

package edu.ucalgary.ensf409;

public class Filing 
{
	private String id;
	private String type;
	private String rails;
	private String drawers;
	private String cabinet;
	private int price;
	private String manuID;
	
	public Filing (String id, String type, String rails, String drawers, String cabinet, int price, String manuID)
	{
		setID (id);
		setType (type);
		setRails (rails);
		setDrawers (drawers);
		setCabinet (cabinet);
		setPrice (price);
		setManuID (manuID);
	}
	//Filing setter methods
	public void setID (String id)
	{
		this.id = id;
	}
	
	public void setType (String type)
	{
		this.type = type;
	}
	
	public void setRails (String rails)
	{
		this.rails = rails;
	}
	
	public void setDrawers (String drawers)
	{
		this.drawers = drawers;
	}
	
	public void setCabinet (String cabinet)
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
	//Filing getter methods
	public String getID () 
	{
		return this.id;
	}
	
	public String getType () 
	{
		return this.type;
	}
	
	public String getRails () 
	{
		return this.rails;
	}
	
	public String getDrawers () 
	{
		return this.drawers;
	}
	
	public String getCabinet () 
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
