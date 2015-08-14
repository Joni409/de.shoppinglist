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

    
    private int ItemId;

    public int getItemId() {
        return ItemId;
    }
    

    public void setItemId(int itemId) {
        this.ItemId = itemId;
    }

    private String ItemName;

    public String getItemName() {
        return ItemName;
    }
    
    public void setItemName(String itemName){
        this.ItemName = itemName;
    }
    
    private int ListenId;

    public int getListenId() {
        return ListenId;
    }
    
    public void setListenId(int listeName){
        this.ListenId = listeName;
    }
    
    private String Listenname;

    public String getListenname() {
        return Listenname;
    }
    
    public void setListenname(String listenname){
        this.Listenname = listenname;
    }
    
    private long Fälligkeitsdatum;

    public long getFälligkeitsdatum() {
        return Fälligkeitsdatum;
    }
    
    public void setFälligkeitsdatum(long fälligkeitsdatum){
        this.Fälligkeitsdatum = fälligkeitsdatum;
    }
    
    private double Preis;

    public double getPreis() {
        return Preis;
    }
    
    public void setPreis(double preis){
        this.Preis = preis;
    }
    
    private boolean Gekauft;

    public boolean getGekauft() {
        return Gekauft;
    }
    
    public void setGekauft(boolean gekauft){
        this.Gekauft = gekauft;
    }
    
    private String Käufer;

    public String getKäufer() {
        return Käufer;
    }
    
    public void setKäufer(String käufer){
        this.Käufer = käufer;
    }
    
    private boolean Notification;

    public boolean getNotification() {
        return Notification;
    }
    
    public void setNotification(boolean notification){
        this.Notification = notification;
    }
    
    public ItemModel(int itemId, String itemName, int listenId, String listenName, long fälligkeitsdatum, double preis, boolean gekauft, String käufer, boolean notification){
        this.ItemId = itemId;
        this.ItemName = itemName;
        this.ListenId = listenId;
        this.Listenname = listenName;
        this.Fälligkeitsdatum = fälligkeitsdatum;
        this.Preis = preis;
        this.Gekauft = gekauft;
        this.Käufer = käufer;
        this.Notification = notification;
    }
}
