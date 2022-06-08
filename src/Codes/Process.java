/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Codes;

 import Codes.DBconnect;
import  Codes.DateTime;
import Interfaces.MemberDashborad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

        
        
public class Process {
    
         static Connection con=null;
         static PreparedStatement pst;
         static ResultSet rs;  
         
          static int MemberID;
          static String MemberName;
          
          static String strMemberID;
         
//         con =DBconnect.Connect();
    
    public Process( ){
                
                 con =DBconnect.Connect();
    }
        
    
    
    public static boolean reservebookAsign(String Id){
        
                      String bookId=Id;
                      boolean reserveStatus=false ;
                      boolean borrowstatus=false ;
                      boolean status=false;
                      
                      
           try {
                      pst = con.prepareStatement("SELECT *  FROM  reserve where memberID = ? and Status = ? ");
                      pst.setString(1, strMemberID);
                      pst.setString(2, "Reserved");
                     
                      rs = pst.executeQuery();

                      if(rs.next()==true){
                          reserveStatus= true;
                      }
                      
                      pst = con.prepareStatement("SELECT *  FROM  borrow where memberID = ? and Status = ? ");
                      pst.setString(1, strMemberID);
                      pst.setString(2, "Borrowed");
                     
                      rs = pst.executeQuery();

                      if(rs.next()==true){
                          borrowstatus= true;
                      }

            } catch (SQLException ex) {
                           Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
            }
           
           
           if( reserveStatus ){
                    JOptionPane.showMessageDialog(null, "You Already Reserved a Book!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
           }else if(borrowstatus){
                    JOptionPane.showMessageDialog(null, "You Already Borrowed a Book!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
           }else{
               
                        if ( bookId.equals("")/* txtBookIDSec.getText().equals("")   || txtISBN.getText().equals("") ||  txtBookTitle.getText().equals("") || txtBookCatogery.getSelectedItem().toString().equals("") || txtAuthor.getText().equals("") */ ) {
                            JOptionPane.showMessageDialog(null, "Select a Row !", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                        } else {

                            try {

                                //String bookid = txtBookIDSec.getText();
                                //                String isbnNO   = txtISBN.getText();
                                //                String bookTitle = txtBookTitle.getText();
                                //
                                //                String Author = txtAuthor.getText();
                                //                
                                //                String bookCatogery = txtBookCatogery.getSelectedItem().toString();

                                int ID =Integer.parseInt(bookId);
                                int c;

                                pst = con.prepareStatement("SELECT * FROM  bookcopy where Book_id = ?");
                                pst.setInt(1, ID);
                                //                pst.setString(2, bookTitle);
                                //                pst.setString(3, bookCatogery);
                                //                pst.setString(4, Author);
                                //                pst.setInt(5, ID);

                                rs = pst.executeQuery();

                                ResultSetMetaData rsd;
                                rsd = rs.getMetaData();
                                c = rsd.getColumnCount();

                                Vector<String> bCopySet = new Vector<String>();

                                int avblCopyNo = 0 ;

                                if( rs.next()==false){
                                    JOptionPane.showMessageDialog(null, "Avilable Count is 0 !", "Oops ...!", JOptionPane.ERROR_MESSAGE);

                                }else{

                                    do{
                                        bCopySet.add( rs.getString("Copy_No"));
                                    }while(rs.next());

                                    for (int i = 0; i < bCopySet.size(); i++){

                                        int bcopy = Integer.parseInt(bCopySet.get(i)) ;


                                        pst = con.prepareStatement("SELECT *  FROM  reserve where copy_no = ? and Status = ? ");
                                        pst.setInt(1, bcopy);
                                        pst.setString(2, "Reserved");

                                        rs = pst.executeQuery();

                                        if(rs.next()==true){
                                            continue;
                                        }else{

                                            pst = con.prepareStatement("SELECT *  FROM  borrow where copy_no = ? and Status = ? ");
                                            pst.setInt(1, bcopy);
                                            pst.setString(2, "Borrowed");

                                            rs = pst.executeQuery();

                                            if(rs.next()==true){
                                                continue;
                                            }else{
                                                avblCopyNo = bcopy;
                                                System.out.print( "avblCopyNo = "+bcopy);
                                                break;
                                            }
                                        }
                                    }


                                    while(rs.next()){
                                        bCopySet.add( rs.getString("Copy_No"));
                                    }

                                    for (int i = 0; i < bCopySet.size(); i++){

                                        int bcopy = Integer.parseInt(bCopySet.get(i)) ;


                                        pst = con.prepareStatement("SELECT *  FROM  reserve where copy_no = ? and Status = ? ");
                                        pst.setInt(1, bcopy);
                                        pst.setString(2, "Reserved");

                                        rs = pst.executeQuery();

                                        if(rs.next()==true){
                                            continue;
                                        }else{

                                            pst = con.prepareStatement("SELECT *  FROM  borrow where copy_no = ? and Status = ? ");
                                            pst.setInt(1, bcopy);
                                            pst.setString(2, "Borrowed");

                                            rs = pst.executeQuery();

                                            if(rs.next()==true){
                                                continue;
                                            }else{
                                                avblCopyNo = bcopy;
                                                System.out.print( "avblCopyNo = "+bcopy);
                                                break;

                                            }
                                        }
                                    }

                                    pst = con.prepareStatement("insert into reserve(Book_id,copy_no,memberID,Reserve_Date,Status)values(?,?,?,?,?)");

                                    pst.setInt(1, ID);
                                    pst.setInt(2, avblCopyNo);
                                    pst.setString(3, strMemberID);
                                    pst.setString(4, DateTime.currentDate().toString());
                                    pst.setString(5, "Reserved");

                                    int k = pst.executeUpdate();
                                    if(k==1){

//                                        txtBookIDSec.setText("");
//                                        txtISBN.setText("");
//                                        txtBookTitle.setText("");
//                                        txtBookCatogery.setSelectedIndex(-1);
//                                        txtAuthor.setText("");
//                                        //txtBookCount.setText("-");
//
//                                        txtBookIDSec.requestFocus();

                                        pst = con.prepareStatement("Update book set avbl_count = avbl_count -1 where book_id =?");
                                        //rs = pst.executeQuery();
                                        pst.setInt(1, ID);

                                        //pst.setInt(2, intbookId);
                                        int k2 = pst.executeUpdate();
                                        if(k2==1){
                                           // JOptionPane.showMessageDialog(this,"Book Succesfully Reserved");
                                        }

                                        //bookTableReLoad();
                                        

                                    }
                                    else{
                                       // JOptionPane.showMessageDialog(this,"Error:: Can't Reserved this Book");
                                    }
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null  ,ex);
                            }
                        }                
           }
           
           return status;
    }  
    
    
    
    
    
    }
    

