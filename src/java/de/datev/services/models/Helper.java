/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.datev.services.models;

import static de.datev.services.restful.config.ApplicationConfiguration.Sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author TG00030
 */
public class Helper{
    public static boolean CheckNotificationStatus(long createTime, boolean gekauft)
    {
        Calendar currentDay = Calendar.getInstance();
        currentDay.get(Calendar.DATE);
        Calendar createDay = Calendar.getInstance();
        createDay.setTimeInMillis(createTime);
        
        if(currentDay.get(Calendar.DAY_OF_YEAR) >= createDay.get(Calendar.DAY_OF_YEAR) 
                && currentDay.get(Calendar.YEAR) >= createDay.get(Calendar.YEAR)
                && !gekauft)
        {
            return true;
        }
        
        return false;
    }
    
    public static ItemModel FillItemModel(ResultSet sqlCurrentResultItem) throws SQLException
    {
        int itemId = sqlCurrentResultItem.getInt("item_id_pk");
        String itemName = sqlCurrentResultItem.getString("item_name_nn");
        int listenId = sqlCurrentResultItem.getInt("item_shoppinglist_fk");
        
        ResultSet listenNameResult = Sql.select("shoppinglist", "shoppinglist_id_pk", String.valueOf(listenId));
        listenNameResult.next();
        String listenname = listenNameResult.getString("shoppinglist_name_nn");
        
        Timestamp fälligkeitsdatum = sqlCurrentResultItem.getTimestamp("item_createDate");
        double preis = sqlCurrentResultItem.getDouble("item_preis");
        boolean gekauft = sqlCurrentResultItem.getBoolean("item_gekauft");
        boolean notificationStatus = Helper.CheckNotificationStatus(fälligkeitsdatum.getTime(), gekauft);
        ItemModel filledItemModel = new ItemModel(
                itemId, 
                itemName, 
                listenId, 
                listenname, 
                String.valueOf(fälligkeitsdatum.getTime()),
                preis,
                gekauft, 
                "Noch nicht implementiert", 
                notificationStatus);
        
        return filledItemModel;
    }
    
    public static ShoppingListModel FillShoppingListModel(ResultSet sqlCurrentResultItem) throws SQLException
    {
        int id = sqlCurrentResultItem.getInt("shoppinglist_id_pk");
        String name = sqlCurrentResultItem.getString("shoppinglist_name_nn");
        String beschreibung = sqlCurrentResultItem.getString("shoppinglist_beschreibung");
        String color = sqlCurrentResultItem.getString("shoppinglist_color");
        
        ShoppingListModel filledShoppingListModel = new ShoppingListModel(
                id, 
                name,
                beschreibung, 
                color);
        
        return filledShoppingListModel;
    }
}
