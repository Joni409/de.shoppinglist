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
                ItemModel currentItem = new ItemModel(rs.getInt("item_id_pk"), rs.getString("item_name_nn"), rs.getString("item_createDate"), rs.getString("item_preis"), rs.getString("item_gekauft"), "Noch nicht implementiert");
                
                result.add(currentItem);
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
                ItemModel currentItem = new ItemModel(rs.getInt("item_id_pk"), rs.getString("item_name_nn"), rs.getString("item_createDate"), rs.getString("item_preis"), rs.getString("item_gekauft"), "Noch nicht implementiert");
                result = currentItem;
            }
        } catch (SQLException e) {

        }
        
        
        
        return result;
    }
    
    public static void updateItem(String id, String name, String einkaufsdatum, String preis, String gekauft, String erlediger) {
        Sql.update("item", new String[]{"item_name_nn","item_createDate", "item_preis", "item_gekauft"}, new String[]{name, einkaufsdatum, preis, gekauft}, "item_id_pk", id);
    }

    public static void createItem(String name, String einkaufsdatum, String preis, String gekauft, String erlediger) {
        Sql.insert("item", new String[]{"", name, "1", einkaufsdatum, preis, gekauft, "1"});
    }
}
