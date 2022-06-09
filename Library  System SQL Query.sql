create database librarysystem; 

CREATE TABLE `member` (
  `memberID` int not null unique,
  `Member_Name` varchar(255),
  `Member_Address` varchar(255),
  `Tel_No` varchar(255),
  PRIMARY KEY (`memberID`)
);

CREATE TABLE `librarian` (
  `Lib_ID` int not null unique,
  `Lib_Name` varchar(255),
  `Lib_Address` varchar(255),
  `Tel_No` varchar(10),
  PRIMARY KEY (`Lib_ID`)
);

CREATE TABLE `Login` (
  `ID` int not null ,
  `role` varchar(255) not null,
  `username` varchar(255) not null unique,
  `password` varchar(255),
  PRIMARY KEY (`ID`, `role`),
  FOREIGN KEY `Login`(`ID`) REFERENCES `Member`(`memberID`),
  FOREIGN KEY `Login`(`ID`) REFERENCES `Librarian`(`Lib_ID`)
);

CREATE TABLE `book` (
  `Book_id` int not null unique,
  `ISBN` varchar(255),
  `Title` varchar(255),
  `catogery` varchar(255),
  `Author` varchar(255),
  `book_count` int DEFAULT 0,
  `avbl_count` int DEFAULT 0,
  PRIMARY KEY (`Book_id`)
);

CREATE TABLE `bookcopy` (
  `copy_no` int not null unique,
  `Book_id` int not null,
  `Edition` varchar(255),
  `Lib_ID` int not null ,
  `Purchase_date` date,
  `Price` double,
  PRIMARY KEY (`copy_no`),
  FOREIGN KEY (`Book_id`) REFERENCES `book`(`Book_id`)
);


CREATE TABLE `reserve` (
  `Book_id` int not null ,
  `copy_no` int not null ,
  `memberID` int not null ,
  `Reserve_Date` date,
  `Status` varchar(255),
  PRIMARY KEY (`Book_id`,`copy_no`, `memberID`, `Reserve_Date`),
  FOREIGN KEY (`Book_id`) REFERENCES `book`(`Book_id`),
  FOREIGN KEY (`copy_no`) REFERENCES `bookcopy`(`copy_no`),
  FOREIGN KEY (`memberID`) REFERENCES `member`(`memberID`)
);

CREATE TABLE `borrow` (
  `Book_id` int not null ,
  `copy_no` int not null ,
  `memberID` int not null,
  `Borrow_Date` date not null,
  `Due_Date` date not null,
  `returned_Date` date,
  `Status` varchar(255),
  `penalty_fee` double default 0.0,
  PRIMARY KEY (`Book_id`,`copy_no`, `memberID`, `Borrow_Date`),
  FOREIGN KEY (`Book_id`) REFERENCES `book`(`Book_id`),
  FOREIGN KEY (`copy_no`) REFERENCES `bookcopy`(`copy_no`),
  FOREIGN KEY (`memberID`) REFERENCES `member`(`memberID`)
);

insert into librarian(Lib_ID,Lib_Name,Lib_Address,Tel_No) value
(1,"Kavindu Piyumantha","aluthgama","0773648123");

insert into Login(ID,role,username,password)values
(1,"Librarian","kavindu","12345");




insert into librarian(Lib_ID,Lib_Name,Lib_Address,Tel_No) value
(1,"Kavindu Piyumantha","aluthgama","1234567890");

insert into reserve(Book_id,copy_no,memberID,Reserve_Date,Status) value 
(1,1,2,'2015-01-26','Reserved');

insert into Login(ID,role,username,password)values
(1,"Librarian","kavindu","12345");

select Lib_Name from librarian where Lib_ID = 1 ;

insert into librarian(Lib_ID,Lib_Name,Lib_Address,Tel_No) value
(1,"Kavindu Piyumantha","aluthgama","1234567890");

update librarian set Lib_Name= "Kavindu Piyumantha" where Lib_ID=1;

SELECT * FROM Book;

insert into book(Book_id,ISBN,Title,catogery,Author)values
(1,'kavincrefdu13c423','1dqwdfref23','dxferfws','Adwlefuthgama');

insert into bookcopy(copy_no,Book_id,Edition)values
(1,1,'10');

insert into purchase(copy_no,Lib_ID,Purchase_date,Price)values
(1,1,'2022/10/12',1253);

drop table bookCopy;
drop table book;
drop table purchase;
drop table borrow;
drop table reserve;
drop table login;
drop table member;
drop table librarian;

SELECT * from Login;
SELECT * from borrow;
select * from borrow;

SELECT * FROM book NATURAL JOIN bookcopy;

Update book set avbl_count = 4  where book_id = 2;

delete from login where ID =3 and role = "Member";

update  borrow set borrow_Date = '2022-03-26' where  Book_id = 1 and copy_no = 1  and memberID = 2 ;



SELECT Book_id,ISBN,Title,catogery,Author,   book_count -( (SELECT  COUNT(*)  from reserve  where Status ="Reserved" and Book_id= Book_id ) +( SELECT  COUNT(*)  from borrow  where  Status ="Borrowed" and Book_id= Book_id  ) )  as "count"  FROM  book;

SELECT book_count, avbl_count FROM book where Book_id = 2;

delete from reserve ;
delete from borrow ;
delete from member ;
delete from bookcopy ;
delete from book ;
delete from login;

Update  book set avbl_count = 0  where book_id;
Update  book set book_count = 0  ;
Update book set avbl_count = avbl_count -1 where book_id =2;
Update reserve set Reserve_Date = '2022-06-06' where book_id =2 and copy_no =3 and memberID = 2;

SELECT br.Book_id, br.copy_no, res.Reserve_Date , br.Borrow_Date,  br.Due_Date,  br.returned_Date, br.Status , br.penalty_fee FROM reserve as res  NATURAL JOIN borrow as br ON res.memberID= br.memberID  where memberID = 2 and res.Status = "Picked Up" ;

Update borrow set Borrow_Date = '2022-06-04' where book_id =2 and copy_no =2 and memberID = 2;
Update borrow set Status = "Picked Up" where book_id =2 and copy_no =2 and memberID = 2;