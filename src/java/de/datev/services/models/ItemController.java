package de.datev.services.models;

import static de.datev.services.restful.config.ApplicationConfiguration.Sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author TG00030
 */
public class ItemController {

    public static List<ItemModel> getAllItems() {

        List<ItemModel> result = new ArrayList<ItemModel>();
        
        try {
            ResultSet rs = Sql.select("item");
            while (rs.next()) {
                ItemModel currentItem = Helper.FillItemModel(rs);
                result.add(currentItem);
            }
        } catch (SQLException e) {

        }
        return result;
    }
    
    public static List<ItemModel> getItems(String listId) {

        List<ItemModel> result = new ArrayList<ItemModel>();
        
        try {
            ResultSet rs = Sql.select("item", "item_shoppinglist_fk", listId);
            while (rs.next()) {
                ItemModel currentItem = Helper.FillItemModel(rs);
                result.add(currentItem);
            }
        } catch (SQLException e) {

        }
        return result;
    }
    
    public static ItemModel getItem(String itemId) {
        ItemModel result = null;
        
        try {
            ResultSet rs = Sql.select("item", "item_id_pk", itemId);
            while (rs.next()) {
                ItemModel currentItem = Helper.FillItemModel(rs);
                result = currentItem;
            }
        } catch (SQLException e) {

        }
        
        
        
        return result;
    }
    
    public static boolean updateItem(HashMap<String, String> updateData, String id) {
        LinkedHashMap <String, String> mapNamesToSqlStructure = new LinkedHashMap<String, String>();
        mapNamesToSqlStructure.put("item_name_nn", "name");
        mapNamesToSqlStructure.put("item_createDate", "fälligkeitsdatum");
        mapNamesToSqlStructure.put("item_preis", "preis");
        mapNamesToSqlStructure.put("item_gekauft", "gekauft");
        mapNamesToSqlStructure.put("item_user_fk", "einkäufer");
        
        String[] columnNames = new String[updateData.size()];
        String[] columnValues = new String[updateData.size()];
        int index = 0;
        
        for(String sqlKey : mapNamesToSqlStructure.keySet())
        {
            String value = mapNamesToSqlStructure.get(sqlKey);
            
            for(String updateKey : updateData.keySet())
            {
                if(value.equals(updateKey))
                {
                    columnNames[index] = sqlKey;
                    columnValues[index] = updateData.get(updateKey);
                    updateData.remove(updateKey);
                    index++;
                    break;
                }
            }
        }
        
        if(updateData.size() == 0)
        {
            Sql.update("item", columnNames, columnValues, "item_id_pk", id);
            return true;
        }
        
        return false;
        
    }

    public static void createItem(String name, String einkaufsdatum, String preis, String gekauft, String erlediger) {
        Sql.insert("item", new String[]{"", name, "1", einkaufsdatum, preis, gekauft, "1"});
    }
}
