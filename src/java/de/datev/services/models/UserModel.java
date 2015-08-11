/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.datev.services.models;

/**
 *
 * @author TG00023
 */
public class UserModel
{
	private int ID;
	private String UserName;
	private String Password;
	
	public UserModel(int ID, String UserName, String Password)
	{
		this.ID = ID;
		this.UserName = UserName;
		this.Password = Password;
	}
	
	public UserModel() {}
	
	public void setID(int ID)
	{
		this.ID = ID;
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	public void setUserName(String UserName)
	{
		this.UserName = UserName;
	}
	
	public String getUserName()
	{
		return this.UserName;
	}
	
	public void setPassword(String Password)
	{
		this.Password = Password;
	}
	
	public String getPassword()
	{
		return this.Password;
	}
}