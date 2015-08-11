/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.datev.services.models;

import static de.datev.services.restful.config.ApplicationConfiguration.Sql;

/**
 *
 * @author TG00023
 */
public class UserModel
{
	private int ID;
	private String UserName;
	private String Password;
	
	private void setID(int ID)
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
	
	private void setPassword(String Password)
	{
		this.Password = Password;
	}
	
	private String getPassword()
	{
		return this.Password;
	}
	
	public void registerUser(String UserName, String Password)
	{
		String[] values = {UserName, Password};
		
		Sql.insert("users", values);
	}
}