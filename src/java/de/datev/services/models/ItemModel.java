/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.datev.services.models;

import java.util.Date;

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
    
    private int Liste;

    public int getListe() {
        return Liste;
    }
    
    public void setListe(int liste){
        this.Liste = liste;
    }
    
    private String Einkaufsdatum;

    public String getEinkaufsdatum() {
        return Einkaufsdatum;
    }
    
    public void setEinkaufsdatum(String einkaufsdatum){
        this.Einkaufsdatum = einkaufsdatum;
    }
    
    private String Preis;

    public String getPreis() {
        return Preis;
    }
    
    public void setPreis(String preis){
        this.Preis = preis;
    }
    
    private String Gekauft;

    public String getGekauft() {
        return Gekauft;
    }
    
    public void setGekauft(String gekauft){
        this.Gekauft = gekauft;
    }
    
    private String Erlediger;

    public String getErlediger() {
        return Erlediger;
    }
    
    public void setErlediger(String erlediger){
        this.Erlediger = erlediger;
    }
    
    private boolean Notification;

    public boolean getNotification() {
        return Notification;
    }
    
    public void setNotification(boolean notification){
        this.Notification = notification;
    }
    
    public ItemModel(){
        
    }
    
    public ItemModel(int id, String name, int liste, String einkaufsdatum, String preis, String gekauft, String erlediger, boolean notification){
        this.ID = id;
        this.Name = name;
        this.Liste = liste;
        this.Einkaufsdatum = einkaufsdatum;
        this.Preis = preis;
        this.Gekauft = gekauft;
        this.Erlediger = erlediger;
        this.Notification = notification;
    }
}
