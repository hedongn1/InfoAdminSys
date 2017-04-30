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
CREATE TABLE student(
    id CHAR(20) NOT NULL,
    name CHAR(20) NOT NULL,
    sex enum('男','女'),
    depart CHAR(30),
    major CHAR(30),
    hometown CHAR(30),
    birthday DATE,
    IDnum CHAR(20),
    address CHAR(100),
    email CHAR(30),
    NO CHAR(20),
    PRIMARY KEY(id)
)CHARACTER SET=utf8;

INSERT INTO student
VALUE ('15307130120','林士翰','男','计算机科学与技术学院','计算机科学与技术',
    '福建平和','1996-08-21','350628199608210015','福建','lshzy137@gmail.com','17717422843');
