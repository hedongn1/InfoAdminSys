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
DROP table selectedcourse;
CREATE TABLE selectedcourse (
    student_id CHAR(20) NOT NULL,
    course_id CHAR(20) NOT NULL,
    score DOUBLE,
    PRIMARY KEY(student_id,course_id),
    FOREIGN KEY(course_id) REFERENCES course(id),
    FOREIGN KEY(student_id) REFERENCES student(id)
)CHARACTER SET=utf8;

INSERT INTO selectedcourse(student_id,course_id)
VALUE ('15307130120','CS1001',0);