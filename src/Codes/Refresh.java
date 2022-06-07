/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Codes;

import Interfaces.LibDashborad;
import Codes.DBconnect;
import  Codes.DateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author KAVINDU PIYUMANTHA
 */



public class Refresh {
    
    Connection con=null;
    PreparedStatement pst;
    ResultSet rs;
    con =DBconnect.Connect();
    

    
    public void reserveTableUpdate(){
          int c;

        try {
//            pst = con.prepareStatement("SELECT * FROM reserve where Status = ? ");
//            pst.setString(1,"Reserved");
            
            pst = con.prepareStatement("SELECT * FROM reserve where Status = ? ");
            pst.setString(1, "Reserved");
            
             rs = pst.executeQuery();
            
            ResultSetMetaData rsd;
            rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            //DefaultTableModel d =(DefaultTableModel )BookReserveTable.getModel();
            //d.setRowCount(0);
            
            if(rs.next()==true){
                     Vector< Vector<String>> v1 = new Vector();
                    do{
                                Vector<String> v2 = new Vector();
                                for(int i=1;i<=c;i++){
                    
                                v2.add(rs.getString("Book_id"));
                                v2.add(rs.getString("copy_no"));
                                v2.add(rs.getString("memberID"));
                                v2.add(rs.getString("Reserve_Date"));
                                v2.add(rs.getString("Status"));
                                // v2.add(rs.getString("Price"));   
                             }
                    v1.add(v2);
                    }while(rs.next());
   
                    
                    
                    for(int i=0 ; i< v1.size() ;i++){
                               
                               if (DateTime.ReserveExpDate(v1.get(i).get(3) )){
                                        //change the status to expired and increas available count
                                          String status = "Expired";
                                          pst = con.prepareStatement("update  reserve set Status = ? where  Book_id = ? and copy_no = ?  and memberID = ? and Reserve_Date = ? ");

                                          pst.setString(1, status);
                                          pst.setInt(2, Integer.parseInt(v1.get(i).get(0)));
                                          pst.setInt(3, Integer.parseInt(v1.get(i).get(1)));
                                          pst.setInt(4, Integer.parseInt(v1.get(i).get(2)));
                                          pst.setString(5, v1.get(i).get(3));

                                         pst.executeUpdate();


                                         pst = con.prepareStatement("Update book set avbl_count = avbl_count +1 where book_id =?");

                                          pst.setInt(1, Integer.parseInt(v1.get(i).get(0)));

                                          pst.executeUpdate();
       
                               }          
                    }
                    
                    reserveTableReload();
 
           }

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }     
        
       reserveTableReload();
   }
    
    
    
}
