DROP DATABASE IF EXISTS CakeShop;
CREATE DATABASE IF NOT EXISTS CakeShop;
SHOW DATABASES ;
USE CakeShop;
#-------------------

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
   CustID VARCHAR(6),
   CustName VARCHAR(30) NOT NULL DEFAULT 'Unknown',
   CustAddress TEXT NOT NULL ,
   Contact VARCHAR(20),
   CONSTRAINT PRIMARY KEY (CustID)
);
SHOW TABLES ;
DESCRIBE Customer;
#---------------------

DROP TABLE IF EXISTS Cake;
CREATE TABLE IF NOT EXISTS Cake(
   CakeID VARCHAR(6),
   CakeName VARCHAR(30),
   Weight DOUBLE,
   Price DOUBLE,
   CONSTRAINT PRIMARY KEY (CakeID)
);
SHOW TABLES ;
DESCRIBE Cake;
#---------------------

DROP TABLE IF EXISTS Worker;
CREATE TABLE IF NOT EXISTS Worker(
   WorkerID VARCHAR(6),
   WorkerName VARCHAR(30),
   WorkerAddress TEXT,
   WorkerContact VARCHAR(20),
   NIC VARCHAR(20),
   BankACNumber VARCHAR(20),
   Availability VARCHAR(5) NOT NULL DEFAULT 'Yes',
   CONSTRAINT PRIMARY KEY (WorkerID)
);
SHOW TABLES ;
DESCRIBE Worker;
#---------------------

DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
   OrderID VARCHAR(6),
   CustID VARCHAR(6),
   WorkerID VARCHAR(6) DEFAULT 'Empty',
   OrderDate DATE,
   DeliveryDate DATE,
   OrderTime VARCHAR(15),
   State VARCHAR(15),
   TotalPrice DOUBLE,
   CONSTRAINT PRIMARY KEY (OrderID),
   CONSTRAINT FOREIGN KEY (CustID) REFERENCES Customer(CustID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (WorkerID) REFERENCES Worker(WorkerID) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE `Order`;
#-----------------------

DROP TABLE IF EXISTS Ingredient;
CREATE TABLE IF NOT EXISTS Ingredient(
   IngredientID VARCHAR(6),
   IngredientName VARCHAR(20),
   Unit VARCHAR(10),
   QtyOnHand DOUBLE DEFAULT 0.00,
   UnitPrice DOUBLE DEFAULT 0.00,
   CONSTRAINT PRIMARY KEY (IngredientID)
);
SHOW TABLES ;
DESCRIBE Ingredient;
#------------------------

DROP TABLE IF EXISTS OrderDetail;
CREATE TABLE IF NOT EXISTS OrderDetail(
   CakeID VARCHAR(6),
   OrderID VARCHAR(6),
   Description TEXT,
   Weight VARCHAR(10),
   Price DOUBLE DEFAULT 0.00,
   CONSTRAINT FOREIGN KEY (CakeID) REFERENCES Cake(CakeID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (OrderID) REFERENCES `Order`(OrderID) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE OrderDetail;
#--------------------------

DROP TABLE IF EXISTS UnavailableList;
CREATE TABLE IF NOT EXISTS UnavailableList(
   IngredientID VARCHAR(6),
   IngredientName VARCHAR (10),
   QTYForBuy DOUBLE ,
   Price DOUBLE,
   CONSTRAINT PRIMARY KEY (IngredientID),
   CONSTRAINT FOREIGN KEY (IngredientID) REFERENCES Ingredient(IngredientID) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE UnavailableList;
#--------------------------

DROP TABLE IF EXISTS Recipe;
CREATE TABLE IF NOT EXISTS Recipe(
   IngredientID VARCHAR(6),
   CakeID VARCHAR(6),
   QTY DOUBLE,
   CONSTRAINT PRIMARY KEY (IngredientID, CakeID),
   CONSTRAINT FOREIGN KEY (IngredientID) REFERENCES Ingredient(IngredientID) ON DELETE CASCADE ON UPDATE CASCADE ,
   CONSTRAINT FOREIGN KEY (CakeID) REFERENCES Cake(CakeID) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE Recipe;
#--------------------------

DROP TABLE IF EXISTS Salary;
CREATE TABLE IF NOT EXISTS Salary(
   PayedDate DATE,
   WorkerID VARCHAR(6),
   WorkedDays INT,
   SalaryPerDay DOUBLE,
   Salary DOUBLE,
   State VARCHAR(10) DEFAULT 'Not Payed',
   CONSTRAINT FOREIGN KEY (WorkerID) REFERENCES Worker(WorkerID) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE Salary;
#--------------------------

DROP TABLE IF EXISTS UserTable;
CREATE TABLE IF NOT EXISTS UserTable(
   WorkerID VARCHAR(6),
   UserName VARCHAR(20),
   Password VARCHAR(50),
   CONSTRAINT PRIMARY KEY (WorkerID),
   CONSTRAINT FOREIGN KEY (WorkerID) REFERENCES Worker(WorkerID) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE UserTable;
#--------------------------

DROP TABLE IF EXISTS IngreDetail;
CREATE TABLE IF NOT EXISTS IngreDetail(
   OrderIndex INT ,
   IngredientID VARCHAR(6),
   OrderID VARCHAR(6),
   Unit VARCHAR(10),
   QTY VARCHAR(10),
   Price DOUBLE,
   CONSTRAINT FOREIGN KEY (IngredientID) REFERENCES Ingredient(IngredientID) ON DELETE CASCADE ON UPDATE CASCADE ,
   CONSTRAINT FOREIGN KEY (OrderID) REFERENCES `Order`(OrderID) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE IngreDetail;
#--------------------------
