/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.datev.services.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;

/**
 *
 * @author TG00030
 */
public class TestHelper
{

    public static List<HashMap<String, String>> CreateShoppinglistSqlResultHashMap(String id)
    {
        String[] shoppinglistKeys = new String[]
        {
            "shoppinglist_id_pk",
            "shoppinglist_name_nn",
            "shoppinglist_beschreibung",
            "shoppinglist_color",
            "shoppinglist_user_fk"
        };

        List<HashMap<String, String>> resultListHashMap = new ArrayList<HashMap<String, String>>();

        if (id.equals("1") || id.equals("all"))
        {
            resultListHashMap.add(FillHashMapWithKeysAndValues(shoppinglistKeys, new String[]
            {
                "1",
                "Einkaufsliste",
                "Lebensmittel",
                "btn-success",
                "1"
            }));
        }
        if (id.equals("5") || id.equals("all"))
        {
            resultListHashMap.add(FillHashMapWithKeysAndValues(shoppinglistKeys, new String[]
            {
                "5",
                "Neuer Computer",
                "Alles für den neuen Computer",
                "btn-info",
                "1"
            }));
        }
        return resultListHashMap;
    }

    private static HashMap<String, String> FillHashMapWithKeysAndValues(String[] keys, String[] values)
    {
        HashMap<String, String> resultHashMap = new HashMap<String, String>();

        for (int i = 0; i < keys.length; i++)
        {
            resultHashMap.put(keys[i], values[i]);
        }

        return resultHashMap;
    }

    public static List<ShoppingListModel> CreateMockedServerShoppinglistResult(String id)
    {
        List<ShoppingListModel> expResult = new ArrayList<ShoppingListModel>();

        if (id.equals("1") || id.equals("all"))
        {
            ShoppingListModel einkaufsliste = new ShoppingListModel(1, "Einkaufsliste", "Lebensmittel", "btn-success");
            List<ItemModel> einkaufslisteItems = new ArrayList<>();

            einkaufslisteItems.addAll(CreateMockedServerItemResult("1"));
            einkaufslisteItems.addAll(CreateMockedServerItemResult("2"));
            einkaufslisteItems.addAll(CreateMockedServerItemResult("11"));
            einkaufslisteItems.addAll(CreateMockedServerItemResult("12"));

            einkaufsliste.setItems(einkaufslisteItems);

            expResult.add(einkaufsliste);
        }
        if (id.equals("5") || id.equals("all"))
        {
            ShoppingListModel neuerComputer = new ShoppingListModel(5, "Neuer Computer", "Alles für den neuen Computer", "btn-info");
            List<ItemModel> neuerComputerItems = new ArrayList<>();

            neuerComputerItems.addAll(CreateMockedServerItemResult("25"));
            neuerComputerItems.addAll(CreateMockedServerItemResult("26"));
            neuerComputerItems.addAll(CreateMockedServerItemResult("27"));
            neuerComputerItems.addAll(CreateMockedServerItemResult("40"));

            neuerComputer.setItems(neuerComputerItems);

            expResult.add(neuerComputer);
        }

        return expResult;
    }

    public static List<ItemModel> CreateMockedServerItemResult(String id)
    {
        List<ItemModel> expResult = new ArrayList<ItemModel>();

        if (id.equals("1") || id.equals("all"))
        {
            expResult.add(new ItemModel(1, "Milch", 1, "Einkaufsliste", Timestamp.valueOf("2014-08-10 10:11:55").getTime(), 0.45, true, "TestUser", SQLHelper.CheckNotificationStatus(Timestamp.valueOf("2014-08-10 10:11:55").getTime(), true)));
        }
        if (id.equals("2") || id.equals("all"))
        {
            expResult.add(new ItemModel(2, "Eier", 1, "Einkaufsliste", Timestamp.valueOf("2015-08-10 10:11:55").getTime(), 1.89, true, "TestUser", SQLHelper.CheckNotificationStatus(Timestamp.valueOf("2015-08-10 10:11:55").getTime(), true)));
        }
        if (id.equals("11") || id.equals("all"))
        {
            expResult.add(new ItemModel(11, "Maoam", 1, "Einkaufsliste", Timestamp.valueOf("2015-07-17 10:11:55").getTime(), 1, false, "TestUser", SQLHelper.CheckNotificationStatus(Timestamp.valueOf("2015-07-17 10:11:55").getTime(), false)));
        }
        if (id.equals("12") || id.equals("all"))
        {
            expResult.add(new ItemModel(12, "Chips", 1, "Einkaufsliste", Timestamp.valueOf("2015-07-17 10:11:55").getTime(), 1.79, false, "TestUser", SQLHelper.CheckNotificationStatus(Timestamp.valueOf("2015-07-17 10:11:55").getTime(), false)));
        }
        if (id.equals("25") || id.equals("all"))
        {
            expResult.add(new ItemModel(25, "Pc", 5, "Neuer Computer", Timestamp.valueOf("2024-12-20 15:00:00").getTime(), 800, false, "TestUser", SQLHelper.CheckNotificationStatus(Timestamp.valueOf("2024-12-20 15:00:00").getTime(), false)));
        }
        if (id.equals("26") || id.equals("all"))
        {
            expResult.add(new ItemModel(26, "Bildschirm", 5, "Neuer Computer", Timestamp.valueOf("2024-12-20 15:00:00").getTime(), 299.95, false, "TestUser", SQLHelper.CheckNotificationStatus(Timestamp.valueOf("2024-12-20 15:00:00").getTime(), false)));
        }
        if (id.equals("27") || id.equals("all"))
        {
            expResult.add(new ItemModel(27, "Maus", 5, "Neuer Computer", Timestamp.valueOf("2024-12-20 15:00:00").getTime(), 20, true, "TestUser", SQLHelper.CheckNotificationStatus(Timestamp.valueOf("2024-12-20 15:00:00").getTime(), true)));
        }
        if (id.equals("40") || id.equals("all"))
        {
            expResult.add(new ItemModel(40, "Touchbildschirm", 5, "Neuer Computer", Timestamp.valueOf("2012-08-20 15:00:00").getTime(), 450, false, "TestUser", SQLHelper.CheckNotificationStatus(Timestamp.valueOf("2012-08-20 15:00:00").getTime(), false)));
        }

        return expResult;
    }

    public static void CompareTwoHashMaps(HashMap<String, String> expResult, HashMap<String, String> result)
    {
        Set<String> keys = expResult.keySet();

        for (String currentKey : keys)
        {
            assertEquals("Der Wert in den Hash Maps ist unterschiedlich", expResult.get(currentKey), result.get(currentKey));
        }
    }

    public static void CompareTwoItemModels(ItemModel expResult, ItemModel result)
    {
        assertEquals("ItemId ist unerschiedlich", expResult.getItemId(), result.getItemId());
        assertEquals("ItemName ist unerschiedlich", expResult.getItemName(), result.getItemName());
        assertEquals("ListenId ist unerschiedlich", expResult.getListenId(), result.getListenId());
        assertEquals("Listenname ist unerschiedlich", expResult.getListenname(), result.getListenname());
        assertEquals("Fälligkeitsdatum ist unerschiedlich", expResult.getFälligkeitsdatum(), result.getFälligkeitsdatum());
        assertEquals("Preis ist unerschiedlich", expResult.getPreis(), result.getPreis(), 0);
        assertEquals("Gekauft ist unerschiedlich", expResult.getGekauft(), result.getGekauft());
        assertEquals("Käufer ist unerschiedlich", expResult.getKäufer(), result.getKäufer());
        assertEquals("Notification ist unerschiedlich", expResult.getNotification(), result.getNotification());
    }

    public static void CompareTwoShoppingListModels(ShoppingListModel expResult, ShoppingListModel result)
    {
        assertEquals("ID ist unerschiedlich", expResult.getID(), result.getID());
        assertEquals("Name ist unerschiedlich", expResult.getName(), result.getName());
        assertEquals("Beschreibung ist unerschiedlich", expResult.getBeschreibung(), result.getBeschreibung());
        assertEquals("Color ist unerschiedlich", expResult.getColor(), result.getColor());
    }

    public static void CompareTwoDatabaseResults(List<ShoppingListModel> expResult, List<ShoppingListModel> result)
    {
        if (expResult.size() == result.size())
        {
            for (int i = 0; i < expResult.size(); i++)
            {
                CompareTwoShoppingListModels(expResult.get(i), result.get(i));
                CompareTwoDatabaseItemListResults(expResult.get(i).getItems(), result.get(i).getItems());
            }
        } else
        {
            assertEquals("Die Listenanzahl ist unterschiedlich", expResult.size(), result.size());
        }
    }

    public static void CompareTwoDatabaseItemListResults(List<ItemModel> expResult, List<ItemModel> result)
    {
        if (expResult.size() == result.size())
        {
            for (int i = 0; i < expResult.size(); i++)
            {
                CompareTwoItemModels(expResult.get(i), result.get(i));
            }
        } else
        {
            assertEquals("Die Listenanzahl ist unterschiedlich", expResult.size(), result.size());
        }
    }
}
