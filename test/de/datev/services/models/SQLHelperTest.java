/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.datev.services.models;

import de.datev.services.database.MySql;
import de.datev.services.restful.config.ApplicationConfiguration;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author TG00030
 */
public class SQLHelperTest {
    
    public SQLHelperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        ApplicationConfiguration.Sql = new MySql("192.168.1.70:3306", "root", "root", "shoppinglist");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of CheckNotificationStatus method, of class SQLHelper.
     */
    @Test
    public void testCheckNotificationStatus_PastTimestamp_NotBought() {
        System.out.println("CheckNotificationStatus");
        Calendar currentDay = Calendar.getInstance();
        currentDay.add(Calendar.DATE, -2);
        long fälligkeitsdatum = currentDay.getTimeInMillis();
        boolean gekauft = false;
        boolean expResult = true;
        boolean result = SQLHelper.CheckNotificationStatus(fälligkeitsdatum, gekauft);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCheckNotificationStatus_CurrentTimestamp_NotBought() {
        System.out.println("CheckNotificationStatus");
        Calendar currentDay = Calendar.getInstance();
        currentDay.add(Calendar.DATE, 0);
        long fälligkeitsdatum = currentDay.getTimeInMillis();
        boolean gekauft = false;
        boolean expResult = true;
        boolean result = SQLHelper.CheckNotificationStatus(fälligkeitsdatum, gekauft);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCheckNotificationStatus_FutureTimestamp_NotBought() {
        System.out.println("CheckNotificationStatus");
        Calendar currentDay = Calendar.getInstance();
        currentDay.add(Calendar.DATE, 2);
        long fälligkeitsdatum = currentDay.getTimeInMillis();
        boolean gekauft = false;
        boolean expResult = false;
        boolean result = SQLHelper.CheckNotificationStatus(fälligkeitsdatum, gekauft);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCheckNotificationStatus_PastTimestamp_Bought() {
        System.out.println("CheckNotificationStatus");
        Calendar currentDay = Calendar.getInstance();
        currentDay.add(Calendar.DATE, -2);
        long fälligkeitsdatum = currentDay.getTimeInMillis();
        boolean gekauft = true;
        boolean expResult = false;
        boolean result = SQLHelper.CheckNotificationStatus(fälligkeitsdatum, gekauft);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCheckNotificationStatus_CurrentTimestamp_Bought() {
        System.out.println("CheckNotificationStatus");
        Calendar currentDay = Calendar.getInstance();
        currentDay.add(Calendar.DATE, 0);
        long fälligkeitsdatum = currentDay.getTimeInMillis();
        boolean gekauft = true;
        boolean expResult = false;
        boolean result = SQLHelper.CheckNotificationStatus(fälligkeitsdatum, gekauft);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCheckNotificationStatus_FutureTimestamp_Bought() {
        System.out.println("CheckNotificationStatus");
        Calendar currentDay = Calendar.getInstance();
        currentDay.add(Calendar.DATE, 2);
        long fälligkeitsdatum = currentDay.getTimeInMillis();
        boolean gekauft = true;
        boolean expResult = false;
        boolean result = SQLHelper.CheckNotificationStatus(fälligkeitsdatum, gekauft);
        assertEquals(expResult, result);
    }

    /**
     * Test of FillItemModel method, of class SQLHelper.
     */
    @Test
    public void testFillItemModel() throws Exception {
        System.out.println("FillItemModel");
        HashMap<String, String> itemRowData = new HashMap<String, String>();
        itemRowData.put("item_id_pk","1");
        itemRowData.put("item_name_nn","Milch");
        itemRowData.put("item_shoppinglist_fk","1");
        itemRowData.put("item_createDate","2014-08-10 10:11:55");
        itemRowData.put("item_preis","0.45");
        itemRowData.put("item_gekauft","1");
        itemRowData.put("item_user_fk","1");
        ItemModel expResult = new ItemModel(1, "Milch", 1, "Einkaufsliste", Long.parseLong("1407658315000"), 0.45, true, "TestUser", false);
        ItemModel result = SQLHelper.FillItemModel(itemRowData);
        TestHelper.CompareTwoItemModels(expResult, result);
        
    }

    /**
     * Test of FillShoppingListModel method, of class SQLHelper.
     */
    @Test
    public void testFillShoppingListModel() throws Exception {
        System.out.println("FillShoppingListModel");
        HashMap<String, String> rowData = new HashMap<String, String>();
        rowData.put("shoppinglist_id_pk","1");
        rowData.put("shoppinglist_name_nn","Einkaufsliste");
        rowData.put("shoppinglist_beschreibung","Lebensmittel");
        rowData.put("shoppinglist_color","btn-success");
        rowData.put("shoppinglist_user_fk","1");
        ShoppingListModel expResult = new ShoppingListModel(1, "Einkaufsliste", "Lebensmittel", "btn-success");
        ShoppingListModel result = SQLHelper.FillShoppingListModel(rowData);
        TestHelper.CompareTwoShoppingListModels(expResult, result);
    }
    
    /**
     * Test of Placeholder method, of class SQLHelper.
     */
    @Test
    public void testSelect_String() throws Exception {
        System.out.println("Select 1 Parameter");
        String table = "shoppinglist";
        List<HashMap<String, String>> expResult = TestHelper.CreateShoppinglistSqlResultHashMap("all");
        List<HashMap<String, String>> result = SQLHelper.select(table);
        
        if(expResult.size() == result.size())
        {
            for (int i = 0; i < expResult.size(); i++)
            {
                HashMap<String, String> currentExpResultHashMap = expResult.get(i);
                HashMap<String, String> currentResultHashMap = result.get(i);
                TestHelper.CompareTwoHashMaps(currentExpResultHashMap, currentResultHashMap);
            }
        }
        else
        {
            assertEquals("Die Listenlänge ist unterschiedlich", expResult.size(), result.size());
        }
    }

    /**
     * Test of Placeholder method, of class SQLHelper.
     */
    @Test
    public void testSelect_3args() throws Exception {
        System.out.println("Select 3 Parameter");
        String table = "shoppinglist";
        String vergleichsspalte = "shoppinglist_id_pk";
        String vergleichswert = "1";
        List<HashMap<String, String>> expResult = TestHelper.CreateShoppinglistSqlResultHashMap("1");
        List<HashMap<String, String>> result = SQLHelper.select(table, vergleichsspalte, vergleichswert);
        
        if(expResult.size() == result.size())
        {
            for (int i = 0; i < expResult.size(); i++)
            {
                HashMap<String, String> currentExpResultHashMap = expResult.get(i);
                HashMap<String, String> currentResultHashMap = result.get(i);
                TestHelper.CompareTwoHashMaps(currentExpResultHashMap, currentResultHashMap);
            }
        }
        else
        {
            assertEquals("Die Listenlänge ist unterschiedlich", expResult.size(), result.size());
        }
    }
}
