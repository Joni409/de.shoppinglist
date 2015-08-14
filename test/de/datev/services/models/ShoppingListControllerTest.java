/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.datev.services.models;

import de.datev.services.database.MySql;
import de.datev.services.restful.config.ApplicationConfiguration;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ShoppingListControllerTest {
    
    public ShoppingListControllerTest() {
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
     * Test of getLists method, of class ShoppingListController.
     */
    @Test
    public void testGetLists() {
        System.out.println("getLists");
        List<ShoppingListModel> expResult = TestHelper.CreateMockedServerShoppinglistResult("all");
        
        List<ShoppingListModel> result = ShoppingListController.getLists();
        
        TestHelper.CompareTwoDatabaseResults(expResult, result);
    }
    
    /**
     * Test of getList method, of class ShoppingListController.
     */
    @Test
    public void testGetList() {
        System.out.println("getList");
        int id = 1;
        ShoppingListModel expResult = TestHelper.CreateMockedServerShoppinglistResult("1").get(0);
        ShoppingListModel result = ShoppingListController.getList(id);
        
        TestHelper.CompareTwoShoppingListModels(expResult, result);
    }

    /**
     * Test of CreateList method, of class ShoppingListController.
     */
    @Test
    public void testCreateList() {
        System.out.println("CreateList");
        String name = "JUnit_testCreateList";
        String beschreibung = "Durch einen Unit Test angelegt";
        String color = "btn-warning";
        String user = "1";
        boolean expResult = true;
        boolean result = ShoppingListController.CreateList(name, beschreibung, color, user);
        assertEquals("Liste konnte nicht erstellt werden", expResult, result);
        
        ApplicationConfiguration.Sql.delete("shoppinglist", "shoppinglist_name_nn", name);
    }

    /**
     * Test of deleteList method, of class ShoppingListController.
     */
    @Test
    public void testDeleteList() {
        System.out.println("deleteList");
        int id = 999999;
        boolean expResult = true;
        
        ApplicationConfiguration.Sql.insert("shoppinglist", new String[]
        {
            String.valueOf(id),
            "JUnit_testDeleteList",
            "Durch einen Unit Test angelegt",
            "btn-warning",
            "1"
        });
        
        boolean result = ShoppingListController.deleteList(id);
        assertEquals(expResult, result);
    }
    
}
