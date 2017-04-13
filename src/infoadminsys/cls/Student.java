/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infoadminsys.cls;

import java.util.*;
import infoadminsys.util.JdbcUtil;

/**
 *
 * @author lsh
 */
public class Student {
   static public String id,name,sex,depart,major,email,NO;
   final static public String type="学生";
   static String Attr="id,name,sex,depart,major,email,NO";
   
   static public void getDataFromDB() {       
       String sql="SELECT"+Attr+"FROM student WHERE student.id=?";
       List<String> params=new ArrayList<String>();      
   }
}
