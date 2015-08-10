package de.datev.services.models;

import static de.datev.services.restful.config.ApplicationConfiguration.Sql;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TG00030
 */
public class ItemController {

    public static List<ItemModel> getItems(String listId) {

        List<ItemModel> result = new ArrayList<ItemModel>();
        
        try {
            ResultSet rs = Sql.select("item", "item_shoppinglist_fk", listId);
            while (rs.next()) {
                ItemModel currentShoppingList = new ItemModel(rs.getInt("item_id_pk"), rs.getString("item_name_nn"));
                
                result.add(currentShoppingList);
            }
        } catch (SQLException e) {

        }
        return result;
    }
    
    public static ItemModel getItem(String itemId) {
        ItemModel result = new ItemModel();
        
        try {
            ResultSet rs = Sql.select("item", "item_id_pk", itemId);
            while (rs.next()) {
                ItemModel currentShoppingList = new ItemModel(rs.getInt("item_id_pk"), rs.getString("item_name_nn"));
                result = currentShoppingList;
            }
        } catch (SQLException e) {

        }
        
        
        
        return result;
    }
    
    public static void CreateList(String name, String beschreibung, String color)
    {
        Sql.insert("shoppinglist", new String[]{"", name, beschreibung, color});
    }

    
}
