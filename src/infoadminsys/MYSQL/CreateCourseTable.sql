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
CREATE TABLE course(
    id CHAR(20) NOT NULL,
    name CHAR(30) NOT NULL,
    teacher_id CHAR(20) NOT NULL,
    status CHAR(30),
    PRIMARY KEY(id),
    FOREIGN KEY(teacher_id) REFERENCES teacher(id)
)CHARACTER SET=utf8;

INSERT INTO course
VALUE ('CS1001','MATLAB程序设计','123','未给分');
