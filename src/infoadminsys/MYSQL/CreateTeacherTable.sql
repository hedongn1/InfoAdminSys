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
CREATE TABLE teacher(
    id CHAR(20) NOT NULL,
    name CHAR(20) NOT NULL,
    title CHAR(20),
    sex enum('男','女'),
    depart CHAR(30),
    hometown CHAR(30),
    birthday DATE,
    IDnum CHAR(20),
    address CHAR(100),
    email CHAR(30),
    cell CHAR(20),
    PRIMARY KEY(id)
)CHARACTER SET=utf8;

INSERT INTO teacher (id,name,title,sex,depart,hometown)
VALUES('123','陈阳','青年研究员','男','计算机学院','福建');
update teacher set birthday = '2000-11-26' where id = '123';

/*
birthday 不能为空，不然会出错
*／
