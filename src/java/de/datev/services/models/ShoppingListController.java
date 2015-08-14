package de.datev.services.models;

import static de.datev.services.restful.config.ApplicationConfiguration.Sql;
import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author TG00030
 */
public class ShoppingListController {

    public static List<ShoppingListModel> getLists() {

        List<ShoppingListModel> result = new ArrayList<ShoppingListModel>();

        try {
            
            List<HashMap<String, String>> resultShoppinglistRows = SQLHelper.select("shoppinglist");
            
            for(HashMap<String, String> currentRow : resultShoppinglistRows)
            {
                ShoppingListModel currentShoppingList = SQLHelper.FillShoppingListModel(currentRow);
                
                List<HashMap<String, String>> resultItemRows = SQLHelper.select("item", "item_shoppinglist_fk", String.valueOf(currentShoppingList.getID()));
                
                List<ItemModel> items = new ArrayList<>();
                
                for(HashMap<String, String> currentItemRow : resultItemRows)
                {
                    ItemModel currentItem = SQLHelper.FillItemModel(currentItemRow);
                    items.add(currentItem);
                }
                
                currentShoppingList.setItems(items);
                
                result.add(currentShoppingList);
            }
        } catch (SQLException e) {

        }
        return result;
    }
    
    public static ShoppingListModel getList(int id)
    {
        ShoppingListModel result = null;

        try {
            List<HashMap<String, String>> resultShoppinglistRows = SQLHelper.select("shoppinglist", "shoppinglist_id_pk", String.valueOf(id));
            
            for (HashMap<String, String> currentShoppinglistRow : resultShoppinglistRows)
            {
                result = SQLHelper.FillShoppingListModel(currentShoppinglistRow);
                
                List<HashMap<String, String>> resultItemRows = SQLHelper.select("item", "item_shoppinglist_fk", String.valueOf(result.getID()));
                
                List<ItemModel> items = new ArrayList<>();
                
                for(HashMap<String, String> currentItemRow : resultItemRows)
                {
                    ItemModel currentItem = SQLHelper.FillItemModel(currentItemRow);
                    items.add(currentItem);
                }
                
                result.setItems(items);
            }
        } catch (SQLException e) {

        }
        return result;
    }
    
    public static boolean CreateList(String name, String beschreibung, String color, String user)
    {
        return Sql.insert("shoppinglist", new String[]{"", name, beschreibung, color, user});
    }

    public static boolean deleteList(int id) {
        return Sql.delete("shoppinglist", "shoppinglist_id_pk", String.valueOf(id));
    }
}
