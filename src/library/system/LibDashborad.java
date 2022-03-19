/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package library.system;


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
        Connect();
        table_reLoad();
        bookCopyTable_reLoad();
        btnDelete_Book.setEnabled(false);
   //     txtBookCatogery.setSelectedIndex(-1);
    }

    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    
    public void Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library","root","Silvatkp99");               
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
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
        }
    
    
    };    
        
   
    
    
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
        jPanel1 = new javax.swing.JPanel();
        tapSection = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
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
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
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
        jPanel4 = new javax.swing.JPanel();
        BookCopySection = new javax.swing.JPanel();
        btnAdd_bookCopy = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        Book_Copy_Table = new javax.swing.JTable();
        btnDelete_bookCopy = new javax.swing.JButton();
        btnUpdate_bookCopy = new javax.swing.JButton();
        btnFind_bookCopy = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtBookID = new javax.swing.JTextField();
        copyNo = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        txtBookCatogery2 = new javax.swing.JComboBox<>();
        txtBookFind1 = new javax.swing.JTextField();
        btnFind_Book1 = new javax.swing.JButton();

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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tapSection.setBackground(new java.awt.Color(255, 255, 255));
        tapSection.setForeground(new java.awt.Color(255, 255, 255));
        tapSection.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

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

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        BookTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BookTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(BookTable);
        if (BookTable.getColumnModel().getColumnCount() > 0) {
            BookTable.getColumnModel().getColumn(4).setHeaderValue("Author");
            BookTable.getColumnModel().getColumn(5).setHeaderValue("Count");
        }

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtBookCatogery, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtBookCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtAuthor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(101, 101, 101)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBookIDSec, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUpdate_Book, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(btnClearBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(selectTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtBookFind, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBookFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind_Book)
                    .addComponent(btnClearSearch))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBookIDSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBookCatogery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(txtBookCount, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd_Book)
                    .addComponent(btnUpdate_Book))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete_Book)
                    .addComponent(btnClearBook))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tapSection.addTab("    Book Section  ", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("ID");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Txt");

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtOk6, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(txtOk7, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtOk4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(txtOk5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(22, 22, 22))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel16))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addGap(31, 31, 31)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtUserName5, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtUserName7, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtUserName6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUserName8, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtUserName9, javax.swing.GroupLayout.Alignment.LEADING))))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(104, 104, 104)
                            .addComponent(jLabel11))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 886, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtUserName5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtUserName6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUserName7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUserName8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUserName9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtOk4)
                            .addComponent(txtOk5))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOk6)
                            .addComponent(txtOk7))
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        tapSection.addTab("   Memebers  Section ", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1266, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 614, Short.MAX_VALUE)
        );

        tapSection.addTab("    Borrow Section   ", jPanel4);

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

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        Book_Copy_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Book_Copy_TableMouseClicked(evt);
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

        btnFind_bookCopy.setBackground(new java.awt.Color(204, 204, 204));
        btnFind_bookCopy.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnFind_bookCopy.setForeground(new java.awt.Color(0, 0, 0));
        btnFind_bookCopy.setText("Find");
        btnFind_bookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFind_bookCopyActionPerformed(evt);
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

        copyNo.setBackground(new java.awt.Color(255, 255, 255));
        copyNo.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        copyNo.setForeground(new java.awt.Color(0, 0, 0));
        copyNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyNoActionPerformed(evt);
            }
        });

        txtDate.setBackground(new java.awt.Color(255, 255, 255));
        txtDate.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtDate.setForeground(new java.awt.Color(0, 0, 0));
        txtDate.setToolTipText("YYYY/MM/DD");

        txtBookCatogery2.setBackground(new java.awt.Color(255, 255, 255));
        txtBookCatogery2.setEditable(true);
        txtBookCatogery2.setForeground(new java.awt.Color(0, 0, 0));
        txtBookCatogery2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "ISBN NO", "Book Title", "Book Catogery", "Author", "Count" }));
        txtBookCatogery2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBookCatogery2ActionPerformed(evt);
            }
        });

        txtBookFind1.setBackground(new java.awt.Color(255, 255, 255));
        txtBookFind1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtBookFind1.setForeground(new java.awt.Color(0, 0, 0));
        txtBookFind1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBookFind1ActionPerformed(evt);
            }
        });

        btnFind_Book1.setBackground(new java.awt.Color(204, 204, 204));
        btnFind_Book1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnFind_Book1.setForeground(new java.awt.Color(0, 0, 0));
        btnFind_Book1.setText("Find");
        btnFind_Book1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFind_Book1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BookCopySectionLayout = new javax.swing.GroupLayout(BookCopySection);
        BookCopySection.setLayout(BookCopySectionLayout);
        BookCopySectionLayout.setHorizontalGroup(
            BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addComponent(copyNo, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                .addComponent(txtPrice)
                                .addComponent(txtDate))))
                    .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, BookCopySectionLayout.createSequentialGroup()
                            .addComponent(btnDelete_bookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(47, 47, 47)
                            .addComponent(btnUpdate_bookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(BookCopySectionLayout.createSequentialGroup()
                            .addComponent(btnFind_bookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(47, 47, 47)
                            .addComponent(btnAdd_bookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(BookCopySectionLayout.createSequentialGroup()
                        .addComponent(txtBookCatogery2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtBookFind1)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind_Book1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        BookCopySectionLayout.setVerticalGroup(
            BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookCopySectionLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(copyNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFind_bookCopy, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdd_bookCopy, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete_bookCopy)
                    .addComponent(btnUpdate_bookCopy))
                .addContainerGap(167, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BookCopySectionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BookCopySectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBookCatogery2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBookFind1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind_Book1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tapSection.addTab("    Book Copy Section  ", BookCopySection);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tapSection, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        pst =con.prepareStatement("Select * from book where "+ type +" like ?");
                        pst.setString(1,textGets);
                       
                    }
                    else if(typeGets == "Book Title"){
                        int text = 0;
                        type = "Title";
                        String textGets = txtBookFind.getText();
                        //text= Integer.parseInt(textGets);
                        
                        pst =con.prepareStatement("Select * from book where "+ type +" = ?");
                        pst.setString(1,textGets);
                    }
                    else if(typeGets == "Book Catogery"){
                        int text = 0;
                        type = "catogery";
                        String textGets = txtBookFind.getText();
                       // text= Integer.parseInt(textGets);
                        
                        pst =con.prepareStatement("Select * from book where "+ type +" = ?");
                        pst.setString(1,textGets);
                    }
                    else if(typeGets == "Author"){
                        int text = 0;
                        type = "Author";
                        String textGets = txtBookFind.getText();
                       // text= Integer.parseInt(textGets);
                        
                        pst =con.prepareStatement("Select * from book where "+ type +" = ?");
                        pst.setString(1,textGets);
                        
                    }
                    else{
                        int text = 0;
                        type = "book_Count";
                        
                        String textGets = txtBookFind.getText();
                        text= Integer.parseInt(textGets);
                        
                        pst =con.prepareStatement("Select * from book where "+ type +" = "+ text);
                    }

            
                    int c;

                    rs= pst.executeQuery();
                    
                   // System.out.println(rs);
                    ResultSetMetaData rsd;
                    rsd = rs.getMetaData();
                    

                        c =rsd.getColumnCount();

    //                    System.out.println(rsd);
    //                    System.out.println(c);

                        DefaultTableModel d =(DefaultTableModel) BookTable.getModel();
                        d.setRowCount(0);


                        
                    // no result error mag popup    
                    if(!rs.first()){
                        JOptionPane.showMessageDialog(null, "No Result Found!", "Oops ...!", JOptionPane.ERROR_MESSAGE);
                       
                    }
                    else{
                        while(rs.next()){
                            Vector v1 = new Vector();
                            //System.out.println(v);//

                                for(int i=1;i<=c;i++){
                                    int idrs = rs.getInt("Book_id");
                                     
                                    
                                    v1.add(idrs);
                                        //System.out.println(rs.getInt("Item_No"));
                                    v1.add(rs.getString("ISBN"));
                                       // System.out.println(rs.getString("Name"));
                                    v1.add(rs.getString("Title"));
                                       // System.out.println(rs.getInt("Quantity"));
                                    v1.add(rs.getString("catogery"));
                                        //System.out.println(rs.getDouble("Price"));
                                    v1.add(rs.getString("Author"));

                                    v1.add(rs.getInt("book_count"));

                                    
                               // System.out.print(v1);//
                               
                               }
                            d.addRow(v1);
                            
                            
                        }
                    }
                        
                    txtBookFind.setText("");

                    //JOptionPane.showMessageDialog(null, "Item(s) not found!", "Ooops!", JOptionPane.ERROR_MESSAGE);

                } catch (SQLException ex) {
                    Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
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
            }
        }
    }//GEN-LAST:event_btnUpdate_BookActionPerformed

    private void btnDelete_BookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete_BookActionPerformed
        // TODO add your handling code here:
        DefaultTableModel d1 =(DefaultTableModel )BookTable.getModel();
        int selectIndex =BookTable.getSelectedRow();

        int id =Integer.parseInt(d1.getValueAt(selectIndex, 0).toString());

        String isbnNO = txtISBN.getText();
        String bookTitle = txtBookTitle.getText();
        String bookCatogery = txtBookCatogery.getSelectedItem().toString();
        String Author = txtAuthor.getText();

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
                JOptionPane.showMessageDialog(this,"Book Succesfully Deleted");
            }
            else{
                JOptionPane.showMessageDialog(this,"Error:: Can't Delete Book");
            }

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDelete_BookActionPerformed

    private void BookTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BookTableMouseClicked
        // TODO add your handling code here:

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

        btnAdd_Book.setEnabled(false);
        btnDelete_Book.setEnabled(true);
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
            }
        }
    }//GEN-LAST:event_btnAdd_BookActionPerformed

    private void btnAdd_bookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_bookCopyActionPerformed
        // TODO add your handling code here:
        
//        java.util.Date date=new java.util.Date();
//        java.sql.Date sqlDate=new java.sql.Date(date.getTime());
        
        String copyno = copyNo.getText();
        String bookId = txtBookID.getText();
        String pDate = txtDate.getText();
        
//            Date depdateD =(Date) DateChooser.getDate();
//            System.out.println(depdateD); 
//            SimpleDateFormat oDateFormat =new SimpleDateFormat("YYYY/MM/DD");
//            String depdate = oDateFormat.format(depdateD);
//            System.out.println(depdate);
        
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
                
                copyNo.setText("");
                txtBookID.setText("");
                txtDate.setText("");
                txtBookCatogery.setSelectedIndex(-1);
                txtPrice.setText("");
                copyNo.requestFocus();
                
                bookCopyTable_reLoad();
                
                
                
                
                JOptionPane.showMessageDialog(this,"New Book Copy Succesfully Added");
            
            }
            else{
                JOptionPane.showMessageDialog(this,"Error:: Can't Add new Book Copy");
            }

        } catch (SQLException ex) {
            Logger.getLogger(LibDashborad.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_btnAdd_bookCopyActionPerformed

    private void Book_Copy_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Book_Copy_TableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Book_Copy_TableMouseClicked

    private void btnDelete_bookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete_bookCopyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDelete_bookCopyActionPerformed

    private void btnUpdate_bookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_bookCopyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdate_bookCopyActionPerformed

    private void btnFind_bookCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind_bookCopyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFind_bookCopyActionPerformed

    private void txtBookIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBookIDActionPerformed

    private void copyNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_copyNoActionPerformed

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
                btnDelete_Book.setEnabled(false);
    }//GEN-LAST:event_btnClearBookActionPerformed

    private void selectTypeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectTypeBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectTypeBoxActionPerformed

    private void txtBookFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBookFindActionPerformed

    private void txtBookCatogery2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookCatogery2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBookCatogery2ActionPerformed

    private void txtBookFind1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBookFind1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBookFind1ActionPerformed

    private void btnFind_Book1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind_Book1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFind_Book1ActionPerformed

    private void btnClearSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSearchActionPerformed
        // TODO add your handling code here:
        
        txtBookFind.setText("");
        table_reLoad();
    }//GEN-LAST:event_btnClearSearchActionPerformed

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
    private javax.swing.JTable BookTable;
    private javax.swing.JTable Book_Copy_Table;
    private javax.swing.JButton btnAdd_Book;
    private javax.swing.JButton btnAdd_bookCopy;
    private javax.swing.JButton btnClearBook;
    private javax.swing.JButton btnClearSearch;
    private javax.swing.JButton btnDelete_Book;
    private javax.swing.JButton btnDelete_bookCopy;
    private javax.swing.JButton btnFind_Book;
    private javax.swing.JButton btnFind_Book1;
    private javax.swing.JButton btnFind_bookCopy;
    private javax.swing.JButton btnUpdate_Book;
    private javax.swing.JButton btnUpdate_bookCopy;
    private javax.swing.JTextField copyNo;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil1;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil2;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil3;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JComboBox<String> selectTypeBox;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel1;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel2;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel3;
    private org.jdatepicker.impl.SqlDateModel sqlDateModel4;
    private javax.swing.JTabbedPane tapSection;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JButton txtBack;
    private javax.swing.JComboBox<String> txtBookCatogery;
    private javax.swing.JComboBox<String> txtBookCatogery2;
    private javax.swing.JLabel txtBookCount;
    private javax.swing.JTextField txtBookFind;
    private javax.swing.JTextField txtBookFind1;
    private javax.swing.JTextField txtBookID;
    private javax.swing.JTextField txtBookIDSec;
    private javax.swing.JTextField txtBookTitle;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JButton txtOk4;
    private javax.swing.JButton txtOk5;
    private javax.swing.JButton txtOk6;
    private javax.swing.JButton txtOk7;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtUserName5;
    private javax.swing.JTextField txtUserName6;
    private javax.swing.JTextField txtUserName7;
    private javax.swing.JTextField txtUserName8;
    private javax.swing.JTextField txtUserName9;
    // End of variables declaration//GEN-END:variables
}
