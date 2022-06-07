/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Codes;

/**
 *
 * @author KAVINDU PIYUMANTHA
 */
import java.sql.Connection;
import Codes.DBconnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class TableReload {
    
    
    
    Connection con=null;
    PreparedStatement pst;
    ResultSet rs;
    
    
    public TableReload(){
         con = DBconnect.Connect();
    }

    
    
    
}
