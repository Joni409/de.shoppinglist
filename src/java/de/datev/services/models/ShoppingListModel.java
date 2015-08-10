/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.datev.services.models;

import java.util.List;

/**
 *
 * @author TG00030
 */
public class ShoppingListModel {

    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    private String Name;

    public String getName() {
        return Name;
    }
    
    public void setName(String name){
        this.Name = Name;
    }
    
    private String Beschreibung;

    public String getBeschreibung() {
        return Beschreibung;
    }
    
    public void setBeschreibung(String beschreibung){
        this.Beschreibung = beschreibung;
    }
    
    private String Color;

    public String getColor() {
        return Color;
    }
    
    public void setColor(String color){
        this.Color = color;
    }
    
    public ShoppingListModel(){
        
    }
    
    public ShoppingListModel(int id, String name, String beschreibung, String color){
        this.ID = id;
        this.Name = name;
        this.Beschreibung = beschreibung;
        this.Color = color;
    }
    
    public ShoppingListModel(int id, String name, String beschreibung, String color, List<ItemModel> items){
        this.ID = id;
        this.Name = name;
        this.Beschreibung = beschreibung;
        this.Color = color;
        this.Items = items;
    }
    
    private List<ItemModel> Items;
    
    public List<ItemModel> getItems(){
        return Items;
    }
    
    public void setItems(List<ItemModel> items){
        this.Items = items;
    }
}
