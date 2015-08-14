/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.datev.services.models;

import static de.datev.services.restful.config.ApplicationConfiguration.Sql;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author TG00030
 */
public class SQLHelper{
    public static boolean CheckNotificationStatus(long createTime, boolean gekauft)
    {
        Calendar currentDay = Calendar.getInstance();
        
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
    
    public static ItemModel FillItemModel(HashMap<String, String> itemRowData) throws SQLException
    {
        int itemId = Integer.parseInt(itemRowData.get("item_id_pk"));
        String itemName = itemRowData.get("item_name_nn");
        int listenId = Integer.parseInt(itemRowData.get("item_shoppinglist_fk"));
        
        List<HashMap<String, String>> listenNameResult = SQLHelper.select("shoppinglist", "shoppinglist_id_pk", String.valueOf(listenId));
        String listenname = listenNameResult.get(0).get("shoppinglist_name_nn");
        
        Timestamp fälligkeitsdatum = Timestamp.valueOf(itemRowData.get("item_createDate"));
        double preis = Double.parseDouble(itemRowData.get("item_preis"));
        boolean gekauft = itemRowData.get("item_gekauft").equals("1");
        
        int userId = Integer.parseInt(itemRowData.get("item_user_fk"));
        List<HashMap<String, String>> userNameResult = SQLHelper.select("user", "user_id_pk", String.valueOf(userId));
        
        String username = null;
        
        if(userNameResult.size() == 1) 
        {
            username = userNameResult.get(0).get("user_name");
        }
        else
        {
            username = "Fehler beim User abruf";
        }
        
        boolean notificationStatus = SQLHelper.CheckNotificationStatus(fälligkeitsdatum.getTime(), gekauft);
        ItemModel filledItemModel = new ItemModel(
                itemId, 
                itemName, 
                listenId, 
                listenname, 
                fälligkeitsdatum.getTime(),
                preis,
                gekauft, 
                username, 
                notificationStatus);
        
        return filledItemModel;
    }
    
    public static ShoppingListModel FillShoppingListModel(HashMap<String, String> rowData) throws SQLException
    {
        int id = Integer.parseInt(rowData.get("shoppinglist_id_pk"));
        String name = rowData.get("shoppinglist_name_nn");
        String beschreibung = rowData.get("shoppinglist_beschreibung");
        String color = rowData.get("shoppinglist_color");
        
        ShoppingListModel filledShoppingListModel = new ShoppingListModel(
                id, 
                name,
                beschreibung, 
                color);
        
        return filledShoppingListModel;
    }
    
    public static List<HashMap<String, String>> select(String table) throws SQLException
    {
        ResultSet rs = Sql.select(table);
        return ConvertResultSetToListHashMap(rs);
    }
    
    public static List<HashMap<String, String>> select(String table, String vergleichsspalte, String vergleichswert) throws SQLException
    {
        ResultSet rs = Sql.select(table, vergleichsspalte, vergleichswert);
        return ConvertResultSetToListHashMap(rs);
    }
    
    private static List<HashMap<String, String>> ConvertResultSetToListHashMap(ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        
        List rows = new ArrayList();
        while(rs.next()){
            HashMap<String, String> row = new HashMap<>();
            for(int i = 1;i <= columnCount; i++){
                String columnLabel = rsmd.getColumnLabel(i);
                String columnValue = rs.getString(i);
                row.put(columnLabel, columnValue);
            }
            rows.add(row);
        }
        return rows;
    }
}
