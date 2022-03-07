CREATE DATABASE Library;

CREATE TABLE Member(
Member_No int NOT NULL PRIMARY KEY auto_increment,
Member_Username varchar(255) NOT NULL unique,
Member_pasword varchar(255) ,
Member_Name varchar(255),
Member_Address varchar(255), 
Tel_No varchar(10)
);

CREATE TABLE Book(
Book_id int auto_increment NOT NULL PRIMARY KEY,
ISBN varchar(255) NOT NULL ,
Title varchar(255) NOT NULL,
catogery varchar(255),
Author varchar(255)
);

CREATE TABLE Reservation(
Reservation_No varchar(255) NOT NULL,
Member_No varchar(255) NOT NULL ,
ISBN varchar(255) NOT NULL ,
Reservation_Date date,
PRIMARY KEY(Reservation_No,Member_No,ISBN),
FOREIGN KEY(Member_No) REFERENCES Member(Member_No),
FOREIGN KEY(ISBN) REFERENCES Book(ISBN)
);

CREATE TABLE BookCopy(
Copy_No varchar(255) NOT NULL ,
ISBN varchar(255) NOT NULL ,
Purchase_Date date,
Price double,
PRIMARY KEY(Copy_No,ISBN),
FOREIGN KEY(ISBN) REFERENCES Book(ISBN)
);

CREATE TABLE Borrows(
Member_No varchar(255) NOT NULL,
Copy_No varchar(255) NOT NULL ,
Due_Date date,
Loan_date date,
Return_date date,
PRIMARY KEY(Member_No,Copy_No),
FOREIGN KEY(Member_No) REFERENCES Member(Member_No),
FOREIGN KEY(Copy_No) REFERENCES BookCopy(Copy_No)
);

CREATE TABLE Librarian(
Lib_No int NOT NULL  auto_increment,
lib_Username varchar(255) NOT NULL unique,
lib_pasword varchar(255) ,
Lib_Name varchar(255),
Lib_Address varchar(255), 
Tel_No varchar(10),
PRIMARY KEY(Lib_No)
);

insert into Librarian values
(1,'kavindu123','123','Kavindu Piyumantha','Aluthgama','0773648123');

insert into Librarian(lib_Username,lib_pasword,Lib_Name,Lib_Address,Tel_No) values
('kavindu13423','123','Kavindu Piyumantha','Aluthgama','0773648123');

delete from librarian where Lib_No=2;