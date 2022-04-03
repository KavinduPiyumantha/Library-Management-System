

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
  PRIMARY KEY (`ID`, `role`)
--   FOREIGN KEY (`ID`) REFERENCES `Member`(`memberID`),
--   FOREIGN KEY (`ID`) REFERENCES `Librarian`(`Lib_ID`)
);


CREATE TABLE `book` (
  `Book_id` int not null unique,
  `ISBN` varchar(255),
  `Title` varchar(255),
  `catogery` varchar(255),
  `Author` varchar(255),
  `book_count` int DEFAULT 0,
  PRIMARY KEY (`Book_id`)
);


CREATE TABLE `bookcopy` (
  `copy_no` int not null unique,
  `Book_id` int not null,
  `Edition` varchar(255),
  PRIMARY KEY (`copy_no`),
  FOREIGN KEY (`Book_id`) REFERENCES `book`(`Book_id`)
);

CREATE TABLE `purchase` (
  `copy_no` int not null ,
  `Lib_ID` int not null ,
  `Purchase_date` date,
  `Price` double,
  PRIMARY KEY (`copy_no`, `Lib_ID`),
  FOREIGN KEY (`copy_no`) REFERENCES `bookcopy`(`copy_no`),
  FOREIGN KEY (`Lib_ID`) REFERENCES `librarian`(`Lib_ID`)
);

CREATE TABLE `reserve` (
  `copy_no` int not null ,
  `memberID` int not null ,
  `Reserve_Date` datetime,
  `Status` varchar(255),
  PRIMARY KEY (`copy_no`, `memberID`, `Reserve_Date`),
  FOREIGN KEY (`copy_no`) REFERENCES `bookcopy`(`copy_no`),
  FOREIGN KEY (`memberID`) REFERENCES `member`(`memberID`)
);

																																	
CREATE TABLE `borrow` (
  `copy_no` int not null ,
  `memberID` int not null,
  `Borrow_Date` datetime,
  `Due_Date` datetime,
  `returned_Date` datetime,
  `Status` varchar(255),
  `penalty_fee` double,
  PRIMARY KEY (`copy_no`, `memberID`, `Borrow_Date`),
  FOREIGN KEY (`copy_no`) REFERENCES `bookcopy`(`copy_no`),
  FOREIGN KEY (`memberID`) REFERENCES `member`(`memberID`)
);







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


SELECT * FROM book NATURAL JOIN bookcopy;