/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interfaces;


import Codes.DBconnect;
import  Codes.DateTime;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LibDashborad extends javax.swing.JFrame {

    
    
          public static int libID;
          public static String libName;
          
          static String strLibID;
         
          

    public LibDashborad() {

        initComponents();
        con =DBconnect.Connect();
        
       //Dispaly Librarian Id and Name   
        strLibID=String.valueOf(libID);
        lblLabirarianID.setText(strLibID);
        lblLibName.setText(libName);
        
        //Table Update
        borrowTableUpdate();
        reserveTableUpdate();
        
        //table Loading
        bookTableReLoad();
        bookCopyTable_reLoad();
        memberTableReLoad();
        reserveTableReload();
        borrowTableReload();

        
        //book section
        btnUpdate_Book.setEnabled(false);
        btnDelete_Book.setEnabled(false);
        
        //book copy Section
        btnUpdate_bookCopy.setEnabled(false);
        btnDelete_bookCopy.setEnabled(false);
        
        //memeber Section
        txtMemeberUpdate.setEnabled(false);
        txtMemeberRemove.setEnabled(false);
        btnloginDeUpdate.setEnabled(false);
        
        

        
     
    }

    
    Connection con=null;
    PreparedStatement pst;
    ResultSet rs;
    
     public void DboardEnable(){
        //LibDashborad.dispose();
        //LibDashborad.setEnabled(true);
    }
    
    
    public  void bookTableReLoad(){
        
        int c;

        try {
            pst = con.prepareStatement("SELECT *  FROM  book" );
            //pst = con.prepareStatement("SELECT * FROM" + " book" +" NATURAL JOIN "+ "bookcopy");
             rs = pst.executeQuery();
            
            ResultSetMetaData rsd;
            rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d =(DefaultTableModel )BookTable.getModel();
            d.setRowCount(0);
            
            while(rs.next()){
                Vector v2 = new Vector();
                        for(int i=1;i<=c;i++){

                            v2.add(rs.getString("Book_id"));
                            v2.add(rs.getString("ISBN"));
                            v2.add(rs.getString("Title"));
                            v2.add(rs.getString("catogery"));
                            v2.add(rs.getString("Author"));
                            v2.add(rs.getString("book_count"));
                            v2.add(rs.getString("avbl_count"));
                        }
                d.addRow(v2);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }
    }
        
    public void bookCopyTable_reLoad(){
        int c;

        try {
            pst = con.prepareStatement("SELECT * FROM  bookcopy" /*+" NATURAL JOIN"+" purchase" */);
             rs = pst.executeQuery();
            
            ResultSetMetaData rsd;
            rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d =(DefaultTableModel )Book_Copy_Table.getModel();
            d.setRowCount(0);
            
            while(rs.next()){
                Vector v2 = new Vector();
                for(int i=1;i<=c;i++){
                    
                    v2.add(rs.getString("Copy_No"));
                    v2.add(rs.getString("Book_id"));
                    v2.add(rs.getString("Edition"));
                    v2.add(rs.getString("Purchase_date"));
                    v2.add(rs.getString("Price"));

                    
                }
                d.addRow(v2);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }
    
    
    };    
    
    public void bookTableReLoadFromOut(){
        bookTableReLoad();
    }
    
   public void memberTableReLoad(){
        int c;

        try {
            pst = con.prepareStatement("SELECT * FROM member");
             rs = pst.executeQuery();
            
            ResultSetMetaData rsd;
            rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d =(DefaultTableModel )memberTable.getModel();
            d.setRowCount(0);
            
            while(rs.next()){
                Vector v2 = new Vector();
                for(int i=1;i<=c;i++){
                    
                    v2.add(rs.getString("memberID"));
                    v2.add(rs.getString("Member_Name"));
                    v2.add(rs.getString("Member_Address"));
                    v2.add(rs.getString("Tel_No"));
                   // v2.add(rs.getString("Price"));

                    
                }
                d.addRow(v2);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }
   }
   
   public void reserveTableReload(){
          int c;

        try {

            pst = con.prepareStatement("SELECT * FROM reserve  ");
            
             rs = pst.executeQuery();
            
            ResultSetMetaData rsd;
            rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d =(DefaultTableModel )BookReserveTable.getModel();
            d.setRowCount(0);
            
            while(rs.next()){
                Vector v2 = new Vector();
                for(int i=1;i<=c;i++){
                    
                    v2.add(rs.getString("Book_id"));
                    v2.add(rs.getString("copy_no"));
                    v2.add(rs.getString("memberID"));
                    v2.add(rs.getString("Reserve_Date"));
                    v2.add(rs.getString("Status"));
                      
                }
                d.addRow(v2);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }     
       
   }
   
   public void reserveTableUpdate(){
          int c;

        try {

            
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
                    bookTableReLoad();
 
           }

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }     
        
       //reserveTableReload();
   }
   
   public void borrowTableReload(){
          int c;

        try {
//            pst = con.prepareStatement("SELECT * FROM reserve where Status = ? ");
//            pst.setString(1,"Reserved");
            
            pst = con.prepareStatement("SELECT * FROM borrow  ");
            
             rs = pst.executeQuery();
            
            ResultSetMetaData rsd;
            rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d =(DefaultTableModel )BookBorrowTable.getModel();
            d.setRowCount(0);
            
            while(rs.next()){
                Vector v2 = new Vector();
                for(int i=1;i<=c;i++){
                    
                    v2.add(rs.getString("Book_id"));
                    v2.add(rs.getString("copy_no"));
                    v2.add(rs.getString("memberID"));
                    v2.add(rs.getString("Borrow_Date"));
                    v2.add(rs.getString("Due_Date"));
                    v2.add(rs.getString("returned_Date"));
                    v2.add(rs.getString("Status"));
                    v2.add(rs.getString("penalty_fee"));
   
                }
                d.addRow(v2);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }     
       
   }
   
   public void borrowTableUpdate(){
        int c;

        try {

            pst = con.prepareStatement("SELECT * FROM borrow where Status = ? ");
            pst.setString(1, "Borrowed");
            
             rs = pst.executeQuery();
            
            ResultSetMetaData rsd;
            rsd = rs.getMetaData();
            c = rsd.getColumnCount();

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

                               }          
                    }
                    
                     bookTableReLoad();
        }

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }     
        
   }
    
        
   public void loadTableData(){
       
        DefaultTableModel d1 =(DefaultTableModel )BookTable.getModel();
        int selectIndex =BookTable.getSelectedRow();


        txtBookIDSec.setText(d1.getValueAt(selectIndex, 0).toString());
        txtISBN.setText(d1.getValueAt(selectIndex, 1).toString());
        txtBookTitle.setText(d1.getValueAt(selectIndex, 2).toString());
        txtBookCatogery.setSelectedItem(d1.getValueAt(selectIndex, 3).toString());
        txtAuthor.setText(d1.getValueAt(selectIndex, 4).toString());
        txtBookCount.setText(d1.getValueAt(selectIndex, 5).toString());
        btnUpdate_Book.setEnabled(true);
        btnDelete_Book.setEnabled(true);
        
   }
    
    public void increase_Book_Count(String id){
        
        String bookId = id;
        
        
            try {
       
                    int intbookId = Integer.parseInt(bookId);
                    pst = con.prepareStatement("SELECT book_count, avbl_count FROM book where Book_id = "+ intbookId);
                    rs = pst.executeQuery();
                    
                    System.out.println("intbookId "+ intbookId);
                    
                    rs.next();
                    int bookCount = rs.getInt(1);
                    int avblcount   = rs.getInt(2);
                    System.out.println("bookCount "+bookCount);
                     System.out.println("avblCount "+avblcount);
                    
                    bookCount = bookCount + 1;
                    avblcount =  avblcount+1;
                    System.out.println("bookCount "+bookCount);
                    System.out.println("avblCount "+avblcount);

                
                    
                    pst = con.prepareStatement("Update book set book_count = ? , avbl_count = ?  where book_id =?");
                    //rs = pst.executeQuery();
                    pst.setInt(1, bookCount);
                    pst.setInt(2, avblcount);
                    
                    pst.setInt(3, intbookId);
                    
                    System.out.println("setid "+intbookId);
                    
                    pst.executeUpdate();
                    
                    bookTableReLoad();
                    
                    }catch (SQLException ex) {
                        Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null  ,ex);
        
                    }

    }
    
     public void decrease_Book_Count(String id){
        
        String bookId = id;
        
        
            try {
       
                    int intbookId = Integer.parseInt(bookId);
                    pst = con.prepareStatement("SELECT book_count, avbl_count FROM book where Book_id = "+ intbookId);
                    rs = pst.executeQuery();
                    
                    System.out.println("intbookId "+ intbookId);
                    
                    rs.next();
                    int bookCount = rs.getInt(1);
                    int avblcount   = rs.getInt(2);
                    
                    System.out.println("bookCount "+bookCount);
                    
                    bookCount = bookCount - 1;
                    avblcount =  avblcount-1;
                    
                    
                    System.out.println("bookCount "+bookCount);

                    //String sbookCount =  Integer.toString(bookCount) ;

                
                    
                    pst = con.prepareStatement("Update book set book_count = ? , avbl_count = ? where book_id =?");
                    //rs = pst.executeQuery();
                    pst.setInt(1, bookCount);
                    pst.setInt(2, avblcount);
                    
                    System.out.println("setcount "+bookCount);
                   // pst.setString(2, bookCount);
                    //pst.setString(3, 1);
                    pst.setInt(3, intbookId);
                    
                    System.out.println("setid "+intbookId);
                    
                    pst.executeUpdate();
                    
                    bookTableReLoad();
                    
                    }catch (SQLException ex) {
                        Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null  ,ex);
        
                    }

            
        
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtBack = new javax.swing.JButton();
        TabSection = new javax.swing.JPanel();
        tapSection = new javax.swing.JTabbedPane();
        BookSection = new javax.swing.JPanel();
        btnAdd_Book = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        BookTable = new javax.swing.JTable();
        btnDelete_Book = new javax.swing.JButton();
        btnUpdate_Book = new javax.swing.JButton();
        btnFind_Book = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtBookTitle = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtAuthor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtBookCatogery = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtBookCount = new javax.swing.JLabel();
        txtBookIDSec = new javax.swing.JTextField();
        btnClearBook = new javax.swing.JButton();
        selectTypeBox = new javax.swing.JComboBox<>();
        txtBookFind = new javax.swing.JTextField();
        btnClearSearch = new javax.swing.JButton();
        BookCopySection = new javax.swing.JPanel();
        btnAdd_bookCopy = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        Book_Copy_Table = new javax.swing.JTable();
        btnDelete_bookCopy = new javax.swing.JButton();
        btnUpdate_bookCopy = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtBookID = new javax.swing.JTextField();
        txtCopyNo = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        selectTypeBoxBookCopy = new javax.swing.JComboBox<>();
        txtBookCopyFind = new javax.swing.JTextField();
        btnFind_BookCopy = new javax.swing.JButton();
        btnClearSearchBookCopy = new javax.swing.JButton();
        btnClearBookCopy = new javax.swing.JButton();
        txtEdition = new javax.swing.JTextField();
        lblEdition = new javax.swing.JLabel();
        MemberSection = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtMamberName = new javax.swing.JTextField();
        txtMemberAdd = new javax.swing.JButton();
        txtMemeberRemove = new javax.swing.JButton();
        txtMemeberUpdate = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        memberTable = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtMemberAddress = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtMemberTep = new javax.swing.JTextField();
        selectTypeBoxMember = new javax.swing.JComboBox<>();
        txtMemberFind = new javax.swing.JTextField();
        btnFind_Member = new javax.swing.JButton();
        btnClearSearch1 = new javax.swing.JButton();
        txtMemberId = new javax.swing.JTextField();
        btnMemberClearText = new javax.swing.JButton();
        btnloginDeUpdate = new javax.swing.JButton();
        ReserveSection = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        BookReserveTable = new javax.swing.JTable();
        btnUpdate_Book1 = new javax.swing.JButton();
        btnFind_Reserve = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtStatusReserve = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        btnClearReseve = new javax.swing.JButton();
        selectTypeBoxReserve = new javax.swing.JComboBox<>();
        txFindReserve = new javax.swing.JTextField();
        btnClearSearch2 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        txtMemberIDReserve = new javax.swing.JLabel();
        txtBookIDReserve = new javax.swing.JLabel();
        txtCopyNoReserve = new javax.swing.JLabel();
        txtDateReserve = new javax.swing.JLabel();
        ReserveSection1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        BookBorrowTable = new javax.swing.JTable();
        btnUpdate_Borrowed = new javax.swing.JButton();
        btnFind_Borrow = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtStatusBorrow = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        btnClearReseve1 = new javax.swing.JButton();
        selectTypeBoxBorrow = new javax.swing.JComboBox<>();
        txFindBorrow = new javax.swing.JTextField();
        btnClearSearchBorrow = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        txtMemberIDBorrow = new javax.swing.JLabel();
        txtBookIDBorrow = new javax.swing.JLabel();
        txtCopyNoBorrow = new javax.swing.JLabel();
        txtDateBorrow = new javax.swing.JLabel();
        btnDirectBorrow = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        lblLabirarianID = new javax.swing.JLabel();
        lblLibName = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelMain.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Archivo SemiBold", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("LIBRARY MANAGEMENT SYSTEM");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Archivo SemiBold", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Laibrarian Dashboard");

        txtBack.setBackground(new java.awt.Color(204, 204, 204));
        txtBack.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBack.setForeground(new java.awt.Color(0, 0, 0));
        txtBack.setText("Logout");
        txtBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBackActionPerformed(evt);
            }
        });

        TabSection.setBackground(new java.awt.Color(255, 255, 255));
        TabSection.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tapSection.setBackground(new java.awt.Color(204, 204, 204));
        tapSection.setForeground(new java.awt.Color(255, 255, 255));
        tapSection.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N

        BookSection.setBackground(new java.awt.Color(255, 255, 255));

        btnAdd_Book.setBackground(new java.awt.Color(204, 204, 204));
        btnAdd_Book.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnAdd_Book.setForeground(new java.awt.Color(0, 0, 0));
        btnAdd_Book.setText("Add");
        btnAdd_Book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd_BookActionPerformed(evt);
            }
        });

        BookTable.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        BookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID", "ISBN No", "Book Title", "Catogery", "Author", "Total Count", "Available Count"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        BookTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BookTableMouseClicked(evt);
            }
        });
        BookTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BookTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(BookTable);

        btnDelete_Book.setBackground(new java.awt.Color(204, 204, 204));
        btnDelete_Book.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnDelete_Book.setForeground(new java.awt.Color(0, 0, 0));
        btnDelete_Book.setText("Remove");
        btnDelete_Book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete_BookActionPerformed(evt);
            }
        });

        btnUpdate_Book.setBackground(new java.awt.Color(204, 204, 204));
        btnUpdate_Book.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnUpdate_Book.setForeground(new java.awt.Color(0, 0, 0));
        btnUpdate_Book.setText("Update");
        btnUpdate_Book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_BookActionPerformed(evt);
            }
        });

        btnFind_Book.setBackground(new java.awt.Color(204, 204, 204));
        btnFind_Book.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnFind_Book.setForeground(new java.awt.Color(0, 0, 0));
        btnFind_Book.setText("Find");
        btnFind_Book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFind_BookActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("ID");

        txtBookTitle.setBackground(new java.awt.Color(255, 255, 255));
        txtBookTitle.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookTitle.setForeground(new java.awt.Color(0, 0, 0));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Book Title");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Book Catogery");

        txtAuthor.setBackground(new java.awt.Color(255, 255, 255));
        txtAuthor.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtAuthor.setForeground(new java.awt.Color(0, 0, 0));
        txtAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAuthorActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Author");

        txtBookCatogery.setBackground(new java.awt.Color(255, 255, 255));
        txtBookCatogery.setEditable(true);
        txtBookCatogery.setForeground(new java.awt.Color(0, 0, 0));
        txtBookCatogery.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kids Story", "Action and Adventure", "Classics", "Comic Book or Graphic Novel", "Detective and Mystery", "Fantasy", "Historical Fiction", "Horror", "Literary Fiction", "Romance", "Science Fiction (Sci-Fi)", "Short Stories", "Suspense and Thrillers", "Women's Fiction", "Biographies and Autobiographies", "Cookbooks", "Essays", "History", "Memoir", "Poetry", "Self-Help", "True Crime", "Science", "Computer Science", "IT", "Biology", "Arts", " " }));
        txtBookCatogery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBookCatogeryActionPerformed(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("ISBN No");

        txtISBN.setBackground(new java.awt.Color(255, 255, 255));
        txtISBN.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtISBN.setForeground(new java.awt.Color(0, 0, 0));
        txtISBN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtISBNActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Count");

        txtBookCount.setBackground(new java.awt.Color(255, 255, 255));
        txtBookCount.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookCount.setForeground(new java.awt.Color(0, 0, 0));
        txtBookCount.setText("-");

        txtBookIDSec.setBackground(new java.awt.Color(255, 255, 255));
        txtBookIDSec.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookIDSec.setForeground(new java.awt.Color(0, 0, 0));
        txtBookIDSec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBookIDSecActionPerformed(evt);
            }
        });

        btnClearBook.setBackground(new java.awt.Color(204, 204, 204));
        btnClearBook.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnClearBook.setForeground(new java.awt.Color(0, 0, 0));
        btnClearBook.setText("Clear ");
        btnClearBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearBookActionPerformed(evt);
            }
        });

        selectTypeBox.setBackground(new java.awt.Color(255, 255, 255));
        selectTypeBox.setEditable(true);
        selectTypeBox.setForeground(new java.awt.Color(0, 0, 0));
        selectTypeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "ISBN NO", "Book Title", "Book Catogery", "Author", "Count" }));
        selectTypeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTypeBoxActionPerformed(evt);
            }
        });

        txtBookFind.setBackground(new java.awt.Color(255, 255, 255));
        txtBookFind.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookFind.setForeground(new java.awt.Color(0, 0, 0));
        txtBookFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBookFindActionPerformed(evt);
            }
        });

        btnClearSearch.setBackground(new java.awt.Color(204, 204, 204));
        btnClearSearch.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnClearSearch.setForeground(new java.awt.Color(0, 0, 0));
        btnClearSearch.setText("Clear ");
        btnClearSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BookSectionLayout = new javax.swing.GroupLayout(BookSection);
        BookSection.setLayout(BookSectionLayout);
        BookSectionLayout.setHorizontalGroup(
            BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookSectionLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, BookSectionLayout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtBookCatogery, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(BookSectionLayout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(BookSectionLayout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtBookCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtAuthor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)))
                        .addGroup(BookSectionLayout.createSequentialGroup()
                            .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(BookSectionLayout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BookSectionLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(101, 101, 101)))
                            .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBookIDSec, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(BookSectionLayout.createSequentialGroup()
                            .addComponent(btnUpdate_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAdd_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(BookSectionLayout.createSequentialGroup()
                            .addComponent(btnDelete_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(47, 47, 47)
                            .addComponent(btnClearBook, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(BookSectionLayout.createSequentialGroup()
                        .addComponent(selectTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtBookFind, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(19, 19, 19))
        );
        BookSectionLayout.setVerticalGroup(
            BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookSectionLayout.createSequentialGroup()
                .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BookSectionLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(selectTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBookFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFind_Book)
                            .addComponent(btnClearSearch))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BookSectionLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtBookIDSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(26, 26, 26)
                        .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtBookCatogery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(txtBookCount, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd_Book)
                            .addComponent(btnUpdate_Book))
                        .addGap(18, 18, 18)
                        .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDelete_Book)
                            .addComponent(btnClearBook))))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        tapSection.addTab("    Book Section  ", BookSection);

        BookCopySection.setBackground(new java.awt.Color(255, 255, 255));

        btnAdd_bookCopy.setBackground(new java.awt.Color(204, 204, 204));
        btnAdd_bookCopy.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnAdd_bookCopy.setForeground(new java.awt.Color(0, 0, 0));
        btnAdd_bookCopy.setText("Add");
        btnAdd_bookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd_bookCopyActionPerformed(evt);
            }
        });

        Book_Copy_Table.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        Book_Copy_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book Copy No", "Book ID", "Edition", "Purchase Date", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Book_Copy_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Book_Copy_TableMouseClicked(evt);
            }
        });
        Book_Copy_Table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Book_Copy_TableKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(Book_Copy_Table);

        btnDelete_bookCopy.setBackground(new java.awt.Color(204, 204, 204));
        btnDelete_bookCopy.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnDelete_bookCopy.setForeground(new java.awt.Color(0, 0, 0));
        btnDelete_bookCopy.setText("Remove");
        btnDelete_bookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete_bookCopyActionPerformed(evt);
            }
        });

        btnUpdate_bookCopy.setBackground(new java.awt.Color(204, 204, 204));
        btnUpdate_bookCopy.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnUpdate_bookCopy.setForeground(new java.awt.Color(0, 0, 0));
        btnUpdate_bookCopy.setText("Update");
        btnUpdate_bookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_bookCopyActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Copy No");

        txtPrice.setBackground(new java.awt.Color(255, 255, 255));
        txtPrice.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtPrice.setForeground(new java.awt.Color(0, 0, 0));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Purchase Date");

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Price");

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Book ID");

        txtBookID.setBackground(new java.awt.Color(255, 255, 255));
        txtBookID.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookID.setForeground(new java.awt.Color(0, 0, 0));
        txtBookID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBookIDActionPerformed(evt);
            }
        });

        txtCopyNo.setBackground(new java.awt.Color(255, 255, 255));
        txtCopyNo.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtCopyNo.setForeground(new java.awt.Color(0, 0, 0));
        txtCopyNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCopyNoActionPerformed(evt);
            }
        });

        txtDate.setBackground(new java.awt.Color(255, 255, 255));
        txtDate.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtDate.setForeground(new java.awt.Color(0, 0, 0));
        txtDate.setToolTipText("YYYY/MM/DD");

        selectTypeBoxBookCopy.setBackground(new java.awt.Color(255, 255, 255));
        selectTypeBoxBookCopy.setEditable(true);
        selectTypeBoxBookCopy.setForeground(new java.awt.Color(0, 0, 0));
        selectTypeBoxBookCopy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Copy No", "Book ID", "Edition", "Purchase Date", "Price" }));
        selectTypeBoxBookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTypeBoxBookCopyActionPerformed(evt);
            }
        });

        txtBookCopyFind.setBackground(new java.awt.Color(255, 255, 255));
        txtBookCopyFind.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookCopyFind.setForeground(new java.awt.Color(0, 0, 0));
        txtBookCopyFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBookCopyFindActionPerformed(evt);
            }
        });

        btnFind_BookCopy.setBackground(new java.awt.Color(204, 204, 204));
        btnFind_BookCopy.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnFind_BookCopy.setForeground(new java.awt.Color(0, 0, 0));
        btnFind_BookCopy.setText("Find");
        btnFind_BookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFind_BookCopyActionPerformed(evt);
            }
        });

        btnClearSearchBookCopy.setBackground(new java.awt.Color(204, 204, 204));
        btnClearSearchBookCopy.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnClearSearchBookCopy.setForeground(new java.awt.Color(0, 0, 0));
        btnClearSearchBookCopy.setText("Clear ");
        btnClearSearchBookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSearchBookCopyActionPerformed(evt);
            }
        });

        btnClearBookCopy.setBackground(new java.awt.Color(204, 204, 204));
        btnClearBookCopy.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnClearBookCopy.setForeground(new java.awt.Color(0, 0, 0));
        btnClearBookCopy.setText("Clear ");
        btnClearBookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearBookCopyActionPerformed(evt);
            }
        });

        txtEdition.setBackground(new java.awt.Color(255, 255, 255));
        txtEdition.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtEdition.setForeground(new java.awt.Color(0, 0, 0));
        txtEdition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditionActionPerformed(evt);
            }
        });

        lblEdition.setBackground(new java.awt.Color(255, 255, 255));
        lblEdition.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblEdition.setForeground(new java.awt.Color(0, 0, 0));
        lblEdition.setText("Edition");

        javax.swing.GroupLayout BookCopySectionLayout = new javax.swing.GroupLayout(BookCopySection);
        BookCopySection.setLayout(BookCopySectionLayout);
        BookCopySectionLayout.setHorizontalGroup(
            BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookCopySectionLayout.createSequentialGroup()
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BookCopySectionLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(selectTypeBoxBookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtBookCopyFind, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind_BookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearSearchBookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BookCopySectionLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(BookCopySectionLayout.createSequentialGroup()
                                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtBookID, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(BookCopySectionLayout.createSequentialGroup()
                                    .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(BookCopySectionLayout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(12, 12, 12))
                                        .addGroup(BookCopySectionLayout.createSequentialGroup()
                                            .addComponent(jLabel20)
                                            .addGap(79, 79, 79)))
                                    .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCopyNo, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                        .addComponent(txtPrice)
                                        .addComponent(txtDate)))
                                .addGroup(BookCopySectionLayout.createSequentialGroup()
                                    .addComponent(lblEdition, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtEdition, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(BookCopySectionLayout.createSequentialGroup()
                                    .addComponent(btnUpdate_bookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAdd_bookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(BookCopySectionLayout.createSequentialGroup()
                                    .addComponent(btnDelete_bookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(47, 47, 47)
                                    .addComponent(btnClearBookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        BookCopySectionLayout.setVerticalGroup(
            BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookCopySectionLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtCopyNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBookID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEdition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEdition))
                .addGap(26, 26, 26)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdd_bookCopy)
                    .addComponent(btnUpdate_bookCopy))
                .addGap(18, 18, 18)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete_bookCopy)
                    .addComponent(btnClearBookCopy))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BookCopySectionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectTypeBoxBookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBookCopyFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind_BookCopy)
                    .addComponent(btnClearSearchBookCopy))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        tapSection.addTab("    Book Copy Section  ", BookCopySection);

        MemberSection.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Member ID");

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Name");

        txtMamberName.setBackground(new java.awt.Color(255, 255, 255));
        txtMamberName.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMamberName.setForeground(new java.awt.Color(0, 0, 0));
        txtMamberName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMamberNameActionPerformed(evt);
            }
        });

        txtMemberAdd.setBackground(new java.awt.Color(204, 204, 204));
        txtMemberAdd.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMemberAdd.setForeground(new java.awt.Color(0, 0, 0));
        txtMemberAdd.setText("Add");
        txtMemberAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMemberAddActionPerformed(evt);
            }
        });

        txtMemeberRemove.setBackground(new java.awt.Color(204, 204, 204));
        txtMemeberRemove.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMemeberRemove.setForeground(new java.awt.Color(0, 0, 0));
        txtMemeberRemove.setText("Remove");
        txtMemeberRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMemeberRemoveActionPerformed(evt);
            }
        });

        txtMemeberUpdate.setBackground(new java.awt.Color(204, 204, 204));
        txtMemeberUpdate.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMemeberUpdate.setForeground(new java.awt.Color(0, 0, 0));
        txtMemeberUpdate.setText("Update");
        txtMemeberUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMemeberUpdateActionPerformed(evt);
            }
        });

        memberTable.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        memberTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Address", "Telephone "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        memberTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                memberTableMouseClicked(evt);
            }
        });
        memberTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                memberTableKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(memberTable);

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Address");

        txtMemberAddress.setBackground(new java.awt.Color(255, 255, 255));
        txtMemberAddress.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMemberAddress.setForeground(new java.awt.Color(0, 0, 0));
        txtMemberAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMemberAddressActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Telephohe");

        txtMemberTep.setBackground(new java.awt.Color(255, 255, 255));
        txtMemberTep.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMemberTep.setForeground(new java.awt.Color(0, 0, 0));
        txtMemberTep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMemberTepActionPerformed(evt);
            }
        });

        selectTypeBoxMember.setBackground(new java.awt.Color(255, 255, 255));
        selectTypeBoxMember.setEditable(true);
        selectTypeBoxMember.setForeground(new java.awt.Color(0, 0, 0));
        selectTypeBoxMember.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Member Id", "Name", "Address", "Telephone" }));
        selectTypeBoxMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTypeBoxMemberActionPerformed(evt);
            }
        });

        txtMemberFind.setBackground(new java.awt.Color(255, 255, 255));
        txtMemberFind.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMemberFind.setForeground(new java.awt.Color(0, 0, 0));
        txtMemberFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMemberFindActionPerformed(evt);
            }
        });

        btnFind_Member.setBackground(new java.awt.Color(204, 204, 204));
        btnFind_Member.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnFind_Member.setForeground(new java.awt.Color(0, 0, 0));
        btnFind_Member.setText("Find");
        btnFind_Member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFind_MemberActionPerformed(evt);
            }
        });

        btnClearSearch1.setBackground(new java.awt.Color(204, 204, 204));
        btnClearSearch1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnClearSearch1.setForeground(new java.awt.Color(0, 0, 0));
        btnClearSearch1.setText("Clear ");
        btnClearSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSearch1ActionPerformed(evt);
            }
        });

        txtMemberId.setBackground(new java.awt.Color(255, 255, 255));
        txtMemberId.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMemberId.setForeground(new java.awt.Color(0, 0, 0));

        btnMemberClearText.setBackground(new java.awt.Color(204, 204, 204));
        btnMemberClearText.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnMemberClearText.setForeground(new java.awt.Color(0, 0, 0));
        btnMemberClearText.setText("Clear ");
        btnMemberClearText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemberClearTextActionPerformed(evt);
            }
        });

        btnloginDeUpdate.setBackground(new java.awt.Color(204, 204, 204));
        btnloginDeUpdate.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnloginDeUpdate.setForeground(new java.awt.Color(0, 0, 0));
        btnloginDeUpdate.setText("Update Login Detais");
        btnloginDeUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginDeUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MemberSectionLayout = new javax.swing.GroupLayout(MemberSection);
        MemberSection.setLayout(MemberSectionLayout);
        MemberSectionLayout.setHorizontalGroup(
            MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MemberSectionLayout.createSequentialGroup()
                .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(MemberSectionLayout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel14)
                                .addComponent(jLabel15)
                                .addComponent(jLabel16)
                                .addComponent(jLabel10))
                            .addGap(54, 54, 54)
                            .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtMemberAddress, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMemberTep, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                .addComponent(txtMamberName, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMemberId, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, MemberSectionLayout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMemeberUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMemeberRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(35, 35, 35)
                            .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(MemberSectionLayout.createSequentialGroup()
                                    .addComponent(btnMemberClearText, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE))
                                .addGroup(MemberSectionLayout.createSequentialGroup()
                                    .addComponent(txtMemberAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE)))))
                    .addGroup(MemberSectionLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(btnloginDeUpdate)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(MemberSectionLayout.createSequentialGroup()
                        .addComponent(selectTypeBoxMember, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMemberFind, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind_Member, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addGap(10, 10, 10))
        );
        MemberSectionLayout.setVerticalGroup(
            MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MemberSectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectTypeBoxMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMemberFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind_Member)
                    .addComponent(btnClearSearch1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MemberSectionLayout.createSequentialGroup()
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtMemberId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMamberName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMemberAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMemberTep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(76, 76, 76)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMemberAdd)
                            .addComponent(txtMemeberUpdate))
                        .addGap(30, 30, 30)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMemeberRemove)
                            .addComponent(btnMemberClearText))
                        .addGap(35, 35, 35)
                        .addComponent(btnloginDeUpdate)
                        .addGap(137, 137, 137))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MemberSectionLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        tapSection.addTab("   Memebers  Section ", MemberSection);

        ReserveSection.setBackground(new java.awt.Color(255, 255, 255));

        BookReserveTable.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        BookReserveTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID", "Book Copy No", "Member ID", "Reserve Date Time", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        BookReserveTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BookReserveTableMouseClicked(evt);
            }
        });
        BookReserveTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BookReserveTableKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(BookReserveTable);

        btnUpdate_Book1.setBackground(new java.awt.Color(204, 204, 204));
        btnUpdate_Book1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnUpdate_Book1.setForeground(new java.awt.Color(0, 0, 0));
        btnUpdate_Book1.setText("Update");
        btnUpdate_Book1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_Book1ActionPerformed(evt);
            }
        });

        btnFind_Reserve.setBackground(new java.awt.Color(204, 204, 204));
        btnFind_Reserve.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnFind_Reserve.setForeground(new java.awt.Color(0, 0, 0));
        btnFind_Reserve.setText("Find");
        btnFind_Reserve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFind_ReserveActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Book ID");

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Member ID");

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Status");

        txtStatusReserve.setBackground(new java.awt.Color(255, 255, 255));
        txtStatusReserve.setEditable(true);
        txtStatusReserve.setForeground(new java.awt.Color(0, 0, 0));
        txtStatusReserve.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Reserved", "Picked Up" }));
        txtStatusReserve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusReserveActionPerformed(evt);
            }
        });

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Copy No");

        btnClearReseve.setBackground(new java.awt.Color(204, 204, 204));
        btnClearReseve.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnClearReseve.setForeground(new java.awt.Color(0, 0, 0));
        btnClearReseve.setText("Clear ");
        btnClearReseve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearReseveActionPerformed(evt);
            }
        });

        selectTypeBoxReserve.setBackground(new java.awt.Color(255, 255, 255));
        selectTypeBoxReserve.setEditable(true);
        selectTypeBoxReserve.setForeground(new java.awt.Color(0, 0, 0));
        selectTypeBoxReserve.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Book ID", "Copy NO", "Member ID", "Reserve Date", "Status" }));
        selectTypeBoxReserve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTypeBoxReserveActionPerformed(evt);
            }
        });

        txFindReserve.setBackground(new java.awt.Color(255, 255, 255));
        txFindReserve.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txFindReserve.setForeground(new java.awt.Color(0, 0, 0));
        txFindReserve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txFindReserveActionPerformed(evt);
            }
        });

        btnClearSearch2.setBackground(new java.awt.Color(204, 204, 204));
        btnClearSearch2.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnClearSearch2.setForeground(new java.awt.Color(0, 0, 0));
        btnClearSearch2.setText("Clear ");
        btnClearSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSearch2ActionPerformed(evt);
            }
        });

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Reserved Date");

        txtMemberIDReserve.setBackground(new java.awt.Color(255, 255, 255));
        txtMemberIDReserve.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMemberIDReserve.setForeground(new java.awt.Color(0, 0, 0));
        txtMemberIDReserve.setText("-");

        txtBookIDReserve.setBackground(new java.awt.Color(255, 255, 255));
        txtBookIDReserve.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookIDReserve.setForeground(new java.awt.Color(0, 0, 0));
        txtBookIDReserve.setText("-");

        txtCopyNoReserve.setBackground(new java.awt.Color(255, 255, 255));
        txtCopyNoReserve.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtCopyNoReserve.setForeground(new java.awt.Color(0, 0, 0));
        txtCopyNoReserve.setText("-");

        txtDateReserve.setBackground(new java.awt.Color(255, 255, 255));
        txtDateReserve.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtDateReserve.setForeground(new java.awt.Color(0, 0, 0));
        txtDateReserve.setText("-");

        javax.swing.GroupLayout ReserveSectionLayout = new javax.swing.GroupLayout(ReserveSection);
        ReserveSection.setLayout(ReserveSectionLayout);
        ReserveSectionLayout.setHorizontalGroup(
            ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReserveSectionLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReserveSectionLayout.createSequentialGroup()
                        .addComponent(btnClearReseve, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdate_Book1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReserveSectionLayout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ReserveSectionLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtStatusReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ReserveSectionLayout.createSequentialGroup()
                                .addComponent(txtDateReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReserveSectionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBookIDReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReserveSectionLayout.createSequentialGroup()
                        .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(txtMemberIDReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReserveSectionLayout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCopyNoReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ReserveSectionLayout.createSequentialGroup()
                        .addComponent(selectTypeBoxReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txFindReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind_Reserve, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        ReserveSectionLayout.setVerticalGroup(
            ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReserveSectionLayout.createSequentialGroup()
                .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReserveSectionLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(selectTypeBoxReserve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txFindReserve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFind_Reserve)
                            .addComponent(btnClearSearch2))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ReserveSectionLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtBookIDReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txtCopyNoReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtMemberIDReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtDateReserve, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtStatusReserve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(76, 76, 76)
                        .addGroup(ReserveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClearReseve)
                            .addComponent(btnUpdate_Book1))))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tapSection.addTab("    Reserved Section  ", ReserveSection);

        ReserveSection1.setBackground(new java.awt.Color(255, 255, 255));
        ReserveSection1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                ReserveSection1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        BookBorrowTable.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        BookBorrowTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID", "Book Copy No", "Member ID", "Borrow Date", "Due Date ", "Returned Date", "Status", "Panalty Fee"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        BookBorrowTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BookBorrowTableMouseClicked(evt);
            }
        });
        BookBorrowTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BookBorrowTableKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(BookBorrowTable);

        btnUpdate_Borrowed.setBackground(new java.awt.Color(204, 204, 204));
        btnUpdate_Borrowed.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnUpdate_Borrowed.setForeground(new java.awt.Color(0, 0, 0));
        btnUpdate_Borrowed.setText("Update");
        btnUpdate_Borrowed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_BorrowedActionPerformed(evt);
            }
        });

        btnFind_Borrow.setBackground(new java.awt.Color(204, 204, 204));
        btnFind_Borrow.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnFind_Borrow.setForeground(new java.awt.Color(0, 0, 0));
        btnFind_Borrow.setText("Find");
        btnFind_Borrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFind_BorrowActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Book ID");

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Member ID");

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Status");

        txtStatusBorrow.setBackground(new java.awt.Color(255, 255, 255));
        txtStatusBorrow.setEditable(true);
        txtStatusBorrow.setForeground(new java.awt.Color(0, 0, 0));
        txtStatusBorrow.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Borrowed", "Returened" }));
        txtStatusBorrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusBorrowActionPerformed(evt);
            }
        });

        jLabel30.setBackground(new java.awt.Color(255, 255, 255));
        jLabel30.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Copy No");

        btnClearReseve1.setBackground(new java.awt.Color(204, 204, 204));
        btnClearReseve1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnClearReseve1.setForeground(new java.awt.Color(0, 0, 0));
        btnClearReseve1.setText("Clear ");
        btnClearReseve1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearReseve1ActionPerformed(evt);
            }
        });

        selectTypeBoxBorrow.setBackground(new java.awt.Color(255, 255, 255));
        selectTypeBoxBorrow.setEditable(true);
        selectTypeBoxBorrow.setForeground(new java.awt.Color(0, 0, 0));
        selectTypeBoxBorrow.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Book ID", "Copy NO", "Member ID", "Borrow Date", "Due Date", "Status" }));
        selectTypeBoxBorrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTypeBoxBorrowActionPerformed(evt);
            }
        });

        txFindBorrow.setBackground(new java.awt.Color(255, 255, 255));
        txFindBorrow.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txFindBorrow.setForeground(new java.awt.Color(0, 0, 0));
        txFindBorrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txFindBorrowActionPerformed(evt);
            }
        });

        btnClearSearchBorrow.setBackground(new java.awt.Color(204, 204, 204));
        btnClearSearchBorrow.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnClearSearchBorrow.setForeground(new java.awt.Color(0, 0, 0));
        btnClearSearchBorrow.setText("Clear ");
        btnClearSearchBorrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSearchBorrowActionPerformed(evt);
            }
        });

        jLabel31.setBackground(new java.awt.Color(255, 255, 255));
        jLabel31.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Borrow Date");

        txtMemberIDBorrow.setBackground(new java.awt.Color(255, 255, 255));
        txtMemberIDBorrow.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtMemberIDBorrow.setForeground(new java.awt.Color(0, 0, 0));
        txtMemberIDBorrow.setText("-");

        txtBookIDBorrow.setBackground(new java.awt.Color(255, 255, 255));
        txtBookIDBorrow.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookIDBorrow.setForeground(new java.awt.Color(0, 0, 0));
        txtBookIDBorrow.setText("-");

        txtCopyNoBorrow.setBackground(new java.awt.Color(255, 255, 255));
        txtCopyNoBorrow.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtCopyNoBorrow.setForeground(new java.awt.Color(0, 0, 0));
        txtCopyNoBorrow.setText("-");

        txtDateBorrow.setBackground(new java.awt.Color(255, 255, 255));
        txtDateBorrow.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtDateBorrow.setForeground(new java.awt.Color(0, 0, 0));
        txtDateBorrow.setText("-");

        btnDirectBorrow.setBackground(new java.awt.Color(204, 204, 204));
        btnDirectBorrow.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnDirectBorrow.setForeground(new java.awt.Color(0, 0, 0));
        btnDirectBorrow.setText("Direct Borrow");
        btnDirectBorrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirectBorrowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ReserveSection1Layout = new javax.swing.GroupLayout(ReserveSection1);
        ReserveSection1.setLayout(ReserveSection1Layout);
        ReserveSection1Layout.setHorizontalGroup(
            ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReserveSection1Layout.createSequentialGroup()
                .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReserveSection1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ReserveSection1Layout.createSequentialGroup()
                                .addComponent(btnClearReseve1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdate_Borrowed, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReserveSection1Layout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ReserveSection1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txtStatusBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ReserveSection1Layout.createSequentialGroup()
                                        .addComponent(txtDateBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReserveSection1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBookIDBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReserveSection1Layout.createSequentialGroup()
                                .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(txtMemberIDBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReserveSection1Layout.createSequentialGroup()
                                .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCopyNoBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE))
                    .addGroup(ReserveSection1Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(btnDirectBorrow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ReserveSection1Layout.createSequentialGroup()
                        .addComponent(selectTypeBoxBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txFindBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind_Borrow, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearSearchBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        ReserveSection1Layout.setVerticalGroup(
            ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReserveSection1Layout.createSequentialGroup()
                .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReserveSection1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(selectTypeBoxBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txFindBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFind_Borrow)
                            .addComponent(btnClearSearchBorrow))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ReserveSection1Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtBookIDBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtCopyNoBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtMemberIDBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txtDateBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(txtStatusBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(76, 76, 76)
                        .addGroup(ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClearReseve1)
                            .addComponent(btnUpdate_Borrowed))
                        .addGap(32, 32, 32)
                        .addComponent(btnDirectBorrow)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tapSection.addTab("    Borrowed Section   ", ReserveSection1);

        javax.swing.GroupLayout TabSectionLayout = new javax.swing.GroupLayout(TabSection);
        TabSection.setLayout(TabSectionLayout);
        TabSectionLayout.setHorizontalGroup(
            TabSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tapSection)
        );
        TabSectionLayout.setVerticalGroup(
            TabSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabSectionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tapSection, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("ID :");

        lblLabirarianID.setBackground(new java.awt.Color(255, 255, 255));
        lblLabirarianID.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblLabirarianID.setForeground(new java.awt.Color(0, 0, 0));
        lblLabirarianID.setText("-");

        lblLibName.setBackground(new java.awt.Color(255, 255, 255));
        lblLibName.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblLibName.setForeground(new java.awt.Color(0, 0, 0));
        lblLibName.setText("-");

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Name :");

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(414, 414, 414))
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(TabSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelMainLayout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblLabirarianID, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelMainLayout.createSequentialGroup()
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblLibName, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(407, 407, 407)
                .addComponent(txtBack, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(lblLabirarianID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLibName)
                    .addComponent(jLabel25))
                .addGap(9, 9, 9)
                .addComponent(TabSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBackActionPerformed
        // TODO add your handling code here:
        Login login =new Login();
        this.hide();
        login.setVisible(true);
        
        
    }//GEN-LAST:event_txtBackActionPerformed

    private void txtMemberTepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMemberTepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMemberTepActionPerformed

    private void txtMemberAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMemberAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMemberAddressActionPerformed

    private void txtMemeberUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMemeberUpdateActionPerformed
 
        
        if ( txtMemberId.getText().equals("") || txtMamberName.getText().equals("") ||  txtMemberAddress.getText().equals("")  || txtMemberTep.getText().equals("")  ) {
            JOptionPane.showMessageDialog(null, "Select a Row !", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);   
        } else {
            try {
                
            String memberid = txtMemberId.getText();
            String memberName = txtMamberName.getText();
            String memberAdress = txtMemberAddress.getText();
            String tepNo = txtMemberTep.getText();
                
                String bookCatogery = txtBookCatogery.getSelectedItem().toString();
                
                int ID =Integer.parseInt(memberid);
                
                pst = con.prepareStatement("update member set Member_Name = ?,Member_Address = ?,Tel_No = ? where memberID = ?");
                pst.setString(1, memberName);
                pst.setString(2, memberAdress);
                pst.setString(3, tepNo);
                pst.setInt(4, ID);

                int k = pst.executeUpdate();

                if(k==1){

                    txtMemberId.setText("");
                    txtMamberName.setText("");
                    txtMemberAddress.setText("");
                    txtMemberTep.setText("");
                    txtBookIDSec.requestFocus();

                    txtMemberId.requestFocus();
                    
                    memberTableReLoad();
                    
                    txtMemberAdd.setEnabled(true);
                    JOptionPane.showMessageDialog(this,"Member Details Succesfully Updated");
                }
                else{
                    JOptionPane.showMessageDialog(this,"Error:: Can't Update Member Details");
                }

            } catch (SQLException ex) {
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
        }
    }//GEN-LAST:event_txtMemeberUpdateActionPerformed

    private void txtMemeberRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMemeberRemoveActionPerformed
      
        if ( txtMemberId.getText().equals("") || txtMamberName.getText().equals("") ||  txtMemberAddress.getText().equals("")  || txtMemberTep.getText().equals("")  ) {
            JOptionPane.showMessageDialog(null, "Select a Row !", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);   
        } else {
            try {
                
                String memberid = txtMemberId.getText();
//                String memberName = txtMamberName.getText();
//                String memberAdress = txtMemberAddress.getText();
//                String tepNo = txtMemberTep.getText();
                
                String bookCatogery = txtBookCatogery.getSelectedItem().toString();
                
                int ID =Integer.parseInt(memberid);
                
                String role = "Member";
                pst = con.prepareStatement("delete from login where ID = ? "+"and"+" role =?");
                pst.setInt(1, ID);
                pst.setString(2, role);

                int k2 = pst.executeUpdate();
                
                pst = con.prepareStatement("delete from member where memberID = ?");
                pst.setInt(1, ID);
                

                int k1 = pst.executeUpdate();

                if(k1==1 && k2==1){

                    txtMemberId.setText("");
                    txtMamberName.setText("");
                    txtMemberAddress.setText("");
                    txtMemberTep.setText("");
                    txtBookIDSec.requestFocus();

                    txtMemberId.requestFocus();
                    
                    memberTableReLoad();
                    
                    txtMemberAdd.setEnabled(true);
                    JOptionPane.showMessageDialog(this,"Member Details Succesfully Deleted");
                }
                else{
                    JOptionPane.showMessageDialog(this,"Error:: Can't Delete Member Details");
                }

            } catch (SQLException ex) {
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
        }
    }//GEN-LAST:event_txtMemeberRemoveActionPerformed

    private void txtMemberAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMemberAddActionPerformed
        // TODO add your handling code here:
        
                
        
        if (txtMemberId.getText().equals("") || txtMamberName.getText().equals("") ||  txtMemberAddress.getText().equals("")  || txtMemberTep.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Enter item!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                
        } else {
            
            String memberid = txtMemberId.getText();
            String memberName = txtMamberName.getText();
            String memberAdress = txtMemberAddress.getText();
            //String bookCatogery = txtMemberTep.getSelectedItem().toString();
            String tepNo = txtMemberTep.getText();

            try {
                pst = con.prepareStatement("insert into member(memberID,member_Name,member_Address,Tel_No)values(?,?,?,?)");

                pst.setString(1, memberid);
                pst.setString(2, memberName);
                pst.setString(3, memberAdress);
                pst.setString(4, tepNo);
               // pst.setString(5, Author);
                int k1 = pst.executeUpdate();

                if(k1==1){
                    
                    int intID = Integer.parseInt(memberid);
                    
                    LoginDetailsSet.memberID = intID;
                    LoginDetailsSet.memberName = memberName;
                    LoginDetailsSet.role = "Member";

                    txtMemberId.setText("");
                    txtMamberName.setText("");
                    txtMemberAddress.setText("");
                    //txtBookCatogery.setSelectedIndex(-1);
                    txtMemberTep.setText("");
                    txtBookIDSec.requestFocus();

                    memberTableReLoad();
                    
                    
                    LoginDetailsSet loginSet = new  LoginDetailsSet();
                    loginSet.setVisible(true);
                    
                    
                    

                    JOptionPane.showMessageDialog(this,"New Member Succesfully Added");

                }
                else{
                    JOptionPane.showMessageDialog(this,"Error:: Can't Add new Member");
                   
                }

            } catch (SQLException ex) {
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex , "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                // JOptionPane.showMessageDialog(null  ,ex);
            }
        }
    }//GEN-LAST:event_txtMemberAddActionPerformed

    private void txtMamberNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMamberNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMamberNameActionPerformed

    private void txtBookIDSecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookIDSecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBookIDSecActionPerformed

    private void txtISBNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtISBNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtISBNActionPerformed

    private void txtBookCatogeryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookCatogeryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBookCatogeryActionPerformed

    private void txtAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAuthorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAuthorActionPerformed

    private void btnFind_BookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind_BookActionPerformed
        // TODO add your handling code here:

        if( txtBookFind.getText().equals("") || selectTypeBox.getSelectedItem().toString().equals("") )
            JOptionPane.showMessageDialog(null, "Enter item!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
        else{

            String typeGets = selectTypeBox.getSelectedItem().toString();

            String type;

            try {

                if(typeGets == "ID"){
                    int text = 0;
                    type = "Book_id";
                    String textGets = txtBookFind.getText();
                    text= Integer.parseInt(textGets);

                    pst =con.prepareStatement("Select * from book where "+ type +" = "+ text);
                }
                else if(typeGets == "ISBN NO"){

                    type = "ISBN";
                    String textGets = txtBookFind.getText();
                    //String finalText = textGets+"%";
                    
                    pst =con.prepareStatement("Select * from book where "+ type +" =?");
                    pst.setString(1,textGets);

                }
                else if(typeGets == "Book Title"){
                    int text = 0;
                    type = "Title";
                    String textGets = txtBookFind.getText();
                    //text= Integer.parseInt(textGets);
                    String finalText = "%"+textGets+"%";
                        System.out.println(finalText);

                    pst =con.prepareStatement("Select * from book where "+ type +" like ?");
                    pst.setString(1,finalText);
                }
                else if(typeGets == "Book Catogery"){
                    int text = 0;
                    type = "catogery";
                    String textGets = txtBookFind.getText();
                   // text= Integer.parseInt(textGets);
                   String finalText ="%"+textGets+"%";
                  
                    pst =con.prepareStatement("Select * from book where "+ type +" like ?");
                    pst.setString(1,finalText);
                }
                else if(typeGets == "Author"){
                    int text = 0;
                    type = "Author";
                    String textGets = txtBookFind.getText();
                   String finalText ="%"+ textGets+"%";

                    pst =con.prepareStatement("Select * from book where "+ type +" like ?");
                    pst.setString(1,finalText);

                }
                else{
                    int text = 0;
                    type = "book_Count";

                    String textGets = txtBookFind.getText();
                    text= Integer.parseInt(textGets);

                    pst =con.prepareStatement("Select * from book where "+ type +" = "+ text);
                }
                
                rs= pst.executeQuery();

                 //no result error mag popup    
                if( rs.next()==false){
                    JOptionPane.showMessageDialog(null, "No Result Found!", "Oops ...!", JOptionPane.ERROR_MESSAGE);

                }else{
                    
                    int c;

                    ResultSetMetaData rsd;
                    rsd = rs.getMetaData();

                    c =rsd.getColumnCount();

                    DefaultTableModel d =(DefaultTableModel) BookTable.getModel();
                    d.setRowCount(0);

                    do{
                        Vector v1 = new Vector();
                        //System.out.println(v1);

                            for(int i=1;i<=c;i++){
                               // int idrs = rs.getInt("Book_id");

                                v1.add(rs.getInt("Book_id"));
                                    //System.out.println(rs.getInt("Book_id"));
                                v1.add(rs.getString("ISBN"));
                                    //System.out.println(rs.getString("ISBN"));
                                v1.add(rs.getString("Title"));
                                    //System.out.println(rs.getString("Title"));
                                v1.add(rs.getString("catogery"));
                                    //System.out.println(rs.getString("catogery"));
                                v1.add(rs.getString("Author"));

                                v1.add(rs.getInt("book_count"));
                                    //System.out.println(rs.getInt("book_count"));

                           // System.out.print(v1);//

                           }
                            System.out.println(v1);
                        d.addRow(v1);
                    }while(rs.next());//
                }

                //txtBookFind.setText("");

                //JOptionPane.showMessageDialog(null, "Item(s) not found!", "Ooops!", JOptionPane.ERROR_MESSAGE);

            } catch (SQLException ex) {
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
            catch(Exception ex){
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
        }
        
    }//GEN-LAST:event_btnFind_BookActionPerformed

    private void btnUpdate_BookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_BookActionPerformed


        if ( txtBookIDSec.getText().equals("") || txtISBN.getText().equals("") ||  txtBookTitle.getText().equals("") || txtBookCatogery.getSelectedItem().toString().equals("") || txtAuthor.getText().equals("") ) {
            JOptionPane.showMessageDialog(null, "Select a Row !", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);   
        } else {
            try {
                
                String bookid = txtBookIDSec.getText();
                String isbnNO = txtISBN.getText();
                String bookTitle = txtBookTitle.getText();

                String Author = txtAuthor.getText();
                
                String bookCatogery = txtBookCatogery.getSelectedItem().toString();
                
                int ID =Integer.parseInt(bookid);
                
                pst = con.prepareStatement("update book set ISBN = ?,Title = ?,catogery = ?,Author = ? where Book_id = ?");
                pst.setString(1, isbnNO);
                pst.setString(2, bookTitle);
                pst.setString(3, bookCatogery);
                pst.setString(4, Author);
                pst.setInt(5, ID);

                int k = pst.executeUpdate();

                if(k==1){

                    txtBookIDSec.setText("");
                    txtISBN.setText("");
                    txtBookTitle.setText("");
                    txtBookCatogery.setSelectedIndex(-1);
                    txtAuthor.setText("");
                    txtBookCount.setText("-");

                    txtBookIDSec.requestFocus();
                    bookTableReLoad();
                    btnAdd_Book.setEnabled(true);
                    JOptionPane.showMessageDialog(this,"Book Succesfully Updated");
                }
                else{
                    JOptionPane.showMessageDialog(this,"Error:: Can't Update Book");
                }

            } catch (SQLException ex) {
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
        }
    }//GEN-LAST:event_btnUpdate_BookActionPerformed

    private void btnDelete_BookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete_BookActionPerformed
        // TODO add your handling code here:

        int id =Integer.parseInt(txtBookIDSec.getText()) ;

//        String isbnNO = txtISBN.getText();
//        String bookTitle = txtBookTitle.getText();
//        String bookCatogery = txtBookCatogery.getSelectedItem().toString();
//        String Author = txtAuthor.getText();

        try {
            pst = con.prepareStatement("delete from book where Book_id = ?");

            pst.setInt(1, id);

            int k = pst.executeUpdate();

            if(k==1){

                txtBookIDSec.setText("");
                txtISBN.setText("");
                txtBookTitle.setText("");
                txtBookCatogery.setSelectedIndex(-1);
                txtAuthor.setText("");
                txtBookCount.setText("-");

                txtBookIDSec.requestFocus();
                
                bookTableReLoad();
                
                btnAdd_Book.setEnabled(true);
                
                btnUpdate_Book.setEnabled(false);
                btnDelete_Book.setEnabled(false);
                JOptionPane.showMessageDialog(this,"Book Succesfully Deleted");
            }
            else{
                JOptionPane.showMessageDialog(this,"Error:: Can't Delete Book");
            }

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }
    }//GEN-LAST:event_btnDelete_BookActionPerformed

    private void BookTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BookTableMouseClicked

        loadTableData();
    }//GEN-LAST:event_BookTableMouseClicked

    private void btnAdd_BookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_BookActionPerformed
        // TODO add your handling code here:

        if (txtBookIDSec.getText().equals("") || txtISBN.getText().equals("") ||  txtBookTitle.getText().equals("") || txtBookCatogery.getSelectedItem().toString().equals("") || txtAuthor.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Enter item!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                
        } else {
            
            String bookid = txtBookIDSec.getText();
            String isbnNO = txtISBN.getText();
            String bookTitle = txtBookTitle.getText();
            String bookCatogery = txtBookCatogery.getSelectedItem().toString();
            String Author = txtAuthor.getText();

            try {
                pst = con.prepareStatement("insert into book(Book_id,ISBN,Title,catogery,Author)values(?,?,?,?,?)");

                pst.setString(1, bookid);
                pst.setString(2, isbnNO);
                pst.setString(3, bookTitle);
                pst.setString(4, bookCatogery);
                pst.setString(5, Author);
                int k = pst.executeUpdate();

                if(k==1){

                    txtBookIDSec.setText("");
                    txtISBN.setText("");
                    txtBookTitle.setText("");
                    txtBookCatogery.setSelectedIndex(-1);
                    txtAuthor.setText("");
                    txtBookIDSec.requestFocus();
                    bookTableReLoad();

                    JOptionPane.showMessageDialog(this,"New Book Succesfully Added");

                }
                else{
                    JOptionPane.showMessageDialog(this,"Error:: Can't Add new Book");
                   
                }

            } catch (SQLException ex) {
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex , "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                // JOptionPane.showMessageDialog(null  ,ex);
            }
        }
    }//GEN-LAST:event_btnAdd_BookActionPerformed

    private void btnAdd_bookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_bookCopyActionPerformed
        // TODO add your handling code here:
        
//        java.util.Date date=new java.util.Date();
//        java.sql.Date sqlDate=new java.sql.Date(date.getTime());
      
        if (txtCopyNo.getText().equals("") || txtBookID.getText().equals("") ||  txtEdition.getText().equals("") || txtDate.getText().equals("") || txtPrice.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Enter item!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                
        } else {

                String copyno = txtCopyNo.getText();
                String bookId = txtBookID.getText();
                String edition = txtEdition.getText();
                String pDate = txtDate.getText();
                String sPrice = txtPrice.getText();

                //int intEdition = Integer.parseInt(edition);
                double dprice = Double.parseDouble(sPrice);

                try {
                    pst = con.prepareStatement("insert into bookcopy(copy_No,Book_id, Edition, Lib_ID, Purchase_date, Price)values(?,?,?,?,?,?)");

                    pst.setString(1, copyno);
                    pst.setString(2, bookId);
                    pst.setString(3, edition);
                    pst.setInt(4, libID);
                    pst.setString(5, pDate);
                    pst.setDouble(6, dprice);

                    int k1 = pst.executeUpdate();

//                    pst = con.prepareStatement("insert into purchase(copy_no,Lib_ID,Purchase_date,Price) values(?,?,?,?)");
//
//                    pst.setString(1, copyno);
//                    pst.setInt(2, libID);
//                    pst.setString(3, pDate);
//                    pst.setDouble(4, dprice);
//
//                    int k2 = pst.executeUpdate();           

                    if(k1==1   /*  && k2==1*/){

                        //get book Count
                        increase_Book_Count(bookId);

                        txtCopyNo.setText("");
                        txtBookID.setText("");
                        txtEdition.setText("");
                        txtDate.setText("");
                        txtPrice.setText("");
                        txtCopyNo.requestFocus();

                        bookCopyTable_reLoad();


                        btnUpdate_bookCopy.setEnabled(false);
                        btnDelete_bookCopy.setEnabled(false);

                        JOptionPane.showMessageDialog(this,"New Book Copy Succesfully Added");

                    }
                    else{
                        JOptionPane.showMessageDialog(this,"Error:: Can't Add new Book Copy");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex , "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                    //JOptionPane.showMessageDialog(null  ,ex);
                }
        }
        
        
    }//GEN-LAST:event_btnAdd_bookCopyActionPerformed

    private void Book_Copy_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Book_Copy_TableMouseClicked
        
        DefaultTableModel d1 =(DefaultTableModel )Book_Copy_Table.getModel();
        int selectIndex =Book_Copy_Table.getSelectedRow();

        txtCopyNo.setText(d1.getValueAt(selectIndex, 0).toString());
        txtBookID.setText(d1.getValueAt(selectIndex, 1).toString());
        txtEdition.setText(d1.getValueAt(selectIndex, 2).toString());
        txtDate.setText(d1.getValueAt(selectIndex, 3).toString());
        //txtPrice.setSelectedItem(d1.getValueAt(selectIndex, 3).toString());
        txtPrice.setText(d1.getValueAt(selectIndex, 4).toString());
        //txtBookCount.setText(d1.getValueAt(selectIndex, 5).toString());
        
      // btnAdd_bookCopy.setEnabled(false);
        
        btnUpdate_bookCopy.setEnabled(true);
        btnDelete_bookCopy.setEnabled(true);
    }//GEN-LAST:event_Book_Copy_TableMouseClicked

    private void btnDelete_bookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete_bookCopyActionPerformed
        // TODO add your handling code here:
        
         int copyNo =Integer.parseInt(txtCopyNo.getText()) ;
         String bookID =txtBookID.getText() ;

//        String isbnNO = txtISBN.getText();
//        String bookTitle = txtBookTitle.getText();
//        String bookCatogery = txtBookCatogery.getSelectedItem().toString();
//        String Author = txtAuthor.getText();

        try {
            
//            pst=con.prepareStatement("delete from purchase where copy_no = ? ");
//            
//            pst.setInt(1, copyNo);
//            
//             int k1 = pst.executeUpdate();
            
            pst = con.prepareStatement("delete from bookcopy where copy_no = ?");

            pst.setInt(1, copyNo);

            int k2 = pst.executeUpdate();

            if(/*k1==1 && */ k2==1){

                txtCopyNo.setText("");
                txtBookID.setText("");
                txtEdition.setText("");
                txtDate.setText("");
                txtPrice.setText("");
                txtCopyNo.requestFocus();
                
                bookCopyTable_reLoad();
                
                
                 decrease_Book_Count(bookID);
                
                
                btnAdd_bookCopy.setEnabled(true);
                btnDelete_Book.setEnabled(false);
                JOptionPane.showMessageDialog(this,"Book Succesfully Deleted");
            }
            else{
                JOptionPane.showMessageDialog(this,"Error:: Can't Delete Book");
            }

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }
        
    }//GEN-LAST:event_btnDelete_bookCopyActionPerformed

    private void btnUpdate_bookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_bookCopyActionPerformed
        // TODO add your handling code here:
        
        
        DefaultTableModel d1 =(DefaultTableModel )Book_Copy_Table.getModel();
        int selectIndex =Book_Copy_Table.getSelectedRow();

        String oldID = d1.getValueAt(selectIndex, 1).toString();
        
        String newID =  txtBookID.getText();
        //copyNo.setText(d1.getValueAt(selectIndex, 0).toString());
        
        if( oldID != newID ){
                
                if ( txtCopyNo.getText().equals("") || txtBookID.getText().equals("") ||  txtDate.getText().equals("") ||  txtPrice.getText().equals("") ) {
                        JOptionPane.showMessageDialog(null, "Select a Row !", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);   
                } else {
                    
                    try {

                        String copyno = txtCopyNo.getText();
                        String bookId = txtBookID.getText();
                        String Edition  =txtEdition.getText();
                        String pDate = txtDate.getText();
                        String sPrice = txtPrice.getText();

                        double dprice = Double.parseDouble(sPrice);

        //                String Author = txtAuthor.getText();
        //                
        //                String bookCatogery = txtBookCatogery.getSelectedItem().toString();
        //                
                       int ID =Integer.parseInt(copyno);
                       
//                       pst = con.prepareStatement("update purchase set  Lib_ID = ?, Purchase_date = ?,Price = ? where copy_no = ?");
//                       pst.setInt(1, libID); 
//                       pst.setString(2, pDate);
//                       pst.setDouble(3, dprice);
//
//                        pst.setInt(4, ID);
//                        
//                        int k1 = pst.executeUpdate();

                        pst = con.prepareStatement("update bookcopy set Book_id = ?, Edition = ?, Lib_ID = ?, Purchase_date = ?,Price = ? where copy_no = ?");
                        pst.setString(1, bookId);
                        pst.setString(2, Edition);
                       pst.setInt(3, libID); 
                       pst.setString(4, pDate);
                       pst.setDouble(5, dprice);

                        pst.setInt(6, ID);

                        int k2 = pst.executeUpdate();

                        if(/*k1==1 && */k2==1){

                            txtCopyNo.setText("");
                            txtBookID.setText("");
                            txtEdition.setText("");
                            txtDate.setText("");
                            txtPrice.setText("");


                            txtCopyNo.requestFocus();
                            bookCopyTable_reLoad();

                            
                             increase_Book_Count(newID);
                             
                             decrease_Book_Count(oldID);

                            btnAdd_bookCopy.setEnabled(true);
                            
                            btnUpdate_bookCopy.setEnabled(false);
                            btnDelete_bookCopy.setEnabled(false);
                            
                            
                            JOptionPane.showMessageDialog(this,"Book Succesfully Updated");
                        }
                        else{
                            JOptionPane.showMessageDialog(this,"Error:: Can't Update Book");
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null  ,ex);
                    }
                }
            
        }else{
            
                if ( txtCopyNo.getText().equals("") || txtBookID.getText().equals("") ||  txtDate.getText().equals("") ||  txtPrice.getText().equals("") ) {
                    JOptionPane.showMessageDialog(null, "Select a Row !", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);   
                } else {
                    try {

                        String copyno = txtCopyNo.getText();
                        String bookId = txtBookID.getText();
                        String pDate = txtDate.getText();
                        String sPrice = txtPrice.getText();

                        double dprice = Double.parseDouble(sPrice);

        //                String Author = txtAuthor.getText();
        //                
        //                String bookCatogery = txtBookCatogery.getSelectedItem().toString();
        //                
                       int ID =Integer.parseInt(copyno);

                        pst = con.prepareStatement("update bookcopy set Book_id = ?, purchase_date = ?,price = ? where copy_No = ?");
                        pst.setString(1, bookId);
                        pst.setString(2, pDate);
                        pst.setDouble(3, dprice);
                        //pst.setString(4, copyno);
                        pst.setInt(4, ID);

                        int k = pst.executeUpdate();

                        if(k==1){

                            txtCopyNo.setText("");
                            txtBookID.setText("");
                            txtDate.setText("");
                            //txtBookCatogery.setSelectedIndex(-1);
                            txtPrice.setText("");
                            //txtBookCount.setText("-");

                            txtCopyNo.requestFocus();
                            bookCopyTable_reLoad();


                            btnAdd_bookCopy.setEnabled(true);
                            
                            btnUpdate_bookCopy.setEnabled(false);
                            btnDelete_bookCopy.setEnabled(false);
                            JOptionPane.showMessageDialog(this,"Book Succesfully Updated");
                        }
                        else{
                            JOptionPane.showMessageDialog(this,"Error:: Can't Update Book");
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null  ,ex);
                    }
                }
        }
        
    }//GEN-LAST:event_btnUpdate_bookCopyActionPerformed

    private void txtBookIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBookIDActionPerformed

    private void txtCopyNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCopyNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCopyNoActionPerformed

    private void memberTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_memberTableMouseClicked

        DefaultTableModel d1 =(DefaultTableModel )memberTable.getModel();
        int selectIndex =memberTable.getSelectedRow();

        txtMemberId.setText(d1.getValueAt(selectIndex, 0).toString());
        txtMamberName.setText(d1.getValueAt(selectIndex, 1).toString());
        txtMemberAddress.setText(d1.getValueAt(selectIndex, 2).toString());
        txtMemberTep.setText(d1.getValueAt(selectIndex, 3).toString());
        //txtPrice.setSelectedItem(d1.getValueAt(selectIndex, 3).toString());
        //txtPrice.setText(d1.getValueAt(selectIndex, 4).toString());
        //txtBookCount.setText(d1.getValueAt(selectIndex, 5).toString());
        
        LoginDetailsSet.memberID =Integer.parseInt(d1.getValueAt(selectIndex, 0).toString());
        LoginDetailsSet.memberName = d1.getValueAt(selectIndex, 1).toString();
        
        //txtMemberAdd.setEnabled(false);
        txtMemeberUpdate.setEnabled(true);
        txtMemeberRemove.setEnabled(true);
        btnloginDeUpdate.setEnabled(true);
        
    }//GEN-LAST:event_memberTableMouseClicked

    private void btnClearBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearBookActionPerformed
        // TODO add your handling code here:
                txtBookIDSec.setText("");
                txtISBN.setText("");
                txtBookTitle.setText("");
                txtBookCatogery.setSelectedIndex(-1);
                txtAuthor.setText("");
                txtBookCount.setText("-");
                
                btnAdd_Book.setEnabled(true);
                btnUpdate_Book.setEnabled(false);
                btnDelete_Book.setEnabled(false);
    }//GEN-LAST:event_btnClearBookActionPerformed

    private void selectTypeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTypeBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectTypeBoxActionPerformed

    private void txtBookFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBookFindActionPerformed

    private void btnClearSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSearchActionPerformed
        // TODO add your handling code here:
        
        txtBookFind.setText("");
        bookTableReLoad();
    }//GEN-LAST:event_btnClearSearchActionPerformed

    private void selectTypeBoxMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTypeBoxMemberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectTypeBoxMemberActionPerformed

    private void txtMemberFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMemberFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMemberFindActionPerformed

    private void btnFind_MemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind_MemberActionPerformed
        // TODO add your handling code here:
        
        if( txtMemberFind.getText().equals("") || selectTypeBoxMember.getSelectedItem().toString().equals("") )
            JOptionPane.showMessageDialog(null, "Enter item!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
        else{

            String typeGets = selectTypeBoxMember.getSelectedItem().toString();

            String type;

            try {

                if(typeGets == "Member Id"){
                    int text = 0;
                    type = "memberID";
                    String textGets = txtMemberFind.getText();
                    text= Integer.parseInt(textGets);

                    pst =con.prepareStatement("Select * from member where "+ type +" = "+ text);
                }
                else if(typeGets == "Name"){

                    type = "Member_Name";
                    String textGets = txtMemberFind.getText();
                    //String finalText = textGets+"%";
                    
                    pst =con.prepareStatement("Select * from member where "+ type +" =?");
                    pst.setString(1,textGets);

                }
                else if(typeGets == "Address"){
                    int text = 0;
                    type = "Member_Address";
                    String textGets = txtMemberFind.getText();
                    //text= Integer.parseInt(textGets);
                    String finalText = "%"+textGets+"%";
                    //System.out.println(finalText);

                    pst =con.prepareStatement("Select * from member where "+ type +" like ?");
                    pst.setString(1,finalText);
                }
                else{
                    int text = 0;
                    type = "Tel_No";
                    String textGets = txtMemberFind.getText();
                   // text= Integer.parseInt(textGets);
                   String finalText ="%"+textGets+"%";
                  
                    pst =con.prepareStatement("Select * from member where "+ type +" like ?");
                    pst.setString(1,finalText);
                }

                
                rs= pst.executeQuery();

                 //no result error mag popup    
                if( rs.next()==false){
                    JOptionPane.showMessageDialog(null, "No Result Found!", "Oops ...!", JOptionPane.ERROR_MESSAGE);

                }else{
                    
                    int c;

                    ResultSetMetaData rsd;
                    rsd = rs.getMetaData();

                    c =rsd.getColumnCount();

                    DefaultTableModel d =(DefaultTableModel) memberTable.getModel();
                    d.setRowCount(0);

                    do{
                        Vector v1 = new Vector();
                        //System.out.println(v1);

                            for(int i=1;i<=c;i++){
                               // int idrs = rs.getInt("Book_id");

                                v1.add(rs.getInt("memberID"));
                                    //System.out.println(rs.getInt("Book_id"));
                                v1.add(rs.getString("Member_Name"));
                                    //System.out.println(rs.getString("ISBN"));
                                v1.add(rs.getString("Member_Address"));
                                    //System.out.println(rs.getString("Title"));
                                v1.add(rs.getString("Tel_No"));
                                    //System.out.println(rs.getString("catogery"));
                                //v1.add(rs.getString("Author"));

                                //v1.add(rs.getInt("book_count"));
                                    //System.out.println(rs.getInt("book_count"));

                           // System.out.print(v1);//

                           }
                            System.out.println(v1);
                        d.addRow(v1);
                    }while(rs.next());//
                }


            } catch (SQLException ex) {
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
            catch(Exception ex){
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
        }
        
    }//GEN-LAST:event_btnFind_MemberActionPerformed

    private void btnClearSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSearch1ActionPerformed
        // TODO add your handling code here:
        
        txtMemberFind.setText("");
        memberTableReLoad();
    }//GEN-LAST:event_btnClearSearch1ActionPerformed

    private void selectTypeBoxBookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTypeBoxBookCopyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectTypeBoxBookCopyActionPerformed

    private void txtBookCopyFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookCopyFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBookCopyFindActionPerformed

    private void btnFind_BookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind_BookCopyActionPerformed
        // TODO add your handling code here:
        
if( txtBookCopyFind.getText().equals("") || selectTypeBoxBookCopy.getSelectedItem().toString().equals("") )
            JOptionPane.showMessageDialog(null, "Enter item!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
        else{

            String typeGets = selectTypeBoxBookCopy.getSelectedItem().toString();

            String type;

            try {

                if(typeGets == "Copy No"){
                    int text = 0;
                    type = "copy_no";
                    String textGets = txtBookCopyFind.getText();
                    text= Integer.parseInt(textGets);
                    
                    //SELECT * FROM "+" bookcopy"+" NATURAL JOIN"+" purchase"
                    pst =con.prepareStatement("Select * from bookcopy NATURAL JOIN purchase where "+ type +" = "+ text);
                }
                else if(typeGets == "Book ID"){

                    type = "Book_id";
                    String textGets = txtBookCopyFind.getText();
                    //String finalText = textGets+"%";
                    
                    pst =con.prepareStatement("Select * from bookcopy NATURAL JOIN purchase where "+ type +" =?");
                    pst.setString(1,textGets);

                }
                else if(typeGets == "Edition"){
                    int text = 0;
                    type = "Edition";
                    String textGets = txtBookCopyFind.getText();
                    //text= Integer.parseInt(textGets);
                    String finalText = "%"+textGets+"%";
                        System.out.println(finalText);

                    pst =con.prepareStatement("Select * from bookcopy NATURAL JOIN purchase where "+ type +" like ?");
                    pst.setString(1,finalText);
                }
                else if(typeGets == "Purchase Date"){
                    int text = 0;
                    type = "Purchase_date";
                    String textGets = txtBookCopyFind.getText();
                   // text= Integer.parseInt(textGets);
                   String finalText ="%"+textGets+"%";
                  
                    pst =con.prepareStatement("Select * from bookcopy NATURAL JOIN purchase where "+ type +" like ?");
                    pst.setString(1,finalText);
                }
                else{
                    int text = 0;
                    type = "Price";
                    String textGets = txtBookCopyFind.getText();
                    String finalText ="%"+ textGets+"%";

                    pst =con.prepareStatement("Select * from bookcopy NATURAL JOIN purchase where "+ type +" like ?");
                    pst.setString(1,finalText);

               }

                
                rs= pst.executeQuery();

                 //no result error mag popup    
                if( rs.next()==false){
                    JOptionPane.showMessageDialog(null, "No Result Found!", "Oops ...!", JOptionPane.ERROR_MESSAGE);

                }else{
                    
                    int c;

                    ResultSetMetaData rsd;
                    rsd = rs.getMetaData();

                    c =rsd.getColumnCount();

                    DefaultTableModel d =(DefaultTableModel) Book_Copy_Table.getModel();
                    d.setRowCount(0);

                    do{
                        Vector v1 = new Vector();
                        //System.out.println(v1);

                            for(int i=1;i<=c;i++){
                               // int idrs = rs.getInt("Book_id");

                                v1.add(rs.getInt("copy_no"));
                                    //System.out.println(rs.getInt("Book_id"));
                                v1.add(rs.getInt("Book_id"));
                                    //System.out.println(rs.getString("ISBN"));
                                v1.add(rs.getString("Edition"));
                                    //System.out.println(rs.getString("Title"));
                                v1.add(rs.getString("Purchase_date"));
                                    //System.out.println(rs.getString("catogery"));
                                v1.add(rs.getInt("Price"));

                               // v1.add(rs.getInt("book_count"));
                                    //System.out.println(rs.getInt("book_count"));

                           // System.out.print(v1);//

                           }
                            //System.out.println(v1);
                        d.addRow(v1);
                    }while(rs.next());//
                }

                //txtBookFind.setText("");

                //JOptionPane.showMessageDialog(null, "Item(s) not found!", "Ooops!", JOptionPane.ERROR_MESSAGE);

            } catch (SQLException ex) {
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
            catch(Exception ex){
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
        }
        
    }//GEN-LAST:event_btnFind_BookCopyActionPerformed

    private void btnClearSearchBookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSearchBookCopyActionPerformed
        // TODO add your handling code here:
        
        txtBookCopyFind.setText("");
         bookCopyTable_reLoad();
    }//GEN-LAST:event_btnClearSearchBookCopyActionPerformed

    private void BookTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BookTableKeyReleased
        // TODO add your handling code here:
        
        loadTableData();
    }//GEN-LAST:event_BookTableKeyReleased

    private void btnClearBookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearBookCopyActionPerformed
        // TODO add your handling code here:
        
        txtCopyNo.setText(" ");
        txtBookID.setText("");
        txtEdition.setText("");
        txtDate.setText("");
        txtPrice.setText("");
       
        
        btnAdd_bookCopy.setEnabled(true);
        btnUpdate_bookCopy.setEnabled(false);
        btnDelete_bookCopy.setEnabled(false);
    }//GEN-LAST:event_btnClearBookCopyActionPerformed

    private void Book_Copy_TableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Book_Copy_TableKeyReleased
        
        DefaultTableModel d1 =(DefaultTableModel )Book_Copy_Table.getModel();
        int selectIndex =Book_Copy_Table.getSelectedRow();

        txtCopyNo.setText(d1.getValueAt(selectIndex, 0).toString());
        txtBookID.setText(d1.getValueAt(selectIndex, 1).toString());
        txtEdition.setText(d1.getValueAt(selectIndex, 2).toString());
        txtDate.setText(d1.getValueAt(selectIndex, 3).toString());
        //txtPrice.setSelectedItem(d1.getValueAt(selectIndex, 3).toString());
        txtPrice.setText(d1.getValueAt(selectIndex, 4).toString());
        //txtBookCount.setText(d1.getValueAt(selectIndex, 5).toString());
        
//        btnAdd_bookCopy.setEnabled(false);
//        
//        btnUpdate_bookCopy.setEnabled(true);
//        btnDelete_bookCopy.setEnabled(true);
    }//GEN-LAST:event_Book_Copy_TableKeyReleased

    private void txtEditionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEditionActionPerformed

    private void BookReserveTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BookReserveTableMouseClicked
        
        DefaultTableModel d1 =(DefaultTableModel )BookReserveTable.getModel();
        int selectIndex =BookReserveTable.getSelectedRow();

        txtBookIDReserve.setText(d1.getValueAt(selectIndex, 0).toString());
        txtCopyNoReserve.setText(d1.getValueAt(selectIndex, 1).toString());
        txtMemberIDReserve.setText(d1.getValueAt(selectIndex, 2).toString());
        txtDateReserve.setText(d1.getValueAt(selectIndex, 3).toString());
        txtStatusReserve.setSelectedItem(d1.getValueAt(selectIndex, 4).toString());

    }//GEN-LAST:event_BookReserveTableMouseClicked

    private void BookReserveTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BookReserveTableKeyReleased
        DefaultTableModel d1 =(DefaultTableModel )BookReserveTable.getModel();
        int selectIndex =BookReserveTable.getSelectedRow();

        txtBookIDReserve.setText(d1.getValueAt(selectIndex, 0).toString());
        txtCopyNoReserve.setText(d1.getValueAt(selectIndex, 1).toString());
        txtMemberIDReserve.setText(d1.getValueAt(selectIndex, 2).toString());
        txtDateReserve.setText(d1.getValueAt(selectIndex, 3).toString());
        txtStatusReserve.setSelectedItem(d1.getValueAt(selectIndex, 4).toString());
    }//GEN-LAST:event_BookReserveTableKeyReleased

    private void btnUpdate_Book1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_Book1ActionPerformed
        
        if ( txtBookIDReserve.getText().equals("") ||  txtCopyNoReserve.getText().equals("")  || txtMemberIDReserve.getText().equals("") || txtDateReserve.getText().equals("")  || txtStatusReserve.getSelectedItem().equals("")  ) {
                JOptionPane.showMessageDialog(null, "Enter item!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                
        } else {
            
            String bookID = txtBookIDReserve.getText();
            String copyNo = txtCopyNoReserve.getText();
            String  memberid = txtMemberIDReserve.getText();
            String resdate =  txtDateReserve.getText();
            //String bookCatogery = txtMemberTep.getSelectedItem().toString();
            String status = txtStatusReserve.getSelectedItem().toString();;
            
            

            try {
                pst = con.prepareStatement("update  reserve set Status = ? where  Book_id = ? and copy_no = ?  and memberID = ? and Reserve_Date = ? ");

                pst.setString(1, status);
                pst.setInt(2, Integer.parseInt(bookID));
                pst.setInt(3, Integer.parseInt(copyNo));
                pst.setInt(4, Integer.parseInt(memberid));
                pst.setString(5, resdate);
               // pst.setString(5, Author);
                int k1 = pst.executeUpdate();

                if(k1==1){
                    
                    
                        if(status == "Picked Up"){
                            try{
        
                            pst =con.prepareStatement("insert into borrow(Book_id, copy_no ,  memberID, Borrow_Date, Due_Date, Status ) value (?,?,?,?,?,?) ");
                            pst.setInt(1, Integer.parseInt(bookID) );
                            pst.setInt(2, Integer.parseInt(copyNo) );
                            pst.setInt(3, Integer.parseInt(memberid) );
                            pst.setString(4, DateTime.currentDate().toString());
                            pst.setString(5, DateTime.reternDate().toString());
                            pst.setString(6, "Borrowed"); 
                            
                             int k2 = pst.executeUpdate();
                            
                                     if(k2==1){
              
                                                txtBookIDReserve.setText("-");
                                                txtCopyNoReserve.setText("-");
                                                txtMemberIDReserve.setText("-");
                                                txtDateReserve.setText("-");
                                                txtStatusReserve.setSelectedItem(""); 

                                                 txtStatusReserve.requestFocus();

                                                 reserveTableReload();
                                                 borrowTableReload();

                                                 JOptionPane.showMessageDialog(this,"Reservation Succesfully Updated");
    
                                     }else{
                                               JOptionPane.showMessageDialog(this,"Error::  Reservation Updated But Can't Update Borrrows  ");
                                     }
                            }
                            catch(SQLException ex){
                                        Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                                        JOptionPane.showMessageDialog(null, ex , "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                            }
                            
                        }else if(status == "Reserved"){
                            
                             JOptionPane.showMessageDialog(this,"Error::  Can't undo Pickedup Books  ");
                        }

                }
                else{
                    JOptionPane.showMessageDialog(this,"Error:: Can't Update Reservation");
                   
                }

            } catch (SQLException ex) {
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex , "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                // JOptionPane.showMessageDialog(null  ,ex);
            }
        }
    }//GEN-LAST:event_btnUpdate_Book1ActionPerformed

    private void btnFind_ReserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind_ReserveActionPerformed
     
        if( txFindReserve.getText().equals("") || selectTypeBoxReserve.getSelectedItem().toString().equals("") )
            JOptionPane.showMessageDialog(null, "Enter item!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
        else{

            String typeGets = selectTypeBoxReserve.getSelectedItem().toString();

            String type;

            try {

                if(typeGets == "Book ID"){
                    int text = 0;
                    type = "Book_id";
                    String textGets = txFindReserve.getText();
                    text= Integer.parseInt(textGets);
                    
                    //SELECT * FROM "+" bookcopy"+" NATURAL JOIN"+" purchase"
                    pst =con.prepareStatement("Select * from reserve  where "+ type +" = "+ text);
                }
                else if(typeGets == "Copy NO"){

                    type = "copy_no";
                    String textGets = txFindReserve.getText();
                    //String finalText = textGets+"%";
                    
                    pst =con.prepareStatement("Select * from reserve  where "+ type +" =?");
                    pst.setString(1,textGets);

                }
                else if(typeGets == "Member ID"){
                    int text = 0;
                    type = "memberID";
                    String textGets = txFindReserve.getText();
                    //text= Integer.parseInt(textGets);
                    String finalText = "%"+textGets+"%";
                        System.out.println(finalText);

                    pst =con.prepareStatement("Select * from reserve where "+ type +" like ?");
                    pst.setString(1,finalText);
                }
                else if(typeGets == "Reserve Date"){
                    int text = 0;
                    type = "Reserve_Date";
                    String textGets = txFindReserve.getText();
                   // text= Integer.parseInt(textGets);
                   String finalText ="%"+textGets+"%";
                  
                    pst =con.prepareStatement("Select * from reserve where "+ type +" like ?");
                    pst.setString(1,finalText);
                }
                else{
                    int text = 0;
                    type = "Status";
                    String textGets = txFindReserve.getText();
                    String finalText ="%"+ textGets+"%";

                    pst =con.prepareStatement("Select * from reserve where "+ type +" like ?");
                    pst.setString(1,finalText);

               }

                
                rs= pst.executeQuery();

                 //no result error mag popup    
                if( rs.next()==false){
                    JOptionPane.showMessageDialog(null, "No Result Found!", "Oops ...!", JOptionPane.ERROR_MESSAGE);

                }else{
                    
                    int c;

                    ResultSetMetaData rsd;
                    rsd = rs.getMetaData();

                    c =rsd.getColumnCount();

                    DefaultTableModel d =(DefaultTableModel) BookReserveTable.getModel();
                    d.setRowCount(0);

                    do{
                        Vector v1 = new Vector();
                        //System.out.println(v1);

                            for(int i=1;i<=c;i++){
                               // int idrs = rs.getInt("Book_id");

                                v1.add(rs.getInt("Book_id"));
                                    //System.out.println(rs.getInt("Book_id"));
                                v1.add(rs.getInt("copy_no"));
                                    //System.out.println(rs.getString("ISBN"));
                                v1.add(rs.getInt("memberID"));
                                    //System.out.println(rs.getString("Title"));
                                v1.add(rs.getString("Reserve_Date"));
                                    //System.out.println(rs.getString("catogery"));
                                v1.add(rs.getString("Status"));


                           }
                            //System.out.println(v1);
                        d.addRow(v1);
                    }while(rs.next());//
                }

                //txtBookFind.setText("");

                //JOptionPane.showMessageDialog(null, "Item(s) not found!", "Ooops!", JOptionPane.ERROR_MESSAGE);

            } catch (SQLException ex) {
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
            catch(Exception ex){
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
        }
    }//GEN-LAST:event_btnFind_ReserveActionPerformed

    private void txtStatusReserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusReserveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusReserveActionPerformed

    private void btnClearReseveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearReseveActionPerformed
        // TODO add your handling code here:
        
        txtBookIDReserve.setText("-");
        txtCopyNoReserve.setText("-");
        txtMemberIDReserve.setText("-");
        txtDateReserve.setText("-");
        txtStatusReserve.setSelectedItem("");    //Reserved
        //reserveTableReload();
       
    }//GEN-LAST:event_btnClearReseveActionPerformed

    private void selectTypeBoxReserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTypeBoxReserveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectTypeBoxReserveActionPerformed

    private void txFindReserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txFindReserveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txFindReserveActionPerformed

    private void btnClearSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSearch2ActionPerformed
      txFindReserve.setText("");
        reserveTableReload();
        reserveTableUpdate();
    }//GEN-LAST:event_btnClearSearch2ActionPerformed

    private void btnMemberClearTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemberClearTextActionPerformed
        // TODO add your handling code here:
        
                
        txtMemberId.setText("");
        txtMamberName.setText("");
        txtMemberAddress.setText("");
        txtMemberTep.setText("");
        txtBookIDSec.requestFocus();
       
        
         txtMemberAdd.setEnabled(true);
         
        txtMemeberUpdate.setEnabled(false);
        txtMemeberRemove.setEnabled(false);
        btnloginDeUpdate.setEnabled(false);
    }//GEN-LAST:event_btnMemberClearTextActionPerformed

    private void btnloginDeUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginDeUpdateActionPerformed
        // TODO add your handling code here:
        
        LoginDetailsSet.role = "Member";
        LoginDetailsSet logDeSet =new LoginDetailsSet();
        //this.setEnabled(false);
        logDeSet.setVisible(true);
    }//GEN-LAST:event_btnloginDeUpdateActionPerformed

    private void memberTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_memberTableKeyReleased
       
        DefaultTableModel d1 =(DefaultTableModel )memberTable.getModel();
        int selectIndex =memberTable.getSelectedRow();

        txtMemberId.setText(d1.getValueAt(selectIndex, 0).toString());
        txtMamberName.setText(d1.getValueAt(selectIndex, 1).toString());
        txtMemberAddress.setText(d1.getValueAt(selectIndex, 2).toString());
        txtMemberTep.setText(d1.getValueAt(selectIndex, 3).toString());

        LoginDetailsSet.memberID =Integer.parseInt(d1.getValueAt(selectIndex, 0).toString());
        LoginDetailsSet.memberName = d1.getValueAt(selectIndex, 1).toString();
        
        txtMemberAdd.setEnabled(false);
        
        txtMemeberUpdate.setEnabled(true);
        txtMemeberRemove.setEnabled(true);
        btnloginDeUpdate.setEnabled(true);
    }//GEN-LAST:event_memberTableKeyReleased

    private void BookBorrowTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BookBorrowTableMouseClicked
         
        DefaultTableModel d1 =(DefaultTableModel )BookBorrowTable.getModel();
        int selectIndex =BookBorrowTable.getSelectedRow();

        txtBookIDBorrow.setText(d1.getValueAt(selectIndex, 0).toString());
        txtCopyNoBorrow.setText(d1.getValueAt(selectIndex, 1).toString());
        txtMemberIDBorrow.setText(d1.getValueAt(selectIndex, 2).toString());
        txtDateBorrow.setText(d1.getValueAt(selectIndex, 3).toString());
        txtStatusBorrow.setSelectedItem(d1.getValueAt(selectIndex, 6).toString());
    }//GEN-LAST:event_BookBorrowTableMouseClicked

    private void BookBorrowTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BookBorrowTableKeyReleased
        DefaultTableModel d1 =(DefaultTableModel )BookBorrowTable.getModel();
        int selectIndex =BookBorrowTable.getSelectedRow();

        txtBookIDBorrow.setText(d1.getValueAt(selectIndex, 0).toString());
        txtCopyNoBorrow.setText(d1.getValueAt(selectIndex, 1).toString());
        txtMemberIDBorrow.setText(d1.getValueAt(selectIndex, 2).toString());
        txtDateBorrow.setText(d1.getValueAt(selectIndex, 3).toString());
        txtStatusBorrow.setSelectedItem(d1.getValueAt(selectIndex, 6).toString());
    }//GEN-LAST:event_BookBorrowTableKeyReleased

    private void btnUpdate_BorrowedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_BorrowedActionPerformed
        if ( txtBookIDBorrow.getText().equals("") ||  txtCopyNoBorrow.getText().equals("")  || txtMemberIDBorrow.getText().equals("") || txtDateBorrow.getText().equals("")  || txtStatusBorrow.getSelectedItem().equals("")  ) {
                JOptionPane.showMessageDialog(null, "Enter item!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                
        } else {
            
                    String bookID = txtBookIDBorrow.getText();
                    String copyNo = txtCopyNoBorrow.getText();
                    String  memberid = txtMemberIDBorrow.getText();
                    String barrowdate =  txtDateBorrow.getText();
                    //String bookCatogery = txtMemberTep.getSelectedItem().toString();
                    String status = txtStatusBorrow.getSelectedItem().toString();;

                    try {

                            if(status == "Returened"){

                                    long daysBetween = DAYS.between(DateTime.StringToLocalDate(barrowdate), DateTime.currentDate());

                                    System.out.println(daysBetween);
                                    double paneltyFee=0;

                                    if(daysBetween > 14){
                                         paneltyFee = ( (int)daysBetween -14) * 5 ;

                                    }

                                    System.out.println(paneltyFee);

                                    pst = con.prepareStatement("update  borrow set  returned_Date = ? , Status = ?, penalty_fee = ? where  Book_id = ? and copy_no = ?  and memberID = ? and Borrow_Date = ? ");

                                    pst.setString(1, DateTime.currentDate().toString());
                                    pst.setString(2, status);
                                    pst.setDouble(3, paneltyFee);
                                    pst.setInt(4, Integer.parseInt(bookID));
                                    pst.setInt(5, Integer.parseInt(copyNo));
                                    pst.setInt(6, Integer.parseInt(memberid));
                                    pst.setString(7, barrowdate);

                                    int k1 = pst.executeUpdate();

                                    pst = con.prepareStatement("Update book set avbl_count = avbl_count +1 where book_id =?");
                                                        //rs = pst.executeQuery();
                                    pst.setInt(1, Integer.parseInt(bookID));

                                                        //pst.setInt(2, intbookId);
                                    int k2 = pst.executeUpdate();


                                    if(k1==1 && k2==1 ){


                                                txtBookIDBorrow.setText("-");
                                                txtCopyNoBorrow.setText("-");
                                                txtMemberIDBorrow.setText("-");
                                                txtDateBorrow.setText("-");
                                                txtStatusBorrow.setSelectedItem(""); 

                                            borrowTableUpdate();
                                            borrowTableReload();
                                            bookTableReLoad();
        
                                            JOptionPane.showMessageDialog(this,"Borrow Succesfully Updated");

                            }
                            else{
                                JOptionPane.showMessageDialog(this,"Error:: Can't Update Borrows");

                            }


                            }
                            else if(status == "Borrowed"){
                                        pst = con.prepareStatement("update  borrow set  returned_Date = Null , Status = ? where  Book_id = ? and copy_no = ?  and memberID = ? and Borrow_Date = ? ");

                                        //pst.setString(1, DateTime.currentDate().toString());
                                        pst.setString(1, status);
                                        pst.setInt(2, Integer.parseInt(bookID));
                                        pst.setInt(3, Integer.parseInt(copyNo));
                                        pst.setInt(4, Integer.parseInt(memberid));
                                        pst.setString(5, barrowdate);   

                                        int k1 = pst.executeUpdate();
                            }

                    } catch (SQLException ex) {
                        Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, ex , "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                        // JOptionPane.showMessageDialog(null  ,ex);
                    }
        }
    }//GEN-LAST:event_btnUpdate_BorrowedActionPerformed

    private void btnFind_BorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind_BorrowActionPerformed
        // TODO add your handling code here:
        
        

       
           
        if( txFindBorrow.getText().equals("") || selectTypeBoxBorrow.getSelectedItem().toString().equals("") )
            JOptionPane.showMessageDialog(null, "Enter item!", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
        else{

            String typeGets = selectTypeBoxBorrow.getSelectedItem().toString();

            String type;

            try {

                if(typeGets == "Book ID"){
                    int text = 0;
                    type = "Book_id";
                    String textGets = txFindBorrow.getText();
                    text= Integer.parseInt(textGets);
                    
                    //SELECT * FROM "+" bookcopy"+" NATURAL JOIN"+" purchase"
                    pst =con.prepareStatement("Select * from borrow  where "+ type +" = "+ text);
                }
                else if(typeGets == "Copy NO"){

                    type = "copy_no";
                    String textGets = txFindBorrow.getText();
                    //String finalText = textGets+"%";
                    
                    pst =con.prepareStatement("Select * from borrow  where "+ type +" =?");
                    pst.setString(1,textGets);

                }
                else if(typeGets == "Member ID"){
                    int text = 0;
                    type = "memberID";
                    String textGets = txFindBorrow.getText();
                    //text= Integer.parseInt(textGets);
                    String finalText = "%"+textGets+"%";
                        System.out.println(finalText);

                    pst =con.prepareStatement("Select * from borrow where "+ type +" like ?");
                    pst.setString(1,finalText);
                }
                else if(typeGets == "Borrow Date"){
                    int text = 0;
                    type = "Borrow_Date";
                    String textGets = txFindBorrow.getText();
                   // text= Integer.parseInt(textGets);
                   String finalText ="%"+textGets+"%";
                  
                    pst =con.prepareStatement("Select * from borrow where "+ type +" like ?");
                    pst.setString(1,finalText);
                } 
                else if(typeGets == "Due Date"){
                    int text = 0;
                    type = "Due_Date";
                    String textGets = txFindBorrow.getText();
                   // text= Integer.parseInt(textGets);
                   String finalText ="%"+textGets+"%";
                  
                    pst =con.prepareStatement("Select * from borrow where "+ type +" like ?");
                    pst.setString(1,finalText);
                }
                else{
                    int text = 0;
                    type = "Status";
                    String textGets = txFindBorrow.getText();
                    String finalText ="%"+ textGets+"%";

                    pst =con.prepareStatement("Select * from borrow where "+ type +" like ?");
                    pst.setString(1,finalText);

               }

               
                rs= pst.executeQuery();

                 //no result error mag popup    
                if( rs.next()==false){
                    JOptionPane.showMessageDialog(null, "No Result Found!", "Oops ...!", JOptionPane.ERROR_MESSAGE);

                }else{
                    
                    int c;

                    ResultSetMetaData rsd;
                    rsd = rs.getMetaData();

                    c =rsd.getColumnCount();

                    DefaultTableModel d =(DefaultTableModel) BookBorrowTable.getModel();
                    d.setRowCount(0);

                    do{
                        Vector v1 = new Vector();
                        //System.out.println(v1);

                            for(int i=1;i<=c;i++){
                               // int idrs = rs.getInt("Book_id");

                                v1.add(rs.getInt("Book_id"));
                                    //System.out.println(rs.getInt("Book_id"));
                                v1.add(rs.getInt("copy_no"));
                                    //System.out.println(rs.getString("ISBN"));
                                v1.add(rs.getInt("memberID"));
                                    //System.out.println(rs.getString("Title"));
                                v1.add(rs.getString("Borrow_Date"));
                                    //System.out.println(rs.getString("catogery"));
                                v1.add(rs.getString("Due_Date"));    
                                v1.add(rs.getString("returned_Date")); 
                                v1.add(rs.getString("Status"));
                                v1.add(rs.getString("penalty_fee"));
                           }
                            //System.out.println(v1);
                        d.addRow(v1);
                    }while(rs.next());//
                }

                //txtBookFind.setText("");

                //JOptionPane.showMessageDialog(null, "Item(s) not found!", "Ooops!", JOptionPane.ERROR_MESSAGE);

            } catch (SQLException ex) {
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
            catch(Exception ex){
                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
        } 
                
    }//GEN-LAST:event_btnFind_BorrowActionPerformed

    private void txtStatusBorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusBorrowActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusBorrowActionPerformed

    private void btnClearReseve1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearReseve1ActionPerformed
        txtBookIDBorrow.setText("-");
        txtCopyNoBorrow.setText("-");
        txtMemberIDBorrow.setText("-");
        txtDateBorrow.setText("-");
        txtStatusBorrow.setSelectedItem("");    //Reserved
    }//GEN-LAST:event_btnClearReseve1ActionPerformed

    private void selectTypeBoxBorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTypeBoxBorrowActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectTypeBoxBorrowActionPerformed

    private void txFindBorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txFindBorrowActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txFindBorrowActionPerformed

    private void btnClearSearchBorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSearchBorrowActionPerformed
        // TODO add your handling code here:
        txFindBorrow.setText("");
        borrowTableReload();
    }//GEN-LAST:event_btnClearSearchBorrowActionPerformed

    private void ReserveSection1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ReserveSection1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_ReserveSection1AncestorAdded

    private void btnDirectBorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirectBorrowActionPerformed
        // TODO add your handling code here:
        ManualBorrow DirectBorrow =new ManualBorrow();
        //this.setEnabled(false);
        DirectBorrow.setVisible(true);
        
    }//GEN-LAST:event_btnDirectBorrowActionPerformed

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
            java.util.logging.Logger.getLogger(LibDashborad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LibDashborad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LibDashborad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LibDashborad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LibDashborad(/*libID, libName*/).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BookBorrowTable;
    private javax.swing.JPanel BookCopySection;
    private javax.swing.JTable BookReserveTable;
    private javax.swing.JPanel BookSection;
    private javax.swing.JTable BookTable;
    private javax.swing.JTable Book_Copy_Table;
    private javax.swing.JPanel MemberSection;
    private javax.swing.JPanel ReserveSection;
    private javax.swing.JPanel ReserveSection1;
    private javax.swing.JPanel TabSection;
    private javax.swing.JButton btnAdd_Book;
    private javax.swing.JButton btnAdd_bookCopy;
    private javax.swing.JButton btnClearBook;
    private javax.swing.JButton btnClearBookCopy;
    private javax.swing.JButton btnClearReseve;
    private javax.swing.JButton btnClearReseve1;
    private javax.swing.JButton btnClearSearch;
    private javax.swing.JButton btnClearSearch1;
    private javax.swing.JButton btnClearSearch2;
    private javax.swing.JButton btnClearSearchBookCopy;
    private javax.swing.JButton btnClearSearchBorrow;
    private javax.swing.JButton btnDelete_Book;
    private javax.swing.JButton btnDelete_bookCopy;
    private javax.swing.JButton btnDirectBorrow;
    private javax.swing.JButton btnFind_Book;
    private javax.swing.JButton btnFind_BookCopy;
    private javax.swing.JButton btnFind_Borrow;
    private javax.swing.JButton btnFind_Member;
    private javax.swing.JButton btnFind_Reserve;
    private javax.swing.JButton btnMemberClearText;
    private javax.swing.JButton btnUpdate_Book;
    private javax.swing.JButton btnUpdate_Book1;
    private javax.swing.JButton btnUpdate_Borrowed;
    private javax.swing.JButton btnUpdate_bookCopy;
    private javax.swing.JButton btnloginDeUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblEdition;
    private javax.swing.JLabel lblLabirarianID;
    private javax.swing.JLabel lblLibName;
    private javax.swing.JTable memberTable;
    private javax.swing.JComboBox<String> selectTypeBox;
    private javax.swing.JComboBox<String> selectTypeBoxBookCopy;
    private javax.swing.JComboBox<String> selectTypeBoxBorrow;
    private javax.swing.JComboBox<String> selectTypeBoxMember;
    private javax.swing.JComboBox<String> selectTypeBoxReserve;
    private javax.swing.JTabbedPane tapSection;
    private javax.swing.JTextField txFindBorrow;
    private javax.swing.JTextField txFindReserve;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JButton txtBack;
    private javax.swing.JComboBox<String> txtBookCatogery;
    private javax.swing.JTextField txtBookCopyFind;
    private javax.swing.JLabel txtBookCount;
    private javax.swing.JTextField txtBookFind;
    private javax.swing.JTextField txtBookID;
    private javax.swing.JLabel txtBookIDBorrow;
    private javax.swing.JLabel txtBookIDReserve;
    private javax.swing.JTextField txtBookIDSec;
    private javax.swing.JTextField txtBookTitle;
    private javax.swing.JTextField txtCopyNo;
    private javax.swing.JLabel txtCopyNoBorrow;
    private javax.swing.JLabel txtCopyNoReserve;
    private javax.swing.JTextField txtDate;
    private javax.swing.JLabel txtDateBorrow;
    private javax.swing.JLabel txtDateReserve;
    private javax.swing.JTextField txtEdition;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtMamberName;
    private javax.swing.JButton txtMemberAdd;
    private javax.swing.JTextField txtMemberAddress;
    private javax.swing.JTextField txtMemberFind;
    private javax.swing.JLabel txtMemberIDBorrow;
    private javax.swing.JLabel txtMemberIDReserve;
    private javax.swing.JTextField txtMemberId;
    private javax.swing.JTextField txtMemberTep;
    private javax.swing.JButton txtMemeberRemove;
    private javax.swing.JButton txtMemeberUpdate;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JComboBox<String> txtStatusBorrow;
    private javax.swing.JComboBox<String> txtStatusReserve;
    // End of variables declaration//GEN-END:variables
}
