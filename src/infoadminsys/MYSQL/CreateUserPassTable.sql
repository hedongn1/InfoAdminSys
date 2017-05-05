/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  lsh
 * Created: Apr 14, 2017
 */
USE InfoAdminSys;
CREATE TABLE userpass(
    username CHAR(20) NOT NULL,
    password CHAR(30) NOT NULL,
    type enum('student','teacher','admin') NOT NULL,
    PRIMARY KEY(username)
)CHARACTER SET=utf8;

INSERT INTO userpass
VALUE ('15307130120','lsh','student');

INSERT INTO userpass
VALUE ('admin','123','admin');

INSERT INTO userpass
VALUE ('yangchen','123','teacher');

ALTER TABLE userpass ALTER password SET DEFAULT '000000';