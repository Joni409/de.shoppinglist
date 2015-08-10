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
public class ShoppingListController {

    public static List<ShoppingListModel> getLists() {

        List<ShoppingListModel> result = new ArrayList<ShoppingListModel>();

        try {
            ResultSet rs = Sql.select("shoppinglist");
            while (rs.next()) {
                ShoppingListModel currentShoppingList = new ShoppingListModel(rs.getInt("shoppinglist_id_pk"), rs.getString("shoppinglist_name_nn"), rs.getString("shoppinglist_beschreibung"), rs.getString("shoppinglist_color"));
                
                ResultSet currentItemResult = Sql.select("item", "item_shoppinglist_fk", currentShoppingList.getID() + "");
                List<ItemModel> items = new ArrayList<>();
                while(currentItemResult.next()){
                    ItemModel item = new ItemModel(currentItemResult.getInt("item_id_pk"), currentItemResult.getString("item_name_nn"));
                    items.add(item);
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
            ResultSet rs = Sql.select("shoppinglist", "shoppinglist_id_pk", String.valueOf(id));
            while (rs.next()) {
                result = new ShoppingListModel(rs.getInt("shoppinglist_id_pk"), rs.getString("shoppinglist_name_nn"), rs.getString("shoppinglist_beschreibung"), rs.getString("shoppinglist_color"));
                
                ResultSet currentItemResult = Sql.select("item", "item_shoppinglist_fk", result.getID() + "");
                List<ItemModel> items = new ArrayList<>();
                while(currentItemResult.next()){
                    ItemModel item = new ItemModel(currentItemResult.getInt("item_id_pk"), currentItemResult.getString("item_name_nn"));
                    items.add(item);
                }
                result.setItems(items);
            }
        } catch (SQLException e) {

        }
        return result;
    }
    
    public static void CreateList(String[] data)
    {
        Sql.insert("shoppinglist", new String[]{data[0], data[1], data[2]});
    }
}
