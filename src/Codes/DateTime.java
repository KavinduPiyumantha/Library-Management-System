/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Codes;


import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date; 
import java.time.LocalDate; 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DateTime { 
    
    
    
     public static LocalDate currentDate(){
         
                LocalDate currentdDate =  LocalDate.now();
                return  currentdDate;
                }
     public static LocalDate reternDate(){
         
         LocalDate currentDate =  LocalDate.now();
         
         LocalDate borwDate =  currentDate.plusDays(14);

          return borwDate;
     }
     
      public static LocalDate StringToLocalDate(String str){

          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

          LocalDate date = LocalDate.parse(str, formatter);
          
          return date;
     }
      
      
      public static boolean ReserveExpDate(String str){
            
          String str1=  str;
          boolean Status = false;
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          LocalDate date = LocalDate.parse(str1, formatter);
          
          //LocalDate expDate = date.plusDays(1);
          
          int dcount = currentDate().compareTo(date);
          
          if(  2 <= dcount){
                Status = true;
          } 
          
          return Status;
     }
      
      
           public static boolean BorrowExpDate(String dueDate){
            
          String duedate =  dueDate;
          boolean Status = false;
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          LocalDate locduedate = LocalDate.parse(duedate, formatter);
          
          //LocalDate expDate = date.plusDays(1);
          
          int dcount = currentDate().compareTo(locduedate);
          
          if(  0 < dcount){
                Status = true;
          } 
          
          return Status;
     }
      
      
     
     }

