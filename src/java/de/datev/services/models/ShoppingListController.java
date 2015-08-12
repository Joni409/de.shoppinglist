package de.datev.services.models;

import static de.datev.services.restful.config.ApplicationConfiguration.Sql;
import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
                ShoppingListModel currentShoppingList = Helper.FillShoppingListModel(rs);
                
                ResultSet currentItemResult = Sql.select("item", "item_shoppinglist_fk", String.valueOf(currentShoppingList.getID()));
                List<ItemModel> items = new ArrayList<>();
                while(currentItemResult.next()){
                    ItemModel currentItem = Helper.FillItemModel(currentItemResult);
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
            ResultSet rs = Sql.select("shoppinglist", "shoppinglist_id_pk", String.valueOf(id));
            while (rs.next()) {
                result = Helper.FillShoppingListModel(rs);
                
                ResultSet currentItemResult = Sql.select("item", "item_shoppinglist_fk", result.getID() + "");
                List<ItemModel> items = new ArrayList<>();
                while(currentItemResult.next()){
                    ItemModel currentItem = Helper.FillItemModel(currentItemResult);
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
