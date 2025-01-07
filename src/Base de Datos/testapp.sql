CREATE SCHEMA IF NOT EXISTS testapp;

USE testapp;

CREATE ROLE IF NOT EXISTS user;
GRANT SELECT, INSERT ON *.* TO user;

CREATE USER IF NOT EXISTS 
    Admin IDENTIFIED BY "admin";

GRANT SELECT, INSERT, DELETE, CREATE USER, GRANT OPTION ON *.* TO Admin;

CREATE USER IF NOT EXISTS
	Anon IDENTIFIED BY "abc123.."
    default role user;

CREATE TABLE IF NOT EXISTS Test(
	testID int NOT NULL AUTO_INCREMENT,
    testName varchar(99) NOT NULL,
    testAuthor varchar(99),
    testFile MEDIUMBLOB NOT NULL,
    
    Primary Key(testID)
);