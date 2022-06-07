package Interfaces;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */



import Interfaces.*;
import Codes.DBconnect;
import  Codes.DateTime;
import Codes.Refresh;
import Codes.TableReload;


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

/**
 *
 * 
 */
public class MemberDashborad extends javax.swing.JFrame {

    
    
          public static int MemberID;
          public static String MemberName;
          
          static String strMemberID;
         
          

    public MemberDashborad() {

        initComponents();
        con =DBconnect.Connect();
        
        strMemberID=String.valueOf(MemberID);
        lblLabirarianID.setText(strMemberID);
        lblLibName.setText(MemberName);
        
        
        //table Loading
        bookTableReLoad();
        MemberRecordsTableReload();
        reserveTableUpdate();
        
        //book section
        btnReserveBook.setEnabled(false);



    }

    
    Connection con=null;
    PreparedStatement pst;
    ResultSet rs;
    
    
 
     public void DboardEnable(){
        //LibDashborad.dispose();
        //LibDashborad.setEnabled(true);
    }
    
    
    public void bookTableReLoad(){
        
        int c;

        try {
            pst = con.prepareStatement("SELECT Book_id, ISBN, Title, catogery, Author, avbl_count   FROM  book" );
            //pst = con.prepareStatement("SELECT * FROM"+ " book" +" NATURAL JOIN "+ "bookcopy");
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
                    v2.add(rs.getString("avbl_count"));
                    
                }
                d.addRow(v2);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }
    }
        
//    public void reserveTableReload(){
//              int c;
//
//            try {
//    //            pst = con.prepareStatement("SELECT * FROM reserve where Status = ? ");
//    //            pst.setString(1,"Reserved");
//
//                pst = con.prepareStatement("SELECT * FROM reserve  ");
//
//                 rs = pst.executeQuery();
//
//                ResultSetMetaData rsd;
//                rsd = rs.getMetaData();
//                c = rsd.getColumnCount();
//
//                DefaultTableModel d =(DefaultTableModel )BookReserveTable.getModel();
//                d.setRowCount(0);
//
//                while(rs.next()){
//                    Vector v2 = new Vector();
//                    for(int i=1;i<=c;i++){
//
//                        v2.add(rs.getString("Book_id"));
//                        v2.add(rs.getString("copy_no"));
//                        v2.add(rs.getString("memberID"));
//                        v2.add(rs.getString("Reserve_Date"));
//                        v2.add(rs.getString("Status"));
//
//                       // v2.add(rs.getString("Price"));
//
//
//                    }
//                    d.addRow(v2);
//                }
//
//
//            } catch (SQLException ex) {
//                Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
//                JOptionPane.showMessageDialog(null  ,ex);
//            }     
//
//       }
   
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
                    
                    //reserveTableReload();
                     bookTableReLoad();


 
           }

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }     
        
       //reserveTableReload();
   }
    
   public void MemberRecordsTableReload(){
          int c;

        try {
//            pst = con.prepareStatement("SELECT * FROM reserve where Status = ? ");
//            pst.setString(1,"Reserved");
            
            pst = con.prepareStatement("SELECT * FROM borrow  where memberID = ?");
            pst.setString(1, strMemberID);
             rs = pst.executeQuery();
            
            ResultSetMetaData rsd;
            rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d =(DefaultTableModel )RecordsTable.getModel();
            d.setRowCount(0);
            
            while(rs.next()){
                Vector v2 = new Vector();
                for(int i=1;i<=c;i++){
                    
                    v2.add(rs.getString("Book_id"));
                    v2.add(rs.getString("copy_no"));
                    //v2.add(rs.getString("memberID"));
                    v2.add(rs.getString("Borrow_Date"));
                    v2.add(rs.getString("Due_Date"));
                    v2.add(rs.getString("returned_Date"));
                    v2.add(rs.getString("Status"));
                    v2.add(rs.getString("penalty_fee"));
   
                }
                d.addRow(v2);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }     
       
   } 
        
   public void loadTableData(){
       
        DefaultTableModel d1 =(DefaultTableModel )BookTable.getModel();
        int selectIndex =BookTable.getSelectedRow();

        //int id =Integer.parseInt(d1.getValueAt(selectIndex, 0).toString());
        // String z = Integer.toString(id);

        //        int count =Integer.parseInt(d1.getValueAt(selectIndex, 5).toString());
        //        String cnt = Integer.toString(count);

        txtBookIDSec.setText(d1.getValueAt(selectIndex, 0).toString());
        txtISBN.setText(d1.getValueAt(selectIndex, 1).toString());
        txtBookTitle.setText(d1.getValueAt(selectIndex, 2).toString());
        txtBookCatogery.setSelectedItem(d1.getValueAt(selectIndex, 3).toString());
        txtAuthor.setText(d1.getValueAt(selectIndex, 4).toString());
        //txtBookCount.setText(d1.getValueAt(selectIndex, 5).toString());
        //txtISBN.requestFocus();

        //btnAdd_Book.setEnabled(false);
        btnReserveBook.setEnabled(true);
//        btnDelete_Book.setEnabled(true);
        
   }
    
    public void increase_Book_Count(String id){
        
        String bookId = id;
        
        
            try {
       
                    int intbookId = Integer.parseInt(bookId);
                    pst = con.prepareStatement("SELECT book_count FROM book where Book_id = "+ intbookId);
                    rs = pst.executeQuery();
                    
                    System.out.println("intbookId "+ intbookId);
                    
                    rs.next();
                    int bookCount = rs.getInt(1);
                    
                    System.out.println("bookCount "+bookCount);
                    
                    bookCount = bookCount + 1;
                    
                    System.out.println("bookCount "+bookCount);

                    //String sbookCount =  Integer.toString(bookCount) ;

                
                    
                    pst = con.prepareStatement("Update book set book_count = ? where book_id =?");
                    //rs = pst.executeQuery();
                    pst.setInt(1, bookCount);
                    
                    System.out.println("setcount "+bookCount);
                   // pst.setString(2, bookCount);
                    //pst.setString(3, 1);
                    pst.setInt(2, intbookId);
                    
                    System.out.println("setid "+intbookId);
                    
                    pst.executeUpdate();
                    
                    bookTableReLoad();
                    
                    }catch (SQLException ex) {
                        Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null  ,ex);
        
                    }

    }
    
     public void decrease_Book_Count(String id){
        
        String bookId = id;
        
        
            try {
       
                    int intbookId = Integer.parseInt(bookId);
                    pst = con.prepareStatement("SELECT book_count FROM book where Book_id = "+ intbookId);
                    rs = pst.executeQuery();
                    
                    System.out.println("intbookId "+ intbookId);
                    
                    rs.next();
                    int bookCount = rs.getInt(1);
                    
                    System.out.println("bookCount "+bookCount);
                    
                    bookCount = bookCount - 1;
                    
                    System.out.println("bookCount "+bookCount);

                    //String sbookCount =  Integer.toString(bookCount) ;

                
                    
                    pst = con.prepareStatement("Update book set book_count = ? where book_id =?");
                    //rs = pst.executeQuery();
                    pst.setInt(1, bookCount);
                    
                    System.out.println("setcount "+bookCount);
                   // pst.setString(2, bookCount);
                    //pst.setString(3, 1);
                    pst.setInt(2, intbookId);
                    
                    System.out.println("setid "+intbookId);
                    
                    pst.executeUpdate();
                    
                    bookTableReLoad();
                    
                    }catch (SQLException ex) {
                        Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
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

        sqlDateModel1 = new org.jdatepicker.impl.SqlDateModel();
        sqlDateModel2 = new org.jdatepicker.impl.SqlDateModel();
        sqlDateModel3 = new org.jdatepicker.impl.SqlDateModel();
        jDatePickerUtil1 = new org.jdatepicker.util.JDatePickerUtil();
        jDatePickerUtil2 = new org.jdatepicker.util.JDatePickerUtil();
        jDatePickerUtil3 = new org.jdatepicker.util.JDatePickerUtil();
        jDatePickerUtil4 = new org.jdatepicker.util.JDatePickerUtil();
        sqlDateModel4 = new org.jdatepicker.impl.SqlDateModel();
        jPanelMain = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtBack = new javax.swing.JButton();
        TabSection = new javax.swing.JPanel();
        tapSection = new javax.swing.JTabbedPane();
        BookSection = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        BookTable = new javax.swing.JTable();
        btnReserveBook = new javax.swing.JButton();
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
        txtBookIDSec = new javax.swing.JTextField();
        selectTypeBox = new javax.swing.JComboBox<>();
        txtBookFind = new javax.swing.JTextField();
        btnClearSearch = new javax.swing.JButton();
        onlyAvailableCheck = new javax.swing.JCheckBox();
        ReserveSection1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        RecordsTable = new javax.swing.JTable();
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
        jLabel2.setText("Member Dashboard");

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

        BookTable.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        BookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID", "ISBN No", "Book Title", "Catogery", "Author", "Available Count"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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

        btnReserveBook.setBackground(new java.awt.Color(204, 204, 204));
        btnReserveBook.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnReserveBook.setForeground(new java.awt.Color(0, 0, 0));
        btnReserveBook.setText("Reserve ");
        btnReserveBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReserveBookActionPerformed(evt);
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

        txtBookIDSec.setBackground(new java.awt.Color(255, 255, 255));
        txtBookIDSec.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookIDSec.setForeground(new java.awt.Color(0, 0, 0));
        txtBookIDSec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBookIDSecActionPerformed(evt);
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

        onlyAvailableCheck.setForeground(new java.awt.Color(0, 0, 0));
        onlyAvailableCheck.setText("Only Available Books");
        onlyAvailableCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlyAvailableCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BookSectionLayout = new javax.swing.GroupLayout(BookSection);
        BookSection.setLayout(BookSectionLayout);
        BookSectionLayout.setHorizontalGroup(
            BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookSectionLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, BookSectionLayout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtBookCatogery, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(BookSectionLayout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(BookSectionLayout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(btnReserveBook, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(BookSectionLayout.createSequentialGroup()
                        .addComponent(selectTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(onlyAvailableCheck)
                            .addGroup(BookSectionLayout.createSequentialGroup()
                                .addComponent(txtBookFind, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnFind_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(onlyAvailableCheck)
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addGap(49, 49, 49)
                        .addComponent(btnReserveBook)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tapSection.addTab("    Book Reservation ", BookSection);

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

        RecordsTable.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        RecordsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID", "Book Copy No", "Borrow Date", "Due Date ", "Returned Date", "Status", "Panalty Fee"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        RecordsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RecordsTableMouseClicked(evt);
            }
        });
        RecordsTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                RecordsTableKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(RecordsTable);

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

        javax.swing.GroupLayout ReserveSection1Layout = new javax.swing.GroupLayout(ReserveSection1);
        ReserveSection1.setLayout(ReserveSection1Layout);
        ReserveSection1Layout.setHorizontalGroup(
            ReserveSection1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
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
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE))
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
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tapSection.addTab("    My Records ", ReserveSection1);

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
                .addContainerGap(15, Short.MAX_VALUE))
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
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(407, 407, 407)
                        .addComponent(txtBack, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblLibName, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblLabirarianID, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(35, 35, 35))
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(414, 414, 414))
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(TabSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
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
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            String avbl = null;
            
             if(onlyAvailableCheck.isSelected()){
                 avbl =" and avbl_count > 0 ";
             }else{
                 avbl =" ";
             }

            try {
                
                
                

                if(typeGets == "ID"){
                    int text = 0;
                    type = "Book_id";
                    String textGets = txtBookFind.getText();
                    text= Integer.parseInt(textGets);

                    pst =con.prepareStatement("Select * from book where "+ type +" = "+ text + avbl );
                }
                else if(typeGets == "ISBN NO"){

                    type = "ISBN";
                    String textGets = txtBookFind.getText();
                    //String finalText = textGets+"%";
                    
                    pst =con.prepareStatement("Select * from book where "+ type +" = ?" + avbl);
                    pst.setString(1,textGets);

                }
                else if(typeGets == "Book Title"){
                    int text = 0;
                    type = "Title";
                    String textGets = txtBookFind.getText();
                    //text= Integer.parseInt(textGets);
                    String finalText = "%"+textGets+"%";
                        System.out.println(finalText);

                    pst =con.prepareStatement("Select * from book where "+ type +" like ?" + avbl);
                    pst.setString(1,finalText);
                }
                else if(typeGets == "Book Catogery"){
                    int text = 0;
                    type = "catogery";
                    String textGets = txtBookFind.getText();
                   // text= Integer.parseInt(textGets);
                   String finalText ="%"+textGets+"%";
                  
                    pst =con.prepareStatement("Select * from book where "+ type +" like ?" + avbl);
                    pst.setString(1,finalText);
                }
                else if(typeGets == "Author"){
                    int text = 0;
                    type = "Author";
                    String textGets = txtBookFind.getText();
                   String finalText ="%"+ textGets+"%";

                    pst =con.prepareStatement("Select * from book where "+ type +" like ?" + avbl);
                    pst.setString(1,finalText);

                }
                else{
                    int text = 0;
                    type = "book_Count";

                    String textGets = txtBookFind.getText();
                    text= Integer.parseInt(textGets);

                    pst =con.prepareStatement("Select * from book where "+ type +" = "+ text + avbl);
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
                Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
            catch(Exception ex){
                Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
        }
        
    }//GEN-LAST:event_btnFind_BookActionPerformed

    private void btnReserveBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReserveBookActionPerformed

                      boolean reserveStatus=false ;
                      boolean borrowstatus=false;
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
               
                        if ( txtBookIDSec.getText().equals("")  /* || txtISBN.getText().equals("") ||  txtBookTitle.getText().equals("") || txtBookCatogery.getSelectedItem().toString().equals("") || txtAuthor.getText().equals("") */ ) {
                            JOptionPane.showMessageDialog(null, "Select a Row !", "Oops Wait...!", JOptionPane.ERROR_MESSAGE);
                        } else {

                            try {

                                String bookid = txtBookIDSec.getText();
                                //                String isbnNO   = txtISBN.getText();
                                //                String bookTitle = txtBookTitle.getText();
                                //
                                //                String Author = txtAuthor.getText();
                                //                
                                //                String bookCatogery = txtBookCatogery.getSelectedItem().toString();

                                int ID =Integer.parseInt(bookid);
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

                                        txtBookIDSec.setText("");
                                        txtISBN.setText("");
                                        txtBookTitle.setText("");
                                        txtBookCatogery.setSelectedIndex(-1);
                                        txtAuthor.setText("");
                                        //txtBookCount.setText("-");

                                        txtBookIDSec.requestFocus();

                                        pst = con.prepareStatement("Update book set avbl_count = avbl_count -1 where book_id =?");
                                        //rs = pst.executeQuery();
                                        pst.setInt(1, ID);

                                        //pst.setInt(2, intbookId);
                                        int k2 = pst.executeUpdate();
                                        if(k2==1){
                                            JOptionPane.showMessageDialog(this,"Book Succesfully Reserved");
                                        }

                                        bookTableReLoad();
                                        //                    btnAdd_Book.setEnabled(true);

                                    }
                                    else{
                                        JOptionPane.showMessageDialog(this,"Error:: Can't Reserved this Book");
                                    }
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null  ,ex);
                            }
                        }                
               
               
           }

 
    }//GEN-LAST:event_btnReserveBookActionPerformed

    private void BookTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BookTableMouseClicked
        // TODO add your handling code here:
//
//        DefaultTableModel d1 =(DefaultTableModel )BookTable.getModel();
//        int selectIndex =BookTable.getSelectedRow();
//
//        //int id =Integer.parseInt(d1.getValueAt(selectIndex, 0).toString());
//        // String z = Integer.toString(id);
//
//        //        int count =Integer.parseInt(d1.getValueAt(selectIndex, 5).toString());
//        //        String cnt = Integer.toString(count);
//
//        txtBookIDSec.setText(d1.getValueAt(selectIndex, 0).toString());
//        txtISBN.setText(d1.getValueAt(selectIndex, 1).toString());
//        txtBookTitle.setText(d1.getValueAt(selectIndex, 2).toString());
//        txtBookCatogery.setSelectedItem(d1.getValueAt(selectIndex, 3).toString());
//        txtAuthor.setText(d1.getValueAt(selectIndex, 4).toString());
//        txtBookCount.setText(d1.getValueAt(selectIndex, 5).toString());
//        //txtISBN.requestFocus();
//
//        btnAdd_Book.setEnabled(false);
//        btnDelete_Book.setEnabled(true);
        
        loadTableData();
    }//GEN-LAST:event_BookTableMouseClicked

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

    private void BookTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BookTableKeyReleased
        // TODO add your handling code here:
        
        loadTableData();
    }//GEN-LAST:event_BookTableKeyReleased

    private void RecordsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RecordsTableMouseClicked
         
        DefaultTableModel d1 =(DefaultTableModel )RecordsTable.getModel();
        int selectIndex =RecordsTable.getSelectedRow();

        txtBookIDBorrow.setText(d1.getValueAt(selectIndex, 0).toString());
        txtCopyNoBorrow.setText(d1.getValueAt(selectIndex, 1).toString());
        txtMemberIDBorrow.setText(d1.getValueAt(selectIndex, 2).toString());
        txtDateBorrow.setText(d1.getValueAt(selectIndex, 3).toString());
        txtStatusBorrow.setSelectedItem(d1.getValueAt(selectIndex, 6).toString());
    }//GEN-LAST:event_RecordsTableMouseClicked

    private void RecordsTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RecordsTableKeyReleased
        DefaultTableModel d1 =(DefaultTableModel )RecordsTable.getModel();
        int selectIndex =RecordsTable.getSelectedRow();

        txtBookIDBorrow.setText(d1.getValueAt(selectIndex, 0).toString());
        txtCopyNoBorrow.setText(d1.getValueAt(selectIndex, 1).toString());
        txtMemberIDBorrow.setText(d1.getValueAt(selectIndex, 2).toString());
        txtDateBorrow.setText(d1.getValueAt(selectIndex, 3).toString());
        txtStatusBorrow.setSelectedItem(d1.getValueAt(selectIndex, 6).toString());
    }//GEN-LAST:event_RecordsTableKeyReleased

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
                else{
                    
                    pst = con.prepareStatement("update  borrow set  Status = ? where  Book_id = ? and copy_no = ?  and memberID = ? and Borrow_Date = ? ");
                    //pst.setString(1, DateTime.currentDate().toString());
                    pst.setString(1, status);
                    pst.setInt(2, Integer.parseInt(bookID));
                    pst.setInt(3, Integer.parseInt(copyNo));
                    pst.setInt(4, Integer.parseInt(memberid));
                    pst.setString(5, barrowdate);
                    
                    int k1 = pst.executeUpdate();
                    
                }

               // pst.setString(5, Author);
                int k1 = pst.executeUpdate();

                if(k1==1){

//                    int intID = Integer.parseInt(memberid);
                    
//                    LoginDetailsSet.memberID = intID;
//                    LoginDetailsSet.memberName = memberName;
//                    LoginDetailsSet.role = "Member";

                        txtBookIDBorrow.setText("-");
                        txtCopyNoBorrow.setText("-");
                        txtMemberIDBorrow.setText("-");
                        txtDateBorrow.setText("-");
                        txtStatusBorrow.setSelectedItem(""); 
//                    //txtBookCatogery.setSelectedIndex(-1);
//                    txtMemberTep.setText("");
                         //txtStatusReserve.requestFocus();

                    //borrowTableReload();
                     MemberRecordsTableReload();
                    
//                    LoginDetailsSet loginSet = new  LoginDetailsSet();
//                    loginSet.setVisible(true);

                    JOptionPane.showMessageDialog(this,"Borrow Succesfully Updated");

                }
                else{
                    JOptionPane.showMessageDialog(this,"Error:: Can't Update Borrows");
                   
                }

            } catch (SQLException ex) {
                Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
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

                    DefaultTableModel d =(DefaultTableModel) RecordsTable.getModel();
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
                Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null  ,ex);
            }
            catch(Exception ex){
                Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
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
         MemberRecordsTableReload();
        //borrowTableReload();
    }//GEN-LAST:event_btnClearSearchBorrowActionPerformed

    private void ReserveSection1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ReserveSection1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_ReserveSection1AncestorAdded

    private void onlyAvailableCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlyAvailableCheckActionPerformed
        // TODO add your handling code here:
        if(onlyAvailableCheck.isSelected()){
            
            int c;

        try {
            pst = con.prepareStatement("SELECT Book_id, ISBN, Title, catogery, Author, avbl_count   FROM  book where  avbl_count > 0" );
            //pst = con.prepareStatement("SELECT * FROM"+ " book" +" NATURAL JOIN "+ "bookcopy");
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
                    v2.add(rs.getString("avbl_count"));
                    
                }
                d.addRow(v2);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(MemberDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }
            
        }else{
            
            bookTableReLoad();
        }
        
    }//GEN-LAST:event_onlyAvailableCheckActionPerformed

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
            java.util.logging.Logger.getLogger(MemberDashborad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MemberDashborad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MemberDashborad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MemberDashborad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MemberDashborad(/*libID, libName*/).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BookSection;
    private javax.swing.JTable BookTable;
    private javax.swing.JTable RecordsTable;
    private javax.swing.JPanel ReserveSection1;
    private javax.swing.JPanel TabSection;
    private javax.swing.JButton btnClearReseve1;
    private javax.swing.JButton btnClearSearch;
    private javax.swing.JButton btnClearSearchBorrow;
    private javax.swing.JButton btnFind_Book;
    private javax.swing.JButton btnFind_Borrow;
    private javax.swing.JButton btnReserveBook;
    private javax.swing.JButton btnUpdate_Borrowed;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil1;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil2;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil3;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblLabirarianID;
    private javax.swing.JLabel lblLibName;
    private javax.swing.JCheckBox onlyAvailableCheck;
    private javax.swing.JComboBox<String> selectTypeBox;
    private javax.swing.JComboBox<String> selectTypeBoxBorrow;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel1;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel2;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel3;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel4;
    private javax.swing.JTabbedPane tapSection;
    private javax.swing.JTextField txFindBorrow;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JButton txtBack;
    private javax.swing.JComboBox<String> txtBookCatogery;
    private javax.swing.JTextField txtBookFind;
    private javax.swing.JLabel txtBookIDBorrow;
    private javax.swing.JTextField txtBookIDSec;
    private javax.swing.JTextField txtBookTitle;
    private javax.swing.JLabel txtCopyNoBorrow;
    private javax.swing.JLabel txtDateBorrow;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JLabel txtMemberIDBorrow;
    private javax.swing.JComboBox<String> txtStatusBorrow;
    // End of variables declaration//GEN-END:variables
}
