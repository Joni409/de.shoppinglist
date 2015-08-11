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
                ShoppingListModel currentShoppingList = new ShoppingListModel(rs.getInt("shoppinglist_id_pk"), rs.getString("shoppinglist_name_nn"), rs.getString("shoppinglist_beschreibung"), rs.getString("shoppinglist_color"));
                
                ResultSet currentItemResult = Sql.select("item", "item_shoppinglist_fk", currentShoppingList.getID() + "");
                List<ItemModel> items = new ArrayList<>();
                while(currentItemResult.next()){
                    int i = currentItemResult.getInt("item_id_pk");
                    String name = currentItemResult.getString("item_name_nn");
                    String date = currentItemResult.getString("item_createDate");
                    String preis = currentItemResult.getString("item_preis");
                    String gekauft = currentItemResult.getString("item_gekauft");
                    Timestamp createTimestamp = currentItemResult.getTimestamp("item_createDate");
                    boolean notificationStatus = Helper.CheckNotificationStatus(createTimestamp.getTime(), currentItemResult.getString("item_gekauft"));
                    ItemModel currentItem = new ItemModel(i, name, date, preis, gekauft, "Noch nicht implementiert", notificationStatus);
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
                result = new ShoppingListModel(rs.getInt("shoppinglist_id_pk"), rs.getString("shoppinglist_name_nn"), rs.getString("shoppinglist_beschreibung"), rs.getString("shoppinglist_color"));
                
                ResultSet currentItemResult = Sql.select("item", "item_shoppinglist_fk", result.getID() + "");
                List<ItemModel> items = new ArrayList<>();
                while(currentItemResult.next()){
                    Timestamp createTimestamp = currentItemResult.getTimestamp("item_createDate");
                    boolean notificationStatus = Helper.CheckNotificationStatus(createTimestamp.getTime(), currentItemResult.getString("item_gekauft"));
                    ItemModel currentItem = new ItemModel(currentItemResult.getInt("item_id_pk"), currentItemResult.getString("item_name_nn"), currentItemResult.getString("item_createDate"), currentItemResult.getString("item_preis"), currentItemResult.getString("item_gekauft"), "Noch nicht implementiert", notificationStatus);
                    items.add(currentItem);
                }
                result.setItems(items);
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
