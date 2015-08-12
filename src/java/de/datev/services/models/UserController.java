package de.datev.services.models;

import static de.datev.services.restful.config.ApplicationConfiguration.Sql;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController
{
	public void registerUser(String UserName, String Password)
	{
		String[] values = {UserName, Password};
		boolean sqlsuccess = Sql.insert("user", values);
	}
	
	public static UserModel getUser(int UserID)
	{
        UserModel result = new UserModel();

        try 
        {
            ResultSet rs = Sql.select("user", "user_id_pk", String.valueOf(UserID));
            while (rs.next()) 
            {
                UserModel currentItem = new UserModel(rs.getInt("user_id_pk"), rs.getString("user_name"), rs.getString("user_password"));
                result = currentItem;
            }
        } catch (SQLException e) {
            
        }
        
        return result;
	}
}