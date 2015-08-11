/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.datev.services.models;

import java.util.Calendar;

/**
 *
 * @author TG00030
 */
public class Helper{
    public static boolean CheckNotificationStatus(long createTime, String gekauft)
    {
        Calendar currentDay = Calendar.getInstance();
        currentDay.get(Calendar.DATE);
        Calendar createDay = Calendar.getInstance();
        createDay.setTimeInMillis(createTime);
        
        if(currentDay.get(Calendar.DAY_OF_YEAR) >= createDay.get(Calendar.DAY_OF_YEAR) 
                && currentDay.get(Calendar.YEAR) >= createDay.get(Calendar.YEAR)
                && gekauft.equals("0"))
        {
            return true;
        }
        
        return false;
    }
}
