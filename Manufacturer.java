package edu.ucalgary.ensf409;

public class Manufacturer
{
	String manuID;
	String name;
	String phoneNumber;
	String province;
	
	public Manufacturer (String manuID, String name, String phoneNumber, String province)
	{
		setID (manuID);
		setName (name);
		setPhoneNumber (phoneNumber);
		setProvince (province);
	}
	
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
