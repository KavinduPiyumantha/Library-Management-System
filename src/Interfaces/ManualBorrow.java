/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interfaces;

import Interfaces.MemberDashborad;
import Interfaces.LibDashborad;
import Codes.DBconnect;
import Codes.DateTime;
import static Interfaces.MemberDashborad.strMemberID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class ManualBorrow extends javax.swing.JFrame {

    /**
     * Creates new form LoginDetailsSet
     */
    
    public static int memberID = 0;
    public static String memberName =null;
    public static String role =null;
    
    public ManualBorrow() {
        initComponents();

        con =DBconnect.Connect();    
        
    }
    
    
    Connection con=null;
    PreparedStatement pst;
    ResultSet rs;
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnManulaBorrow = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtMbBookId = new javax.swing.JTextField();
        btnClose = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtMbCopyNo = new javax.swing.JTextField();
        txtMbMemberId = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnManulaBorrow.setBackground(new java.awt.Color(204, 204, 204));
        btnManulaBorrow.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnManulaBorrow.setForeground(new java.awt.Color(0, 0, 0));
        btnManulaBorrow.setText("Borrow");
        btnManulaBorrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManulaBorrowActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Book ID        :");

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Copy No       :");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Member ID  :");

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));

        txtMbBookId.setBackground(new java.awt.Color(255, 255, 255));
        txtMbBookId.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMbBookId.setForeground(new java.awt.Color(0, 0, 0));

        btnClose.setBackground(new java.awt.Color(204, 204, 204));
        btnClose.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnClose.setForeground(new java.awt.Color(0, 0, 0));
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Direct Borrow");

        txtMbCopyNo.setBackground(new java.awt.Color(255, 255, 255));
        txtMbCopyNo.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMbCopyNo.setForeground(new java.awt.Color(0, 0, 0));

        txtMbMemberId.setBackground(new java.awt.Color(255, 255, 255));
        txtMbMemberId.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMbMemberId.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel13))
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMbBookId, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                    .addComponent(txtMbCopyNo, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                    .addComponent(txtMbMemberId, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)))
                            .addComponent(btnManulaBorrow, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btnClose)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtMbCopyNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtMbMemberId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(jLabel13))
                    .addComponent(txtMbBookId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(btnManulaBorrow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
//   public void bookTableReLoad(){
//        
//        int c;
//
//        try {
//            pst = con.prepareStatement("SELECT Book_id, ISBN, Title, catogery, Author, avbl_count   FROM  book" );
//            //pst = con.prepareStatement("SELECT * FROM"+ " book" +" NATURAL JOIN "+ "bookcopy");
//             rs = pst.executeQuery();
//            
//             
//            ResultSetMetaData rsd;
//            rsd = rs.getMetaData();
//            c = rsd.getColumnCount();
//            
//            DefaultTableModel d =(DefaultTableModel )BookTable.getModel();
//            d.setRowCount(0);
//            
//            while(rs.next()){
//                Vector v2 = new Vector();
//                for(int i=1;i<=c;i++){
//                    
//                    v2.add(rs.getString("Book_id"));
//                    v2.add(rs.getString("ISBN"));
//                    v2.add(rs.getString("Title"));
//                    v2.add(rs.getString("catogery"));
//                    v2.add(rs.getString("Author"));
//                    v2.add(rs.getString("avbl_count"));
//                    
//                }
//                d.addRow(v2);
//            }
//            
//
//        } catch (SQLException ex) {
//            Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(null  ,ex);
//        }
//    }
//
//
    public void borrowTableUpdate(){
        int c;

        try {

            pst = con.prepareStatement("SELECT * FROM borrow where Status = ? ");
            pst.setString(1, "Borrowed");
            
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
                                v2.add(rs.getString("Due_Date"));
                                v2.add(rs.getString("Status"));
                                // v2.add(rs.getString("Price"));   
                             }
                    v1.add(v2);
                    }while(rs.next());
   
                    
                    
                    for(int i=0 ; i< v1.size() ;i++){
                               
                               if (DateTime.BorrowExpDate(v1.get(i).get(3) )){
                                        //change the status to expired and increas available count
                                          String status = "Over Due";
                                          pst = con.prepareStatement("update  borrow set Status = ? where  Book_id = ? and copy_no = ?  and memberID = ? and Due_Date = ? ");

                                          pst.setString(1, status);
                                          pst.setInt(2, Integer.parseInt(v1.get(i).get(0)));
                                          pst.setInt(3, Integer.parseInt(v1.get(i).get(1)));
                                          pst.setInt(4, Integer.parseInt(v1.get(i).get(2)));
                                          pst.setString(5, v1.get(i).get(3));

                                         pst.executeUpdate();

//                                         pst = con.prepareStatement("Update book set avbl_count = avbl_count +1 where book_id =?");
//                                         pst.setInt(1, Integer.parseInt(v1.get(i).get(0)));
//                                         pst.executeUpdate();
                               }          
                    }
                    
                    //reserveTableReload();
                    // Interfaces.LibDashborad.bookTableReLoadFromOut();
        }

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }     
        
       //reserveTableReload();
   }
    
    private void btnManulaBorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManulaBorrowActionPerformed

        if(txtMbBookId.getText().equals("") || txtMbCopyNo.equals("")  || txtMbMemberId.equals("")){
             JOptionPane.showMessageDialog(null, "Please Fill All Tex Boxes", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
        }
        else{
            
            
            try {
                
                boolean reserveStatus = false;
                boolean borrowStatus = false;
                boolean bookAvblStatusRes = false;
                boolean bookAvblStatusBorrow = false;
                
                String bookId = txtMbBookId.getText();
                String copyNo = txtMbCopyNo.getText();
                String memberId = txtMbMemberId.getText();
                
                
                          pst = con.prepareStatement("SELECT *  FROM  reserve where memberID = ? and Status = ? ");
                          pst.setString(1, memberId);
                          pst.setString(2, "Reserved");

                          rs = pst.executeQuery();

                          if(rs.next()==true){
                              reserveStatus= true;
                          }


                          String staustB[]={"Borrowed","Over Due"};

                         for(int i=0;i < staustB.length ;i++){
                                  pst = con.prepareStatement("SELECT *  FROM  borrow where memberID = ? and Status = ? ");
                                  pst.setString(1, memberId);
                                  pst.setString(2, staustB[i]);

                                  rs = pst.executeQuery();

                                  if(rs.next()==true){
                                      borrowStatus= true;
                                 }
                         }
                         
                         
                          pst = con.prepareStatement("SELECT  *  FROM  reserve where Book_id = ? and copy_no = ? and Status = ? ");
                          pst.setString(1, bookId);
                          pst.setString(2, copyNo);
                          pst.setString(3, "Reserved");

                          rs = pst.executeQuery();

                          if(rs.next()==true){
                              bookAvblStatusRes= true;
                          }
                          
                          
                         for(int i=0;i < staustB.length ;i++){
                                  pst = con.prepareStatement("SELECT *  FROM  borrow where Book_id = ? and copy_no = ? and Status = ?  ");
                                  pst.setString(1, bookId);
                                  pst.setString(2, copyNo);
                                  pst.setString(3, staustB[i]);

                                  rs = pst.executeQuery();

                                  if(rs.next()==true){
                                      bookAvblStatusBorrow= true;
                                      break;
                                 }
                         }
                         
                         if(reserveStatus){
                             JOptionPane.showMessageDialog(this,"Error:: this Member Alrady Reserved a book ");
                         }else if(borrowStatus){
                              JOptionPane.showMessageDialog(this,"Error:: this Member Alrady Borrowed a book ");
                             
                         }else if(bookAvblStatusRes){
                              JOptionPane.showMessageDialog(this,"Error:: this Book is Reserved Check another book or make sure you enter correct details of a book ");
                         }else if(bookAvblStatusBorrow){
                             JOptionPane.showMessageDialog(this,"Error:: this Book is Already  Borrowed, make sure you enter correct details of a book ");
                         }else{
                             
                            pst =con.prepareStatement("insert into borrow(Book_id, copy_no ,  memberID, Borrow_Date, Due_Date, Status ) value (?,?,?,?,?,?) ");
                            pst.setInt(1, Integer.parseInt(bookId) );
                            pst.setInt(2, Integer.parseInt(copyNo) );
                            pst.setInt(3, Integer.parseInt(memberId) );
                            pst.setString(4, DateTime.currentDate().toString());
                            pst.setString(5, DateTime.reternDate().toString());
                            pst.setString(6, "Borrowed"); 
                            
                             int k2 = pst.executeUpdate();
                             
                             if(k2==1){
                   
                                txtMbBookId.setText("");
                                txtMbCopyNo.setText("");
                                txtMbMemberId.setText("");

                                this.hide();
                                 pst = con.prepareStatement("Update book set avbl_count = avbl_count -1 where book_id =?");
                                 pst.setInt(1, Integer.parseInt(bookId));
     
                                 int k3 = pst.executeUpdate();
                                 
                                 
                                 borrowTableUpdate();
                                 //Interfaces.LibDashborad.bookTableReLoadFromOut();
                                 
                                JOptionPane.showMessageDialog(this,"Book is  Succesfully Borrowed");
                                
                                }else{
                                 JOptionPane.showMessageDialog(this,"Error:: Can't Borrow that Book, Please try Again");
                                }

                         }
 
//                pst=con.prepareStatement("insert into Login(ID,role,username,password) value(?,?,?,?) ");
//                pst.setInt(1, memberID);
//                pst.setString(2, role);
//                pst.setString(3, username);
//                pst.setString(4,password);
//                
//               int k = pst.executeUpdate();

    
            } catch (SQLException ex) {
                Logger.getLogger(ManualBorrow.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        }

        //login.setVisible(true);
    }//GEN-LAST:event_btnManulaBorrowActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        
        this.hide();
    }//GEN-LAST:event_btnCloseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManualBorrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManualBorrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManualBorrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManualBorrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManualBorrow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnManulaBorrow;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtMbBookId;
    private javax.swing.JTextField txtMbCopyNo;
    private javax.swing.JTextField txtMbMemberId;
    // End of variables declaration//GEN-END:variables
}
