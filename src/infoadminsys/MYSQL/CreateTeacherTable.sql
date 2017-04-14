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
    title CHAR(20) NOT NULL,
    PRIMARY KEY(id)
)CHARACTER SET=utf8;

INSERT INTO teacher
VALUE ('123','陈阳','青年研究员');