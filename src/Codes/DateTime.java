/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Codes;


import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date; 
import java.time.LocalDate; 
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author KAVINDU PIYUMANTHA
 */


public class DateTime {
    
    
    
     public static LocalDate currentDate(){
         
                LocalDate currentdDate =  LocalDate.now();
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//                Date date = new Date(); 
//                String currentDate = formatter.format(date);
                
                return  currentdDate;
                }
     public static LocalDate reternDate(){
         
         LocalDate currentDate =  LocalDate.now();
         
         LocalDate borwDate =  currentDate.plusDays(14);

          return borwDate;
     }
     
     }

