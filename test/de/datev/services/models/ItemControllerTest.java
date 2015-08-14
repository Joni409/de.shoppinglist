/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.datev.services.models;

import de.datev.services.database.MySql;
import de.datev.services.restful.config.ApplicationConfiguration;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author TG00030
 */
public class ItemControllerTest
{
    
    public ItemControllerTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        ApplicationConfiguration.Sql = new MySql("192.168.1.70:3306", "root", "root", "shoppinglist");
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of getAllItems method, of class ItemController.
     */
    @Test
    public void testGetAllItems()
    {
        System.out.println("getAllItems");
        List<ItemModel> expResult = TestHelper.CreateMockedServerItemResult("all");
        List<ItemModel> result = ItemController.getAllItems();
        
        TestHelper.CompareTwoDatabaseItemListResults(expResult, result);
    }

    /**
     * Test of getItems method, of class ItemController.
     */
    @Test
    public void testGetItems()
    {
        System.out.println("getItems");
        String listId = "1";
        List<ItemModel> expResult = TestHelper.CreateMockedServerShoppinglistResult(listId).get(0).getItems();
        List<ItemModel> result = ItemController.getItems(listId);
        
        TestHelper.CompareTwoDatabaseItemListResults(expResult, result);
    }

    /**
     * Test of getItem method, of class ItemController.
     */
    @Test
    public void testGetItem()
    {
        System.out.println("getItem");
        String itemId = "1";
        ItemModel expResult = TestHelper.CreateMockedServerItemResult(itemId).get(0);
        ItemModel result = ItemController.getItem(itemId);
        
        TestHelper.CompareTwoItemModels(expResult, result);
    }

    /**
     * Test of updateItem method, of class ItemController.
     */
    @Test
    public void testUpdateItem()
    {
        System.out.println("updateItem");
        HashMap<String, String> updateData = new HashMap<String, String>();
        updateData.put("fälligkeitsdatum", "2015-08-12 00:00:00");
        
        String id = "999999";
        
        ApplicationConfiguration.Sql.insert("item", new String[]
        {
            id,
            "JUnit_testUpdateItem",
            "1",
            "2014-08-10 10:11:55",
            "6.66",
            "0",
            "1"
        });
        
        
        boolean expResult = true;
        boolean result = ItemController.updateItem(updateData, id);
        
        ApplicationConfiguration.Sql.delete("item", "item_id_pk", id);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of createItem method, of class ItemController.
     */
    @Test
    public void testCreateItem()
    {
        System.out.println("createItem");
        String name = "JUnit_testCreateItem";
        String einkaufsdatum = "2015-09-01 00:00:00";
        String preis = "9.95";
        String gekauft = "0";
        String erlediger = "1";
        String id = "1";
        boolean expResult = true;
        boolean result = ItemController.createItem(name, einkaufsdatum, preis, gekauft, erlediger, id);
        
        ApplicationConfiguration.Sql.delete("item", "item_name_nn", name);
        
        assertEquals(expResult, result);
    }
    
}
