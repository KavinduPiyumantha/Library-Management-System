/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interfaces;


import Codes.DBconnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KAVINDU PIYUMANTHA
 */
public class LibDashborad extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public LibDashborad() {
        initComponents();
        con =DBconnect.Connect();
        table_reLoad();
        bookCopyTable_reLoad();
        
        //book section
        btnUpdate_Book.setEnabled(false);
        btnDelete_Book.setEnabled(false);
        
        //book copy Section
        btnUpdate_bookCopy.setEnabled(false);
        btnDelete_bookCopy.setEnabled(false);
        
        
        
   //     txtBookCatogery.setSelectedIndex(-1);
    }

    
    Connection con=null;
    PreparedStatement pst;
    ResultSet rs;
    
    
//    public void Connect(){
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library","root","Silvatkp99");               
//            
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
//    
    
    
    public void table_reLoad(){
        
        int c;

        try {
            pst = con.prepareStatement("SELECT * FROM book");
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
            pst = con.prepareStatement("SELECT * FROM bookcopy");
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
                    v2.add(rs.getString("Purchase_Date"));
                    v2.add(rs.getString("Price"));

                    
                }
                d.addRow(v2);
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null  ,ex);
        }
    
    
    };    
        
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
        txtBookCount.setText(d1.getValueAt(selectIndex, 5).toString());
        //txtISBN.requestFocus();

        //btnAdd_Book.setEnabled(false);
        btnUpdate_Book.setEnabled(true);
        btnDelete_Book.setEnabled(true);
        
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
                    
                    table_reLoad();
                    
                    }catch (SQLException ex) {
                        Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
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
                    
                    table_reLoad();
                    
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
        selectTypeBox2 = new javax.swing.JComboBox<>();
        txtBookFind3 = new javax.swing.JTextField();
        btnFind_Book3 = new javax.swing.JButton();
        btnClearSearch2 = new javax.swing.JButton();
        btnClearBookCopy = new javax.swing.JButton();
        MemberSection = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtUserName5 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtUserName6 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtUserName7 = new javax.swing.JTextField();
        txtOk4 = new javax.swing.JButton();
        txtOk5 = new javax.swing.JButton();
        txtOk6 = new javax.swing.JButton();
        txtOk7 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtUserName8 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtUserName9 = new javax.swing.JTextField();
        selectTypeBox1 = new javax.swing.JComboBox<>();
        txtBookFind2 = new javax.swing.JTextField();
        btnFind_Book2 = new javax.swing.JButton();
        btnClearSearch1 = new javax.swing.JButton();
        txtUserName10 = new javax.swing.JTextField();
        BorrowSection = new javax.swing.JPanel();

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
                "Book ID", "ISBN No", "Book Title", "Catogery", "Author", "Count"
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(BookSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(BookSectionLayout.createSequentialGroup()
                        .addComponent(selectTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtBookFind, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(12, Short.MAX_VALUE))
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
                "Book Copy No", "Book ID", "Purchase Date", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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

        selectTypeBox2.setBackground(new java.awt.Color(255, 255, 255));
        selectTypeBox2.setEditable(true);
        selectTypeBox2.setForeground(new java.awt.Color(0, 0, 0));
        selectTypeBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "ISBN NO", "Book Title", "Book Catogery", "Author", "Count" }));
        selectTypeBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTypeBox2ActionPerformed(evt);
            }
        });

        txtBookFind3.setBackground(new java.awt.Color(255, 255, 255));
        txtBookFind3.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookFind3.setForeground(new java.awt.Color(0, 0, 0));
        txtBookFind3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBookFind3ActionPerformed(evt);
            }
        });

        btnFind_Book3.setBackground(new java.awt.Color(204, 204, 204));
        btnFind_Book3.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnFind_Book3.setForeground(new java.awt.Color(0, 0, 0));
        btnFind_Book3.setText("Find");
        btnFind_Book3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFind_Book3ActionPerformed(evt);
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

        btnClearBookCopy.setBackground(new java.awt.Color(204, 204, 204));
        btnClearBookCopy.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnClearBookCopy.setForeground(new java.awt.Color(0, 0, 0));
        btnClearBookCopy.setText("Clear ");
        btnClearBookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearBookCopyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BookCopySectionLayout = new javax.swing.GroupLayout(BookCopySection);
        BookCopySection.setLayout(BookCopySectionLayout);
        BookCopySectionLayout.setHorizontalGroup(
            BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookCopySectionLayout.createSequentialGroup()
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                        .addComponent(txtDate))))
                            .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(BookCopySectionLayout.createSequentialGroup()
                                    .addComponent(btnUpdate_bookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAdd_bookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(BookCopySectionLayout.createSequentialGroup()
                                    .addComponent(btnDelete_bookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(47, 47, 47)
                                    .addComponent(btnClearBookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BookCopySectionLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(selectTypeBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtBookFind3, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind_Book3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectTypeBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBookFind3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind_Book3)
                    .addComponent(btnClearSearch2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tapSection.addTab("    Book Copy Section  ", BookCopySection);

        MemberSection.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("ID");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("User Name");

        txtUserName5.setBackground(new java.awt.Color(255, 255, 255));
        txtUserName5.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtUserName5.setForeground(new java.awt.Color(0, 0, 0));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Password");

        txtUserName6.setBackground(new java.awt.Color(255, 255, 255));
        txtUserName6.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtUserName6.setForeground(new java.awt.Color(0, 0, 0));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Name");

        txtUserName7.setBackground(new java.awt.Color(255, 255, 255));
        txtUserName7.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtUserName7.setForeground(new java.awt.Color(0, 0, 0));
        txtUserName7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserName7ActionPerformed(evt);
            }
        });

        txtOk4.setBackground(new java.awt.Color(204, 204, 204));
        txtOk4.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtOk4.setForeground(new java.awt.Color(0, 0, 0));
        txtOk4.setText("Find");
        txtOk4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOk4ActionPerformed(evt);
            }
        });

        txtOk5.setBackground(new java.awt.Color(204, 204, 204));
        txtOk5.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtOk5.setForeground(new java.awt.Color(0, 0, 0));
        txtOk5.setText("Add");
        txtOk5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOk5ActionPerformed(evt);
            }
        });

        txtOk6.setBackground(new java.awt.Color(204, 204, 204));
        txtOk6.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtOk6.setForeground(new java.awt.Color(0, 0, 0));
        txtOk6.setText("Remove");
        txtOk6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOk6ActionPerformed(evt);
            }
        });

        txtOk7.setBackground(new java.awt.Color(204, 204, 204));
        txtOk7.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtOk7.setForeground(new java.awt.Color(0, 0, 0));
        txtOk7.setText("Update");
        txtOk7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOk7ActionPerformed(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "User Name", "Password", "Name", "Address", "Telephone "
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Address");

        txtUserName8.setBackground(new java.awt.Color(255, 255, 255));
        txtUserName8.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtUserName8.setForeground(new java.awt.Color(0, 0, 0));
        txtUserName8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserName8ActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Telephohe");

        txtUserName9.setBackground(new java.awt.Color(255, 255, 255));
        txtUserName9.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtUserName9.setForeground(new java.awt.Color(0, 0, 0));
        txtUserName9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserName9ActionPerformed(evt);
            }
        });

        selectTypeBox1.setBackground(new java.awt.Color(255, 255, 255));
        selectTypeBox1.setEditable(true);
        selectTypeBox1.setForeground(new java.awt.Color(0, 0, 0));
        selectTypeBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "ISBN NO", "Book Title", "Book Catogery", "Author", "Count" }));
        selectTypeBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTypeBox1ActionPerformed(evt);
            }
        });

        txtBookFind2.setBackground(new java.awt.Color(255, 255, 255));
        txtBookFind2.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookFind2.setForeground(new java.awt.Color(0, 0, 0));
        txtBookFind2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBookFind2ActionPerformed(evt);
            }
        });

        btnFind_Book2.setBackground(new java.awt.Color(204, 204, 204));
        btnFind_Book2.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnFind_Book2.setForeground(new java.awt.Color(0, 0, 0));
        btnFind_Book2.setText("Find");
        btnFind_Book2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFind_Book2ActionPerformed(evt);
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

        txtUserName10.setBackground(new java.awt.Color(255, 255, 255));
        txtUserName10.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtUserName10.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout MemberSectionLayout = new javax.swing.GroupLayout(MemberSection);
        MemberSection.setLayout(MemberSectionLayout);
        MemberSectionLayout.setHorizontalGroup(
            MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MemberSectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOk6, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOk4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(MemberSectionLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addComponent(jLabel13))
                            .addComponent(jLabel10))))
                .addGap(12, 12, 12)
                .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtOk7, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOk5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtUserName10, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUserName5, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtUserName7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUserName6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUserName8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUserName9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(MemberSectionLayout.createSequentialGroup()
                        .addComponent(selectTypeBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtBookFind2, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind_Book2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(selectTypeBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBookFind2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind_Book2)
                    .addComponent(btnClearSearch1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MemberSectionLayout.createSequentialGroup()
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtUserName10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtUserName5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtUserName6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUserName7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUserName8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUserName9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(70, 70, 70)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtOk4)
                            .addComponent(txtOk5))
                        .addGap(33, 33, 33)
                        .addGroup(MemberSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOk6)
                            .addComponent(txtOk7))
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MemberSectionLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        tapSection.addTab("   Memebers  Section ", MemberSection);

        BorrowSection.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout BorrowSectionLayout = new javax.swing.GroupLayout(BorrowSection);
        BorrowSection.setLayout(BorrowSectionLayout);
        BorrowSectionLayout.setHorizontalGroup(
            BorrowSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1266, Short.MAX_VALUE)
        );
        BorrowSectionLayout.setVerticalGroup(
            BorrowSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 614, Short.MAX_VALUE)
        );

        tapSection.addTab("    Borrow Section   ", BorrowSection);

        javax.swing.GroupLayout TabSectionLayout = new javax.swing.GroupLayout(TabSection);
        TabSection.setLayout(TabSectionLayout);
        TabSectionLayout.setHorizontalGroup(
            TabSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tapSection, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        TabSectionLayout.setVerticalGroup(
            TabSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabSectionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tapSection)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainLayout.createSequentialGroup()
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(247, 247, 247)
                                .addComponent(txtBack, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainLayout.createSequentialGroup()
                        .addComponent(TabSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(txtBack))
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)))
                .addGap(20, 20, 20)
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

    private void txtUserName9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserName9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserName9ActionPerformed

    private void txtUserName8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserName8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserName8ActionPerformed

    private void txtOk7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOk7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOk7ActionPerformed

    private void txtOk6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOk6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOk6ActionPerformed

    private void txtOk5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOk5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOk5ActionPerformed

    private void txtOk4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOk4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOk4ActionPerformed

    private void txtUserName7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserName7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserName7ActionPerformed

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
        // TODO add your handling code here:

//        DefaultTableModel d1 =(DefaultTableModel )BookTable.getModel();
//        int selectIndex =BookTable.getSelectedRow();

//        int id =Integer.parseInt(d1.getValueAt(selectIndex, 0).toString());

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
                    table_reLoad();
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
                
                table_reLoad();
                
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
                    table_reLoad();

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
        
        String copyno = txtCopyNo.getText();
        String bookId = txtBookID.getText();
        String pDate = txtDate.getText();
        String sPrice = txtPrice.getText();
    
        double dprice = Double.parseDouble(sPrice);

        try {
            pst = con.prepareStatement("insert into bookcopy(copy_No,Book_id,purchase_date,price)values(?,?,?,?)");

            pst.setString(1, copyno);
            pst.setString(2, bookId);
            pst.setString(3, pDate);
            pst.setDouble(4, dprice);
            
            int k = pst.executeUpdate();

            if(k==1){

                

                
                //get book Count
                increase_Book_Count(bookId);
                
                txtCopyNo.setText("");
                txtBookID.setText("");
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
        
        
    }//GEN-LAST:event_btnAdd_bookCopyActionPerformed

    private void Book_Copy_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Book_Copy_TableMouseClicked
        
        DefaultTableModel d1 =(DefaultTableModel )Book_Copy_Table.getModel();
        int selectIndex =Book_Copy_Table.getSelectedRow();

        txtCopyNo.setText(d1.getValueAt(selectIndex, 0).toString());
        txtBookID.setText(d1.getValueAt(selectIndex, 1).toString());
        txtDate.setText(d1.getValueAt(selectIndex, 2).toString());
        //txtPrice.setSelectedItem(d1.getValueAt(selectIndex, 3).toString());
        txtPrice.setText(d1.getValueAt(selectIndex, 3).toString());
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
            pst = con.prepareStatement("delete from bookcopy where copy_no = ?");

            pst.setInt(1, copyNo);

            int k = pst.executeUpdate();

            if(k==1){

                 txtCopyNo.setText("");
                txtBookID.setText("");
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

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MouseClicked

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
        table_reLoad();
    }//GEN-LAST:event_btnClearSearchActionPerformed

    private void selectTypeBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTypeBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectTypeBox1ActionPerformed

    private void txtBookFind2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookFind2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBookFind2ActionPerformed

    private void btnFind_Book2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind_Book2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFind_Book2ActionPerformed

    private void btnClearSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSearch1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearSearch1ActionPerformed

    private void selectTypeBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTypeBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectTypeBox2ActionPerformed

    private void txtBookFind3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookFind3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBookFind3ActionPerformed

    private void btnFind_Book3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind_Book3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFind_Book3ActionPerformed

    private void btnClearSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSearch2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearSearch2ActionPerformed

    private void BookTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BookTableKeyReleased
        // TODO add your handling code here:
        
        loadTableData();
    }//GEN-LAST:event_BookTableKeyReleased

    private void btnClearBookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearBookCopyActionPerformed
        // TODO add your handling code here:
        
        txtCopyNo.setText(" ");
        txtBookID.setText("");
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
        txtDate.setText(d1.getValueAt(selectIndex, 2).toString());
        //txtPrice.setSelectedItem(d1.getValueAt(selectIndex, 3).toString());
        txtPrice.setText(d1.getValueAt(selectIndex, 3).toString());
        //txtBookCount.setText(d1.getValueAt(selectIndex, 5).toString());
        
//        btnAdd_bookCopy.setEnabled(false);
//        
//        btnUpdate_bookCopy.setEnabled(true);
//        btnDelete_bookCopy.setEnabled(true);
    }//GEN-LAST:event_Book_Copy_TableKeyReleased

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
                new LibDashborad().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BookCopySection;
    private javax.swing.JPanel BookSection;
    private javax.swing.JTable BookTable;
    private javax.swing.JTable Book_Copy_Table;
    private javax.swing.JPanel BorrowSection;
    private javax.swing.JPanel MemberSection;
    private javax.swing.JPanel TabSection;
    private javax.swing.JButton btnAdd_Book;
    private javax.swing.JButton btnAdd_bookCopy;
    private javax.swing.JButton btnClearBook;
    private javax.swing.JButton btnClearBookCopy;
    private javax.swing.JButton btnClearSearch;
    private javax.swing.JButton btnClearSearch1;
    private javax.swing.JButton btnClearSearch2;
    private javax.swing.JButton btnDelete_Book;
    private javax.swing.JButton btnDelete_bookCopy;
    private javax.swing.JButton btnFind_Book;
    private javax.swing.JButton btnFind_Book2;
    private javax.swing.JButton btnFind_Book3;
    private javax.swing.JButton btnUpdate_Book;
    private javax.swing.JButton btnUpdate_bookCopy;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil1;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil2;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil3;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JComboBox<String> selectTypeBox;
    private javax.swing.JComboBox<String> selectTypeBox1;
    private javax.swing.JComboBox<String> selectTypeBox2;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel1;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel2;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel3;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel4;
    private javax.swing.JTabbedPane tapSection;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JButton txtBack;
    private javax.swing.JComboBox<String> txtBookCatogery;
    private javax.swing.JLabel txtBookCount;
    private javax.swing.JTextField txtBookFind;
    private javax.swing.JTextField txtBookFind2;
    private javax.swing.JTextField txtBookFind3;
    private javax.swing.JTextField txtBookID;
    private javax.swing.JTextField txtBookIDSec;
    private javax.swing.JTextField txtBookTitle;
    private javax.swing.JTextField txtCopyNo;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JButton txtOk4;
    private javax.swing.JButton txtOk5;
    private javax.swing.JButton txtOk6;
    private javax.swing.JButton txtOk7;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtUserName10;
    private javax.swing.JTextField txtUserName5;
    private javax.swing.JTextField txtUserName6;
    private javax.swing.JTextField txtUserName7;
    private javax.swing.JTextField txtUserName8;
    private javax.swing.JTextField txtUserName9;
    // End of variables declaration//GEN-END:variables
}
