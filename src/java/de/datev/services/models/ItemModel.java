/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.datev.services.models;

/**
 *
 * @author TG00030
 */
public class ItemModel {

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
        this.Name = name;
    }
    
    public ItemModel(){
        
    }
    
    public ItemModel(int id, String name){
        this.ID = id;
        this.Name = name;
    }
}
