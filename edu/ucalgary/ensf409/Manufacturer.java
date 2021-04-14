/**
  @author      nathaniel lipura <a href="mailto:nathaniel.lipura@ucalgary.ca">nathaniel.lipura@ucalgary.ca</a>
  @author      kruti bhatt <a href="mailto:kruti.bhatt1@ucalgary.ca">kruti.bhatt1@ucalgary.ca</a>
  @version     1.0
  @since       1.0
*/
 
package edu.ucalgary.ensf409;


public class Manufacturer
{
	private String manuID;
	private String name;
	private String phoneNumber;
	private String province;
	
	public Manufacturer (String manuID, String name, String phoneNumber, String province)
	{
		setID (manuID);
		setName (name);
		setPhoneNumber (phoneNumber);
		setProvince (province);
	}
	//Manufacturer setter methods
	public void setID (String id)
	{
		this.manuID = id;
	}
	
	public void setName (String name)
	{
		this.name = name;
	}
	
	public void setPhoneNumber (String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public void setProvince (String province)
	{
		this.province = province;
	}
	//Manufacturer getter methods
	public String getID () 
	{
		return this.manuID;
	}
	
	public String getName () 
	{
		return this.name;
	}
	
	public String getPhoneNumber () 
	{
		return this.phoneNumber;
	}
	
	public String getProvince () 
	{
		return this.province;
	}
}

