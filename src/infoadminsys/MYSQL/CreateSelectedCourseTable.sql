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
    FOREIGN KEY(course_id) REFERENCES course(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(student_id) REFERENCES student(id) ON UPDATE CASCADE ON DELETE CASCADE
)CHARACTER SET=utf8;

INSERT INTO selectedcourse
VALUES ('15307130120','cs1001',0);
